package com.pitercoding.projeto_springinitializr.service;

import com.pitercoding.projeto_springinitializr.domain.Anime;
import com.pitercoding.projeto_springinitializr.exception.BadRequestException;
import com.pitercoding.projeto_springinitializr.mapper.AnimeMapper;
import com.pitercoding.projeto_springinitializr.requests.AnimePostRequestBody;
import com.pitercoding.projeto_springinitializr.repository.AnimeRepository;
import com.pitercoding.projeto_springinitializr.requests.AnimePutRequestBody;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final AnimeMapper animeMapper;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }


    public Anime findByIdOrThrowBadRequestException(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found!"));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(animeMapper.toAnime(animePostRequestBody));
    }

    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    @Transactional
    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        savedAnime.setName(animePutRequestBody.getName());
        animeRepository.save(savedAnime);
    }
}
