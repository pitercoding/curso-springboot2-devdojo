package com.pitercoding.springboot2.essentials.controller;

import com.pitercoding.springboot2.essentials.domain.Anime;
import com.pitercoding.springboot2.essentials.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("anime")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final DateUtil dateUtil;

    @GetMapping(path = "list")
    public List<Anime> list() {
        log.info(dateUtil.formatLocalDateTimeToDateBaseStyle(LocalDateTime.now()));
        return List.of(new Anime("Boku No Hero"), new Anime("Berserk"));
    }

    @GetMapping(path = "list2")
    public List<Anime> list2() {
        log.info(dateUtil.formatLocalDateTimeToDateBaseStyle(LocalDateTime.now()));
        return List.of(new Anime("DBZ"), new Anime("One Piece"));
    }
}
