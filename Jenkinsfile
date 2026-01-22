pipeline {
  agent any
  options { timestamps(); ansiColor('xterm') }
  tools { jdk 'jdk17'; maven 'maven3' }
  parameters { booleanParam(name: 'BUILD_DOCKER', defaultValue: false, description: 'Build Docker images') }
  environment { MAVEN_OPTS = '-Xmx1024m' }
  stages {
    stage('Checkout') { steps { checkout scm } }
    stage('Java & Maven Versions') { steps { sh 'java -version || true'; sh 'mvn -version' } }
    stage('Build') {
      steps { sh 'mvn -B -DskipTests=false clean verify' }
      post { always { junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true } }
    }
    stage('Package') {
      steps { sh 'mvn -B -DskipTests package' }
      post { success { archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true } }
    }
    stage('Docker Build') {
      when { expression { return params.BUILD_DOCKER } }
      steps { sh 'echo Customize this stage to build/push your images' }
    }
    stage('Test') { steps { sh 'docker-compose up -d && sleep 30 && curl -f http://localhost:8081/actuator/health' } }
            stage('Deploy') { steps { sh 'docker-compose up -d' } }
  }
  post { failure { echo 'Build failed' } success { echo 'Build succeeded' } }
}
