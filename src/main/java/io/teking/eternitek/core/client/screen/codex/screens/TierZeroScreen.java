package io.teking.eternitek.core.client.screen.codex.screens;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.util.techtree.TechNode;
import io.teking.eternitek.core.util.techtree.TechNodeData;
import io.teking.eternitek.core.util.techtree.TechTreeLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.teking.eternitek.core.client.screen.codex.screens.CodexScreen.BOOK_ICON;

public class TierZeroScreen extends BaseTierScreen {
    private Map<String, TechNode> nodes = new HashMap<>();
    private Map<String, TechNodeData> techTreeData;
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
        loadTechTree();
    }

    private void loadTechTree() {
        try {
            ResourceManager manager = this.client.getResourceManager();
            Map<Identifier, Resource> resources = manager.findResources("techtree", path -> path.getPath().endsWith(".json"));

            if (resources.isEmpty()) {
                errorMessage = "No tech tree JSON files found in 'techtree' directory.";
                return;
            }

            Identifier id = resources.keySet().iterator().next();
            Optional<Resource> resource = manager.getResource(id);

            if (resource.isPresent()) {
                try (InputStream inputStream = resource.get().getInputStream()) {
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    techTreeData = new Gson().fromJson(reader, new TypeToken<Map<String, TechNodeData>>(){}.getType());

                    if (techTreeData != null) {
                        for (Map.Entry<String, TechNodeData> entry : techTreeData.entrySet()) {
                            String key = entry.getKey();
                            TechNodeData data = entry.getValue();

                            Identifier nodeId = EternitekCore.id(key);
                            Identifier icon = Identifier.of(data.icon);

                            TechNode node = new TechNode(
                                    nodeId,
                                    icon,
                                    icon, // Using same texture for icon16 for now
                                    data.position.x,
                                    data.position.y,
                                    data.quest // Pass the quest identifier
                            );

                            nodes.put(key, node);
                        }
                    }
                } catch (IOException e) {
                    errorMessage = "Failed to read tech tree file: " + e.getMessage();
                }
            } else {
                errorMessage = "Tech tree file not found: " + id;
            }
        } catch (Exception e) {
            errorMessage = "Failed to load tech tree: " + e.getMessage();
        }
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
        System.out.println("Mouse clicked at: (" + mouseX + ", " + mouseY + "), Button: " + button);
        if (button == 0) { // Left-click
            int centerX = bookRenderX + (mainPageWidth / 2);
            int centerY = bookRenderY + (bookHeight / 2);
            System.out.println("Center: (" + centerX + ", " + centerY + "), Offset: (" + offsetX + ", " + offsetY + ")");

            for (Map.Entry<String, TechNode> entry : nodes.entrySet()) {
                TechNode node = entry.getValue();
                int nodeX = centerX + node.getX() + offsetX;
                int nodeY = centerY + node.getY() + offsetY;
                int size = 32; // Assuming all nodes are 32x32 based on your JSON
                System.out.println("Checking node: " + entry.getKey() + " at (" + nodeX + ", " + nodeY + "), Size: " + size);

                // Check if the click is within the node's bounds
                if (mouseX >= nodeX && mouseX <= nodeX + size &&
                        mouseY >= nodeY && mouseY <= nodeY + size) {
                    System.out.println("Node clicked: " + entry.getKey());
                    String questId = node.getQuest();
                    System.out.println("Quest ID: " + questId);
                    if (questId != null && !questId.isEmpty()) {
                        System.out.println("Opening quest screen for: " + questId);
                        openQuestScreen(questId); // Open the quest screen
                        return true; // Consume the click event
                    } else {
                        System.out.println("No quest associated with this node.");
                    }
                }
            }
        }
        System.out.println("Click not handled by nodes, passing to super.");
        return super.mouseClicked(mouseX, mouseY, button); // Pass to parent if not handled
    }

    @Override
    protected void renderTechTree(DrawContext context, int mouseX, int mouseY, float delta) {
        int lineColor = 0xFF0000FF; // ARGB format (alpha, red, green, blue)

        int centerX = bookRenderX + (mainPageWidth / 2);
        int centerY = bookRenderY + (bookHeight / 2);

        for (Map.Entry<String, TechNode> entry : nodes.entrySet()) {
            TechNode node = entry.getValue();
            String parentKey = techTreeData.get(entry.getKey()).parent_connection;
            if (!parentKey.equals("root") && nodes.containsKey(parentKey)) {
                TechNode parentNode = nodes.get(parentKey);
                drawLineBetweenNodes(context, parentNode, node, centerX, centerY, lineColor);
            }
        }

        for (Map.Entry<String, TechNode> entry : nodes.entrySet()) {
            TechNode node = entry.getValue();
            drawNode(context, node, centerX, centerY);
        }
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
        public static void drawConnectingLine(DrawContext context, int startX, int startY, int endX, int endY, int width, int color) {
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

    private void openQuestScreen(String questId) {
        MinecraftClient.getInstance().setScreen(new QuestScreen(this, questId));
    }
}