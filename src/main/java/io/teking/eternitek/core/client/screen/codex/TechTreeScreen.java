package io.teking.eternitek.core.client.screen.codex;

import io.teking.eternitek.core.client.screen.codex.widget.NodeWidget;
import io.teking.eternitek.core.resource.TechTreeReloadListener;
import io.teking.eternitek.core.util.techtree.TechTree;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TechTreeScreen extends CodexScreen {

    private final TechTree tree;

    public TechTreeScreen(Identifier treeId) {

        super();

        this.tree = TechTreeReloadListener.TREES.get(treeId);
        if(tree == null) {
            setError(Text.translatable("texts.eternitek.no_tree", treeId));
            return;
        }

        this.addDrawableChild(
                new NodeWidget(this.tree.nodes().getFirst())
        );

    }

}
