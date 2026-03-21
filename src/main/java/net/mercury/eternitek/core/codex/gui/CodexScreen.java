package net.mercury.eternitek.core.codex.gui;

import com.mojang.blaze3d.platform.Window;
import net.mercury.eternitek.core.codex.gui.widget.NodeWidget;
import net.mercury.eternitek.core.codex.research.Tree;
import net.mercury.eternitek.core.registry.EternitekRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.List;

public class CodexScreen extends Screen {

    private final List<NodeWidget> nodes;

    protected int offX;
    protected int offY;

    public CodexScreen(Identifier tree) {

        super(Component.empty());
        Window window = Minecraft.getInstance().getWindow();
        this.offX = window.getGuiScaledWidth() / 2;
        this.offY = window.getGuiScaledHeight() / 2;

        this.nodes = EternitekRegistries.RESEARCH.getOrDefault(tree, new Tree(tree.toString(), tree, List.of()))
                .nodes()
                .stream()
                .map(NodeWidget::new)
                .toList();

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        graphics.fill(0, 0, this.width, this.height, 0xAA000000);

        for (NodeWidget node : this.nodes) {
            node.updateOffset(this.offX, this.offY);
            node.extractRenderState(graphics, mouseX, mouseY, a);
        }

        for (NodeWidget node : this.nodes) {
            node.extractTooltipRenderState(graphics, mouseX, mouseY, a);
        }

    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {

        if (event.button() != 0) return super.mouseDragged(event, dx, dy);

        this.offX += (int) dx;
        this.offY += (int) dy;

        return super.mouseDragged(event, dx, dy);

    }

}
