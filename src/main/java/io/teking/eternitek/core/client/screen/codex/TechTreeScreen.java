package io.teking.eternitek.core.client.screen.codex;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.client.screen.codex.widget.NodeWidget;
import io.teking.eternitek.core.resource.TechTreeReloadListener;
import io.teking.eternitek.core.util.render.RenderHelper;
import io.teking.eternitek.core.util.techtree.TechNode;
import io.teking.eternitek.core.util.techtree.TechTree;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TechTreeScreen extends CodexScreen {

    private final TechTree tree;

    public TechTreeScreen(Identifier treeId) {

        super();

        this.tree = TechTreeReloadListener.TREES.get(treeId);
        if(tree == null) setError(Text.translatable("texts.eternitek.no_tree", treeId));

    }

    @Override
    protected void init() {

        super.init();

        for(TechNode node : this.tree.nodes()) {
            int x = (this.width / 2) - 14 + node.x();
            int y = (this.height / 2) - 14 + (-1 * node.y());
            this.addDrawableChild(new NodeWidget(x, y, node));
        }

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);

        RenderHelper helper = new RenderHelper(context);

        for(TechNode node : this.tree.nodes()) {
            if(node.connections().contains(EternitekCore.id("root"))) continue;
            for(Identifier connect : node.connections()) {
                if(!this.tree.getNodeMap().containsKey(connect)) continue;
                helper.drawConnectingLine(node, this.tree.getNodeMap().get(connect), 2, 0xFF27374D);
            }
        }

        for(Drawable drawable : this.drawables) {
            drawable.render(context, mouseX, mouseY, delta);
        }

    }

}
