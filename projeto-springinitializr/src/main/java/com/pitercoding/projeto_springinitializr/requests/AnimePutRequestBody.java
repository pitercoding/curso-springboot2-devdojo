package com.pitercoding.projeto_springinitializr.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePutRequestBody {
    @NotNull(message = "Anime id cannot be null")
    private Long id;

    @NotBlank(message = "Anime name cannot be blank")
    private String name;
}
