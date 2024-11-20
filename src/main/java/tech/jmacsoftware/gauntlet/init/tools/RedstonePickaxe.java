package tech.jmacsoftware.gauntlet.init.tools;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import tech.jmacsoftware.gauntlet.Gauntlet;

public class RedstonePickaxe extends PickaxeItem {

	public RedstonePickaxe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}

	public static RedstonePickaxe register(String name,
			ToolMaterial material, float attackDamage, float attackSpeed) {

		RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Gauntlet.id(name));
		Item.Settings settings = new Item.Settings().registryKey(registryKey);
		RedstonePickaxe redstonePickaxe = new RedstonePickaxe(material, attackDamage, attackSpeed, settings);
		return Registry.register(Registries.ITEM, registryKey, redstonePickaxe);
	}

	@Override
	public boolean postMine(ItemStack itemStack, World world,
			BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {

		boolean superResult = super.postMine(itemStack, world, blockState, blockPos, livingEntity);
		if (superResult && !world.isClient && isValidBlock(blockState)) {

			boolean hasSilkTouch = livingEntity.getStackInHand(livingEntity.getActiveHand()).hasEnchantments()
					&& livingEntity.getStackInHand(livingEntity.getActiveHand()).getEnchantments()
							.getEnchantments()
							.stream()
							.anyMatch(enchantment -> enchantment.getKey().isPresent()
									&& enchantment.getKey().get().equals(Enchantments.SILK_TOUCH));

			switch (livingEntity.getFacing().getAxis()) {
				case X -> {
					breakAdjacent(world, blockPos.north(), hasSilkTouch);
					breakAdjacent(world, blockPos.south(), hasSilkTouch);
					breakAdjacent(world, blockPos.up(), hasSilkTouch);
					breakAdjacent(world, blockPos.down(), hasSilkTouch);
					breakAdjacent(world, blockPos.north().up(), hasSilkTouch);
					breakAdjacent(world, blockPos.north().down(), hasSilkTouch);
					breakAdjacent(world, blockPos.south().up(), hasSilkTouch);
					breakAdjacent(world, blockPos.south().down(), hasSilkTouch);
				}
				case Y -> {
					breakAdjacent(world, blockPos.north(), hasSilkTouch);
					breakAdjacent(world, blockPos.south(), hasSilkTouch);
					breakAdjacent(world, blockPos.east(), hasSilkTouch);
					breakAdjacent(world, blockPos.west(), hasSilkTouch);
					breakAdjacent(world, blockPos.north().east(), hasSilkTouch);
					breakAdjacent(world, blockPos.north().west(), hasSilkTouch);
					breakAdjacent(world, blockPos.south().east(), hasSilkTouch);
					breakAdjacent(world, blockPos.south().west(), hasSilkTouch);
				}
				case Z -> {
					breakAdjacent(world, blockPos.east(), hasSilkTouch);
					breakAdjacent(world, blockPos.west(), hasSilkTouch);
					breakAdjacent(world, blockPos.up(), hasSilkTouch);
					breakAdjacent(world, blockPos.down(), hasSilkTouch);
					breakAdjacent(world, blockPos.east().up(), hasSilkTouch);
					breakAdjacent(world, blockPos.east().down(), hasSilkTouch);
					breakAdjacent(world, blockPos.west().up(), hasSilkTouch);
					breakAdjacent(world, blockPos.west().down(), hasSilkTouch);
				}
			};
		}

		return superResult;
	}

	private boolean isValidBlock(BlockState blockState) {

		return (blockState.isIn(BlockTags.BASE_STONE_OVERWORLD)
				|| blockState.isIn(BlockTags.BASE_STONE_NETHER)
				|| blockState.getBlock().equals(Blocks.END_STONE));
	}

	private void breakAdjacent(World world, BlockPos blockPos, boolean hasSilkTouch) {

		if (isValidBlock(world.getBlockState(blockPos)) && world.breakBlock(blockPos, !hasSilkTouch) && hasSilkTouch) {

			world.spawnEntity(new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(),
					world.getBlockState(blockPos).getBlock().asItem().getDefaultStack()));
		}
	}
}
