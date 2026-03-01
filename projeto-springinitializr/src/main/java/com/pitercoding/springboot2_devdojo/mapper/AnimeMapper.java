package com.pitercoding.springboot2_devdojo.mapper;

import com.pitercoding.springboot2_devdojo.domain.Anime;
import com.pitercoding.springboot2_devdojo.requests.AnimePostRequestBody;
import com.pitercoding.springboot2_devdojo.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
    public abstract Anime toAnime(AnimePutRequestBody animePostRequestBody);
}
