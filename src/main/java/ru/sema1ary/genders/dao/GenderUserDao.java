package ru.sema1ary.genders.dao;

import com.j256.ormlite.dao.Dao;
import lombok.NonNull;
import ru.sema1ary.genders.model.GenderUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenderUserDao extends Dao<GenderUser, Long> {
    GenderUser save(@NonNull GenderUser user) throws SQLException;

    void saveAll(@NonNull List<GenderUser> users) throws SQLException;

    Optional<GenderUser> findById(@NonNull Long id) throws SQLException;

    Optional<GenderUser> findByUsername(@NonNull String username) throws SQLException;

    List<GenderUser> findAll() throws SQLException;
}
