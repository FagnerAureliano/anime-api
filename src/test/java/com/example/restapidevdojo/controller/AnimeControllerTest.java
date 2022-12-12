package com.example.restapidevdojo.controller;

import com.example.restapidevdojo.domain.Anime;
import com.example.restapidevdojo.service.AnimeService;
import com.example.restapidevdojo.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn( AnimeCreator.createValidAnime()) ;

//        BDDMockito.when(animeServiceMock.listByName(List.of(AnimeCreator.createValidAnime())
//                .thenReturn( AnimeCreator.createValidAnime()) ;
//

    }
    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful(){
        String expectedAnime = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();
        Assertions.assertThat(animePage)
                .isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedAnime);
    }
    @Test
    @DisplayName("list returns list of anime when successful")
    void listAll_ReturnsListOfAnime_WhenSuccessful(){
        String expectedAnime = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeController.listAll().getBody();
        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedAnime);
    }
    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsListOfAnime_WhenSuccessful(){
       Long expectedAnimeId = AnimeCreator.createValidAnime().getId();

       Anime anime = animeController.findById(1).getBody();

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedAnimeId);
    }

}