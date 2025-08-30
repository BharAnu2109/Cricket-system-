package com.cricket.system.player.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "players")
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Player name is required")
    @Column(nullable = false)
    private String name;
    
    @Min(value = 16, message = "Player must be at least 16 years old")
    private Integer age;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    private String country;
    
    @Enumerated(EnumType.STRING)
    private PlayerRole role;
    
    @Enumerated(EnumType.STRING)
    private BattingStyle battingStyle;
    
    @Enumerated(EnumType.STRING)
    private BowlingStyle bowlingStyle;
    
    private Integer matchesPlayed = 0;
    private Integer runsScored = 0;
    private Integer wicketsTaken = 0;
    
    // Constructors
    public Player() {}
    
    public Player(String name, Integer age, String country, PlayerRole role) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.role = role;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public PlayerRole getRole() { return role; }
    public void setRole(PlayerRole role) { this.role = role; }
    
    public BattingStyle getBattingStyle() { return battingStyle; }
    public void setBattingStyle(BattingStyle battingStyle) { this.battingStyle = battingStyle; }
    
    public BowlingStyle getBowlingStyle() { return bowlingStyle; }
    public void setBowlingStyle(BowlingStyle bowlingStyle) { this.bowlingStyle = bowlingStyle; }
    
    public Integer getMatchesPlayed() { return matchesPlayed; }
    public void setMatchesPlayed(Integer matchesPlayed) { this.matchesPlayed = matchesPlayed; }
    
    public Integer getRunsScored() { return runsScored; }
    public void setRunsScored(Integer runsScored) { this.runsScored = runsScored; }
    
    public Integer getWicketsTaken() { return wicketsTaken; }
    public void setWicketsTaken(Integer wicketsTaken) { this.wicketsTaken = wicketsTaken; }
}

enum PlayerRole {
    BATSMAN, BOWLER, ALL_ROUNDER, WICKET_KEEPER
}

enum BattingStyle {
    RIGHT_HANDED, LEFT_HANDED
}

enum BowlingStyle {
    RIGHT_ARM_FAST, LEFT_ARM_FAST, RIGHT_ARM_MEDIUM, LEFT_ARM_MEDIUM,
    RIGHT_ARM_SPIN, LEFT_ARM_SPIN, WICKET_KEEPER
}