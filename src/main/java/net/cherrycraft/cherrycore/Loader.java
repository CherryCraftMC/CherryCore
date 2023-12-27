package net.cherrycraft.cherrycore;

import net.cherrycraft.cherrycore.chatsystem.ChatHistoryLogger;
import net.cherrycraft.cherrycore.chatsystem.ChatSystem;
import net.cherrycraft.cherrycore.languageSystem.command.LanguageCommand;
import net.cherrycraft.cherrycore.manager.CommandManager;
import net.cherrycraft.cherrycore.manager.LanguageManager;
import net.cherrycraft.cherrycore.scoreboard.Scoreboard;
import net.cherrycraft.cherrycore.tablist.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class Loader implements Listener {


    public static void registerCommands(CherryCore plugin) {
        registeredCommand(new LanguageCommand("language"), plugin);
    }


    private static void registeredCommand(CommandManager command, CherryCore plugin) {
        command.register(plugin);
        plugin.getLogger().info("Command '" + command.getCommandName() + "' has been registered.");
    }

    public static void registerListeners(CherryCore plugin) {
        // Register listeners here
        //Bukkit.getPluginManager().registerEvents(new ExampleListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new ChatHistoryLogger(), plugin);
        Bukkit.getPluginManager().registerEvents(new LanguageManager(), plugin);
        Bukkit.getPluginManager().registerEvents(new ChatSystem(), plugin);
        Bukkit.getPluginManager().registerEvents(new Scoreboard(), plugin);
        Bukkit.getPluginManager().registerEvents(new Scoreboard(), plugin);
        Bukkit.getPluginManager().registerEvents(new Tablist(), plugin);
    }

    public static void loadWorlds(CherryCore plugin) {
        // Load worlds here
        //WorldLoader.loadWorld("world");
        //WorldLoader.loadWorld("townymap");
    }
}
