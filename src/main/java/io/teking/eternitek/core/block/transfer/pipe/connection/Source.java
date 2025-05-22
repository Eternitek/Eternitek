package io.teking.eternitek.core.block.transfer.pipe.connection;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;

public interface Source<T extends TransferVariant<?>> extends Connectible<T> {

    Storage<T> getStorage();

    @Override
    default boolean canExtract(T variant) {
        return true;
    }

    @Override
    default boolean canInsert(ItemVariant variant) {
        return false;
    }

    @Override
    default boolean isPushing() {
        return !isEmpty();
    }

}
