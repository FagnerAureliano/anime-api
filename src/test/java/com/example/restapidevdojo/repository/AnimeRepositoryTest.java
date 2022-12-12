package com.example.restapidevdojo.repository;

import com.example.restapidevdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;
    @Test
    @DisplayName("Save Persists Anime When Successful")
    void save_PersistsAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save Persists Anime When Successful")
    void save_UpdateAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);

        animeSaved.setName("Jojo");
        Anime animeUpdated = animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());

    }

    @Test
    @DisplayName("Delete Removes Anime When Successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);

        animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();

    }
    @Test
    @DisplayName("Find By Name Returns List of Anime When Anime Exists")
    void findByName_ReturnsAnimeList_WhenExists(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);

        List<Anime> animeList = animeRepository.findByName(animeSaved.getName());

        Assertions.assertThat(animeList)
                .isNotEmpty()
                .contains(animeSaved);

    }
    @Test
    @DisplayName("Find By Name Returns Empty When Anime IsNot Found")
    void findByName_ReturnsEmptyList_WhenNoAnimeFound(){
        List<Anime> animeList = animeRepository.findByName("Nonexistent Anime");

        Assertions.assertThat(animeList).isEmpty();

    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty(){
        Anime anime = new Anime();
//        Assertions.assertThatThrownBy(() -> animeRepository.save(anime)).isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> animeRepository.save(anime))
                .withMessageContaining("The anime name cannot be empty");

    }

    private Anime createAnime(){
        return Anime.builder()
                .name("Berserk")
                .build();
    }
}