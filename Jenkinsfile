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

        stage('Deploy with Helm') {
    steps {
        sh """
        
        # Ensure namespace exists
        kubectl create namespace microservices-test || true

        # 1. Deploy Product Service
        helm upgrade --install product-app ./charts/product-service \
          --namespace microservices-test \
          --create-namespace \
          --set image.repository=$DOCKERHUB_USER/ecommerce-product-service-java \
          --set image.tag=${IMAGE_TAG}

        # 2. Deploy Cart Service
        helm upgrade --install cart-app ./charts/cart-service \
          --namespace microservices-test \
          --create-namespace \
          --set image.repository=$DOCKERHUB_USER/ecommerce-cart-service-node \
          --set image.tag=${IMAGE_TAG}

        # 3. Deploy Inventory Service
        helm upgrade --install inventory-app ./charts/inventory-service \
          --namespace microservices-test \
          --create-namespace \
          --set image.repository=$DOCKERHUB_USER/ecommerce-inventory-service-go \
          --set image.tag=${IMAGE_TAG}

        # 4. Deploy Frontend
        helm upgrade --install frontend-app ./charts/frontend-service \
          --namespace microservices-test \
          --create-namespace \
          --set image.repository=$DOCKERHUB_USER/ecommerce-frontend \
          --set image.tag=${IMAGE_TAG} \
          --set service.type=NodePort
        """
            }
        }
    }
}
