package org.sight.jooqstart;

import org.assertj.core.api.Assertions;
import org.jooq.generated.tables.pojos.Film;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sight.jooqstart.film.FilmRepositoryHasA;
import org.sight.jooqstart.film.FilmRepositoryIsA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JooqDaoWrapperTest {
    @Autowired
    FilmRepositoryIsA filmRepositoryIsA; // 상속

    @Autowired
    FilmRepositoryHasA filmRepositoryHasA; // 컴포지트

    @Test
    void testIsA() {
        Film byId = filmRepositoryIsA.findById(10L);
    }

    @Test
    @DisplayName("""
            상속) 자동생성 DAO 사용
                - 영화 길이가 100 ~ 180분 사이인 영화 조회
            """)
    void 상속_DAO_1() {
        // given
        var start = 100;
        var end = 180;

        // when
        List<Film> films = filmRepositoryIsA.fetchRangeOfJLength(start, end);

        // then
        Assertions.assertThat(films).allSatisfy(file ->
                Assertions.assertThat(file.getLength()).isBetween(start, end)
        );
    }

    @Test
    @DisplayName("""
            컴포지션) 자동생성 DAO 사용
                - 영화 길이가 100 ~ 180분 사이인 영화 조회
            """)
    void 컴포지션_DAO_1() {
        var start = 100;
        var end = 180;

        // when
        List<Film> films = filmRepositoryHasA.findByRangeBetween(start, end);

        // then
        Assertions.assertThat(films).allSatisfy(file ->
                Assertions.assertThat(file.getLength()).isBetween(start, end)
        );
    }
}
