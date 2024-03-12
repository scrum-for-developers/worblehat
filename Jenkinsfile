pipeline {
  agent any
  environment {
    SONAR_URL = 'http://localhost:9000/sonar'
    SITE_DEPLOY_PATH = '/scrumfordevelopers/nginx_root/worblehat-site'
  }

  options {
      preserveStashes(buildCount: 5)
//      disableConcurrentBuilds()
  }

  stages {

    stage('BUILD') {
//      when {
//        branch 'master'
//      }
      steps {
        rtMavenResolver (
            id: 'local-artifactory-resolver',
            serverId: 'artifactory',
            releaseRepo: 'libs-release',
            snapshotRepo: 'libs-snapshot'
        )
        rtMavenDeployer (
            id: 'local-artifactory-deployer',
            serverId: 'artifactory',
            releaseRepo: 'libs-release-local',
            snapshotRepo: 'libs-snapshot-local'
        )
        rtMavenRun (
            tool: 'apache-maven-3.9.6',
            pom: 'pom.xml',
            goals: '-DskipTests clean install',
            opts: '-Xms1024m -Xmx4096m',
            resolverId: 'local-artifactory-resolver',
            deployerId: 'local-artifactory-deployer',
        )
        stash name:'executable', includes:'**worblehat-web/target/*-executable.jar'
      }
    }

    stage('UNIT TEST') {
//      when {
//        branch 'master'
//      }
      steps {
        sh './mvnw -B test -Pcoverage'
      }
      post {
        always {
          junit '**/target/surefire-reports/TEST-*.xml'
        }
      }
    }

    stage('QUALITY') {
//      when {
//        branch 'master'
//      }
        steps {
          withSonarQubeEnv(installationName: 'sonarqube') {
            sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
          }
        }
    }

//    FIXME - Long running processes could be run in parallel
//    stage('LONG RUNNING') {
//        parallel {

            stage('REPORTING') {
              when {
                branch 'master'
              }
              steps {
                sh './mvnw -B site:site site:stage'
                sh 'cp -r target/staging/. ${SITE_DEPLOY_PATH}/site'
              }
            }

            stage('ACCEPTANCE TEST') {
                //      when {
                //        branch 'master'
                //      }
                steps {
                    sh './mvnw -B -P runITs verify'
                    publishHTML(
                        [allowMissing         : false,
                        alwaysLinkToLastBuild: true,
                        keepAll              : true,
                        reportDir            : 'worblehat-acceptancetests/target/cucumber',
                        reportFiles          : 'index.html',
                        reportName           : 'Acceptance Test Report',
                        reportTitles         : 'Acceptance Test Report']
                    )
                }

                post {
                    always {
                        archiveArtifacts artifacts: 'worblehat-acceptancetests/target/*.mp4', fingerprint: true
                        archiveArtifacts artifacts: 'worblehat-acceptancetests/target/screenshots/*.png', fingerprint: true
                        cucumber buildStatus: 'FAILURE',
                            failedFeaturesNumber: 1,
                            failedScenariosNumber: 1,
                            skippedStepsNumber: 1,
                            failedStepsNumber: 1,
                            fileIncludePattern: '**/target/cucumber-report.json',
                            sortingMethod: 'ALPHABETICAL',
                            trendsLimit: 100
                    }
                }
            }

//        }
//    }

    stage('DEPLOY DEV') {
        when {
        branch 'master'
        }
        steps {
            unstash name:'executable'
            sh "sudo /etc/init.d/worblehat-test stop"
            sh "./mvnw -B -f worblehat-domain/pom.xml liquibase:update -Pjenkins " +
                    "-Dpsd.dbserver.url=jdbc:mysql://localhost:3306/worblehat_test " +
                    "-Dpsd.dbserver.username=worblehat " +
                    "-Dpsd.dbserver.password=worblehat"

            sh "cp ./worblehat-web/target/*-executable.jar /opt/worblehat-test/worblehat.jar"
            sh "sudo /etc/init.d/worblehat-test start"
        }
    }


    stage('PROD APPROVAL') {
      when {
        branch 'master'
      }
      options {
          timeout(time: 5, unit: 'MINUTES')
      }
      steps {
        milestone(ordinal: 50, label: "PROD_APPROVAL_REACHED")
        script {
          input message: 'Should we deploy to Prod?', ok: 'Yes, please.'
        }
      }
    }

    stage('DEPLOY PROD') {
      when {
        branch 'master'
      }
      steps {
        lock(resource: "PROD_ENV", label: null) {
          unstash name:'executable'
          sh "sudo /etc/init.d/worblehat-prod stop"
          sh "./mvnw -B -f worblehat-domain/pom.xml liquibase:update " +
                  "-Dpsd.dbserver.url=jdbc:mysql://localhost:3306/worblehat_prod " +
                  "-Dpsd.dbserver.username=worblehat " +
                  "-Dpsd.dbserver.password=worblehat"
          sh "cp ./worblehat-web/target/*-executable.jar /opt/worblehat-prod/worblehat.jar"
          sh "sudo /etc/init.d/worblehat-prod start"
        }
      }
    }
  }
  post {
    cleanup {
      echo 'Cleaning up'
      deleteDir()
    }
  }
}
