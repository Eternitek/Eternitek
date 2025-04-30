package io.teking.eternitek.core.client.screen.codex;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.client.screen.codex.widget.NodeWidget;
import io.teking.eternitek.core.resource.TechTreeReloadListener;
import io.teking.eternitek.core.util.render.RenderHelper;
import io.teking.eternitek.core.util.techtree.TechNode;
import io.teking.eternitek.core.util.techtree.TechTree;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static io.teking.eternitek.core.resource.TechTreeReloadListener.TREES;

public class TechTreeScreen extends CodexScreen {

    private final TechTree tree;

    public TechTreeScreen(Identifier treeId) {

        super();

        this.tree = TechTreeReloadListener.TREES.get(treeId);
        if(tree == null) setError(Text.translatable("texts.eternitek.no_tree", treeId));

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);

        RenderHelper helper = new RenderHelper(context);
        for(TechNode node : this.tree.nodes()) {

            NodeWidget widget = new NodeWidget(node);

            if(node.parent().compareTo(EternitekCore.id("root")) != 0 && this.tree.getNodeMap().containsKey(node.parent())) {
                helper.drawConnectingLine(node, this.tree.getNodeMap().get(node.parent()), 2, 0xFF526D82);
            }

            widget.render(context, mouseX, mouseY, delta);

        }

    }

}
