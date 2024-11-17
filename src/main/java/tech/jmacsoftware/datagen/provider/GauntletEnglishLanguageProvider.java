package tech.jmacsoftware.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.jetbrains.annotations.NotNull;

import tech.jmacsoftware.Gauntlet;
import tech.jmacsoftware.enums.CustomItemGroupsData;
import tech.jmacsoftware.init.CustomTools;

import java.util.concurrent.CompletableFuture;

public class GauntletEnglishLanguageProvider extends FabricLanguageProvider {

	public GauntletEnglishLanguageProvider(FabricDataOutput fabricDataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(fabricDataOutput, registriesFuture);
	}

	private static void addText(@NotNull TranslationBuilder builder, @NotNull Text text, @NotNull String value) {
		if (text.getContent() instanceof TranslatableTextContent translatableTextContent) {
			builder.add(translatableTextContent.getKey(), value);
		} else {
			Gauntlet.LOGGER.warn("Failed to add translation for text: {}", text.getString());
		}
	}

	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {

		translationBuilder.add(CustomTools.REDSTONE_PICKAXE, "Redstone Pickaxe");
		addText(translationBuilder, CustomItemGroupsData.GAUNTLET_TOOLS.getTitle(), "Gauntlet Tools");
	}

	public static void load() {}
}
