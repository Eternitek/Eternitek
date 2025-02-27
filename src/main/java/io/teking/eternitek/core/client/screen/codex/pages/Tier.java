package io.teking.eternitek.core.client.screen.codex.pages;

import net.minecraft.util.Identifier;

public class Tier {
    public static String name;
    public static String mainResource;
    public static Identifier icon;

    public Tier(String name, String mainResource, Identifier icon) {
        this.name = name;
        this.mainResource = mainResource;
        this.icon = icon;
    }
}