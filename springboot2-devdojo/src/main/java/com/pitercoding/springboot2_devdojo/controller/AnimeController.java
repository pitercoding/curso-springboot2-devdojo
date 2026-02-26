package com.pitercoding.springboot2_devdojo.controller;

import com.pitercoding.springboot2_devdojo.domain.Anime;
import com.pitercoding.springboot2_devdojo.service.AnimeService;
import com.pitercoding.springboot2_devdojo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @GetMapping
    public List<Anime> list() {
        log.info(dateUtil.formatLocalDateTimeToDateBaseStyle(LocalDateTime.now()));
        return animeService.listAll();
    }
}
