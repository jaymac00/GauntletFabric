package tech.jmacsoftware.enums;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;

import tech.jmacsoftware.list.CustomTagList;

public enum CustomToolMaterials {

	REDSTONE(CustomTagList.Blocks.INCORRECT_FOR_REDSTONE_TOOL, 1561, 8.0F,
			3.0F, 10, CustomTagList.Items.REDSTONE_TOOL_MATERIALS);

	private final int durability, enchantmentValue;
	private final float speed, attackDamageBonus;
	private final TagKey<Block> incorrectBlocksForDrops;
	private final TagKey<Item> repairItems;

	CustomToolMaterials(TagKey<Block> incorrectBlocksForDrops, int durability, float speed,
			float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems) {
		this.incorrectBlocksForDrops = incorrectBlocksForDrops;
		this.durability = durability;
		this.speed = speed;
		this.attackDamageBonus = attackDamageBonus;
		this.enchantmentValue = enchantmentValue;
		this.repairItems = repairItems;
	}

	public ToolMaterial deriveToolMaterial() {
		return new ToolMaterial(this.incorrectBlocksForDrops, this.durability, this.speed,
				this.attackDamageBonus, this.enchantmentValue, this.repairItems);
	}

	public int getDurability() {
		return durability;
	}

	public int getEnchantmentValue() {
		return enchantmentValue;
	}

	public float getSpeed() {
		return speed;
	}

	public float getAttackDamageBonus() {
		return attackDamageBonus;
	}

	public TagKey<Block> getIncorrectBlocksForDrops() {
		return incorrectBlocksForDrops;
	}

	public TagKey<Item> getRepairItems() {
		return repairItems;
	}
}
