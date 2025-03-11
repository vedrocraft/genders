package ru.sema1ary.genders.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sema1ary.genders.dao.GenderUserDao;
import ru.sema1ary.genders.model.Gender;
import ru.sema1ary.genders.model.GenderUser;
import ru.sema1ary.genders.service.GenderUserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GenderUserServiceImpl implements GenderUserService {
    private final GenderUserDao userDao;

    @Override
    public GenderUser save(@NonNull GenderUser user) {
        try {
            return userDao.save(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(@NonNull List<GenderUser> users) {
        try {
            userDao.saveAll(users);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<GenderUser> findById(@NonNull Long id) {
        try {
            return userDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<GenderUser> findByUsername(@NonNull String username) {
        try {
            return userDao.findByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GenderUser> findAll() {
        try {
            return userDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GenderUser getUser(@NonNull String username) {
        return findByUsername(username).orElseGet(() -> save(GenderUser.builder()
                .username(username)
                .gender(Gender.OTHER)
                .build()));
    }
}
