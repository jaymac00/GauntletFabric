package tech.jmacsoftware.gauntlet.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import tech.jmacsoftware.gauntlet.init.CustomBlocks;
import tech.jmacsoftware.gauntlet.init.CustomItems;
import tech.jmacsoftware.gauntlet.init.blocks.Gravestone;

public class GauntletModelProvider extends FabricModelProvider {

	public GauntletModelProvider(FabricDataOutput fabricDataOutput) {
		super(fabricDataOutput);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

		blockStateModelGenerator.registerSingleton(CustomBlocks.GRAVESTONE_BLOCK, Gravestone.TEXTURE_MAP, Models.SLAB);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {

		itemModelGenerator.register(CustomItems.REDSTONE_PICKAXE, Models.HANDHELD);
	}
}
