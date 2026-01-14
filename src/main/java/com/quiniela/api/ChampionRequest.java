package com.quiniela.api;

import jakarta.validation.constraints.NotNull;

public record ChampionRequest(
    @NotNull Long teamId
) {
}
