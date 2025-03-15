package io.teking.eternitek.core.client.screen.codex.screens;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.resource.TechTreeReloadListener;
import io.teking.eternitek.core.util.render.RenderHelper;
import io.teking.eternitek.core.util.techtree.TechNode;
import io.teking.eternitek.core.util.techtree.TechTree;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;
import org.joml.Vector2i;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static io.teking.eternitek.core.client.screen.codex.screens.CodexScreen.BOOK_ICON;

public class TierZeroScreen extends BaseTierScreen {

    private TechTree techTree;
    private String errorMessage = null;

    public TierZeroScreen(CodexScreen parentScreen) {
        super(Text.of("Tier 0: Crude"), parentScreen);

        // Center the view initially
        this.offsetX = -mainPageWidth / 2 + 45 + 200;
        this.offsetY = -bookHeight / 2 + 45 + 138;
    }

    @Override
    protected void init() {
        super.init();
        this.techTree = TechTreeReloadListener.TREES.get(EternitekCore.id("tier_0"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Add our tier-specific render code here
        if (errorMessage != null) {
            int x = 5;
            int y = this.height - 15;
            context.drawText(this.textRenderer, errorMessage, x, y, 0xFF0000, true);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        EternitekCore.LOGGER.info("Mouse clicked at: ({}, {}), button: {}", mouseX, mouseY, button);
        if (button == 0) { // Left-click
            int centerX = bookRenderX + (mainPageWidth / 2);
            int centerY = bookRenderY + (bookHeight / 2);
            EternitekCore.LOGGER.info("Center: ({}, {}), offset: ({}, {})", centerX, centerY, offsetX, offsetY);

            for (Map.Entry<Identifier, TechNode> entry : techTree.getNodeMap().entrySet()) {
                Identifier nodeId = entry.getKey();
                TechNode node = entry.getValue();
                int nodeX = centerX + node.getPosition().x() + offsetX;
                int nodeY = centerY + node.getPosition().y() + offsetY;
                int size = 32; // Assuming all nodes are 32x32 based on your JSON
                EternitekCore.LOGGER.info("Checking node: {} at ({}, {}), size: {}", nodeId, nodeX, nodeY, size);

                // Check if the click is within the node's bounds
                if (mouseX >= nodeX && mouseX <= nodeX + size &&
                        mouseY >= nodeY && mouseY <= nodeY + size) {
                    EternitekCore.LOGGER.info("Node clicked: {}", nodeId);
                    Identifier questId = node.getQuest();
                    EternitekCore.LOGGER.info("Quest ID: {}", questId);
                    if (questId != null) {
                        EternitekCore.LOGGER.info("Opening quest screen for: {}", questId);
                        openQuestScreen(questId); // Open the quest screen
                        return true; // Consume the click event
                    } else {
                        EternitekCore.LOGGER.info("No quest associated with this node.");
                    }
                }
            }
        }

        EternitekCore.LOGGER.info("Click not handled by nodes, passing to super.");
        return super.mouseClicked(mouseX, mouseY, button); // Pass to parent if not handled

    }

    @Override
    protected void renderTechTree(DrawContext context, int mouseX, int mouseY, float delta) {

        int lineColor = 0xFF0000FF; // ARGB format (alpha, red, green, blue)

        int centerX = bookRenderX + (mainPageWidth / 2);
        int centerY = bookRenderY + (bookHeight / 2);

        Map<Identifier, TechNode> nodes = techTree.getNodeMap();
        for(TechNode node : nodes.values()) {
            Identifier parentId = node.getParent();
            if (!parentId.equals(EternitekCore.id("root")) && nodes.containsKey(parentId)) {
                TechNode parent = nodes.get(parentId);
                drawLineBetweenNodes(context, parent, node, centerX, centerY, lineColor);
            }
        }

        for(TechNode node : nodes.values()) {
            drawNode(context, node, centerX, centerY);
        }

    }

    private void drawNode(DrawContext context, TechNode node, int centerX, int centerY) {

        Vector2i position = node.getPosition();

        int nodeX = centerX + position.x() + offsetX;
        int nodeY = centerY + position.y() + offsetY;

        // Draw the 32x32 icon
        context.drawTexture(node.getIcon(), nodeX, nodeY, 0, 0, 32, 32, 32, 32);

    }

    private void drawLineBetweenNodes(DrawContext context, TechNode node1, TechNode node2, int centerX, int centerY, int lineColor) {
        // Calculate the line's start and end points
        int startX = centerX + node1.getPosition().x() + offsetX + 16; // Add 16 to center the line on the node
        int startY = centerY + node1.getPosition().y() + offsetY + 16;

        int endX = centerX + node2.getPosition().x() + offsetX + 16;
        int endY = centerY + node2.getPosition().y() + offsetY + 16;

        RenderHelper.drawConnectingLine(context, startX, startY, endX, endY, 3, lineColor);
    }

    private void openQuestScreen(Identifier questId) {
        MinecraftClient.getInstance().setScreen(new QuestScreen(this, questId));
    }

}