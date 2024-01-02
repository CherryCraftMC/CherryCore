package net.cherrycraft.cherrycore.chatsystem.utils;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.checkerframework.checker.nullness.qual.Nullable;

public enum RankUtil {
    OWNER("<gradient:#fabf37:#fff024:0>", 1, "owner"),
    MANAGER("<gradient:#ff8624:#ff2479:1>", 2, "manager"),
    DEVELOPER("<#e3ff24>", 3, "developer"),
    GAME_MASTER("<#e3ff24>", 4, "game-master"),
    ADMIN("<#c624ff>", 5, "admin"),
    MODERATOR("<#24a8ff>", 6, "moderator"),
    TRAINEE("<#7bc9fc>", 7, "trainee"),
    DESIGNER("<gradient:#ee7bfc:#7bb7fc:0>", 8, "designer"),
    BUILDER("<#10c4b7>", 9, "builder"),
    YOUTUBE("<#ea4444>", 10, "youtube"),
    CHERRY_PLATINUM_LIFETIME("<gradient:#a0b2c6:#abcdf3:0>", 11, "cherry-platinum-lifetime"),
    CHERRY_PLATINUM("<#a0b2c6>", 12, "cherry-platinum"),
    CHERRY_GOLD_LIFETIME("<gradient:#f3dfab:#f8c373:1>", 13, "cherry-gold-lifetime"),
    CHERRY_GOLD("<#f3dfab>", 14, "cherry-gold"),
    CHERRY_LIFETIME("<gradient:#ae0a0a:#f32424:1>", 15, "cherry-lifetime"),
    CHERRY("<#f32424>", 16, "cherry"),
    DEFAULT("<gray>", 17, "default");

    private final String color;
    private final int priority;
    private final String groupName;

    RankUtil(String color, int priority, String groupName) {
        this.color = color;
        this.priority = priority;
        this.groupName = groupName;
    }

    public static int compareRanks(RankUtil rank1, RankUtil rank2) {
        return Integer.compare(rank1.getPriority(), rank2.getPriority());
    }

    public @Nullable Group getLPRank() {
        return LuckPermsProvider.get().getGroupManager().getGroup(this.groupName);
    }

    public String getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }
}