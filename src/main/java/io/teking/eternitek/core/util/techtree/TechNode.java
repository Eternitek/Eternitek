package io.teking.eternitek.core.util.techtree;

import net.minecraft.util.Identifier;

public class TechNode {
    private final Identifier id;
    private final Identifier icon32;
    private final Identifier icon16;
    private final int x;
    private final int y;
    private final String quest; // New field for quest identifier

    public TechNode(Identifier id, Identifier icon32, Identifier icon16, int x, int y, String quest) {
        this.id = id;
        this.icon32 = icon32;
        this.icon16 = icon16;
        this.x = x;
        this.y = y;
        this.quest = quest;
    }

    public Identifier getIcon32() {
        return icon32;
    }

    public Identifier getIcon16() {
        return icon16;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getQuest() {
        return quest;
    }
}