package net.mercury.eternitek.core.codex.gui.widget;

import net.mercury.eternitek.core.codex.research.Node;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.stream.Collectors;

public class NodeWidget extends AbstractWidget {

    private final Node node;

    public NodeWidget(Node node) {
        super(
                node.position().x,
                node.position().y,
                22,
                22,
                node.tooltip().getFirst()
        );
        this.node = node;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {

        int x = this.getX();
        int y = this.getY();

        int size = this.isHovered ? 24 : 22;
        int offset = size / 2;
        graphics.fill(
                x - offset,
                y - offset,
                x + offset,
                y + offset,
                Color.WHITE.getRGB()
        );

        if (this.isHovered) {

            graphics.renderOutline(
                    x - 14,
                    y - 14,
                    28,
                    28,
                    Color.WHITE.getRGB()
            );

            graphics.renderTooltip(
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

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                this.node.icon(),
                x - 8, y - 8,
                0, 0,
                16, 16,
                16, 16
        );

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {

    }

}
