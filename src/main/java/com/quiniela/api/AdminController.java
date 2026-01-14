package com.quiniela.api;

import com.quiniela.domain.Match;
import com.quiniela.domain.Team;
import com.quiniela.repository.MatchRepository;
import com.quiniela.repository.TeamRepository;
import com.quiniela.service.QuinielaService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  private final TeamRepository teamRepository;
  private final MatchRepository matchRepository;
  private final QuinielaService quinielaService;

  public AdminController(
      TeamRepository teamRepository,
      MatchRepository matchRepository,
      QuinielaService quinielaService) {
    this.teamRepository = teamRepository;
    this.matchRepository = matchRepository;
    this.quinielaService = quinielaService;
  }

  @PostMapping("/teams")
  @ResponseStatus(HttpStatus.CREATED)
  public Team createTeam(@Valid @RequestBody TeamRequest request) {
    Team team = new Team(request.name(), request.groupName());
    return teamRepository.save(team);
  }

  @PostMapping("/matches")
  @ResponseStatus(HttpStatus.CREATED)
  public Match createMatch(@Valid @RequestBody MatchRequest request) {
    Team homeTeam = teamRepository.findById(request.homeTeamId())
        .orElseThrow();
    Team awayTeam = teamRepository.findById(request.awayTeamId())
        .orElseThrow();
    LocalDateTime kickoff = LocalDateTime.parse(request.kickoffAt());
    Match match = new Match(homeTeam, awayTeam, request.phase(), kickoff);
    return matchRepository.save(match);
  }

  @PostMapping("/matches/{matchId}/result")
  public Match recordResult(@PathVariable Long matchId, @Valid @RequestBody ResultRequest request) {
    return quinielaService.recordMatchResult(matchId, request.homeGoals(), request.awayGoals());
  }

  @PostMapping("/champion")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void applyChampion(@Valid @RequestBody ChampionRequest request) {
    quinielaService.applyChampionBonus(request.teamId());
  }
}
