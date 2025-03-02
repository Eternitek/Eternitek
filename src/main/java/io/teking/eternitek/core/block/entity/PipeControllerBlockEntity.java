package io.teking.eternitek.core.block.entity;

import io.teking.eternitek.core.registry.EternitekBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PipeControllerBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    private boolean isPulling = true; // Default behavior: pulling items

    public PipeControllerBlockEntity(BlockPos pos, BlockState state) {
        super(EternitekBlockEntities.PIPE_CONTROLLER_BLOCK_ENTITY, pos, state);
    }

    public void toggleMode() {
        isPulling = !isPulling; // Toggle between pulling and pushing
    }

    public boolean isPulling() {
        return isPulling;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Pipe Controller");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }
}
