package com.quiniela.api;

import jakarta.validation.constraints.NotBlank;

public record TeamRequest(
    @NotBlank String name,
    @NotBlank String groupName
) {
}
