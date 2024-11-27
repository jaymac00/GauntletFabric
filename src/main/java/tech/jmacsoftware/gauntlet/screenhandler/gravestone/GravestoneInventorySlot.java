package tech.jmacsoftware.gauntlet.screenhandler.gravestone;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class GravestoneInventorySlot extends Slot {

	public GravestoneInventorySlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean canInsert(ItemStack itemStack) {
		return false;
	}
}
