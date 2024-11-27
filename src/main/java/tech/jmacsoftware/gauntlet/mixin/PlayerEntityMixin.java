package tech.jmacsoftware.gauntlet.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import tech.jmacsoftware.gauntlet.init.CustomBlocks;
import tech.jmacsoftware.gauntlet.init.CustomEntities;
import tech.jmacsoftware.gauntlet.init.blocks.Gravestone;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends LivingEntity {

	@Unique
	private Arm mainArm = PlayerEntity.DEFAULT_MAIN_ARM;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At(value = "HEAD"), method = "setMainArm")
	private void captureMainArm(Arm arm, CallbackInfo info) {
		this.mainArm = arm;
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;vanishCursedItems()V",
			shift = At.Shift.AFTER), method = "dropInventory")
	private void capturePlayerInventory(ServerWorld world, CallbackInfo info) {

		PlayerEntity player = (PlayerEntity)(Object) this;
		BlockPos blockPos = player.getBlockPos();
		PlayerInventory playerInventory = player.getInventory();

		while (!world.getBlockState(blockPos).isReplaceable()) {
			blockPos = blockPos.add(0, 1, 0);
		}

		world.setBlockState(blockPos, CustomBlocks.GRAVESTONE_BLOCK.getDefaultState());
		Optional<Gravestone.GravestoneEntity> gravestoneEntity = world.getBlockEntity(blockPos, CustomEntities.GRAVESTONE_ENTITY);

		if (gravestoneEntity.isPresent()) {

			gravestoneEntity.get().setTitle(player.getDisplayName());
			SimpleInventory gravestoneInventory = gravestoneEntity.get().getInventory();
			for (int slotIndex = 0; slotIndex < 41; ++slotIndex) {

				if (!playerInventory.getStack(slotIndex).isEmpty()) {
					gravestoneInventory.setStack(slotIndex, playerInventory.getStack(slotIndex));
				}
			}
			playerInventory.clear();
		}
	}

	@Override
	public Iterable<ItemStack> getArmorItems() {

		PlayerEntity player = (PlayerEntity)(Object) this;
		return player.getInventory().armor;
	}

	@Override
	public ItemStack getEquippedStack(EquipmentSlot slot) {

		PlayerEntity player = (PlayerEntity)(Object) this;
		if (slot == EquipmentSlot.MAINHAND) {
			return player.getInventory().getMainHandStack();
		} else if (slot == EquipmentSlot.OFFHAND) {
			return player.getInventory().offHand.getFirst();
		} else {
			return slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR ?
					player.getInventory().armor.get(slot.getEntitySlotId()) : ItemStack.EMPTY;
		}
	}

	@Override
	public void equipStack(EquipmentSlot slot, ItemStack stack) {

		PlayerEntity player = (PlayerEntity)(Object) this;
		stack.getItem().postProcessComponents(stack);
		if (slot == EquipmentSlot.MAINHAND) {
			this.onEquipStack(slot, player.getInventory().main.set(player.getInventory().selectedSlot, stack), stack);
		} else if (slot == EquipmentSlot.OFFHAND) {
			this.onEquipStack(slot, player.getInventory().offHand.set(0, stack), stack);
		} else if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
			this.onEquipStack(slot, player.getInventory().armor.set(slot.getEntitySlotId(), stack), stack);
		}
	}

	@Override
	public Arm getMainArm() {
		return this.mainArm;
	}
}