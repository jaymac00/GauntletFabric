package tech.jmacsoftware.gauntletclient.init;

import net.minecraft.client.gui.screen.ingame.HandledScreens;

import tech.jmacsoftware.gauntlet.init.CustomScreenHandlers;
import tech.jmacsoftware.gauntletclient.init.screens.GravestoneInventoryScreen;

public class CustomScreens {

	public static void registerScreens() {
		HandledScreens.register(CustomScreenHandlers.GRAVESTONE_INVENTORY_SCREEN_HANDLER, GravestoneInventoryScreen::new);
	}
}
