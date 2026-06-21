pipeline {
    agent any

    environment {
        DOCKER_USER = "emadsingab"
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
                docker build -t $DOCKER_USER/ecommerce-product-service-java:$IMAGE_TAG ./product-service-java
                docker build -t $DOCKER_USER/ecommerce-cart-service-node:$IMAGE_TAG ./cart-service-node
                docker build -t $DOCKER_USER/ecommerce-inventory-service-go:$IMAGE_TAG ./inventory-service-go
                """
            }
        }

        stage('Build Frontend Image') {
            steps {
                sh """
                docker build -t $DOCKER_USER/ecommerce-frontend:$IMAGE_TAG ./frontend
                """
            }
        }

        stage('Push Images') {
            steps {
                sh """
                docker push $DOCKER_USER/ecommerce-product-service-java:$IMAGE_TAG
                docker push $DOCKER_USER/ecommerce-cart-service-node:$IMAGE_TAG
                docker push $DOCKER_USER/ecommerce-inventory-service-go:$IMAGE_TAG
                docker push $DOCKER_USER/ecommerce-frontend:$IMAGE_TAG
                """
            }
        }

        stage('Deploy with Helm') {
            steps {
                sh """
                helm upgrade --install ecommerce ./helm/ecommerce \
                --set productService.image=$DOCKER_USER/ecommerce-product-service-java:$IMAGE_TAG \
                --set cartService.image=$DOCKER_USER/ecommerce-cart-service-node:$IMAGE_TAG \
                --set inventoryService.image=$DOCKER_USER/ecommerce-inventory-service-go:$IMAGE_TAG \
                --set frontend.image=$DOCKER_USER/ecommerce-frontend:$IMAGE_TAG
                """
            }
        }
    }
}
