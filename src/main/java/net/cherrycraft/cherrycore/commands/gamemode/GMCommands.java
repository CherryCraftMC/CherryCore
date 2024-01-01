package net.cherrycraft.cherrycore.commands.gamemode;

import net.cherrycraft.cherrycore.manager.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.cherrycraft.cherrycore.chatsystem.utils.PlayerValidator.isPlayerNameValid;


public class GMCommands extends CommandManager {
    public GMCommands(String commandName) {
        super("gm");
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
        switch (args[0]) {
            case "0":
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case "1":
                player.setGameMode(GameMode.CREATIVE);
                break;
            case "2":
                player.setGameMode(GameMode.ADVENTURE);
                break;
            case "c":
                player.setGameMode(GameMode.CREATIVE);
                break;
            case "s":
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case "a":
                player.setGameMode(GameMode.ADVENTURE);
                break;
            case "3":
                player.setGameMode(GameMode.SPECTATOR);
                break;
            case "sp":
                player.setGameMode(GameMode.SPECTATOR);
                break;
        }
    }
}