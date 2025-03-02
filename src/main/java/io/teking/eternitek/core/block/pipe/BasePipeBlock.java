package io.teking.eternitek.core.block.pipe;

import io.teking.eternitek.core.block.entity.PipeControllerBlockEntity;
import io.teking.eternitek.core.block.entity.PipeTileEntity;
import io.teking.eternitek.core.util.pipes.ItemTransferHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BasePipeBlock extends Block implements BlockEntityProvider {
    public BasePipeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PipeTileEntity(pos, state);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);

        // Check if there's a Pipe Controller nearby
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);

            if (neighborState.getBlock() instanceof PipeControllerBlock) {
                PipeControllerBlockEntity controller = (PipeControllerBlockEntity) world.getBlockEntity(neighborPos);

                if (controller != null) {
                    if (controller.isPulling()) {
                        pullItems(world, pos);
                    } else {
                        pushItems(world, pos);
                    }
                }
                break; // Only interact with one controller at a time
            }
        }

        // Schedule the next tick
        world.scheduleBlockTick(pos, this, 10);
    }


    private void pullItems(ServerWorld world, BlockPos pos) {
        for (Direction direction : Direction.values()) { // Iterate through all directions
            Inventory sourceInventory = ItemTransferHelper.getInventoryAt(world, pos.offset(direction), direction.getOpposite());
            Inventory targetInventory = ItemTransferHelper.getInventoryAt(world, pos, direction);

            if (sourceInventory == null || targetInventory == null) continue;

            // Iterate through source slots and transfer items
            for (int i = 0; i < sourceInventory.size(); i++) {
                ItemStack stackInSource = sourceInventory.getStack(i);

                if (!stackInSource.isEmpty()) {
                    // Try transferring items to the target inventory
                    boolean success = ItemTransferHelper.transferItem(sourceInventory, targetInventory, i, stackInSource.getCount());

                    if (success) break; // Transfer one stack at a time
                }
            }
        }
    }

    private void pushItems(ServerWorld world, BlockPos pos) {
        for (Direction direction : Direction.values()) { // Iterate through all directions
            Inventory sourceInventory = ItemTransferHelper.getInventoryAt(world, pos, direction);
            Inventory targetInventory = ItemTransferHelper.getInventoryAt(world, pos.offset(direction), direction.getOpposite());

            if (sourceInventory == null || targetInventory == null) continue;

            // Iterate through source slots and transfer items
            for (int i = 0; i < sourceInventory.size(); i++) {
                ItemStack stackInSource = sourceInventory.getStack(i);

                if (!stackInSource.isEmpty()) {
                    // Try transferring items to the target inventory
                    boolean success = ItemTransferHelper.transferItem(sourceInventory, targetInventory, i, stackInSource.getCount());

                    if (success) break; // Transfer one stack at a time
                }
            }
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);

        if (!world.isClient) {
            world.scheduleBlockTick(pos, this, 10); // Schedule the first tick (10 ticks later)
        }
    }

}

