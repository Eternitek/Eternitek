package io.teking.eternitek.core.util.fun;

import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public class Facts {

    private static final List<String> FACTS = new ArrayList<>();

    public static void register() {

        add("A.E.S.I.R. suits can manually detonate their onboard fusion reactors");
        add("A.E.S.I.R. Armor is powered by a series of Fimbulwinter-class cold fusion batteries");
        add("there are five levels of Eternitek keycards");
        add("ICARUS is the largest satellite ever put in low Earth orbit");

    }

    public static String getRandom() {
        return "Did you know that " + FACTS.get(Random.create().nextInt(FACTS.size())) + "?";
    }

    private static void add(String fact) {
        FACTS.add(fact);
    }

}
