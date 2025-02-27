package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.util.techtree.TechNode;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static io.teking.eternitek.core.client.screen.codex.screens.CodexScreen.BOOK_ICON;

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
    //protected void renderTechTree(DrawContext context, int mouseX, int mouseY, float delta) {
    //    TechNode testNode = new TechNode(
    //            Identifier.of("eternitek", "test_node"),
    //            Identifier.of("eternitek", "textures/item/codex.png"),
    //            Identifier.of("eternitek", "textures/item/codex.png"),
    //            0, 100 // These coordinates are now relative to the book's top-left corner
    //    );
//
    //    // Render the 32x32 icon
    //    context.drawTexture(testNode.getIcon32(), testNode.getX(), testNode.getY(), 0, 0, 32, 32, 16, 16);
//
    //    // Render the 16x16 icon
    //    context.drawTexture(testNode.getIcon16(), testNode.getX() + 40, testNode.getY(), 0, 0, 16, 16, 16, 16);
    //}
    protected void renderTechTree(DrawContext context, int mouseX, int mouseY, float delta) {
        int centerX = 0; // Center X coordinate
        int centerY = 0; // Center Y coordinate

        for (int x = -200; x <= 200; x += 50) {
            for (int y = -200; y <= 200; y += 50) {
                context.drawTexture(BOOK_ICON, centerX + x, centerY + y, 0, 0, 32, 32, 32, 32);
                context.drawText(textRenderer, (centerX + x) + "," + (centerY + y), centerX + x, centerY + y + 32, 0xFFFFFF, true);
            }
        }
    }


}
