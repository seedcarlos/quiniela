package com.quiniela.repository;

import com.quiniela.domain.Prediction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
  List<Prediction> findByMatchId(Long matchId);
  List<Prediction> findByUserId(Long userId);
}
