package com.pitercoding.springboot2_devdojo.service;

import com.pitercoding.springboot2_devdojo.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {
    // private final AnimeRepository animeRepository;

    private static final List<Anime> animes;

    static {
        animes = new ArrayList<>(List.of(new Anime(1L, "CDZ"), new Anime(2L, "Berserk")));
    }

    public List<Anime> listAll() {
        return animes;
    }

    public Anime findById(Long id) {
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found!"));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3, 10000));
        animes.add(anime);
        return anime;
    }

    public void delete(Long id) {
        animes.remove(findById(id));
    }

    public void replace(Anime anime) {
        delete(anime.getId());
        animes.add(anime);
    }
}
