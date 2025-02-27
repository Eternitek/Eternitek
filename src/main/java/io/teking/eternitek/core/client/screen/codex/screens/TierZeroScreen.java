package io.teking.eternitek.core.client.screen.codex.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class TierZeroScreen extends BaseTierScreen {
    public TierZeroScreen(CodexScreen parentScreen) {
        super(Text.of("Tier 0: Crude"), parentScreen);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Add our tier-specific render code here
    }
}
