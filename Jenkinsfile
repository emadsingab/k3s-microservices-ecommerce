pipeline {
    agent any

    environment {
        DOCKERHUB_USER = "emadsingab"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                git url: "https://github.com/emadsingab/k3s-microservices-ecommerce.git", branch: "main"
            }
        }

        stage('Build Images') {
            steps {
                sh """
                docker build -t $DOCKERHUB_USER/product-service:${IMAGE_TAG} ./product-service-java
                docker build -t $DOCKERHUB_USER/cart-service:${IMAGE_TAG} ./cart-service-node
                docker build -t $DOCKERHUB_USER/inventory-service:${IMAGE_TAG} ./inventory-service-go
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
                docker push $DOCKERHUB_USER/product-service:${IMAGE_TAG}
                docker push $DOCKERHUB_USER/cart-service:${IMAGE_TAG}
                docker push $DOCKERHUB_USER/inventory-service:${IMAGE_TAG}
                docker push $DOCKERHUB_USER/frontend:${IMAGE_TAG}
                """
            }
        }

        stage('Deploy Product Service') {
            steps {
                sh """
                helm upgrade --install product-app ./helm/product-service \
                --set image.repository=$DOCKERHUB_USER/product-service \
                --set image.tag=${IMAGE_TAG}
                """
            }
        }

        stage('Deploy Cart Service') {
            steps {
                sh """
                helm upgrade --install cart-app ./helm/cart-service \
                --set image.repository=$DOCKERHUB_USER/cart-service \
                --set image.tag=${IMAGE_TAG}
                """
            }
        }

        stage('Deploy Inventory Service') {
            steps {
                sh """
                helm upgrade --install inventory-app ./helm/inventory-service \
                --set image.repository=$DOCKERHUB_USER/inventory-service \
                --set image.tag=${IMAGE_TAG}
                """
            }
        }

        stage('Deploy Frontend') {
            steps {
                sh """
                helm upgrade --install frontend-app ./helm/frontend-service \
                --set image.repository=$DOCKERHUB_USER/frontend \
                --set image.tag=${IMAGE_TAG}
                """
            }
        }

        stage('Deploy Database') {
            steps {
                sh """
                helm upgrade --install db-core ./helm/postgres-service
                """
            }
        }
    }
}
