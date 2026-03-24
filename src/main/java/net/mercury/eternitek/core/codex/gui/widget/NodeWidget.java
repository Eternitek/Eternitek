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
                node.position().x - 11,
                node.position().y - 11,
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

        graphics.fill(
                x,
                y,
                x + this.width,
                y + this.height,
                Color.WHITE.getRGB()
        );

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                this.node.icon(),
                x + 3, y + 3,
                0, 0,
                16, 16,
                16, 16
        );

        if (isMouseOver(mouseX, mouseY)) {

            graphics.outline(
                    x - 2,
                    y - 2,
                    this.width + 4,
                    this.height + 4,
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
        return (x <= mouseX && mouseX <= (x + this.width)) &&
                (y <= mouseY && mouseY <= (y + this.height));
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
