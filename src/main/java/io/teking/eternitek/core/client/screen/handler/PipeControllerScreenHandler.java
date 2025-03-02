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

        this.addSlot(new Slot(playerInventory));

    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true; // Add proper checks if needed
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

}