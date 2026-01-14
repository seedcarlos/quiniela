package com.quiniela.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "home_team_id", nullable = false)
  private Team homeTeam;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "away_team_id", nullable = false)
  private Team awayTeam;

  @NotBlank
  @Column(nullable = false)
  private String phase;

  @Column(nullable = false)
  private LocalDateTime kickoffAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MatchStatus status = MatchStatus.SCHEDULED;

  private Integer homeGoals;
  private Integer awayGoals;

  public Match() {
  }

  public Match(Team homeTeam, Team awayTeam, String phase, LocalDateTime kickoffAt) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.phase = phase;
    this.kickoffAt = kickoffAt;
  }

  public Long getId() {
    return id;
  }

  public Team getHomeTeam() {
    return homeTeam;
  }

  public void setHomeTeam(Team homeTeam) {
    this.homeTeam = homeTeam;
  }

  public Team getAwayTeam() {
    return awayTeam;
  }

  public void setAwayTeam(Team awayTeam) {
    this.awayTeam = awayTeam;
  }

  public String getPhase() {
    return phase;
  }

  public void setPhase(String phase) {
    this.phase = phase;
  }

  public LocalDateTime getKickoffAt() {
    return kickoffAt;
  }

  public void setKickoffAt(LocalDateTime kickoffAt) {
    this.kickoffAt = kickoffAt;
  }

  public MatchStatus getStatus() {
    return status;
  }

  public void setStatus(MatchStatus status) {
    this.status = status;
  }

  public Integer getHomeGoals() {
    return homeGoals;
  }

  public void setHomeGoals(Integer homeGoals) {
    this.homeGoals = homeGoals;
  }

  public Integer getAwayGoals() {
    return awayGoals;
  }

  public void setAwayGoals(Integer awayGoals) {
    this.awayGoals = awayGoals;
  }
}
