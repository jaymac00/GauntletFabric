package tech.jmacsoftware.gauntlet.init;

import net.minecraft.screen.ScreenHandlerType;

import tech.jmacsoftware.gauntlet.network.BlockPosPayload;
import tech.jmacsoftware.gauntlet.screenhandler.gravestone.GravestoneInventoryScreenHandler;

public class CustomScreenHandlers {

	public static final ScreenHandlerType<GravestoneInventoryScreenHandler> GRAVESTONE_INVENTORY_SCREEN_HANDLER =
			GravestoneInventoryScreenHandler.register("gravestone_inventory",
					GravestoneInventoryScreenHandler::new,BlockPosPayload.PACKET_CODEC);

	public static void load() {}
}
