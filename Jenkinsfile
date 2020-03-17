pipeline {
  agent none
  environment {
    SONAR_URL = 'http://localhost:9000/sonar'
    SITE_DEPLOY_PATH = '/scrumfordevelopers/nginx_root/worblehat-site'
  }

  stages {

    stage('BUILD') {
      agent any
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
            tool: 'apache-maven-3.6.3',
            pom: 'pom.xml',
            goals: '-DskipTests clean install',
            opts: '-Xms1024m -Xmx4096m',
            resolverId: 'local-artifactory-resolver',
            deployerId: 'local-artifactory-deployer',
        )
      }
    }

    stage('UNIT TEST') {
      agent any
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
        agent any
//      when {
//        branch 'master'
//      }
        steps {
        sh './mvnw -B sonar:sonar -Pjenkins'
        }
    }

    stage('LONG RUNNING') {
        parallel {

            stage('REPORTING') {
              agent any
              when {
                branch 'master'
              }
              steps {
                sh './mvnw -B site:site site:stage'
                sh 'cp -r target/staging/. ${SITE_DEPLOY_PATH}/site'
              }
            }

            stage('ACCEPTANCE TEST') {
                agent any
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
                        archiveArtifacts artifacts: 'worblehat-acceptancetests/target/*.flv', fingerprint: true
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

        }
    }

    stage('DEPLOY DEV') {
        agent any
        when {
        branch 'master'
        }
        steps {
            sh "sudo /etc/init.d/worblehat-test stop"
            sh "./mvnw -B -f worblehat-domain/pom.xml liquibase:update -Pjenkins " +
                    "-Dpsd.dbserver.url=jdbc:mysql://localhost:3306/worblehat_test " +
                    "-Dpsd.dbserver.username=worblehat " +
                    "-Dpsd.dbserver.password=worblehat"

            sh "cp ${env.WORKSPACE}/worblehat-web/target/*-executable.jar /opt/worblehat-test/worblehat.jar"
            sh "sudo /etc/init.d/worblehat-test start"
        }
    }


    stage('PROD APPROVAL') {
      agent none
      when {
        branch 'master'
      }
      steps {
        milestone(ordinal: 50, label: "PROD_APPROVAL_REACHED")
        script {
          input message: 'Should we deploy to Prod?', ok: 'Yes, please.'
        }
      }
    }

    stage('DEPLOY PROD') {
      agent any
      when {
        branch 'master'
      }
      steps {
        lock(resource: "PROD_ENV", label: null) {
          sh "sudo /etc/init.d/worblehat-prod stop"
          sh "./mvnw -B -f worblehat-domain/pom.xml liquibase:update " +
                  "-Dpsd.dbserver.url=jdbc:mysql://localhost:3306/worblehat_prod " +
                  "-Dpsd.dbserver.username=worblehat " +
                  "-Dpsd.dbserver.password=worblehat"
          sh "cp ${env.WORKSPACE}/worblehat-web/target/*-executable.jar /opt/worblehat-prod/worblehat.jar"
          sh "sudo /etc/init.d/worblehat-prod start"
        }
      }
    }
  }
}
