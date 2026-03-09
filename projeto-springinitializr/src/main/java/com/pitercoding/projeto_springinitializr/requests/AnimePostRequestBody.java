package com.pitercoding.projeto_springinitializr.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePostRequestBody {
    @NotBlank(message = "Anime name cannot be blank")
    private String name;
}
