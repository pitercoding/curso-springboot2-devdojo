package com.pitercoding.springboot2_devdojo.service;

import com.pitercoding.springboot2_devdojo.domain.Anime;
import com.pitercoding.springboot2_devdojo.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {
    // private final AnimeRepository animeRepository;

    public List<Anime> listAll() {
        return List.of(new Anime(1L, "CDZ"), new Anime(2L, "Berserk"));
    }
}
