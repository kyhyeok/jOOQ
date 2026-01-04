package org.sight.jooqstart;

import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.jooq.generated.tables.JActor;
import org.jooq.generated.tables.records.ActorRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sight.jooqstart.actor.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JooqActiveRecordTest {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    DSLContext dslContext;

    @Test
    @DisplayName("SELECT 절 예제")
    void activeRecord_조회_예제() {
        // given
        Long actorId = 1L;

        // when
        ActorRecord actorRecord = actorRepository.findRecordByActorId(actorId);

        // then
        assertThat(actorRecord).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("activeRecord refresh 예제")
    void activeRecord_refresh_예제() {
        // given
        Long actorId = 1L;
        ActorRecord actorRecord = actorRepository.findRecordByActorId(actorId);
        actorRecord.setFirstName(null);

        // when
        actorRecord.refresh();

        // then
        assertThat(actorRecord.getFirstName()).isNotBlank();
    }

    @Test
    @DisplayName("activeRecord store 예제 - insert")
    @Transactional
    void activeRecord_insert_예제() {
        // given
        ActorRecord actorRecord = dslContext.newRecord(JActor.ACTOR);

        // when
        actorRecord.setFirstName("John");
        actorRecord.setLastName("Doe");
        actorRecord.store();
//        actorRecord.insert(); insert도 가능
        actorRecord.refresh();

        // then
        Assertions.assertThat(actorRecord.getLastName()).isNotNull();
    }

    @Test
    @DisplayName("activeRecord store 예제 - update")
    @Transactional
    void activeRecord_update_예제() {
        // given
        Long actorId = 1L;
        String newName = "test";
        ActorRecord actor = actorRepository.findRecordByActorId(actorId);

        // when
        actor.setFirstName(newName);
        actor.store(); // 혹은 actor.update()

        assertThat(actor.getFirstName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("activeRecord delete 예제")
    @Transactional
    void activeRecord_delete_예제() {
        // given
        ActorRecord actorRecord = dslContext.newRecord(JActor.ACTOR);

        actorRecord.setFirstName("John");
        actorRecord.setLastName("Doe");
        actorRecord.store();

        // when
        int result = actorRecord.delete();

        assertThat(result).isEqualTo(1);
    }
}