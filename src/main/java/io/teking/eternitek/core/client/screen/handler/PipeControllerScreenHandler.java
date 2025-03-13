package io.teking.eternitek.core.client.screen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class PipeControllerScreenHandler extends ScreenHandler {

    private final BlockPos blockPos;

    public PipeControllerScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {

        super(null, syncId);

        this.blockPos = buf.readBlockPos();

        // Add player inventory slots
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Add player hotbar slots
        for (int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
        }

    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        // Implement quick move logic here
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true; // Add proper checks if needed
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

}