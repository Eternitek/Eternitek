package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.util.techtree.TechNode;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TierZeroScreen extends BaseTierScreen {
    public TierZeroScreen(CodexScreen parentScreen) {
        super(Text.of("Tier 0: Crude"), parentScreen);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Add our tier-specific render code here
    }

    @Override
    protected void renderTechTree(DrawContext context, int mouseX, int mouseY, float delta) {
        TechNode testNode = new TechNode(
                Identifier.of("eternitek", "test_node"),
                Identifier.of("eternitek", "textures/item/codex.png"),
                Identifier.of("eternitek", "textures/item/codex.png"),
                150, 150 // x and y coordinates
        );

        // Render the 32x32 icon
        context.drawTexture(testNode.getIcon32(), testNode.getX(), testNode.getY(), 0, 0, 32, 32, 32, 32);

        // Render the 16x16 icon
        context.drawTexture(testNode.getIcon16(), testNode.getX() + 40, testNode.getY(), 0, 0, 16, 16, 16, 16);
    }

}
