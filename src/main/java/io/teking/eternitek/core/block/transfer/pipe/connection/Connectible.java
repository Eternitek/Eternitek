package io.teking.eternitek.core.block.transfer.pipe.connection;

import io.teking.eternitek.core.block.entity.PipeBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.minecraft.block.entity.BlockEntity;

/**
 * Should be implemented by <em>all</em> {@link BlockEntity BlockEntities} that can be connected to by a
 * {@link PipeBlockEntity Pipe}. If this is unimplemented, {@link PipeBlockEntity Pipes} will not connect to your custom
 * {@link BlockEntity}.
 *
 * @see Source
 * @see Target
 * @param <T> the type of the resource in the variant
 */
public interface Connectible<T extends TransferVariant<?>> {

    /**
     * Checks whether the {@code BlockEntity} can be extracted from.
     *
     * @param variant the variant to be extracted
     * @return whether extraction will be successful
     * @see Source
     * @see Target
     * @see #extract(long)
     */
    boolean canExtract(T variant);

    /**
     * Checks whether the {@code BlockEntity} can be inserted into.
     *
     * @param variant the variant to be inserted
     * @return whether insertion will be successful
     * @see Source
     * @see Target
     * @see #insert(long)
     */
    boolean canInsert(ItemVariant variant);

    /**
     * Attempts to extract a given amount of the resource from the {@code BlockEntity}.
     *
     * @param amount how much of the resource to extract
     * @return how much of the resource was extracted
     */
    long extract(long amount);

    /**
     * Attempts to insert a given amount of the resource into the {@code BlockEntity}
     *
     * @param amount how much of the resource to insert
     * @return how much of the resource was inserted
     */
    long insert(long amount);

    /**
     * Checks if the next {@link PipeBlockEntity Pipe} can pull from this {@link Connectible} as though it were a
     * {@link Source}, even if it's not.
     *
     * @return {@code true} if the {@code BlockEntity} is a {@link Source} or a {@link PipeBlockEntity Pipe} for which
     *         {@link #isEmpty()} is {@code false}, otherwise returns {@code false}
     */
    boolean isPushing();

    /**
     * Checks if the {@link Connectible} has any of the resource still in it. If this returns {@code true},
     * {@link #isPushing()} will always return {@code false}.
     *
     * @return whether the internal amount of the resource is {@code 0}
     */
    boolean isEmpty();

}
