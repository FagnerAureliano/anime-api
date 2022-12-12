package com.example.restapidevdojo.util;

import com.example.restapidevdojo.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Berserk")
                .build();
    }

    public static  Anime createValidAnime(){
        return Anime.builder()
                .id(1L)
                .name("Berserk")
                .build();
    }

    public static  Anime createValidUpdatedAnime(){
        return Anime.builder()
                .id(1L)
                .name("Berserk 2")
                .build();
    }
}
