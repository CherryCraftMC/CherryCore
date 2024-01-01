package net.cherrycraft.cherrycore.chatsystem.utils;

import org.bukkit.Bukkit;

public class PlayerValidator {
    public static boolean isPlayerNameValid(String playerName) {
        return Bukkit.getPlayer(playerName) != null;
    }
}
