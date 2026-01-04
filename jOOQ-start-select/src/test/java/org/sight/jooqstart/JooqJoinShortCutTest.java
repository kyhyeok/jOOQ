package org.sight.jooqstart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sight.jooqstart.film.FilmRepository;
import org.sight.jooqstart.film.FilmWithActors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JooqJoinShortCutTest {

    @Autowired
    FilmRepository filmRepository;

    @Test
    @DisplayName("implicitPathJoin_테스트")
    void implicitPathJoin_테스트() {

        List<FilmWithActors> original = filmRepository.findFilmWithActorList(1L, 10L);
        List<FilmWithActors> implicit = filmRepository.findFilmWithActorsListImplicitPathJoin(1L, 10L);

        assertThat(original)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(implicit);
    }

    @Test
    @DisplayName("implicitPathJoin_ManyToOne_테스트")
    void implicitPathJoin_ManyToOne_테스트() {

        List<FilmWithActors> original = filmRepository.findFilmWithActorList(1L, 10L);
        List<FilmWithActors> implicit = filmRepository.findFilmWithActorsListImplicitPathManyToOneJoin(1L, 10L);

        assertThat(original)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(implicit);
    }

    @Test
    @DisplayName("explicitPathJoin_테스트")
    void explicitPathJoin_테스트() {

        List<FilmWithActors> original = filmRepository.findFilmWithActorList(1L, 10L);
        List<FilmWithActors> explicit = filmRepository.findFilmWithActorsListExplicitPathJoin(1L, 10L);

        assertThat(original)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(explicit);
    }
}