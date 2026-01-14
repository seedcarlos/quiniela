package com.quiniela.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PredictionRequest(
    @NotNull Long matchId,
    @Min(0) int homeGoals,
    @Min(0) int awayGoals
) {
}
