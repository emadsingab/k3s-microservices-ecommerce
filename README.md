## 1) Local manual run inside Vagrant VM

### Terminal 1 - Product Service Java

```bash
cd ~/ecommerce-k3s-lab-v2/product-service-java
mvn spring-boot:run
```

Test:

```bash
curl http://localhost:8081/api/products
curl http://localhost:8081/actuator/prometheus
```

### Terminal 2 - Cart Service Node

Node 22 recommended.

```bash
cd ~/ecommerce-k3s-lab-v2/cart-service-node
npm install
npm start
```

Test:

```bash
curl http://localhost:8082/health
curl http://localhost:8082/metrics
```

### Terminal 3 - Inventory Service Go

Go 1.23 recommended.

```bash
cd ~/ecommerce-k3s-lab-v2/inventory-service-go
go mod tidy
go run .
```

Test:

```bash
curl http://localhost:8083/health
curl http://localhost:8083/api/inventory/1
curl http://localhost:8083/metrics
```

### Terminal 4 - Frontend

```bash
cd ~/ecommerce-k3s-lab-v2/frontend
npm install
npm run dev -- --host 0.0.0.0
```

Open from your host machine:

```text
http://192.168.100.180:5173
```

Vite proxy routes:

```text
/products  -> localhost:8081
/cart      -> localhost:8082
/inventory -> localhost:8083
```

---
