package com.pitercoding.projeto_springinitializr.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnimePostRequestBody {

    @NotBlank(message = "Anime name cannot be blank")
    private String name;
}
