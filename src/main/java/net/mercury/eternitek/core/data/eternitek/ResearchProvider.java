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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class ResearchProvider extends FabricCodecDataProvider<Tree> {

    private Map<Identifier, Tree> toRegister;

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
            this.parent.register(this.id, new Tree(name, id, nodes));
        }

    }

    protected static class NodeBuilder {

        private Identifier id;
        private List<Component> tooltip;
        private Identifier icon;
        private Vector2i position;
        private List<Identifier> connections;

        private final TreeBuilder parent;

        public NodeBuilder(TreeBuilder parent) {
            this.parent = parent;
            this.tooltip = new ArrayList<>();
            this.connections = new ArrayList<>();
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

        public NodeBuilder connections(List<Identifier> connections) {
            this.connections = connections;
            return this;
        }

        public NodeBuilder connection(Identifier connection) {
            this.connections.add(connection);
            return this;
        }

        public NodeBuilder connection(String connection) {
            this.connections.add(Identifier.parse(connection));
            return this;
        }

        public TreeBuilder build() {
            Node result = new Node(id, tooltip, icon, position, connections);
            parent.addNode(result);
            return parent;
        }

    }

}
