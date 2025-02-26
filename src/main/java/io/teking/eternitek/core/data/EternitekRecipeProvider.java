package io.teking.eternitek.core.data;

import io.teking.eternitek.core.registry.EternitekBlocks;
import io.teking.eternitek.core.registry.EternitekItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.teking.eternitek.core.registry.EternitekItems.CRUDE_STEEL_INGOT;

public class EternitekRecipeProvider extends FabricRecipeProvider {

    public EternitekRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {

        offerBlasting(recipeExporter, List.of(Items.IRON_INGOT), RecipeCategory.MISC, CRUDE_STEEL_INGOT, 0.35F, 300, "");
        offer2x2BrickRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, EternitekBlocks.BLAST_BRICKS, EternitekItems.BlAST_BRICK);
    }

    public static void offer2x2BrickRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 4).input('#', input).pattern("##").pattern("##").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

}
