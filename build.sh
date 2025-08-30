# Build script to compile all services
#!/bin/bash

echo "Building Cricket System Microservices..."

# Build all Maven projects
echo "Building Eureka Server..."
cd eureka-server && mvn clean package -DskipTests && cd ..

echo "Building Config Server..."
cd config-server && mvn clean package -DskipTests && cd ..

echo "Building API Gateway..."
cd api-gateway && mvn clean package -DskipTests && cd ..

echo "Building Player Service..."
cd player-service && mvn clean package -DskipTests && cd ..

echo "Building Match Service..."
cd match-service && mvn clean package -DskipTests && cd ..

echo "Building Team Service..."
cd team-service && mvn clean package -DskipTests && cd ..

echo "Building Notification Service..."
cd notification-service && mvn clean package -DskipTests && cd ..

echo "Installing Frontend dependencies..."
cd frontend && npm install && cd ..

echo "All services built successfully!"
echo "To run the system:"
echo "1. Start with: docker-compose up -d zookeeper kafka"
echo "2. Then run: docker-compose up --build"
echo "3. Access the application at: http://localhost:3000"