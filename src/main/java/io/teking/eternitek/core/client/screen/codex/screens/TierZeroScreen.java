package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.util.techtree.TechNode;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

import static io.teking.eternitek.core.client.screen.codex.screens.CodexScreen.BOOK_ICON;

public class TierZeroScreen extends BaseTierScreen {
    private final TechNode node1;
    private final TechNode node2;

    public TierZeroScreen(CodexScreen parentScreen) {
        super(Text.of("Tier 0: Crude"), parentScreen);

        node1 = new TechNode(
                Identifier.of("eternitek", "node1"),
                Identifier.of("minecraft", "textures/item/apple.png"), // Replace with your icon
                Identifier.of("minecraft", "textures/item/apple.png"),
                -50,   // x, relative to the center
                0      // y, relative to center
        );

        node2 = new TechNode(
                Identifier.of("eternitek", "node2"),
                Identifier.of("minecraft", "textures/item/baked_potato.png"), // Replace with your icon
                Identifier.of("minecraft", "textures/item/baked_potato.png"),
                50,  // x, relative to center
                0    // y, relative to center
        );

        // Center the view initially
        this.offsetX = -mainPageWidth / 2 + 45;
        this.offsetY = -bookHeight / 2 + 45;
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Add our tier-specific render code here
    }

    @Override
    protected void renderTechTree(DrawContext context, int mouseX, int mouseY, float delta) {

        // Line color (blue)
        int lineColor = 0xFF0000FF; // ARGB format (alpha, red, green, blue)

        //Get the screen position.
        int bookScreenX = bookRenderX;
        int bookScreenY = bookRenderY;

        // The middle of the book is the origin for the tech tree.
        int treeOriginX = bookScreenX + (mainPageWidth / 2);
        int treeOriginY = bookScreenY + (bookHeight / 2);

        // Draw the "link" (a line) between the nodes
        drawLineBetweenNodes(context, node1, node2, treeOriginX, treeOriginY, lineColor);

        // Draw the nodes.
        drawNode(context, node1, treeOriginX, treeOriginY);
        drawNode(context, node2, treeOriginX, treeOriginY);
    }

    private void drawNode(DrawContext context, TechNode node, int centerX, int centerY) {
        int nodeX = centerX + node.getX() + offsetX;
        int nodeY = centerY + node.getY() + offsetY;

        // Draw the 32x32 icon
        context.drawTexture(node.getIcon32(), nodeX, nodeY, 0, 0, 32, 32, 32, 32);
    }

    private void drawLineBetweenNodes(DrawContext context, TechNode node1, TechNode node2, int centerX, int centerY, int lineColor) {
        // Calculate the line's start and end points
        int startX = centerX + node1.getX() + offsetX + 16; // Add 16 to center the line on the node
        int startY = centerY + node1.getY() + offsetY + 16;

        int endX = centerX + node2.getX() + offsetX + 16;
        int endY = centerY + node2.getY() + offsetY + 16;

        GuiGraphicsHelper.drawConnectingLine(context, startX, startY, endX, endY, 3, lineColor);
    }


    public static class GuiGraphicsHelper {

        public static void drawConnectingLine(DrawContext context, int startX, int startY, int endX, int endY, int width, int color){
            float angle = (float) Math.atan2(endY - startY, endX - startX);
            float lineLength = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
            // Save the transformation matrix
            context.getMatrices().push();
            // Perform the transformations
            context.getMatrices().translate(startX, startY, 0);
            context.getMatrices().multiply(new Quaternionf().rotationZ(angle));

            // Draw the rectangle
            context.fill(0, -width / 2, (int) lineLength, width / 2, color);

            // Restore the transformation matrix
            context.getMatrices().pop();
        }
    }
}
