package io.teking.eternitek.core.mixin;

import com.mojang.datafixers.util.Pair;
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
import net.minecraft.screen.slot.ArmorSlot;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingRecipeInput, CraftingRecipe> {

    @Shadow @Final private RecipeInputInventory craftingInput;

    @Shadow @Final private CraftingResultInventory craftingResult;

    @Shadow @Final private static EquipmentSlot[] EQUIPMENT_SLOT_ORDER;

    @Shadow @Final private static Map<EquipmentSlot, Identifier> EMPTY_ARMOR_SLOT_TEXTURES;

    public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/PlayerScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;"), cancellable = true)
    private void eternitek$changePositioning(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {

        this.addSlot(new CraftingResultSlot(inventory.player, this.craftingInput, this.craftingResult, 0, 154, 28));

        for(int i = 0; i < 2; ++i) {
            for(int j = 0; j < 2; ++j) {
                this.addSlot(new Slot(this.craftingInput, j + i * 2, 101 + j * 18, 17 + i * 18));
            }
        }

        for(int i = 0; i < 4; ++i) {
            EquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[i];
            Identifier identifier = (Identifier)EMPTY_ARMOR_SLOT_TEXTURES.get(equipmentSlot);
            this.addSlot(new ArmorSlot(inventory, owner, equipmentSlot, 39 - i, 8, 8 + i * 18, identifier));
        }

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }

        this.addSlot(new Slot(inventory, 40, 77, 62) {
            public void setStack(ItemStack stack, ItemStack previousStack) {
                owner.onEquipStack(EquipmentSlot.OFFHAND, previousStack, stack);
                super.setStack(stack, previousStack);
            }

            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
            }
        });


        ci.cancel();

    }

}
