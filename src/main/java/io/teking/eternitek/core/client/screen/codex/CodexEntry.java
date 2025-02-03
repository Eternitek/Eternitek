package io.teking.eternitek.core.client.screen.codex;

import io.teking.eternitek.core.EternitekCore;
import net.minecraft.util.Identifier;

public class CodexEntry {

    private final String name;
    private final Identifier identifier;

    public CodexEntry(String name) {
        this.name = name;
        this.identifier = EternitekCore.id(name);
    }

}
