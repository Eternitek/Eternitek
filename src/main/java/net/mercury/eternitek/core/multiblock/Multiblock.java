package net.mercury.eternitek.core.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Map;

public record Multiblock(StructureTemplate structure, boolean canRotate, Map<Block, HolderSet<Block>> tags) {

    public record Data(
            Identifier id,
            Identifier structure,
            boolean canRotate,
            Map<Identifier, HolderSet<Block>> tags
    ) {

        public static final Codec<Data> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("id").forGetter(Data::id),
                Identifier.CODEC.fieldOf("structure").forGetter(Data::structure),
                Codec.BOOL.fieldOf("can_rotate").orElse(true).forGetter(Data::canRotate),
                ExtraCodecs.strictUnboundedMap(
                        Identifier.CODEC,
                        RegistryCodecs.homogeneousList(Registries.BLOCK)
                ).fieldOf("tags").forGetter(Data::tags)
        ).apply(instance, Data::new));

    }

}
