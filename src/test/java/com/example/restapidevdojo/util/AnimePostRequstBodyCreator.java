package com.example.restapidevdojo.util;

import com.example.restapidevdojo.DTO.AnimePostRequestBody;
import com.example.restapidevdojo.domain.Anime;

public class AnimePostRequstBodyCreator {

    public static AnimePostRequestBody createAnimePostRequestBody(){
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
