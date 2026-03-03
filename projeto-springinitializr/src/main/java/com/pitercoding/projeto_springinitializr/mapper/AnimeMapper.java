package com.pitercoding.projeto_springinitializr.mapper;

import com.pitercoding.projeto_springinitializr.domain.Anime;
import com.pitercoding.projeto_springinitializr.requests.AnimePostRequestBody;
import com.pitercoding.projeto_springinitializr.requests.AnimePutRequestBody;
import org.springframework.stereotype.Component;

@Component
public class AnimeMapper {
    public Anime toAnime(AnimePostRequestBody animePostRequestBody) {
        if (animePostRequestBody == null) {
            return null;
        }

        return Anime.builder()
                .name(animePostRequestBody.getName())
                .build();
    }

    public Anime toAnime(AnimePutRequestBody animePutRequestBody) {
        if (animePutRequestBody == null) {
            return null;
        }

        return Anime.builder()
                .id(animePutRequestBody.getId())
                .name(animePutRequestBody.getName())
                .build();
    }
}
