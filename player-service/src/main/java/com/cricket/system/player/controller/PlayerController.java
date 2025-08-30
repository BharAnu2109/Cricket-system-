package com.cricket.system.player.controller;

import com.cricket.system.player.model.Player;
import com.cricket.system.player.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(player -> ResponseEntity.ok(player))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player) {
        Player createdPlayer = playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @Valid @RequestBody Player playerDetails) {
        try {
            Player updatedPlayer = playerService.updatePlayer(id, playerDetails);
            return ResponseEntity.ok(updatedPlayer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        try {
            playerService.deletePlayer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Player>> getPlayersByCountry(@PathVariable String country) {
        List<Player> players = playerService.getPlayersByCountry(country);
        return ResponseEntity.ok(players);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Player>> searchPlayers(@RequestParam String name) {
        List<Player> players = playerService.searchPlayersByName(name);
        return ResponseEntity.ok(players);
    }

    @GetMapping("/top-batsmen")
    public ResponseEntity<List<Player>> getTopBatsmen() {
        List<Player> players = playerService.getTopBatsmen();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/top-bowlers")
    public ResponseEntity<List<Player>> getTopBowlers() {
        List<Player> players = playerService.getTopBowlers();
        return ResponseEntity.ok(players);
    }
}