package net.mercury.eternitek.core.codex.gui;

import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.gui.widget.NodeWidget;
import net.mercury.eternitek.core.codex.research.Node;
import net.mercury.eternitek.core.registry.EternitekRegistries;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Vector2i;

import java.util.List;

public class CodexScreen extends Screen {

    private final List<NodeWidget> nodes;

    protected int offX;
    protected int offY;

    public CodexScreen(Identifier tree) {

        super(Component.empty());
        this.offX = this.width / 2;
        this.offY = this.height / 2;

        this.nodes = EternitekRegistries.RESEARCH_TREE.get(tree)
                .orElseThrow()
                .value()
                .nodes()
                .stream()
                .map(NodeWidget::new)
                .toList();

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {

        for (NodeWidget node : this.nodes) {
            node.updateOffset(this.offX, this.offY);
            node.render(graphics, mouseX, mouseY, a);
        }

    }

}
