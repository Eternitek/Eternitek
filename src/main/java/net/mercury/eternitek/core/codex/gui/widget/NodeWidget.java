package net.mercury.eternitek.core.codex.gui.widget;

import net.mercury.eternitek.core.codex.research.Node;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class NodeWidget extends AbstractWidget {

    private final Node node;

    private int offX;
    private int offY;

    public NodeWidget(Node node) {
        super(
                node.position().x,
                node.position().y,
                22,
                22,
                node.tooltip().getFirst()
        );
        this.node = node;
        this.offX = 0;
        this.offY = 0;
    }

    public void updateOffset(int x, int y) {
        this.offX = x;
        this.offY = y;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        int x = getX() + this.offX;
        int y = getY() + this.offY;

        int size = isMouseOver(mouseX, mouseY) ? 24 : 22;
        int offset = size / 2;
        graphics.fill(
                x - offset,
                y - offset,
                x + offset,
                y + offset,
                Color.WHITE.getRGB()
        );

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                this.node.icon(),
                x - 8, y - 8,
                0, 0,
                16, 16,
                16, 16
        );

        if (isMouseOver(mouseX, mouseY)) {

            graphics.outline(
                    x - 14,
                    y - 14,
                    28,
                    28,
                    Color.WHITE.getRGB()
            );

        }

    }

    public void extractTooltipRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        if (!isMouseOver(mouseX, mouseY)) return;
        graphics.tooltip(
                Minecraft.getInstance().font,
                this.node.tooltip().stream()
                        .map(Component::getVisualOrderText)
                        .map(ClientTooltipComponent::create)
                        .collect(Collectors.toList()),
                mouseX, mouseY,
                DefaultTooltipPositioner.INSTANCE,
                null
        );
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        int x = getX() + this.offX;
        int y = getY() + this.offY;
        int offset = this.width / 2;
        return ((x - offset) <= mouseX && mouseX <= (x + offset)) &&
                ((y - offset) <= mouseY && mouseY <= (y + offset));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {

    }

    public Node node() {
        return this.node;
    }

    public Identifier id() {
        return this.node.id();
    }

    public List<Identifier> children() {
        return this.node.children();
    }

}
