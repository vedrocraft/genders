package ru.sema1ary.genders.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import lombok.NonNull;
import ru.sema1ary.genders.dao.GenderUserDao;
import ru.sema1ary.genders.model.GenderUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class GenderUserDaoImpl extends BaseDaoImpl<GenderUser, Long> implements GenderUserDao {
    public GenderUserDaoImpl(ConnectionSource connectionSource, Class<GenderUser> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public GenderUser save(@NonNull GenderUser user) throws SQLException {
        createOrUpdate(user);
        return user;
    }

    @Override
    public void saveAll(@NonNull List<GenderUser> users) throws SQLException {
        callBatchTasks((Callable<Void>) () -> {
            for (GenderUser user : users) {
                createOrUpdate(user);
            }
            return null;
        });
    }

    @Override
    public Optional<GenderUser> findById(@NonNull Long id) throws SQLException {
        GenderUser result = queryForId(id);
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<GenderUser> findByUsername(@NonNull String username) throws SQLException {
        QueryBuilder<GenderUser, Long> queryBuilder = queryBuilder();
        Where<GenderUser, Long> where = queryBuilder.where();
        String columnName = "username";

        SelectArg selectArg = new SelectArg(SqlType.STRING, username.toLowerCase());
        where.raw("LOWER(" + columnName + ")" + " = LOWER(?)", selectArg);
        return Optional.ofNullable(queryBuilder.queryForFirst());
    }

    @Override
    public List<GenderUser> findAll() throws SQLException {
        return queryForAll();
    }
}
