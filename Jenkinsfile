pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'emadsingab'
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/emadsingab/k3s-microservices-ecommerce.git'
            }
        }

        stage('Build Backend Services') {
            steps {
                sh '''
                docker build -t $DOCKERHUB_USER/ecommerce-product-service-java:$IMAGE_TAG ./product-service-java
                docker build -t $DOCKERHUB_USER/ecommerce-cart-service-node:$IMAGE_TAG ./cart-service-node
                docker build -t $DOCKERHUB_USER/ecommerce-inventory-service-go:$IMAGE_TAG ./inventory-service-go
                '''
            }
        }

        stage('Build Frontend') {
            steps {
                sh '''
                docker build -t $DOCKERHUB_USER/ecommerce-frontend:$IMAGE_TAG ./frontend
                '''
            }
        }

        stage('Login to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh '''
                    echo $PASS | docker login -u $USER --password-stdin
                    '''
                }
            }
        }

        stage('Push Images') {
            steps {
                sh '''
                docker push $DOCKERHUB_USER/ecommerce-product-service-java:$IMAGE_TAG
                docker push $DOCKERHUB_USER/ecommerce-cart-service-node:$IMAGE_TAG
                docker push $DOCKERHUB_USER/ecommerce-inventory-service-go:$IMAGE_TAG
                docker push $DOCKERHUB_USER/ecommerce-frontend:$IMAGE_TAG
                '''
            }
        }

        stage('Update Helm Values') {
            steps {
                sh '''
                sed -i "s|:v[0-9]*|:$IMAGE_TAG|g" ./helm/ecommerce/values.yaml || true
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                helm upgrade --install ecommerce ./helm/ecommerce
                '''
            }
        }
    }
}
