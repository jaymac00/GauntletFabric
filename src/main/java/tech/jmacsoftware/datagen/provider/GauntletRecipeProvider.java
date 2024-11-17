package tech.jmacsoftware.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import tech.jmacsoftware.init.CustomTools;

import java.util.concurrent.CompletableFuture;

public class GauntletRecipeProvider extends FabricRecipeProvider {

	public GauntletRecipeProvider(FabricDataOutput fabricDataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(fabricDataOutput, registriesFuture);
	}

	@Override
	protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {

		return new RecipeGenerator(wrapperLookup, recipeExporter) {
			@Override
			public void generate() {
				createShaped(RecipeCategory.TOOLS, CustomTools.REDSTONE_PICKAXE)
						.input('D', ConventionalItemTags.DIAMOND_GEMS)
						.input('R', ConventionalItemTags.REDSTONE_DUSTS)
						.input('S', ConventionalItemTags.WOODEN_RODS)
						.pattern("DDD")
						.pattern("RSR")
						.pattern(" S ")
						.criterion(hasTag(ConventionalItemTags.DIAMOND_GEMS), conditionsFromTag(ConventionalItemTags.DIAMOND_GEMS))
						.criterion(hasTag(ConventionalItemTags.REDSTONE_DUSTS), conditionsFromTag(ConventionalItemTags.REDSTONE_DUSTS))
						.criterion(hasTag(ConventionalItemTags.WOODEN_RODS), conditionsFromTag(ConventionalItemTags.WOODEN_RODS))
						.offerTo(recipeExporter);
			}
		};
	}

	@Override
	public String getName() {
		return "";
	}

	private static String hasTag(TagKey<?> tagKey) {
		return "has_" + tagKey.id().toString();
	}
}
