package io.teking.eternitek.core.machine;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tier {

    public static final List<Tier> TIERS = new ArrayList<>();

    public static final Tier T0 = addTier(new Tier("Crude",      "Heat Resistant Brick",   Identifier.of("textures/block/oak_planks.png"),      0));
    public static final Tier T1 = addTier(new Tier("Makeshift",  "Brass",                  Identifier.of("textures/block/stone.png"),           1));
    public static final Tier T2 = addTier(new Tier("Industrial", "Steel",                  Identifier.of("textures/block/iron_block.png"),      2));
    public static final Tier T3 = addTier(new Tier("Advanced",   "Aluminum",               Identifier.of("textures/block/gold_block.png"),      3));
    public static final Tier T4 = addTier(new Tier("Refined",    "Stainless Steel",        Identifier.of("textures/block/diamond_block.png"),   4));
    public static final Tier T5 = addTier(new Tier("Reclaimed",  "Sci-fi BS",              Identifier.of("textures/block/emerald_block.png"),   5));
    public static final Tier T6 = addTier(new Tier("Reawakened", "Recharged Power Source", Identifier.of("textures/block/netherite_block.png"), 6));
    public static final Tier T7 = addTier(new Tier("Resonant",   "Sci-fi BS",              Identifier.of("textures/block/obsidian.png"),        7));
    public static final Tier T8 = addTier(new Tier("Evolved",    "Unknown",                Identifier.of("textures/block/bedrock.png"),         8));

    public String name;
    public String mainResource;
    public Identifier icon;
    public int power;

    public Tier(String name, String mainResource, Identifier icon, int power) {
        this.name = "T" + power + ": " + name;
        this.mainResource = mainResource;
        this.icon = icon;
        this.power = power;
    }

    public static Tier addTier(Tier tier) {
        TIERS.add(tier);
        return tier;
    }

}