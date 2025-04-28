package io.teking.eternitek.core.block.transfer.pipe.connection;

import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;

public interface Target<T extends TransferVariant<?>> extends Connectible<T> {

    @Override
    default boolean canExtract(T variant) {
        return false;
    }

    @Override
    default boolean canInsert(T variant) {
        return true;
    }

    @Override
    default boolean isPushing() {
        return false;
    }

}
