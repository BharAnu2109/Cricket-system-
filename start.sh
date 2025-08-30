#!/bin/bash

# Cricket System Startup Script
echo "🏏 Starting Cricket Management System..."

# Function to check if port is available
check_port() {
    lsof -i:$1 > /dev/null 2>&1
    return $?
}

# Function to wait for service to be ready
wait_for_service() {
    local url=$1
    local service_name=$2
    local max_attempts=30
    local attempt=1
    
    echo "Waiting for $service_name to be ready..."
    
    while [ $attempt -le $max_attempts ]; do
        if curl -f -s $url > /dev/null 2>&1; then
            echo "✅ $service_name is ready!"
            return 0
        fi
        echo "⏳ Attempt $attempt/$max_attempts - waiting for $service_name..."
        sleep 5
        attempt=$((attempt + 1))
    done
    
    echo "❌ $service_name failed to start after $max_attempts attempts"
    return 1
}

# Check if required ports are available
echo "🔍 Checking required ports..."
ports=(8761 8888 8080 8081 8082 8083 8084 9092 2181)
for port in "${ports[@]}"; do
    if check_port $port; then
        echo "⚠️  Port $port is already in use. Please stop the service using this port."
        exit 1
    fi
done

echo "✅ All required ports are available"

# Start Kafka and Zookeeper
echo "🚀 Starting Kafka infrastructure..."
docker-compose up -d zookeeper kafka

# Wait for Kafka to be ready
sleep 10

# Start Spring Boot services in the correct order
echo "🚀 Starting Eureka Server..."
cd eureka-server
mvn spring-boot:run > ../logs/eureka-server.log 2>&1 &
EUREKA_PID=$!
cd ..

# Wait for Eureka to be ready
wait_for_service "http://localhost:8761" "Eureka Server"

echo "🚀 Starting Config Server..."
cd config-server
mvn spring-boot:run > ../logs/config-server.log 2>&1 &
CONFIG_PID=$!
cd ..

# Wait for Config Server to be ready
wait_for_service "http://localhost:8888/actuator/health" "Config Server"

echo "🚀 Starting API Gateway..."
cd api-gateway
mvn spring-boot:run > ../logs/api-gateway.log 2>&1 &
GATEWAY_PID=$!
cd ..

echo "🚀 Starting business services..."

cd player-service
mvn spring-boot:run > ../logs/player-service.log 2>&1 &
PLAYER_PID=$!
cd ..

cd match-service
mvn spring-boot:run > ../logs/match-service.log 2>&1 &
MATCH_PID=$!
cd ..

cd team-service
mvn spring-boot:run > ../logs/team-service.log 2>&1 &
TEAM_PID=$!
cd ..

cd notification-service
mvn spring-boot:run > ../logs/notification-service.log 2>&1 &
NOTIFICATION_PID=$!
cd ..

# Wait for services to be ready
sleep 15
wait_for_service "http://localhost:8080/actuator/health" "API Gateway"

echo "🚀 Starting React Frontend..."
cd frontend
npm start > ../logs/frontend.log 2>&1 &
FRONTEND_PID=$!
cd ..

# Create logs directory
mkdir -p logs

# Store PIDs for cleanup
echo $EUREKA_PID > logs/eureka.pid
echo $CONFIG_PID > logs/config.pid
echo $GATEWAY_PID > logs/gateway.pid
echo $PLAYER_PID > logs/player.pid
echo $MATCH_PID > logs/match.pid
echo $TEAM_PID > logs/team.pid
echo $NOTIFICATION_PID > logs/notification.pid
echo $FRONTEND_PID > logs/frontend.pid

echo ""
echo "🎉 Cricket Management System is starting up!"
echo ""
echo "📊 Service URLs:"
echo "   • Frontend:           http://localhost:3000"
echo "   • API Gateway:        http://localhost:8080"
echo "   • Eureka Dashboard:   http://localhost:8761"
echo "   • Player Service:     http://localhost:8081"
echo "   • Match Service:      http://localhost:8082"
echo "   • Team Service:       http://localhost:8083"
echo "   • Notification Service: http://localhost:8084"
echo ""
echo "📂 Logs are available in the 'logs' directory"
echo "🛑 To stop all services, run: ./stop.sh"
echo ""
echo "⏳ Please wait a few moments for all services to fully start..."
echo "🌐 The application will be available at: http://localhost:3000"