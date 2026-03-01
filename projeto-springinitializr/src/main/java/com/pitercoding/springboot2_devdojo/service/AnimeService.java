package com.pitercoding.springboot2_devdojo.service;

import com.pitercoding.springboot2_devdojo.domain.Anime;
import com.pitercoding.springboot2_devdojo.requests.AnimePostRequestBody;
import com.pitercoding.springboot2_devdojo.repository.AnimeRepository;
import com.pitercoding.springboot2_devdojo.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public List<Anime> listAll() {
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequestException(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found!"));
    }

    public Anime save(AnimePostRequestBody animeDTO) {
        return animeRepository.save(Anime.builder().name(animeDTO.getName()).build());
    }

    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = Anime.builder()
                .id(savedAnime.getId())
                .name(animePutRequestBody.getName())
                .build();
        animeRepository.save(anime);
    }
}
