package com.pitercoding.springboot2_devdojo.repository;

import com.pitercoding.springboot2_devdojo.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

}
