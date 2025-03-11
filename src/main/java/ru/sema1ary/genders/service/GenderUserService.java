package ru.sema1ary.genders.service;

import lombok.NonNull;
import ru.sema1ary.genders.model.GenderUser;
import ru.sema1ary.vedrocraftapi.service.Service;

import java.util.List;
import java.util.Optional;

public interface GenderUserService extends Service {
    GenderUser save(@NonNull GenderUser user);

    void saveAll(@NonNull List<GenderUser> users);

    Optional<GenderUser> findById(@NonNull Long id);

    Optional<GenderUser> findByUsername(@NonNull String username);

    List<GenderUser> findAll();

    GenderUser getUser(@NonNull String username);
}
