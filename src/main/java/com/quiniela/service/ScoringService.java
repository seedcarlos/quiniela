package com.quiniela.service;

import com.quiniela.domain.Match;
import com.quiniela.domain.Prediction;
import org.springframework.stereotype.Service;

@Service
public class ScoringService {

  public int calculatePoints(Match match, Prediction prediction) {
    if (match.getHomeGoals() == null || match.getAwayGoals() == null) {
      return 0;
    }

    int points = 0;
    int matchOutcome = Integer.compare(match.getHomeGoals(), match.getAwayGoals());
    int predictedOutcome = Integer.compare(prediction.getHomeGoals(), prediction.getAwayGoals());

    if (matchOutcome == predictedOutcome) {
      points += 2;
    }

    if (match.getHomeGoals().equals(prediction.getHomeGoals())) {
      points += 1;
    }

    if (match.getAwayGoals().equals(prediction.getAwayGoals())) {
      points += 1;
    }

    return points;
  }
}
