import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

class NotificationService {
  constructor() {
    this.client = null;
    this.connected = false;
  }

  connect(onMessageReceived) {
    const socket = new SockJS('http://localhost:8084/ws');
    this.client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log('Connected to notification service');
        this.connected = true;
        this.client.subscribe('/topic/notifications', (message) => {
          const notification = JSON.parse(message.body);
          onMessageReceived(notification);
        });
      },
      onDisconnect: () => {
        console.log('Disconnected from notification service');
        this.connected = false;
      },
      onStompError: (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      },
    });

    this.client.activate();
  }

  disconnect() {
    if (this.client) {
      this.client.deactivate();
    }
  }

  isConnected() {
    return this.connected;
  }
}

export default NotificationService;