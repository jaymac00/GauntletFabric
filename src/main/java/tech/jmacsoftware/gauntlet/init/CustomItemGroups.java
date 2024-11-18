package tech.jmacsoftware.gauntlet.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import tech.jmacsoftware.gauntlet.Gauntlet;
import tech.jmacsoftware.gauntlet.enums.CustomItemGroupData;

import java.util.Optional;

public class CustomItemGroups {

	public static final ItemGroup GAUNTLET_TOOLS = register(CustomItemGroupData.GAUNTLET_TOOLS,
			CustomItems.REDSTONE_PICKAXE);

	public static ItemGroup register(CustomItemGroupData customItemGroupsData, Item icon) {

		ItemGroup itemGroup = FabricItemGroup.builder()
				.icon(icon::getDefaultStack)
				.entries(((displayContext, entries) -> Registries.ITEM.getIds()
						.stream()
						.filter(key -> key.getNamespace().equals(Gauntlet.MOD_ID))
						.map(Registries.ITEM::get)
						.map(Optional::of)
						.forEach(entry -> entries.add(entry.orElse(null)))))
				.displayName(customItemGroupsData.getTitle())
				.build();
		return Registry.register(Registries.ITEM_GROUP, customItemGroupsData.getRegistryKey(), itemGroup);
	}

	public static void load() {}
}
