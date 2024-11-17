package tech.jmacsoftware.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import tech.jmacsoftware.init.CustomTools;

import java.util.concurrent.CompletableFuture;

public class GauntletItemTagProvider  extends FabricTagProvider<Item> {

	public GauntletItemTagProvider(FabricDataOutput fabricDataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(fabricDataOutput, RegistryKeys.ITEM, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

		getOrCreateTagBuilder(ItemTags.PICKAXES)
				.add(CustomTools.REDSTONE_PICKAXE);
	}
}
