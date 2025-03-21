package ru.sema1ary.genders.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.sema1ary.genders.model.Gender;
import ru.sema1ary.genders.model.GenderUser;
import ru.sema1ary.genders.service.GenderUserService;
import ru.sema1ary.vedrocraftapi.player.PlayerUtil;
import ru.sema1ary.vedrocraftapi.service.ConfigService;

@RequiredArgsConstructor
@Command(name = "genders", aliases = {"gender"})
public class GendersCommand {
    private final ConfigService configService;
    private final GenderUserService userService;

    @Async
    @Execute(name = "reload")
    @Permission("genders.reload")
    void reload(@Context CommandSender sender) {
        configService.reload();

        PlayerUtil.sendMessage(sender, (String) configService.get("reload-message"));
    }

    @Async
    @Execute
    void execute(@Context Player sender, @Arg("гендер") Gender gender) {
        GenderUser user = userService.getUser(sender.getName());

        if(user.getGender().equals(gender)) {
            PlayerUtil.sendMessage(sender, (String) configService.get("gender-already-selected-message"));
            return;
        }

        user.setGender(gender);
        userService.save(user);

        PlayerUtil.sendMessage(sender, (String) configService.get("gender-successful-change-message"));
    }
}
