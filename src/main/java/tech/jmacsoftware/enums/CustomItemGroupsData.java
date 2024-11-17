package tech.jmacsoftware.enums;

import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import tech.jmacsoftware.Gauntlet;

public enum CustomItemGroupsData {

	GAUNTLET_TOOLS(createTitle("gauntlet_tools"), createRegistryKey("gauntlet_tools"));

	private Text title;

	private RegistryKey<ItemGroup> registryKey;

	CustomItemGroupsData(Text title, RegistryKey<ItemGroup> registryKey) {
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
