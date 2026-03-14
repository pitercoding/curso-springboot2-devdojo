package com.pitercoding.projeto_springinitializr.integration;

import com.pitercoding.projeto_springinitializr.domain.Anime;
import com.pitercoding.projeto_springinitializr.domain.DevDojoUser;
import com.pitercoding.projeto_springinitializr.repository.AnimeRepository;
import com.pitercoding.projeto_springinitializr.repository.DevDojoUserRepository;
import com.pitercoding.projeto_springinitializr.requests.AnimePostRequestBody;
import com.pitercoding.projeto_springinitializr.util.AnimeCreator;
import com.pitercoding.projeto_springinitializr.util.AnimePostRequestBodyCreator;
import com.pitercoding.projeto_springinitializr.wrapper.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private DevDojoUserRepository devDojoUserRepository;

    private static final DevDojoUser USER = DevDojoUser.builder()
            .name("Dev Dojo Academy")
            .password("{bcrypt}$2a$12$f3Ig2HEyH5FsIB9KUS.VsOwBpcG/NdDuxSq0QsUcpQUzq/3wH2dB2")
            .username("devdojo")
            .authorities("ROLE_USER")
            .build();

    private static final DevDojoUser ADMIN = DevDojoUser.builder()
            .name("Racha Cuca")
            .password("{bcrypt}$2a$12$f3Ig2HEyH5FsIB9KUS.VsOwBpcG/NdDuxSq0QsUcpQUzq/3wH2dB2")
            .username("rachacuca")
            .authorities("ROLE_USER, ROLE_ADMIN")
            .build();

    @TestConfiguration
    @Lazy
    static class Config {

        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("devdojo", "chapolin");
            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleUAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("rachacuca", "chapolin");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("list returns a list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        devDojoUserRepository.save(USER);

        String expectedName = savedAnime.getName();

        List<Anime> animePage = testRestTemplateRoleUser.exchange("/animes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
        }).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList()
                .getFirst()
                .getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAllNonPageable returns list of animes when successful")
    void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        devDojoUserRepository.save(USER);

        String expectedName = savedAnime.getName();

        java.util.List<Anime> animes = testRestTemplateRoleUser.exchange("/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<java.util.List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(animes.getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        devDojoUserRepository.save(USER);

        Long expectedId = savedAnime.getId();

        Anime anime = testRestTemplateRoleUser.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        devDojoUserRepository.save(USER);
        
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = savedAnime.getName();

        String url = String.format("/animes/find?name=%s", expectedName);

        java.util.List<Anime> animes = testRestTemplateRoleUser.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<java.util.List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(animes.getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsAnEmptyListOfAnime_WhenAnimeIsNotFound() {

        devDojoUserRepository.save(USER);

        java.util.List<Anime> animes = testRestTemplateRoleUser.exchange("/animes/find?name=dbz",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<java.util.List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

        devDojoUserRepository.save(USER);

        ResponseEntity<Anime> animeResponseEntity = testRestTemplateRoleUser.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        devDojoUserRepository.save(USER);

        savedAnime.setName("new name");

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes",
                HttpMethod.PUT,
                new HttpEntity<>(savedAnime),
                Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        devDojoUserRepository.save(ADMIN);

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleAdmin.exchange("/animes/admin/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        devDojoUserRepository.save(USER);

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes/admin/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
