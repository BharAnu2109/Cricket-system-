package com.cricket.system.player.service;

import com.cricket.system.player.model.Player;
import com.cricket.system.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    private static final String PLAYER_TOPIC = "player-events";

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Player createPlayer(Player player) {
        Player savedPlayer = playerRepository.save(player);
        // Send event to Kafka
        kafkaTemplate.send(PLAYER_TOPIC, "Player created: " + savedPlayer.getName());
        return savedPlayer;
    }

    public Player updatePlayer(Long id, Player playerDetails) {
        return playerRepository.findById(id)
                .map(player -> {
                    player.setName(playerDetails.getName());
                    player.setAge(playerDetails.getAge());
                    player.setCountry(playerDetails.getCountry());
                    player.setRole(playerDetails.getRole());
                    player.setBattingStyle(playerDetails.getBattingStyle());
                    player.setBowlingStyle(playerDetails.getBowlingStyle());
                    Player updatedPlayer = playerRepository.save(player);
                    kafkaTemplate.send(PLAYER_TOPIC, "Player updated: " + updatedPlayer.getName());
                    return updatedPlayer;
                })
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
    }

    public void deletePlayer(Long id) {
        playerRepository.findById(id)
                .ifPresentOrElse(
                        player -> {
                            playerRepository.delete(player);
                            kafkaTemplate.send(PLAYER_TOPIC, "Player deleted: " + player.getName());
                        },
                        () -> {
                            throw new RuntimeException("Player not found with id: " + id);
                        }
                );
    }

    public List<Player> getPlayersByCountry(String country) {
        return playerRepository.findByCountry(country);
    }

    public List<Player> searchPlayersByName(String name) {
        return playerRepository.findByNameContaining(name);
    }

    public List<Player> getTopBatsmen() {
        return playerRepository.findTopBatsmen();
    }

    public List<Player> getTopBowlers() {
        return playerRepository.findTopBowlers();
    }
}