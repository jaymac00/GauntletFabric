package tech.jmacsoftware.gauntlet.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import tech.jmacsoftware.gauntlet.init.CustomBlocks;

import java.util.concurrent.CompletableFuture;

public class GauntletBlockTagProvider extends FabricTagProvider<Block> {

	public GauntletBlockTagProvider(FabricDataOutput fabricDataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(fabricDataOutput, RegistryKeys.BLOCK, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
				.add(CustomBlocks.GRAVESTONE_BLOCK);
	}
}
