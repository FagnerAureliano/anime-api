package com.example.restapidevdojo.util;

import com.example.restapidevdojo.DTO.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

    public static AnimePutRequestBody createAnimePutRequestBody(){
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createAnimeToBeSaved().getId())
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
