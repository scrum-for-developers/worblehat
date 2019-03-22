pipeline {
  agent none
  triggers {
    pollSCM '* 7-19 * * 1-5'
  }
  environment {
    SONAR_URL = 'http://localhost:9000/sonar'
    SITE_DEPLOY_PATH = '/scrumfordevelopers/nginx_root/worblehat-site'
  }

  stages {
    stage('BUILD') {
      agent any
      steps {
        sh 'mvn -B clean install -DskipTests'
        rtUpload (
          serverId: "1213362221@1425654692567",
          spec:
            """{
              "files": [
                {
                  "pattern": "worblehat-web/**/*.jar",
                  "target": "example-repo-local/"
                }
              ]
            }"""
          )
      }
    }

    stage('UNIT TEST') {
      agent any
      steps {
        sh 'mvn -B verify -Pcoverage'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('QUALITY') {
      agent any
      when {
        branch 'master'
      }
      steps {
        sh 'mvn -B sonar:sonar -Pjenkins'
      }
    }

    stage('REPORTING') {
      agent any
      when {
        branch 'master'
      }
      steps {
        sh 'mvn -B site:site site:stage'
        sh 'cp -r target/staging/. ${SITE_DEPLOY_PATH}/site'
      }
    }

    stage('DEPLOY DEV') {
      agent any
      when {
        branch 'master'
      }
      steps {
        lock(resource: "DEV_ENV", label: null) {
          sh "sudo /etc/init.d/worblehat-test stop"
          sh "mvn -B -f worblehat-domain/pom.xml liquibase:update -Pjenkins " +
                  "-Dpsd.dbserver.url=jdbc:mysql://localhost:3306/worblehat_test " +
                  "-Dpsd.dbserver.username=worblehat " +
                  "-Dpsd.dbserver.password=worblehat"

          sh "cp ${env.WORKSPACE}/worblehat-web/target/*.jar /opt/worblehat-test/worblehat.jar"
          sh "sudo /etc/init.d/worblehat-test start"
        }
      }
    }

    stage('ACCEPTANCE TEST') {
      agent any
      when {
        branch 'master'
      }
      steps {
        lock(resource: "DEV_ENV", label: null) {
          sh 'mvn -B verify -Pjenkins -Pheadless -Pinclude-acceptancetests -Dapplication.url=http://localhost/worblehat-test'
          publishHTML(
                  [allowMissing         : false,
                   alwaysLinkToLastBuild: false,
                   keepAll              : false,
                   reportDir            : 'worblehat-acceptancetests/target/jbehave/view',
                   reportFiles          : 'reports.html',
                   reportName           : 'Worblehat Acceptance Test Report',
                   reportTitles         : 'Worblehat Acceptance Test Report']
          )
        }
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
          sh "mvn -B -f worblehat-domain/pom.xml liquibase:update " +
                  "-Dpsd.dbserver.url=jdbc:mysql://localhost:3306/worblehat_prod " +
                  "-Dpsd.dbserver.username=worblehat " +
                  "-Dpsd.dbserver.password=worblehat"
          sh "cp ${env.WORKSPACE}/worblehat-web/target/*.jar /opt/worblehat-prod/worblehat.jar"
          sh "sudo /etc/init.d/worblehat-prod start"
        }
      }
    }
  }
}
