package org.sight.jooqstart.web;

import lombok.Getter;
import org.sight.jooqstart.film.FilmWithActors;

import java.util.List;

@Getter
public class FileWithActorPagedResponse {
    private PagedResponse page;
    private List<FilmActorResponse> filmWithActor;

    public FileWithActorPagedResponse(PagedResponse page, List<FilmWithActors> filmWithActorsList) {
        this.page = page;
        this.filmWithActor = filmWithActorsList.stream().map(FilmActorResponse::new).toList();
    }

    @Getter
    public static class FilmActorResponse {
        private final String filmTitle;
        private final String actorFullName;
        private final Long filmId;

        public FilmActorResponse(FilmWithActors filmWithActors) {
            this.filmTitle = filmWithActors.getTitle();
            this.actorFullName = filmWithActors.getActorFullName();
            this.filmId = filmWithActors.getFilmId();
        }
    }
}
