package net.mercury.eternitek.core.codex.gui;

import com.mojang.blaze3d.platform.Window;
import net.mercury.eternitek.core.codex.gui.widget.NodeWidget;
import net.mercury.eternitek.core.codex.research.Node;
import net.mercury.eternitek.core.codex.research.Tree;
import net.mercury.eternitek.core.registry.EternitekRegistries;
import net.mercury.eternitek.core.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2i;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CodexScreen extends Screen {

    private final Tree tree;
    private final List<NodeWidget> nodes;

    protected int offX;
    protected int offY;

    public CodexScreen(Identifier id) {

        super(Component.empty());
        Window window = Minecraft.getInstance().getWindow();
        this.offX = window.getGuiScaledWidth() / 2;
        this.offY = window.getGuiScaledHeight() / 2;

        this.tree = EternitekRegistries.RESEARCH.getOrDefault(id, new Tree(id.toString(), id, Map.of()));
        this.nodes = this.tree
                .nodes()
                .values()
                .stream()
                .map(NodeWidget::new)
                .toList();

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        graphics.fill(0, 0, this.width, this.height, 0xAA000000);

        Matrix3x2fStack matrices = graphics.pose();

        matrices.pushMatrix();

        matrices.translate(this.offX, this.offY);

        for (NodeWidget widget : this.nodes) {

            for (Identifier id : widget.children()) {

                if (!this.tree.nodes().containsKey(id)) continue;

                Node parent = widget.node();
                Node child = this.tree.nodes().get(id);

                Vector2i start = parent.position();
                Vector2i end = child.position();

                RenderHelper.pixelLine(
                        graphics,
                        start.x(),
                        start.y(),
                        end.x(),
                        end.y(),
                        0xFFFFFFFF
                );

            }

        }

        matrices.popMatrix();

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
