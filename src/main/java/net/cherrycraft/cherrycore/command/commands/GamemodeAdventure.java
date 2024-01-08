/*
 * (c) 2024 CherryCraft. All rights reserved.
 *
 *  This software is the confidential and proprietary information of CherryCraft
 *  ("Confidential Information"). You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you
 *  entered into with CherryCraft.
 *
 *  UNAUTHORIZED COPYING, DISTRIBUTION, OR REPRODUCTION OF THIS SOFTWARE, IN WHOLE OR
 *  IN PART, IS STRICTLY PROHIBITED. UNLESS OTHERWISE EXPRESSLY AGREED UPON IN A
 *  WRITTEN AGREEMENT, CHERRYCRAFT PROVIDES THIS SOFTWARE "AS IS" WITHOUT WARRANTY OF ANY
 *  KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 *  NON-INFRINGEMENT.
 *
 *  For inquiries, please contact CherryCraft at copyright@cherrycraft.net.
 */

package net.cherrycraft.cherrycore.command.commands;

import net.cherrycraft.cherrycore.manager.Command;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.cherrycraft.cherrycore.chatsystem.utils.PlayerValidator.isPlayerNameValid;

public class GamemodeAdventure extends Command {

    public GamemodeAdventure(String commandName) {
        super("gma");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player targetPlayer = null;
        if (args.length > 1) {
            if (isPlayerNameValid(args[1])) {
                targetPlayer = Bukkit.getPlayer(args[1]);
            } else {
                sender.sendMessage("<red>The player name is not valid!");
                return false;
            }
        }
        Player player = targetPlayer != null ? targetPlayer : (Player) sender;

        player.setGameMode(GameMode.ADVENTURE);
        return true;
    }

    @Override
    public String getPermission() {
        return "gamemode";
    }

    @Override
    public String getPermissionMessage() {
        return "You don't have permission to use this command!";
    }
}
