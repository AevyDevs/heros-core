package net.herospvp.heroscore.utils.strings.message;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public enum MessageType {
    INFO(ChatColor.YELLOW),
    WARNING(ChatColor.GREEN),
    ERROR(ChatColor.RED),
    SUPER_ERROR(ChatColor.DARK_RED);

    private final ChatColor color;

    MessageType(ChatColor color) {
        this.color = color;
    }
}
