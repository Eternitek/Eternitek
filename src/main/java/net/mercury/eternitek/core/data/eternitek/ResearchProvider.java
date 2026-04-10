package net.mercury.eternitek.core.data.eternitek;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.mercury.eternitek.core.codex.research.Node;
import net.mercury.eternitek.core.codex.research.Tree;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Vector2i;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class ResearchProvider extends FabricCodecDataProvider<Tree> {

    private final Map<Identifier, Tree> toRegister;

    protected ResearchProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output,
                registries,
                PackOutput.Target.DATA_PACK,
                "research",
                Tree.CODEC
        );
        this.toRegister = new HashMap<>();
    }

    @Override
    protected void configure(BiConsumer<Identifier, Tree> provider, HolderLookup.Provider lookup) {
        TreeBuilder builder = new TreeBuilder(this);
        generateResearch(builder);
        for (Identifier id : toRegister.keySet()) {
            provider.accept(id, toRegister.get(id));
        }
    }

    protected void register(Identifier id, Tree tree) {
        this.toRegister.put(id, tree);
    }

    protected abstract void generateResearch(TreeBuilder builder);

    protected static class TreeBuilder {

        private String name;
        private Identifier id;
        private List<Node> nodes;

        private final ResearchProvider parent;

        public TreeBuilder(ResearchProvider parent) {
            this.nodes = new ArrayList<>();
            this.parent = parent;
        }

        public TreeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TreeBuilder id(Identifier id) {
            this.id = id;
            return this;
        }

        public TreeBuilder id(String id) {
            this.id = Identifier.parse(id);
            return this;
        }

        public NodeBuilder node() {
            return new NodeBuilder(this);
        }

        private void addNode(Node node) {
            this.nodes.add(node);
        }

        public void build() {
            this.parent.register(this.id, new Tree(name, id, Tree.nodeMap(nodes)));
            this.name = null;
            this.id = null;
            this.nodes = new ArrayList<>();
        }

    }

    protected static class NodeBuilder {

        private Identifier id;
        private List<Component> tooltip;
        private Identifier icon;
        private Vector2i position;
        private List<Identifier> children;
        private Optional<Identifier> quest;

        private final TreeBuilder parent;

        public NodeBuilder(TreeBuilder parent) {
            this.parent = parent;
            this.tooltip = new ArrayList<>();
            this.children = new ArrayList<>();
            this.quest = Optional.empty();
        }

        public NodeBuilder id(Identifier id) {
            this.id = id;
            return this;
        }

        public NodeBuilder id(String id) {
            this.id = Identifier.parse(id);
            return this;
        }

        public NodeBuilder tooltip(List<Component> tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public NodeBuilder tooltip(Component line) {
            this.tooltip.add(line);
            return this;
        }

        public NodeBuilder icon(Identifier icon) {
            this.icon = icon;
            return this;
        }

        public NodeBuilder icon(String icon) {
            this.icon = Identifier.parse(icon);
            return this;
        }

        public NodeBuilder position(Vector2i position) {
            this.position = position;
            return this;
        }

        public NodeBuilder position(int x, int y) {
            this.position = new Vector2i(x, y);
            return this;
        }

        public NodeBuilder children(List<Identifier> children) {
            this.children = children;
            return this;
        }

        public NodeBuilder child(Identifier child) {
            this.children.add(child);
            return this;
        }

        public NodeBuilder child(String child) {
            this.children.add(Identifier.parse(child));
            return this;
        }

        public NodeBuilder quest(Identifier quest) {
            this.quest = Optional.of(quest);
            return this;
        }

        public NodeBuilder quest(String quest) {
            this.quest = Optional.of(Identifier.parse(quest));
            return this;
        }

        public TreeBuilder build() {
            Node result = new Node(id, tooltip, icon, position, children, quest);
            parent.addNode(result);
            return parent;
        }

    }

}
