package net.cherrycraft.cherrycore;

import net.cherrycraft.cherrycore.chatsystem.ChatHistoryLogger;
import net.cherrycraft.cherrycore.chatsystem.ChatSystem;
import net.cherrycraft.cherrycore.manager.LanguageManager;
import net.cherrycraft.cherrycore.scoreboard.Scoreboard;
import net.cherrycraft.cherrycore.tablist.Tablist;
import org.bukkit.event.Listener;

public class Loader implements Listener {

    private final CherryCore plugin;

    public Loader(CherryCore plugin) {
        this.plugin = plugin;

        registerListener(new ChatHistoryLogger(),
                new LanguageManager(),
                new ChatSystem(),
                new Scoreboard(),
                new Tablist());
    }

    public static void loadWorlds(CherryCore plugin) {
        // Load worlds here
        //WorldLoader.loadWorld("world");
        //WorldLoader.loadWorld("townymap");
    }

    private void registerListener(Listener... listeners) {
        for (Listener listener : listeners)
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
