package org.sight.jooqstart;

import org.jooq.generated.tables.pojos.Actor;
import org.jooq.generated.tables.records.ActorRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sight.jooqstart.actor.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JooqInsertTest {

    @Autowired
    ActorRepository actorRepository;

    @Test
    @DisplayName("자동생성된 DAO를 통한 insert")
    @Transactional
    void insert_dao() {
        // given
        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");
        actor.setLastUpdate(LocalDateTime.now());

        // when
        actorRepository.saveByDao(actor);

        // then
        assertThat(actor.getActorId()).isNotNull();
    }

    @Test
    @DisplayName("ActiveRecord 를 통한 insert")
    @Transactional
    void insert_by_record() {
        // given
        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");
//        actor.setLastUpdate(LocalDateTime.now());

        // when
        ActorRecord actorRecord = actorRepository.saveByRecord(actor);

        // then
        assertThat(actorRecord.getActorId()).isNotNull();
        assertThat(actor.getActorId()).isNull();
    }

    @Test
    @DisplayName("SQL 실행 후 PK만 반환")
    @Transactional
    void insert_with_returning_pk() {
        // given
        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");

        // when
        Long pk = actorRepository.saveWithReturningPkOnly(actor);

        // then
        assertThat(pk).isNotNull();
    }

    @Test
    @DisplayName("SQL 실행 후 해당 ROW 전체 반환")
    @Transactional
    void insert_with_returning() {
        // given
        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");
        actor.setLastUpdate(LocalDateTime.now());

        // when
        Actor newActor = actorRepository.saveWithReturning(actor);

        // then
        assertThat(newActor).hasNoNullFieldsOrProperties();
    }

    /**
     * insert into `actor` (`first_name`, `last_name`) values (?, ?), (?, ?), (?, ?), (?, ?), (?, ?)
     */
    @Test
    @DisplayName("bulk insert 예제")
    @Transactional
    void bulk_insert() {
        // given
        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");

        Actor actor2 = new Actor();
        actor2.setFirstName("John 2");
        actor2.setLastName("Doe 2");

        List<Actor> actorList = List.of(actor, actor2);

        // when
        actorRepository.bulkInsertWithRows(actorList);
    }
}
