package net.cherrycraft.cherrycore.manager;

import org.bukkit.command.CommandSender;

public abstract class Command {
    private final String commandName;
    private String usage;
    private String description;

    public Command(String commandName) {
        this.commandName = commandName;
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    public String getCommandName() {
        return commandName;
    }

    public abstract String getPermission();

    public abstract String getPermissionMessage();

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
