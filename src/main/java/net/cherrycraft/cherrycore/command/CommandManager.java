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

package net.cherrycraft.cherrycore.command;

import net.cherrycraft.cherrycore.CherryCore;
import net.cherrycraft.cherrycore.command.commands.*;
import net.cherrycraft.cherrycore.languageSystem.command.LanguageCommand;
import net.cherrycraft.cherrycore.manager.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class CommandManager {

    private final CherryCore plugin;

    private final Set<Command> commands = new HashSet<>();

    public CommandManager(CherryCore plugin) {
        this.plugin = plugin;

        registerCommand(new LanguageCommand("language"));
        registerCommand(new GMCommands("gm"));
        registerCommand(new GamemodeCreative("gmc"));
        registerCommand(new GamemodeSurvival("gms"));
        registerCommand(new GamemodeAdventure("gma"));
        registerCommand(new GamemodeSpectator("gmsp"));
    }

    private void registerCommand(Command command) {
        PluginCommand pluginCommand = plugin.getCommand(command.getCommandName());
        if (pluginCommand != null) {
            pluginCommand.setExecutor(plugin.getCommandListener());
            commands.add(command);
            ((Plugin) plugin).getLogger().info("Command '" + command.getCommandName() + "' has been registered.");
        } else {
            plugin.getLogger().warning("Failed to register command: " + command.getCommandName());
        }
    }

    public boolean runCommand(CommandSender sender, String label, String[] args) {
        System.out.println("Attempting to run command: " + label);
        for (Command command : commands) {
            System.out.println(command.getCommandName() + "=" + label);
            if (command.getCommandName().equalsIgnoreCase(label)) {
                String permission = command.getPermission();
                if (permission != null && !sender.hasPermission("cherrycore." + permission)) {
                    sender.sendMessage("<red>" + command.getPermissionMessage());
                    return false;
                }
                return command.execute(sender, args);
            }
        }
        return false;
    }
}
