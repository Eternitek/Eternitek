package io.teking.eternitek.core.data;

import io.wispforest.lavender.client.LavenderBookScreen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.awt.print.Book;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.teking.eternitek.core.registry.EternitekItems.*;

public class EternitekRecipeProvider extends FabricRecipeProvider {

    public EternitekRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {

        offerBlasting(recipeExporter, List.of(Items.IRON_INGOT), RecipeCategory.MISC , CRUDE_STEEL_INGOT, 0.35F, 300, "");

    }

}
