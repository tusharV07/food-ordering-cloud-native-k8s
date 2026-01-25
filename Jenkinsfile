pipeline {
  agent any

  environment {
    DOCKERHUB_CREDENTIALS = credentials('Docker_hub_creds')
    VERSION = "${env.BUILD_ID}"
  }

  tools {
    maven "Maven"
    nodejs "Nodejs"
  }

  parameters {
    choice(name: 'SERVICE', choices: ['restaurantlisting', 'foodcatalogue', 'order', 'frontend'], description: 'Select service to build')
  }

  stages {

    stage('Maven Build') {
      when {
        expression { params.SERVICE != 'frontend' }
      }
      steps {
        script {
          dir("services/${params.SERVICE == 'order' ? 'order-service' : params.SERVICE}") {
            sh 'mvn clean package -DskipTests'
          }
        }
      }
    }

    stage('Run Tests') {
      when {
        expression { params.SERVICE != 'frontend' }
      }
      steps {
        script {
          dir("services/${params.SERVICE == 'order' ? 'order-service' : params.SERVICE}") {
            sh 'mvn test'
          }
        }
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression { params.SERVICE != 'frontend' }
      }
      steps {
        script {
          dir("services/${params.SERVICE == 'order' ? 'order-service' : params.SERVICE}") {
            sh 'mvn clean install org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.host.url=http://43.205.144.178:9000/ -Dsonar.login=squ_fd4ff2fb9c5585e3ba799d6e355b59608e6bee43'
          }
        }
      }
    }

    stage('Check code coverage') {
      when {
        expression { params.SERVICE != 'frontend' }
      }
      steps {
        script {
          def token = "squ_fd4ff2fb9c5585e3ba799d6e355b59608e6bee43"
          def sonarQubeUrl = "http://43.205.144.178:9000/api"
          def componentKey = ""
          
          if (params.SERVICE == 'restaurantlisting') {
            componentKey = "com.tsv:restaurantlisting"
          } else if (params.SERVICE == 'foodcatalogue') {
            componentKey = "com.tsv:foodcatalogue"
          } else if (params.SERVICE == 'order') {
            componentKey = "com.tsv:order"
          }
          
          def coverageThreshold = 80.0

          def response = sh(
            script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
            returnStdout: true
          ).trim()

          def coverage = sh(
            script: "echo '${response}' | jq -r '.component.measures[0].value'",
            returnStdout: true
          ).trim().toDouble()

          echo "Coverage: ${coverage}"

          if (coverage < coverageThreshold) {
            error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
          }
        }
      }
    }

    stage('Install Dependencies') {
      when {
        expression { params.SERVICE == 'frontend' }
      }
      steps {
        dir('frontend/food-delivery-mvp') {
          sh 'npm ci'
        }
      }
    }

    stage('Build Project') {
      when {
        expression { params.SERVICE == 'frontend' }
      }
      steps {
        dir('frontend/food-delivery-mvp') {
          sh 'npm run build'
        }
      }
    }

    stage('Docker Build and Push') {
      steps {
        script {
          sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
          
          if (params.SERVICE == 'frontend') {
            dir('frontend/food-delivery-mvp') {
              sh "docker build -t tusharvyavahare/food-delivery-frontend:${VERSION} ."
              sh "docker push tusharvyavahare/food-delivery-frontend:${VERSION}"
            }
          } else {
            dir("services/${params.SERVICE == 'order' ? 'order-service' : params.SERVICE}") {
              def imageName = params.SERVICE == 'order' ? 'order-service' : "${params.SERVICE}-service"
              sh "docker build -t tusharvyavahare/${imageName}:${VERSION} ."
              sh "docker push tusharvyavahare/${imageName}:${VERSION}"
            }
          }
        }
      }
    }

    stage('Cleanup Workspace') {
      steps {
        deleteDir()
      }
    }

    stage('Update Image Tag in GitOps') {
      steps {
        checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'git-ssh', url: 'git@github.com:tusharV07/food-ordering-cloud-native-k8s.git']])
        script {
          def manifestFile = ""
          def imageName = ""
          
          if (params.SERVICE == 'restaurantlisting') {
            manifestFile = "k8s/aws/restaurant-manifest.yml"
            imageName = "restaurantlisting-service"
          } else if (params.SERVICE == 'foodcatalogue') {
            manifestFile = "k8s/aws/food-catalogue-manifest.yml"
            imageName = "foodcatalogue-service"
          } else if (params.SERVICE == 'order') {
            manifestFile = "k8s/aws/order-manifest.yml"
            imageName = "order-service"
          } else if (params.SERVICE == 'frontend') {
            manifestFile = "k8s/aws/angular-manifest.yml"
            imageName = "food-delivery-frontend"
          }
          
          sh """
            sed -i "s/image:.*/image: tusharvyavahare\\/${imageName}:${VERSION}/" ${manifestFile}
          """
          sh 'git checkout main'
          sh 'git add .'
          sh 'git commit -m "Update image tag"'
          sshagent(['git-ssh']) {
            sh('git push')
          }
        }
      }
    }

  }

}
