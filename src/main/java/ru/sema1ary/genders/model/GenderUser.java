package ru.sema1ary.genders.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sema1ary.genders.dao.impl.GenderUserDaoImpl;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "players", daoClass = GenderUserDaoImpl.class)
public class GenderUser {
    @DatabaseField(unique = true, generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField(canBeNull = false)
    private Gender gender;
}
