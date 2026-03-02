package com.pitercoding.projeto_springinitializr.mapper;

import com.pitercoding.projeto_springinitializr.domain.Anime;
import com.pitercoding.projeto_springinitializr.requests.AnimePostRequestBody;
import com.pitercoding.projeto_springinitializr.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
    public abstract Anime toAnime(AnimePutRequestBody animePostRequestBody);
}
