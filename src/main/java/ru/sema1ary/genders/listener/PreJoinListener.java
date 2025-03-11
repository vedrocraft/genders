package ru.sema1ary.genders.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import ru.sema1ary.genders.model.Gender;
import ru.sema1ary.genders.model.GenderUser;
import ru.sema1ary.genders.service.GenderUserService;

@RequiredArgsConstructor
public class PreJoinListener implements Listener {
    private final GenderUserService userService;

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        String username = event.getName();

        if(username.isEmpty()) {
            return;
        }

        if(userService.findByUsername(username).isEmpty()) {
            userService.save(GenderUser.builder()
                    .username(username)
                    .gender(Gender.OTHER)
                    .build());
        }
    }
}
