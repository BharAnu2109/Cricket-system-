#!/bin/bash

echo "🛑 Stopping Cricket Management System..."

# Stop Docker services
echo "Stopping Kafka and Zookeeper..."
docker-compose down

# Stop Spring Boot services
if [ -f logs/eureka.pid ]; then
    echo "Stopping Eureka Server..."
    kill $(cat logs/eureka.pid) 2>/dev/null || true
    rm logs/eureka.pid
fi

if [ -f logs/config.pid ]; then
    echo "Stopping Config Server..."
    kill $(cat logs/config.pid) 2>/dev/null || true
    rm logs/config.pid
fi

if [ -f logs/gateway.pid ]; then
    echo "Stopping API Gateway..."
    kill $(cat logs/gateway.pid) 2>/dev/null || true
    rm logs/gateway.pid
fi

if [ -f logs/player.pid ]; then
    echo "Stopping Player Service..."
    kill $(cat logs/player.pid) 2>/dev/null || true
    rm logs/player.pid
fi

if [ -f logs/match.pid ]; then
    echo "Stopping Match Service..."
    kill $(cat logs/match.pid) 2>/dev/null || true
    rm logs/match.pid
fi

if [ -f logs/team.pid ]; then
    echo "Stopping Team Service..."
    kill $(cat logs/team.pid) 2>/dev/null || true
    rm logs/team.pid
fi

if [ -f logs/notification.pid ]; then
    echo "Stopping Notification Service..."
    kill $(cat logs/notification.pid) 2>/dev/null || true
    rm logs/notification.pid
fi

if [ -f logs/frontend.pid ]; then
    echo "Stopping Frontend..."
    kill $(cat logs/frontend.pid) 2>/dev/null || true
    rm logs/frontend.pid
fi

# Kill any remaining Java processes for our services
echo "Cleaning up any remaining processes..."
pkill -f "spring-boot:run" 2>/dev/null || true
pkill -f "cricket-system" 2>/dev/null || true

echo "✅ All services stopped successfully!"
echo "🧹 Log files are preserved in the 'logs' directory"