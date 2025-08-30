import React, { useState, useEffect } from 'react';
import './styles/App.css';
import PlayerList from './components/PlayerList';
import NotificationService from './services/NotificationService';

function App() {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const notificationService = new NotificationService();
    notificationService.connect((notification) => {
      setNotifications(prev => [notification, ...prev.slice(0, 9)]); // Keep last 10 notifications
    });

    return () => {
      notificationService.disconnect();
    };
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>🏏 Cricket Management System</h1>
        <p>Microservices Architecture with Spring Boot & React</p>
      </header>
      
      <div className="notification-bar">
        <h3>Live Notifications</h3>
        <div className="notifications">
          {notifications.length === 0 ? (
            <p>No notifications yet...</p>
          ) : (
            notifications.map((notification, index) => (
              <div key={index} className="notification">
                <span className="notification-type">{notification.type}</span>
                <span className="notification-message">{notification.message}</span>
                <span className="notification-time">
                  {new Date(notification.timestamp).toLocaleTimeString()}
                </span>
              </div>
            ))
          )}
        </div>
      </div>

      <main className="main-content">
        <PlayerList />
      </main>

      <footer className="App-footer">
        <p>Built with Spring Boot 3.2 (Java 21) + React.js + Kafka + Microservices</p>
      </footer>
    </div>
  );
}

export default App;