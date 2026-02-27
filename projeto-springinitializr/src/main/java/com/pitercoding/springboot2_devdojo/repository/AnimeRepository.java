package com.pitercoding.springboot2_devdojo.repository;

import com.pitercoding.springboot2_devdojo.domain.Anime;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository {
    List<Anime> listAll();
}
