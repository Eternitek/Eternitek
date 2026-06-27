package net.mercury.eternitek.core.registry;

import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.block.pipe.TransferPipeForkBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class EternitekBlocks {

    public static final Block TRANSFER_PIPE = registerBlock("transfer_pipe",
            TransferPipeForkBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

    public static void register() {}

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        ResourceKey<Block> key = EternitekCore.key(Registries.BLOCK, name);
        return Registry.register(
                BuiltInRegistries.BLOCK,
                key,
                factory.apply(properties.setId(key))
        );
    }
}
