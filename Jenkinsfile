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

        stage('Build Backend Images') {
            steps {
                sh """
                docker build -t $DOCKERHUB_USER/ecommerce-product-service-java:${IMAGE_TAG} ./product-service-java
                docker build -t $DOCKERHUB_USER/ecommerce-cart-service-node:${IMAGE_TAG} ./cart-service-node
                docker build -t $DOCKERHUB_USER/ecommerce-inventory-service-go:${IMAGE_TAG} ./inventory-service-go
                """
            }
        }

        stage('Build Frontend Image') {
            steps {
                sh """
                docker build -t $DOCKERHUB_USER/ecommerce-frontend:${IMAGE_TAG} ./frontend
                """
            }
        }

        stage('Docker Login') {
            steps {
                sh """
                echo "PLEASE LOGIN MANUALLY ON JENKINS NODE FIRST"
                docker info
                """
            }
        }

        stage('Push Images') {
            steps {
                sh """
                docker push $DOCKERHUB_USER/ecommerce-product-service-java:${IMAGE_TAG}
                docker push $DOCKERHUB_USER/ecommerce-cart-service-node:${IMAGE_TAG}
                docker push $DOCKERHUB_USER/ecommerce-inventory-service-go:${IMAGE_TAG}
                docker push $DOCKERHUB_USER/ecommerce-frontend:${IMAGE_TAG}
                """
            }
        }

        stage('Deploy with Helm') {
            steps {
                sh """
                helm upgrade --install ecommerce ./helm/ecommerce \
                --set productService.image=$DOCKERHUB_USER/ecommerce-product-service-java:${IMAGE_TAG} \
                --set cartService.image=$DOCKERHUB_USER/ecommerce-cart-service-node:${IMAGE_TAG} \
                --set inventoryService.image=$DOCKERHUB_USER/ecommerce-inventory-service-go:${IMAGE_TAG} \
                --set frontend.image=$DOCKERHUB_USER/ecommerce-frontend:${IMAGE_TAG}
                """
            }
        }
    }
}
