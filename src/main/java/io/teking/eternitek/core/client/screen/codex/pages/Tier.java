package io.teking.eternitek.core.client.screen.codex.pages;

import net.minecraft.util.Identifier;

public class Tier {
    public String name;
    public String mainResource;
    public Identifier icon;

    public Tier(String name, String mainResource, Identifier icon) {
        this.name = name;
        this.mainResource = mainResource;
        this.icon = icon;
    }

}