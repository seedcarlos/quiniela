package com.quiniela.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MatchRequest(
    @NotNull Long homeTeamId,
    @NotNull Long awayTeamId,
    @NotBlank String phase,
    @NotBlank String kickoffAt
) {
}
