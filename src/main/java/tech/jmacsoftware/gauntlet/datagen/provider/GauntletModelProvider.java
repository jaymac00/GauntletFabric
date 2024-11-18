package tech.jmacsoftware.gauntlet.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import tech.jmacsoftware.gauntlet.init.CustomItems;

public class GauntletModelProvider extends FabricModelProvider {

	public GauntletModelProvider(FabricDataOutput fabricDataOutput) {
		super(fabricDataOutput);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {

		itemModelGenerator.register(CustomItems.REDSTONE_PICKAXE, Models.HANDHELD);
	}
}
