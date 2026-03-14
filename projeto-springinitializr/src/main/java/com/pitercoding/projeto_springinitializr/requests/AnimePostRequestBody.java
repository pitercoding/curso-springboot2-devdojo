package com.pitercoding.projeto_springinitializr.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody {
    @NotBlank(message = "Anime name cannot be blank")
    @Schema(description = "This is anime's name", example = "Shiryu Amigo")
    private String name;
}
