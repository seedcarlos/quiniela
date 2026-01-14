package com.quiniela.domain;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @Email
  @NotBlank
  @Column(nullable = false, unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "championship_pick_id")
  private Team championshipPick;

  @Column(nullable = false)
  private Integer bonusPoints = 0;

  @Column(nullable = false)
  private Integer totalPoints = 0;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Prediction> predictions = new ArrayList<>();

  public User() {
  }

  public User(String name, String email, Role role) {
    this.name = name;
    this.email = email;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Team getChampionshipPick() {
    return championshipPick;
  }

  public void setChampionshipPick(Team championshipPick) {
    this.championshipPick = championshipPick;
  }

  public Integer getBonusPoints() {
    return bonusPoints;
  }

  public void setBonusPoints(Integer bonusPoints) {
    this.bonusPoints = bonusPoints;
  }

  public Integer getTotalPoints() {
    return totalPoints;
  }

  public void setTotalPoints(Integer totalPoints) {
    this.totalPoints = totalPoints;
  }

  public List<Prediction> getPredictions() {
    return predictions;
  }

  public void addPrediction(Prediction prediction) {
    predictions.add(prediction);
    prediction.setUser(this);
  }
}
