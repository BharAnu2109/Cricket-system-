import React, { useState, useEffect } from 'react';
import PlayerService from '../services/PlayerService';

const PlayerList = () => {
  const [players, setPlayers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [newPlayer, setNewPlayer] = useState({
    name: '',
    age: '',
    country: '',
    role: 'BATSMAN'
  });

  useEffect(() => {
    fetchPlayers();
  }, []);

  const fetchPlayers = async () => {
    try {
      setLoading(true);
      const data = await PlayerService.getAllPlayers();
      setPlayers(data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch players. Make sure the services are running.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await PlayerService.createPlayer({
        ...newPlayer,
        age: parseInt(newPlayer.age)
      });
      setNewPlayer({ name: '', age: '', country: '', role: 'BATSMAN' });
      setShowForm(false);
      fetchPlayers();
    } catch (err) {
      setError('Failed to create player');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this player?')) {
      try {
        await PlayerService.deletePlayer(id);
        fetchPlayers();
      } catch (err) {
        setError('Failed to delete player');
      }
    }
  };

  if (loading) return <div className="loading">Loading players...</div>;

  return (
    <div className="player-list">
      <div className="player-list-header">
        <h2>Player Management</h2>
        <button 
          className="btn-primary"
          onClick={() => setShowForm(!showForm)}
        >
          {showForm ? 'Cancel' : 'Add Player'}
        </button>
      </div>

      {error && <div className="error-message">{error}</div>}

      {showForm && (
        <form onSubmit={handleSubmit} className="player-form">
          <div className="form-group">
            <input
              type="text"
              placeholder="Player Name"
              value={newPlayer.name}
              onChange={(e) => setNewPlayer({...newPlayer, name: e.target.value})}
              required
            />
          </div>
          <div className="form-group">
            <input
              type="number"
              placeholder="Age"
              min="16"
              value={newPlayer.age}
              onChange={(e) => setNewPlayer({...newPlayer, age: e.target.value})}
              required
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              placeholder="Country"
              value={newPlayer.country}
              onChange={(e) => setNewPlayer({...newPlayer, country: e.target.value})}
              required
            />
          </div>
          <div className="form-group">
            <select
              value={newPlayer.role}
              onChange={(e) => setNewPlayer({...newPlayer, role: e.target.value})}
            >
              <option value="BATSMAN">Batsman</option>
              <option value="BOWLER">Bowler</option>
              <option value="ALL_ROUNDER">All Rounder</option>
              <option value="WICKET_KEEPER">Wicket Keeper</option>
            </select>
          </div>
          <button type="submit" className="btn-primary">Add Player</button>
        </form>
      )}

      <div className="players-grid">
        {players.length === 0 ? (
          <p>No players found. Add some players to get started!</p>
        ) : (
          players.map(player => (
            <div key={player.id} className="player-card">
              <h3>{player.name}</h3>
              <p><strong>Age:</strong> {player.age}</p>
              <p><strong>Country:</strong> {player.country}</p>
              <p><strong>Role:</strong> {player.role}</p>
              <p><strong>Matches:</strong> {player.matchesPlayed || 0}</p>
              <p><strong>Runs:</strong> {player.runsScored || 0}</p>
              <p><strong>Wickets:</strong> {player.wicketsTaken || 0}</p>
              <button 
                className="btn-danger"
                onClick={() => handleDelete(player.id)}
              >
                Delete
              </button>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default PlayerList;