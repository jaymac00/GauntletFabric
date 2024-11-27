package tech.jmacsoftware.gauntletclient;

import net.fabricmc.api.ClientModInitializer;

import tech.jmacsoftware.gauntletclient.init.CustomScreens;

public class GauntletClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		CustomScreens.registerScreens();
	}
}