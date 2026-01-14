package com.quiniela.api;

import jakarta.validation.constraints.Min;

public record ResultRequest(
    @Min(0) int homeGoals,
    @Min(0) int awayGoals
) {
}
