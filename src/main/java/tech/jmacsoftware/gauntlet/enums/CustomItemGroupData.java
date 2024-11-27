package tech.jmacsoftware.gauntlet.enums;

import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import tech.jmacsoftware.gauntlet.Gauntlet;

public enum CustomItemGroupData {

	GAUNTLET_MOD(createTitle("gauntlet_mod"), createRegistryKey("gauntlet_mod"));

	private final Text title;

	private final RegistryKey<ItemGroup> registryKey;

	CustomItemGroupData(Text title, RegistryKey<ItemGroup> registryKey) {
		this.title = title;
		this.registryKey = registryKey;
	}

	public Text getTitle() {
		return title;
	}

	public RegistryKey<ItemGroup> getRegistryKey() {
		return registryKey;
	}

	private static Text createTitle(String title) {
		return Text.translatable("itemGroup." + Gauntlet.MOD_ID + "." + title);
	}

	private static RegistryKey<ItemGroup> createRegistryKey(String name) {
		return RegistryKey.of(RegistryKeys.ITEM_GROUP, Gauntlet.id(name));
	}
}
