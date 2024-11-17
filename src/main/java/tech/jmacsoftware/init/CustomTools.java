package tech.jmacsoftware.init;

import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import tech.jmacsoftware.Gauntlet;
import tech.jmacsoftware.enums.CustomToolMaterials;

public class CustomTools {

	public static final PickaxeItem REDSTONE_PICKAXE = registerPickaxe("redstone_pickaxe",
			CustomToolMaterials.REDSTONE.deriveToolMaterial(), 1.0F, -2.8F);

	public static PickaxeItem registerPickaxe(String name,
			ToolMaterial material, float attackDamage, float attackSpeed) {

		RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Gauntlet.id(name));
		Item.Settings settings = new Item.Settings().registryKey(registryKey);
		PickaxeItem pickaxeItem = new PickaxeItem(material, attackDamage, attackSpeed, settings);
		return Registry.register(Registries.ITEM, registryKey, pickaxeItem);
	}

	public static void load() {}
}
