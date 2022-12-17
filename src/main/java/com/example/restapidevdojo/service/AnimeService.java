package com.example.restapidevdojo.service;

import com.example.restapidevdojo.DTO.AnimePostRequestBody;
import com.example.restapidevdojo.DTO.AnimePutRequestBody;
import com.example.restapidevdojo.domain.Anime;
import com.example.restapidevdojo.exceptions.BadRequestException;
import com.example.restapidevdojo.mapper.AnimeMapper;
import com.example.restapidevdojo.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }
    public List<Anime> listAllNonPageable() {
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime findById(long id) {
        return animeRepository.findById(id).orElseThrow(() -> new BadRequestException("Anime not found"));
    }
    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
//        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
        Anime anime = Anime.builder()
                .name(animePostRequestBody.getName())
                .build();

        return animeRepository.save(anime);
    }

    public void delete(long id) {
        animeRepository.delete(findById(id));
    }

    @Transactional
    public void replace(AnimePutRequestBody animePutRequestBody) {

        Anime savedAnime = findById(animePutRequestBody.getId());
//        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
//        anime.setId(savedAnime.getId());
        Anime anime = Anime.builder()
                .id(savedAnime.getId())
                .name(animePutRequestBody.getName())
                .build();
         animeRepository.save(anime);
//        return animeRepository.save(anime);
    }

}
