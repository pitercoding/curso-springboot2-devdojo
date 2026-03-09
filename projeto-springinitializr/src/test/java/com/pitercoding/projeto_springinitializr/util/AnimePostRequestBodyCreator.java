package com.pitercoding.projeto_springinitializr.util;

import com.pitercoding.projeto_springinitializr.domain.Anime;
import com.pitercoding.projeto_springinitializr.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

    public static AnimePostRequestBody createAnimePostRequestBody() {
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
