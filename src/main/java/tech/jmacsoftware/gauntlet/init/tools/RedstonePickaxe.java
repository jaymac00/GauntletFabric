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
		if (superResult && !world.isClient
				&& (blockState.streamTags().anyMatch(tagKey ->
						tagKey.equals(BlockTags.BASE_STONE_OVERWORLD) || tagKey.equals(BlockTags.BASE_STONE_NETHER))
				|| blockState.getBlock().equals(Blocks.END_STONE))) {

			boolean hasSilkTouch = livingEntity.getStackInHand(livingEntity.getActiveHand()).hasEnchantments()
					&& livingEntity.getStackInHand(livingEntity.getActiveHand()).getEnchantments().getEnchantments()
							.stream()
							.anyMatch(enchantment -> enchantment.getKey().isPresent()
									&& enchantment.getKey().get().equals(Enchantments.SILK_TOUCH));

			switch (livingEntity.getFacing().getAxis()) {
				case X -> {
					breakAdjacent(world, blockState, blockPos.north(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.south(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.up(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.down(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.north().up(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.north().down(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.south().up(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.south().down(), hasSilkTouch);
				}
				case Y -> {
					breakAdjacent(world, blockState, blockPos.north(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.south(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.east(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.west(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.north().east(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.north().west(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.south().east(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.south().west(), hasSilkTouch);
				}
				case Z -> {
					breakAdjacent(world, blockState, blockPos.east(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.west(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.up(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.down(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.east().up(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.east().down(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.west().up(), hasSilkTouch);
					breakAdjacent(world, blockState, blockPos.west().down(), hasSilkTouch);
				}
			};
		}

		return superResult;
	}

	private void breakAdjacent(World world, BlockState originalBlockState, BlockPos newBlockPos, boolean hasSilkTouch) {

		if (world.getBlockState(newBlockPos).getBlock().asItem().equals(originalBlockState.getBlock().asItem())
				&& world.breakBlock(newBlockPos, !hasSilkTouch) && hasSilkTouch) {

			world.spawnEntity(new ItemEntity(world, newBlockPos.getX(), newBlockPos.getY(), newBlockPos.getZ(),
					originalBlockState.getBlock().asItem().getDefaultStack()));
		}
	}
}
