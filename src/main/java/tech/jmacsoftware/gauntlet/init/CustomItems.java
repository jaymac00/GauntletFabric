package tech.jmacsoftware.gauntlet.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;

import tech.jmacsoftware.gauntlet.init.blocks.Gravestone;
import tech.jmacsoftware.gauntlet.init.tools.RedstonePickaxe;

public class CustomItems {

	public static final RedstonePickaxe REDSTONE_PICKAXE = RedstonePickaxe.register("redstone_pickaxe",
			ToolMaterial.DIAMOND, 1.0F, -2.8F);

	public static final Gravestone.GravestoneItem GRAVESTONE_ITEM = Gravestone.GravestoneItem.register(Gravestone.ID);

	public static void load() {

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
			entries.addAfter(Items.NETHERITE_HOE, CustomItems.REDSTONE_PICKAXE);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
			entries.addBefore(Items.CHEST, CustomItems.GRAVESTONE_ITEM);
		});
	}
}
