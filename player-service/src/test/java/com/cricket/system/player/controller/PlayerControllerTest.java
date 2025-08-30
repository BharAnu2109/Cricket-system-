package com.cricket.system.player.controller;

import com.cricket.system.player.model.Player;
import com.cricket.system.player.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPlayers_ShouldReturnPlayerList() throws Exception {
        Player player1 = new Player("Virat Kohli", 35, "India", null);
        Player player2 = new Player("Kane Williamson", 33, "New Zealand", null);
        
        when(playerService.getAllPlayers()).thenReturn(Arrays.asList(player1, player2));

        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Virat Kohli"))
                .andExpect(jsonPath("$[1].name").value("Kane Williamson"));
    }

    @Test
    void getPlayerById_WhenPlayerExists_ShouldReturnPlayer() throws Exception {
        Player player = new Player("Virat Kohli", 35, "India", null);
        player.setId(1L);
        
        when(playerService.getPlayerById(1L)).thenReturn(Optional.of(player));

        mockMvc.perform(get("/api/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Virat Kohli"));
    }

    @Test
    void getPlayerById_WhenPlayerNotExists_ShouldReturnNotFound() throws Exception {
        when(playerService.getPlayerById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/players/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPlayer_WithValidData_ShouldReturnCreatedPlayer() throws Exception {
        Player inputPlayer = new Player("AB de Villiers", 40, "South Africa", null);
        Player savedPlayer = new Player("AB de Villiers", 40, "South Africa", null);
        savedPlayer.setId(1L);
        
        when(playerService.createPlayer(any(Player.class))).thenReturn(savedPlayer);

        mockMvc.perform(post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputPlayer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("AB de Villiers"));
    }
}