package com.quiniela.repository;

import com.quiniela.domain.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
  Optional<Team> findByName(String name);
}
