package tech.jmacsoftware.gauntlet.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import tech.jmacsoftware.gauntlet.datagen.provider.GauntletEnglishLanguageProvider;
import tech.jmacsoftware.gauntlet.datagen.provider.GauntletItemTagProvider;
import tech.jmacsoftware.gauntlet.datagen.provider.GauntletModelProvider;
import tech.jmacsoftware.gauntlet.datagen.provider.GauntletRecipeProvider;

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
