package io.teking.eternitek.core.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiningHammerItem extends PickaxeItem {
    private final int rangeX;
    private final int rangeY;
    private final int rangeZ;

    public MiningHammerItem(ToolMaterial material, Settings settings, int rangeX, int rangeY, int rangeZ) {
        super(material, settings);
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
            breakBlocks(world, pos, miner, stack);
        }
        return super.postMine(stack, world, state, pos, miner);
    }

    private void breakBlocks(World world, BlockPos pos, LivingEntity miner, ItemStack stack) {
        for (int x = -rangeX; x <= rangeX; x++) {
            for (int y = -rangeY; y <= rangeY; y++) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    BlockPos newPos = pos.add(x, y, z);
                    breakExtraBlock(world, newPos, miner, stack);
                }
            }
        }
    }

    private void breakExtraBlock(World world, BlockPos pos, LivingEntity miner, ItemStack stack) {
        BlockState state = world.getBlockState(pos);
        if (state.getHardness(world, pos) != 0.0F && stack.isSuitableFor(state)) {
            world.breakBlock(pos, true);

            // Damage the item
            if (miner instanceof PlayerEntity player) {
                stack.damage(1, player, EquipmentSlot.MAINHAND);
            }
        }
    }
}
