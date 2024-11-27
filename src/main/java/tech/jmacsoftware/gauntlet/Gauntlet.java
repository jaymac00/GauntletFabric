package tech.jmacsoftware.gauntlet;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tech.jmacsoftware.gauntlet.init.CustomBlocks;
import tech.jmacsoftware.gauntlet.init.CustomEntities;
import tech.jmacsoftware.gauntlet.init.CustomItemGroups;
import tech.jmacsoftware.gauntlet.init.CustomItems;
import tech.jmacsoftware.gauntlet.init.CustomScreenHandlers;

public class Gauntlet implements ModInitializer {

	public static final String MOD_ID = "gauntlet";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric World!");

		LOGGER.info("Loading Gauntlet Mod...");

		CustomItemGroups.load();
		CustomBlocks.load();
		CustomEntities.load();
		CustomItems.load();
		CustomScreenHandlers.load();

		LOGGER.info("Loaded!");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}