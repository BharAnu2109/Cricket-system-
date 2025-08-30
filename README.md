# Cricket Management System 🏏

A comprehensive cricket management system built with **Spring Boot 3.2**, **React.js**, **Java 17**, **Kafka**, and **Microservices** architecture.

## 🏗️ Architecture Overview

This system implements a modern microservices architecture with the following components:

### Backend Services (Spring Boot 3.2 + Java 17)
- **Eureka Server** (Port 8761) - Service Discovery
- **Config Server** (Port 8888) - Centralized Configuration
- **API Gateway** (Port 8080) - Single entry point for all requests
- **Player Service** (Port 8081) - Player management
- **Match Service** (Port 8082) - Match management  
- **Team Service** (Port 8083) - Team management
- **Notification Service** (Port 8084) - Real-time notifications with Kafka + WebSocket

### Frontend
- **React.js Application** (Port 3000) - Modern web interface

### Infrastructure
- **Apache Kafka** (Port 9092) - Event streaming platform
- **Zookeeper** (Port 2181) - Kafka coordination
- **H2 Database** - In-memory databases for each service

## 🚀 Features

- **Player Management**: Create, update, delete, and search players
- **Real-time Notifications**: Live updates using Kafka and WebSocket
- **Microservices Architecture**: Scalable and maintainable design
- **Service Discovery**: Automatic service registration and discovery
- **API Gateway**: Centralized routing and CORS handling
- **Responsive UI**: Modern React.js interface

## 🛠️ Technologies Used

### Backend
- Java 17
- Spring Boot 3.2.1
- Spring Cloud 2023.0.0
- Spring Data JPA
- Apache Kafka 3.6.1
- Netflix Eureka
- Spring Cloud Gateway
- H2 Database
- WebSocket + STOMP

### Frontend
- React.js 19.1.1
- Axios for HTTP requests
- STOMP.js for WebSocket communication
- Modern CSS with responsive design

### DevOps
- Docker & Docker Compose
- Maven for build management

## 📋 Prerequisites

- **Java 17** or higher
- **Node.js 18** or higher
- **Maven 3.6** or higher
- **Docker & Docker Compose** (for containerized deployment)

## 🚀 Quick Start

### Option 1: One-Click Startup (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Cricket-system-
   ```

2. **Build and start everything**
   ```bash
   ./start.sh
   ```

3. **Access the application**
   - Frontend: http://localhost:3000
   - API Gateway: http://localhost:8080
   - Eureka Dashboard: http://localhost:8761

4. **Stop the system**
   ```bash
   ./stop.sh
   ```

5. **Add demo data (optional)**
   ```bash
   ./add-demo-data.sh
   ```

### Option 2: Docker Compose

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Cricket-system-
   ```

2. **Build all services**
   ```bash
   ./build.sh
   ```

3. **Start infrastructure services**
   ```bash
   docker-compose up -d zookeeper kafka
   ```

4. **Start all services**
   ```bash
   docker-compose up --build
   ```

5. **Access the application**
   - Frontend: http://localhost:3000
   - API Gateway: http://localhost:8080
   - Eureka Dashboard: http://localhost:8761

### Option 3: Manual Startup

1. **Start Infrastructure**
   ```bash
   # Start Kafka and Zookeeper
   docker-compose up -d zookeeper kafka
   ```

2. **Start Services in Order**
   ```bash
   # 1. Eureka Server
   cd eureka-server && mvn spring-boot:run &
   
   # 2. Config Server
   cd config-server && mvn spring-boot:run &
   
   # 3. API Gateway
   cd api-gateway && mvn spring-boot:run &
   
   # 4. Business Services
   cd player-service && mvn spring-boot:run &
   cd match-service && mvn spring-boot:run &
   cd team-service && mvn spring-boot:run &
   cd notification-service && mvn spring-boot:run &
   ```

3. **Start Frontend**
   ```bash
   cd frontend && npm start
   ```

## 📊 Service Ports

| Service | Port | Description |
|---------|------|-------------|
| Frontend | 3000 | React.js Web Application |
| API Gateway | 8080 | Main entry point |
| Player Service | 8081 | Player management |
| Match Service | 8082 | Match management |
| Team Service | 8083 | Team management |
| Notification Service | 8084 | Real-time notifications |
| Config Server | 8888 | Configuration management |
| Kafka | 9092 | Event streaming |
| Zookeeper | 2181 | Kafka coordination |
| Eureka Server | 8761 | Service discovery |

## 🎯 API Endpoints

### Player Service (via API Gateway)
- `GET /api/players` - Get all players
- `POST /api/players` - Create a new player
- `GET /api/players/{id}` - Get player by ID
- `PUT /api/players/{id}` - Update player
- `DELETE /api/players/{id}` - Delete player
- `GET /api/players/country/{country}` - Get players by country
- `GET /api/players/search?name={name}` - Search players by name

### Real-time Features
- WebSocket endpoint: `ws://localhost:8084/ws`
- Topic: `/topic/notifications`
- Events: Player creation, updates, deletions

## 🔧 Configuration

### Environment Variables
```bash
# For Docker deployment
SPRING_PROFILES_ACTIVE=docker
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka/
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092
```

### Database Configuration
Each service uses H2 in-memory database:
- Console: `http://localhost:808{x}/h2-console`
- JDBC URL: `jdbc:h2:mem:{servicename}db`
- Username: `sa`
- Password: `password`

## 🧪 Testing

### Running Tests
```bash
# Test all services
mvn test

# Test specific service
cd player-service && mvn test
```

### Manual Testing
1. **Create a Player**
   ```bash
   curl -X POST http://localhost:8080/api/players \
   -H "Content-Type: application/json" \
   -d '{
     "name": "Virat Kohli",
     "age": 35,
     "country": "India",
     "role": "BATSMAN"
   }'
   ```

2. **Get All Players**
   ```bash
   curl http://localhost:8080/api/players
   ```

## 🐛 Troubleshooting

### Common Issues

1. **Port Conflicts**
   - Ensure ports 3000, 8080-8084, 8761, 8888, 9092, 2181 are available
   - Stop any existing services using these ports

2. **Kafka Connection Issues**
   - Ensure Kafka and Zookeeper are running before starting other services
   - Check Docker logs: `docker-compose logs kafka`

3. **Service Discovery Issues**
   - Eureka Server must be running before other services
   - Check Eureka dashboard at http://localhost:8761

4. **Frontend API Calls Failing**
   - Ensure API Gateway is running
   - Check CORS configuration
   - Verify backend services are registered in Eureka

### Logs
```bash
# View logs for specific service
docker-compose logs [service-name]

# View all logs
docker-compose logs -f
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License.

## 🙏 Acknowledgments

- Spring Boot Team for the excellent framework
- Apache Kafka for event streaming capabilities
- React.js community for the frontend framework
- Netflix OSS for microservices tools

---

**Built with ❤️ using Spring Boot 3.2, Java 17, React.js, and Kafka**