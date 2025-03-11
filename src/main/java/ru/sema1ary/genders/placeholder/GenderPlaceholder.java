package ru.sema1ary.genders.placeholder;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import ru.sema1ary.genders.model.GenderUser;
import ru.sema1ary.genders.service.GenderUserService;

@RequiredArgsConstructor
public class GenderPlaceholder extends PlaceholderExpansion {
    private final GenderUserService userService;

    @Override
    public @NotNull String getIdentifier() {
        return "genders";
    }

    @Override
    public @NotNull String getAuthor() {
        return "sema1ary";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if(player.getName() == null || player.getName().isEmpty()) {
            return null;
        }

        GenderUser user = userService.getUser(player.getName());
        if(params.equalsIgnoreCase("gender")) {
            switch (user.getGender()) {
                default -> {
                    return "";
                }
                case MALE -> {
                    return "<#4169E1> ♂♀";
                }
                case FEMALE -> {
                    return "<#DA70D6> ♂";
                }
            }
        }

        return null; // Placeholder is unknown by the expansion
    }
}
