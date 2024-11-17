package tech.jmacsoftware.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import tech.jmacsoftware.datagen.provider.GauntletEnglishLanguageProvider;
import tech.jmacsoftware.datagen.provider.GauntletItemTagProvider;
import tech.jmacsoftware.datagen.provider.GauntletModelProvider;
import tech.jmacsoftware.datagen.provider.GauntletRecipeProvider;

public class GauntletDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(GauntletEnglishLanguageProvider::new);
		pack.addProvider(GauntletItemTagProvider::new);
		pack.addProvider(GauntletModelProvider::new);
		pack.addProvider(GauntletRecipeProvider::new);
	}
}
