package com.quiniela.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "predictions")
public class Prediction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "match_id", nullable = false)
  private Match match;

  @NotNull
  @Column(nullable = false)
  private Integer homeGoals;

  @NotNull
  @Column(nullable = false)
  private Integer awayGoals;

  @Column(nullable = false)
  private Integer pointsAwarded = 0;

  public Prediction() {
  }

  public Prediction(User user, Match match, Integer homeGoals, Integer awayGoals) {
    this.user = user;
    this.match = match;
    this.homeGoals = homeGoals;
    this.awayGoals = awayGoals;
  }

  public Long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Match getMatch() {
    return match;
  }

  public void setMatch(Match match) {
    this.match = match;
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

  public Integer getPointsAwarded() {
    return pointsAwarded;
  }

  public void setPointsAwarded(Integer pointsAwarded) {
    this.pointsAwarded = pointsAwarded;
  }
}
