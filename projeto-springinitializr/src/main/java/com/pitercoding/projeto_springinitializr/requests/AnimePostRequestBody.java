package com.pitercoding.projeto_springinitializr.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnimePostRequestBody {

    @NotEmpty(message = "Anime name cannot be empty")
    private String name;
}
