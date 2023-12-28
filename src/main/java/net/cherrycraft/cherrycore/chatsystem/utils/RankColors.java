package net.cherrycraft.cherrycore.chatsystem.utils;

public enum RankColors {
    OWNER("<gradient:#fabf37:#fff024:0>", 1),
    MANAGER("<gradient:#ff8624:#ff2479:1>", 2),
    DEVELOPER("<#e3ff24>", 3),
    GAME_MASTER("<#e3ff24>", 4),
    ADMIN("<#c624ff>", 5),
    MODERATOR("<#24a8ff>", 6),
    TRAINEE("<#7bc9fc>", 7),
    DESIGNER("<gradient:#ee7bfc:#7bb7fc:0>", 8),
    BUILDER("<#10c4b7>", 9),
    YOUTUBE("<#ea4444>", 10),
    CHERRY_PLATINUM_LIFETIME("<gradient:#a0b2c6:#abcdf3:0>", 11),
    CHERRY_PLATINUM("<#a0b2c6>", 12),
    CHERRY_GOLD_LIFETIME("<gradient:#f3dfab:#f8c373:1>", 13),
    CHERRY_GOLD("<#f3dfab>", 14),
    CHERRY_LIFETIME("<gradient:#ae0a0a:#f32424:1>", 15),
    CHERRY("<#f32424>", 16),
    DEFAULT("<gray>", 17);

    private final String color;
    private final int priority;

    RankColors(String color, int priority) {
        this.color = color;
        this.priority = priority;
    }

    public static int compareRanks(RankColors rank1, RankColors rank2) {
        return Integer.compare(rank1.getPriority(), rank2.getPriority());
    }

    public String getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }
}