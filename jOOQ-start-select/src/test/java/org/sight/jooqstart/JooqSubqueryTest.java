package org.sight.jooqstart;

import org.assertj.core.api.Assertions;
import org.jooq.generated.tables.pojos.Film;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sight.jooqstart.film.FilmPriceSummary;
import org.sight.jooqstart.film.FilmRentalSummary;
import org.sight.jooqstart.film.FilmRepository;
import org.sight.jooqstart.film.FilmRepositoryHasA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class JooqSubqueryTest {

    @Autowired
    private FilmRepositoryHasA filmRepository;

    @Test
    @DisplayName("""
            영화별 대여료가
             1.0 이하면 'Cheap',
             3.0 이하면 'Moderate',
             그 이상이면 'Expensive'로 분류하고,
            각 영화의 총 재고 수를 조회한다.
            """)
    void 스칼라_서브쿼리_예제() {
        String filmTitle = "EGG";

        List<FilmPriceSummary> priceSummaryList = filmRepository.findFilmPriceSummaryByFilmTitle(filmTitle);
        Assertions.assertThat(priceSummaryList).isNotEmpty();
    }

    @Test
    @DisplayName("평균 대여 기간이 가장 긴 영화부터 정렬해서 조회한다.")
    void from절_서브쿼리_인라인뷰_예제() {
        String filmTitle = "EGG";

        List<FilmRentalSummary> priceRentalSummaryList = filmRepository.findFilmRentalSummaryByFilmTitle(filmTitle);
        Assertions.assertThat(priceRentalSummaryList).isNotEmpty();
    }

    @Test
    @DisplayName("대여된 기록이 있는 영화가 있는 영화만 조회")
    void 조건절_서브쿼리_예제() {
        String filmTitle = "EGG";

        List<Film> filmList = filmRepository.findRentedFilmByTitle(filmTitle);
        Assertions.assertThat(filmList).isNotEmpty();

    }

    @Test
    void Enum_Converter_테스트() {
        // when
        List<FilmPriceSummary> result = filmRepository
                .findFilmPriceSummaryByFilmTitle("EGG");

        // then
        assertThat(result).allSatisfy(summary -> {
            assertThat(summary.getPriceCategory())
                    .isInstanceOf(FilmPriceSummary.PriceCategory.class);
            assertThat(summary.getPriceCategory())
                    .isIn(
                            FilmPriceSummary.PriceCategory.CHEAP,
                            FilmPriceSummary.PriceCategory.MODERATE,
                            FilmPriceSummary.PriceCategory.EXPENSIVE
                    );
        });
    }
}