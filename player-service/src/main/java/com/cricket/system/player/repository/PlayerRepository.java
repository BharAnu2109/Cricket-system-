package com.cricket.system.player.repository;

import com.cricket.system.player.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    
    List<Player> findByCountry(String country);
    
    @Query("SELECT p FROM Player p WHERE p.name LIKE %:name%")
    List<Player> findByNameContaining(@Param("name") String name);
    
    List<Player> findByRole(String role);
    
    @Query("SELECT p FROM Player p ORDER BY p.runsScored DESC")
    List<Player> findTopBatsmen();
    
    @Query("SELECT p FROM Player p ORDER BY p.wicketsTaken DESC")
    List<Player> findTopBowlers();
}