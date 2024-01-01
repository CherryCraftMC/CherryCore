package net.cherrycraft.cherrycore.commands.gamemode.sepperateCommands;

import net.cherrycraft.cherrycore.manager.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.cherrycraft.cherrycore.chatsystem.utils.PlayerValidator.isPlayerNameValid;


public class GamemodeSpectator extends CommandManager {
    public GamemodeSpectator(String commandName) {
        super("gmsp");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("cherrycore.gamemode")) {
            sender.sendMessage("<red>You don't have permission to use this command!");
            return;
        }
        Player targetPlayer = null;
        if (args.length > 1) {
            if (isPlayerNameValid(args[1])) {
                targetPlayer = Bukkit.getPlayer(args[1]);
            } else {
                sender.sendMessage("<red>The player name is not valid!");
                return;
            }
        }
        Player player = targetPlayer != null ? targetPlayer : (Player) sender;
        player.setGameMode(GameMode.SPECTATOR);
    }
}
