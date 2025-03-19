package ru.sema1ary.genders;

import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sema1ary.genders.command.GendersCommand;
import ru.sema1ary.genders.listener.PreJoinListener;
import ru.sema1ary.genders.model.GenderUser;
import ru.sema1ary.genders.placeholder.GenderPlaceholder;
import ru.sema1ary.genders.service.GenderUserService;
import ru.sema1ary.genders.service.impl.GenderUserServiceImpl;
import ru.sema1ary.vedrocraftapi.BaseCommons;
import ru.sema1ary.vedrocraftapi.command.LiteCommandBuilder;
import ru.sema1ary.vedrocraftapi.ormlite.ConnectionSourceUtil;
import ru.sema1ary.vedrocraftapi.ormlite.DatabaseUtil;
import ru.sema1ary.vedrocraftapi.service.ConfigService;
import ru.sema1ary.vedrocraftapi.service.ServiceManager;
import ru.sema1ary.vedrocraftapi.service.impl.ConfigServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Genders extends JavaPlugin implements BaseCommons {
    @Override
    public void onEnable() {
        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        DatabaseUtil.initConnectionSource(
                this,
                getService(ConfigService.class),
                GenderUser.class
        );

        ServiceManager.registerService(GenderUserService.class, new GenderUserServiceImpl(
                getDao(GenderUser.class)
        ));

        getServer().getPluginManager().registerEvents(new PreJoinListener(
                getService(GenderUserService.class)
        ), this);

        LiteCommandBuilder.builder()
                .commands(new GendersCommand(
                        getService(ConfigService.class),
                        getService(GenderUserService.class))
                )
                .build();

        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new GenderPlaceholder(getService(GenderUserService.class)).register();
        }
    }

    @Override
    public void onDisable() {
        ConnectionSourceUtil.closeConnection(true);
    }
}
