package org.sight.jooqstart.film;

import lombok.RequiredArgsConstructor;
import org.sight.jooqstart.web.FileWithActorPagedResponse;
import org.sight.jooqstart.web.PagedResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    public FileWithActorPagedResponse getFilmActorPageResponse(Long page, Long pageSize) {
        List<FilmWithActors> filmWithActorsList = filmRepository.findFilmWithActorList(page, pageSize);
        PagedResponse pagedResponse = new PagedResponse(page, pageSize);
        return new FileWithActorPagedResponse(pagedResponse, filmWithActorsList);
    }


}
