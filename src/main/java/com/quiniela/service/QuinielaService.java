package com.quiniela.service;

import com.quiniela.domain.Match;
import com.quiniela.domain.MatchStatus;
import com.quiniela.domain.Prediction;
import com.quiniela.domain.Team;
import com.quiniela.domain.User;
import com.quiniela.repository.MatchRepository;
import com.quiniela.repository.PredictionRepository;
import com.quiniela.repository.TeamRepository;
import com.quiniela.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuinielaService {

  private final MatchRepository matchRepository;
  private final PredictionRepository predictionRepository;
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  private final ScoringService scoringService;

  public QuinielaService(
      MatchRepository matchRepository,
      PredictionRepository predictionRepository,
      TeamRepository teamRepository,
      UserRepository userRepository,
      ScoringService scoringService) {
    this.matchRepository = matchRepository;
    this.predictionRepository = predictionRepository;
    this.teamRepository = teamRepository;
    this.userRepository = userRepository;
    this.scoringService = scoringService;
  }

  @Transactional
  public Match recordMatchResult(Long matchId, int homeGoals, int awayGoals) {
    Match match = matchRepository.findById(matchId)
        .orElseThrow(() -> new EntityNotFoundException("Match not found"));
    match.setHomeGoals(homeGoals);
    match.setAwayGoals(awayGoals);
    match.setStatus(MatchStatus.FINISHED);

    List<Prediction> predictions = predictionRepository.findByMatchId(matchId);
    for (Prediction prediction : predictions) {
      int previousPoints = prediction.getPointsAwarded();
      int points = scoringService.calculatePoints(match, prediction);
      prediction.setPointsAwarded(points);
      User user = prediction.getUser();
      user.setTotalPoints(user.getTotalPoints() - previousPoints + points);
      userRepository.save(user);
    }

    return matchRepository.save(match);
  }

  @Transactional
  public Prediction submitPrediction(Long userId, Long matchId, int homeGoals, int awayGoals) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
    Match match = matchRepository.findById(matchId)
        .orElseThrow(() -> new EntityNotFoundException("Match not found"));

    Prediction prediction = new Prediction(user, match, homeGoals, awayGoals);
    if (match.getStatus() == MatchStatus.FINISHED) {
      int points = scoringService.calculatePoints(match, prediction);
      prediction.setPointsAwarded(points);
      user.setTotalPoints(user.getTotalPoints() + points);
    }
    Prediction saved = predictionRepository.save(prediction);
    return saved;
  }

  @Transactional
  public User pickChampion(Long userId, Long teamId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new EntityNotFoundException("Team not found"));
    user.setChampionshipPick(team);
    return userRepository.save(user);
  }

  @Transactional
  public void applyChampionBonus(Long championTeamId) {
    Team champion = teamRepository.findById(championTeamId)
        .orElseThrow(() -> new EntityNotFoundException("Team not found"));
    List<User> users = userRepository.findAll();
    for (User user : users) {
      int bonus = champion.equals(user.getChampionshipPick()) ? 5 : 0;
      user.setBonusPoints(bonus);
      int predictionsPoints = user.getPredictions().stream()
          .mapToInt(Prediction::getPointsAwarded)
          .sum();
      user.setTotalPoints(predictionsPoints + bonus);
      userRepository.save(user);
    }
  }
}
