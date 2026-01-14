package com.quiniela.api;

import com.quiniela.domain.Role;
import com.quiniela.domain.User;
import com.quiniela.repository.UserRepository;
import com.quiniela.service.QuinielaService;
import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

  private final UserRepository userRepository;
  private final QuinielaService quinielaService;

  public ParticipantController(UserRepository userRepository, QuinielaService quinielaService) {
    this.userRepository = userRepository;
    this.quinielaService = quinielaService;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public User register(@Valid @RequestBody RegisterRequest request) {
    User user = new User(request.name(), request.email(), Role.PARTICIPANT);
    return userRepository.save(user);
  }

  @PostMapping("/{userId}/predictions")
  @ResponseStatus(HttpStatus.CREATED)
  public void submitPrediction(
      @PathVariable Long userId,
      @Valid @RequestBody PredictionRequest request) {
    quinielaService.submitPrediction(userId, request.matchId(), request.homeGoals(), request.awayGoals());
  }

  @PostMapping("/{userId}/champion")
  public User pickChampion(@PathVariable Long userId, @Valid @RequestBody ChampionRequest request) {
    return quinielaService.pickChampion(userId, request.teamId());
  }

  @GetMapping("/leaderboard")
  public List<User> leaderboard() {
    return userRepository.findAll().stream()
        .sorted(Comparator.comparing(User::getTotalPoints).reversed())
        .toList();
  }
}
