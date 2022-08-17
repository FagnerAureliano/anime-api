package com.example.restapidevdojo.mapper;

import com.example.restapidevdojo.DTO.AnimePostRequestBody;
import com.example.restapidevdojo.DTO.AnimePutRequestBody;
import com.example.restapidevdojo.domain.Anime;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
