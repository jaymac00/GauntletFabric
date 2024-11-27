package tech.jmacsoftware.gauntlet.screenhandler.gravestone;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

import tech.jmacsoftware.gauntlet.Gauntlet;
import tech.jmacsoftware.gauntlet.init.CustomBlocks;
import tech.jmacsoftware.gauntlet.init.CustomScreenHandlers;
import tech.jmacsoftware.gauntlet.init.blocks.Gravestone;
import tech.jmacsoftware.gauntlet.network.BlockPosPayload;

public class GravestoneInventoryScreenHandler extends ScreenHandler {

	private final ScreenHandlerContext screenHandlerContext;

	public GravestoneInventoryScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload blockPosPayload) {

		this(syncId, playerInventory,
				(Gravestone.GravestoneEntity) playerInventory.player.getWorld().getBlockEntity(blockPosPayload.blockPos()));
	}

	public GravestoneInventoryScreenHandler(int syncId, PlayerInventory playerInventory, Gravestone.GravestoneEntity gravestoneEntity) {

		super(CustomScreenHandlers.GRAVESTONE_INVENTORY_SCREEN_HANDLER, syncId);
		this.screenHandlerContext = ScreenHandlerContext.create(gravestoneEntity.getWorld(), gravestoneEntity.getPos());

		SimpleInventory inventory = gravestoneEntity.getInventory();
		checkSize(inventory, 41);
		inventory.onOpen(playerInventory.player);

		addPlayerInventory(playerInventory);
		addGravestoneInventory(inventory);
	}

	public static ExtendedScreenHandlerType<GravestoneInventoryScreenHandler, BlockPosPayload> register(String name,
			ExtendedScreenHandlerType.ExtendedFactory<GravestoneInventoryScreenHandler, BlockPosPayload> factory,
					PacketCodec<? super RegistryByteBuf, BlockPosPayload> codec) {

		RegistryKey<ScreenHandlerType<?>> registryKey = RegistryKey.of(RegistryKeys.SCREEN_HANDLER, Gauntlet.id(name));
		return Registry.register(Registries.SCREEN_HANDLER, registryKey, new ExtendedScreenHandlerType<>(factory, codec));
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slotIndex) {

		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = getSlot(slotIndex);
		if (slotIndex > 35 && slot != null && slot.hasStack()) {

			ItemStack inSlot = slot.getStack();
			newStack = inSlot.copy();

			if (slotIndex < 72) {
				insertItem(inSlot, slotIndex - 36, 36, false);
			}
			if (!inSlot.isEmpty() && !insertItem(inSlot, 0, 36, false)) {
				return ItemStack.EMPTY;
			}

			if (inSlot.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(this.screenHandlerContext, player, CustomBlocks.GRAVESTONE_BLOCK);
	}

	private void addPlayerInventory(PlayerInventory playerInventory) {

		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				addSlot(new Slot(playerInventory, 9 + (column + (row * 9)), 8 + ((column + 1) * 18), 102 + (row * 18)));
			}
		}

		for (int column = 0; column < 9; ++column) {
			addSlot(new Slot(playerInventory, column, 8 + ((column + 1) * 18), 160));
		}
	}

	private void addGravestoneInventory(SimpleInventory inventory) {

		for (int row = 1; row < 4; ++row) {
			for (int column = 0; column < 9; ++column) {
				addSlot(new GravestoneInventorySlot(inventory, column + (row * 9), 8 + ((column + 2) * 18), row * 18));
			}
		}

		for (int column = 0; column < 9; ++column) {
			addSlot(new GravestoneInventorySlot(inventory, column, 8 + ((column + 2) * 18), 72));
		}

		addSlot(new GravestoneInventorySlot(inventory, 36, 8, 72));
		addSlot(new GravestoneInventorySlot(inventory, 37, 8, 54));
		addSlot(new GravestoneInventorySlot(inventory, 38, 8, 36));
		addSlot(new GravestoneInventorySlot(inventory, 39, 8, 18));
		addSlot(new GravestoneInventorySlot(inventory, 40, 26, 72));
	}
}
