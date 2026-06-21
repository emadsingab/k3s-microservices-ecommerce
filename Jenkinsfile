pipeline {
    agent any

    environment {
        DOCKERHUB_USER = "emadsingab"
        IMAGE_TAG = "${BUILD_NUMBER}"
        REPO = "https://github.com/emadsingab/k3s-microservices-ecommerce.git"
    }

    stages {

        stage('Checkout') {
            steps {
                git url: "${REPO}", branch: "main"
            }
        }

        stage('Build Images') {
            steps {
                sh """
                docker build -t $DOCKERHUB_USER/product-service-java:${IMAGE_TAG} ./product-service-java
                docker build -t $DOCKERHUB_USER/cart-service-node:${IMAGE_TAG} ./cart-service-node
                docker build -t $DOCKERHUB_USER/inventory-service-go:${IMAGE_TAG} ./inventory-service-go
                docker build -t $DOCKERHUB_USER/frontend:${IMAGE_TAG} ./frontend
                """
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh """
                    echo $PASS | docker login -u $USER --password-stdin
                    """
                }
            }
        }

        stage('Push Images') {
            steps {
                sh """
                docker push $DOCKERHUB_USER/product-service-java:${IMAGE_TAG}
                docker push $DOCKERHUB_USER/cart-service-node:${IMAGE_TAG}
                docker push $DOCKERHUB_USER/inventory-service-go:${IMAGE_TAG}
                docker push $DOCKERHUB_USER/frontend:${IMAGE_TAG}
                """
            }
        }

        stage('Deploy with Helmfile') {
            steps {
                sh """
                helmfile --no-diff -e default apply
                """
            }
        }
    }
}
