package com.example.restapidevdojo.controller;

import com.example.restapidevdojo.DTO.AnimePostRequestBody;
import com.example.restapidevdojo.DTO.AnimePutRequestBody;
import com.example.restapidevdojo.domain.Anime;
import com.example.restapidevdojo.service.AnimeService;
import com.example.restapidevdojo.util.AnimeCreator;
import com.example.restapidevdojo.util.AnimePostRequstBodyCreator;
import com.example.restapidevdojo.util.AnimePutRequestBodyCreator;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@Log4j2
@DisplayName("Test for Anime Controller")
@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());


    }

    @Test
    @DisplayName("list Returns List Of Anime Inside Page Object When Successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
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
    @DisplayName("list Returns List of Anime When Successful")
    void listAll_ReturnsListOfAnime_WhenSuccessful() {
        String expectedAnime = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeController.listAll().getBody();
        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("findById Returns Anime When Successful")
    void findById_ReturnsListOfAnime_WhenSuccessful() {
        Long expectedAnimeId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.findById(1).getBody();

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedAnimeId);
    }

    @Test
    @DisplayName("findByName Returns Anime When Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        String expectedAnime = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeController.findByName("anime").getBody();
        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("findByName Returns Empty List Of Anime When Anime Is Not Found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("anime").getBody();
        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save Persists Anime When Successful")
    void save_PersistsAnime_WhenSuccessful() {
        Anime anime = animeController.save(AnimePostRequstBodyCreator.createAnimePostRequestBody()).getBody();
        Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(AnimeCreator.createValidAnime());

    }

    @Test
    @DisplayName("replace Updates Anime When Successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody())).doesNotThrowAnyException();
        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());
        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete Removes Anime When Successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeController.delete(1)).doesNotThrowAnyException();
        ResponseEntity<Void> entity = animeController.delete(1);
        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }
}