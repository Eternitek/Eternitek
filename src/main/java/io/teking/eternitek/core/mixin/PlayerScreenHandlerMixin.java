package io.teking.eternitek.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import io.teking.eternitek.core.EternitekCore;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.HashMap;
import java.util.Map;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin {

    @Unique
    private static final HashMap<EquipmentSlot, Identifier> SLOT_TEXTURES = new HashMap<>();

    static {
        SLOT_TEXTURES.put(EquipmentSlot.HEAD, EternitekCore.id("item/slot/helmet"));
        SLOT_TEXTURES.put(EquipmentSlot.CHEST, EternitekCore.id("item/slot/chestplate"));
        SLOT_TEXTURES.put(EquipmentSlot.LEGS, EternitekCore.id("item/slot/leggings"));
        SLOT_TEXTURES.put(EquipmentSlot.FEET, EternitekCore.id("item/slot/boots"));
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/CraftingResultSlot;<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/inventory/Inventory;III)V"))
    private void eternitek$modifyCraftingOutputSlotPosition(Args args) {
        int x = 157 - 5;
        int y = 25 - 5;
        args.set(4, x);
        args.set(5, y);
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V", ordinal = 0))
    private void eternitek$modifyCraftingGridPosition(Args args, @Local(ordinal = 0) int i, @Local(ordinal = 1) int j) {
        int x = 101 + (j * 18) - 6;
        int y = 17 + (i * 18) - 6;
        args.set(2, x);
        args.set(3, y);
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/ArmorSlot;<init>(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;IIILnet/minecraft/util/Identifier;)V"))
    private void eternitek$modifyArmorSlotsPosition(Args args, @Local(ordinal = 0) int i, @Local() EquipmentSlot slot) {
        // x value is fine, don't modify
        int y = 8 + (i * 18) - 4;
        Identifier id = SLOT_TEXTURES.get(slot);
        args.set(5, y);
        args.set(6, id);
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V", ordinal = 1))
    private void eternitek$modifyInventorySlotsPosition(Args args, @Local(ordinal = 0) int i, @Local(ordinal = 1) int j) {
        // x value is fine, don't modify
        int y = 90 + (i * 18) - 6;
        args.set(3, y);
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V", ordinal = 2))
    private void eternitek$modifyHotbarSlotsPosition(Args args, @Local(ordinal = 0) int i) {
        int x = 8 + (i * 19) - 4;
        int y = 142 + 4;
        args.set(2, x);
        args.set(3, y);
    }

}
