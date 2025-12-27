package org.sight.jooqstart.utils.jooq;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class JooqListConditionUtil {
    public static <T> Condition inIfNotEmpty(Field<Long> id, List<T> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return DSL.noCondition();
        }

        return id.in(idList);
    }
}
