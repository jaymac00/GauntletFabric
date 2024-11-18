package tech.jmacsoftware.gauntlet.list;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import tech.jmacsoftware.gauntlet.Gauntlet;

public class CustomTagList {

	public static class Blocks {

		public static final TagKey<Block> INCORRECT_FOR_REDSTONE_TOOL =
				TagKey.of(RegistryKeys.BLOCK, Gauntlet.id("incorrect_for_redstone_tool"));
	}

	public static class Items {

		public static final TagKey<Item> REDSTONE_TOOL_MATERIALS =
				TagKey.of(RegistryKeys.ITEM, Gauntlet.id("redstone_tool_materials"));
	}
}
