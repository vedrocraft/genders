package ru.sema1ary.genders;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sema1ary.genders.command.GendersCommand;
import ru.sema1ary.genders.listener.PreJoinListener;
import ru.sema1ary.genders.model.GenderUser;
import ru.sema1ary.genders.placeholder.GenderPlaceholder;
import ru.sema1ary.genders.service.GenderUserService;
import ru.sema1ary.genders.service.impl.GenderUserServiceImpl;
import ru.sema1ary.vedrocraftapi.command.LiteCommandBuilder;
import ru.sema1ary.vedrocraftapi.ormlite.ConnectionSourceUtil;
import ru.sema1ary.vedrocraftapi.ormlite.DaoFinder;
import ru.sema1ary.vedrocraftapi.service.ConfigService;
import ru.sema1ary.vedrocraftapi.service.ServiceGetter;
import ru.sema1ary.vedrocraftapi.service.ServiceManager;
import ru.sema1ary.vedrocraftapi.service.impl.ConfigServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Genders extends JavaPlugin implements DaoFinder, ServiceGetter {
    private JdbcPooledConnectionSource connectionSource;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        initConnectionSource();

        ServiceManager.registerService(GenderUserService.class, new GenderUserServiceImpl(getDao(
                connectionSource, GenderUser.class
        )));

        getServer().getPluginManager().registerEvents(new PreJoinListener(
                ServiceManager.getService(GenderUserService.class)
        ), this);

        LiteCommandBuilder.builder()
                .commands(new GendersCommand(ServiceManager.getService(ConfigService.class),
                        ServiceManager.getService(GenderUserService.class)))
                .build();

        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new GenderPlaceholder(ServiceManager.getService(GenderUserService.class)).register();
        }
    }

    @Override
    public void onDisable() {
        ConnectionSourceUtil.closeConnection(true, connectionSource);
    }

    @SneakyThrows
    private void initConnectionSource() {
        if(ServiceManager.getService(ConfigService.class).get("sql-use")) {
            connectionSource = ConnectionSourceUtil.connectSQL(
                    ServiceManager.getService(ConfigService.class).get("sql-host"),
                    ServiceManager.getService(ConfigService.class).get("sql-database"),
                    ServiceManager.getService(ConfigService.class).get("sql-user"),
                    ServiceManager.getService(ConfigService.class).get("sql-password"),
                    GenderUser.class);
            return;
        }

        Path databaseFilePath = Paths.get("plugins/genders/database.sqlite");
        if(!Files.exists(databaseFilePath) && !databaseFilePath.toFile().createNewFile()) {
            return;
        }

        connectionSource = ConnectionSourceUtil.connectNoSQLDatabase(
                databaseFilePath.toString(), GenderUser.class);
    }
}
