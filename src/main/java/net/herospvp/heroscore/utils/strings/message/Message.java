package net.herospvp.heroscore.utils.strings.message;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {

    public static void sendMessage(CommandSender sender, MessageType type, boolean point, String prefix, String message, String... args) {
        for (int i=0; i<args.length; i++) {
            message = message.replace("{"+i+"}",args[i]);
        }

        sender.sendMessage(
                ChatColor.GOLD + "(( "
                        + type.getColor() + ChatColor.BOLD
                        + ChatColor.ITALIC + prefix + ChatColor.GOLD + " )) "
                        + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', point ? addPoint(message) : message));
    }

    public static void sendMessage(CommandSender sender, String message, boolean point) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', point ? addPoint(message) : message));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sendMessage(sender, message, true);
    }

    public static void sendMessage(CommandSender sender, MessageType type, String prefix, String message, String... args) {
        sendMessage(sender, type, true, prefix, message, args);
    }

    public static void sendMessage(CommandSender sender, MessageType type, String prefix, String message) {
        sendMessage(sender, type, true, prefix, message, "");
    }

    private static String addPoint(String message) {
        if (!(message.endsWith(".") || message.endsWith("!") || message.endsWith("?"))) {
            message = message.concat(ChatColor.RESET + ".");
        }
        return message;
    }
}
