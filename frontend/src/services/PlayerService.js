import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // API Gateway URL

class PlayerService {
  static async getAllPlayers() {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/players`);
      return response.data;
    } catch (error) {
      console.error('Error fetching players:', error);
      throw error;
    }
  }

  static async createPlayer(player) {
    try {
      const response = await axios.post(`${API_BASE_URL}/api/players`, player);
      return response.data;
    } catch (error) {
      console.error('Error creating player:', error);
      throw error;
    }
  }

  static async updatePlayer(id, player) {
    try {
      const response = await axios.put(`${API_BASE_URL}/api/players/${id}`, player);
      return response.data;
    } catch (error) {
      console.error('Error updating player:', error);
      throw error;
    }
  }

  static async deletePlayer(id) {
    try {
      await axios.delete(`${API_BASE_URL}/api/players/${id}`);
    } catch (error) {
      console.error('Error deleting player:', error);
      throw error;
    }
  }
}

export default PlayerService;