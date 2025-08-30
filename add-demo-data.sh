#!/bin/bash

echo "🏏 Adding demo data to Cricket Management System..."

# Wait for services to be ready
sleep 5

# Sample players data
echo "Adding sample players..."

curl -X POST http://localhost:8080/api/players \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Virat Kohli",
    "age": 35,
    "country": "India",
    "role": "BATSMAN",
    "runsScored": 12000,
    "matchesPlayed": 250
  }' || echo "Failed to add Virat Kohli"

curl -X POST http://localhost:8080/api/players \
  -H "Content-Type: application/json" \
  -d '{
    "name": "MS Dhoni",
    "age": 42,
    "country": "India",
    "role": "WICKET_KEEPER",
    "runsScored": 10500,
    "matchesPlayed": 350
  }' || echo "Failed to add MS Dhoni"

curl -X POST http://localhost:8080/api/players \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jasprit Bumrah",
    "age": 30,
    "country": "India",
    "role": "BOWLER",
    "wicketsTaken": 130,
    "matchesPlayed": 120
  }' || echo "Failed to add Jasprit Bumrah"

curl -X POST http://localhost:8080/api/players \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Kane Williamson",
    "age": 33,
    "country": "New Zealand",
    "role": "BATSMAN",
    "runsScored": 8500,
    "matchesPlayed": 200
  }' || echo "Failed to add Kane Williamson"

curl -X POST http://localhost:8080/api/players \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Trent Boult",
    "age": 34,
    "country": "New Zealand",
    "role": "BOWLER",
    "wicketsTaken": 180,
    "matchesPlayed": 190
  }' || echo "Failed to add Trent Boult"

curl -X POST http://localhost:8080/api/players \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ben Stokes",
    "age": 32,
    "country": "England",
    "role": "ALL_ROUNDER",
    "runsScored": 6000,
    "wicketsTaken": 75,
    "matchesPlayed": 180
  }' || echo "Failed to add Ben Stokes"

curl -X POST http://localhost:8080/api/players \
  -H "Content-Type: application/json" \
  -d '{
    "name": "AB de Villiers",
    "age": 40,
    "country": "South Africa",
    "role": "BATSMAN",
    "runsScored": 9500,
    "matchesPlayed": 220
  }' || echo "Failed to add AB de Villiers"

curl -X POST http://localhost:8080/api/players \
  -H "Content-Type: application/json" \
  -d '{
    "name": "David Warner",
    "age": 37,
    "country": "Australia",
    "role": "BATSMAN",
    "runsScored": 8800,
    "matchesPlayed": 200
  }' || echo "Failed to add David Warner"

echo ""
echo "✅ Demo data has been added!"
echo "🌐 Check the frontend at http://localhost:3000 to see the players"
echo "📊 You should also see real-time notifications for each player creation"