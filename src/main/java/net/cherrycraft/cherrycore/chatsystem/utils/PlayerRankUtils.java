package net.cherrycraft.cherrycore.chatsystem.utils;

import net.luckperms.api.LuckPermsProvider;

public class PlayerRankUtils {

    public static String getPlayerRank(String playerName) {
        Boolean player = PlayerValidator.isPlayerNameValid(playerName) ? PlayerValidator.isPlayerNameValid(playerName) : null;
        assert player != null;
        if (!player) {
            LuckPermsProvider.get().getUserManager().getUser(playerName).getPrimaryGroup();
            return "success";
        }
        return "error";
    }

    public static String setPlayerRank(String playerName, String rank) {
        Boolean player = PlayerValidator.isPlayerNameValid(playerName) ? PlayerValidator.isPlayerNameValid(playerName) : null;
        assert player != null;
        if (!player) {
            if (LuckPermsProvider.get().getGroupManager().getGroup(rank) != null) {
                return "error";
            }
            LuckPermsProvider.get().getUserManager().getUser(playerName).setPrimaryGroup(rank);
            return "success";
        }
        return "error";
    }


}
