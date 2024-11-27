package tech.jmacsoftware.gauntletclient.init.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import tech.jmacsoftware.gauntlet.Gauntlet;
import tech.jmacsoftware.gauntlet.screenhandler.gravestone.GravestoneInventoryScreenHandler;

@Environment(EnvType.CLIENT)
public class GravestoneInventoryScreen extends HandledScreen<GravestoneInventoryScreenHandler> {

	private static final Identifier TEXTURE = Gauntlet.id("textures/gui/container/gravestone_inventory.png");

	public GravestoneInventoryScreen(GravestoneInventoryScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.backgroundHeight = 184;
		this.backgroundWidth = 212;
		this.playerInventoryTitleX = 26;
		this.playerInventoryTitleY = this.backgroundHeight - 93;
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

		int x = (this.width - this.backgroundWidth) / 2;
		int y = (this.height - this.backgroundHeight) / 2;
		context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,
				x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {

		renderBackground(context, mouseX, mouseY, delta);
		super.render(context, mouseX, mouseY, delta);
		drawMouseoverTooltip(context, mouseX, mouseY);
	}
}
