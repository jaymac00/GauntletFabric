package tech.jmacsoftware.gauntlet.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

import tech.jmacsoftware.gauntlet.init.blocks.Gravestone;

public class CustomEntities {

	public static final BlockEntityType<Gravestone.GravestoneEntity> GRAVESTONE_ENTITY =
			Gravestone.GravestoneEntity.register(Gravestone.ID,
					FabricBlockEntityTypeBuilder.create(Gravestone.GravestoneEntity::new, CustomBlocks.GRAVESTONE_BLOCK).build());

	public static void load() {}
}
