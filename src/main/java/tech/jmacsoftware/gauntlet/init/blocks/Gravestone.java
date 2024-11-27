package tech.jmacsoftware.gauntlet.init.blocks;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import tech.jmacsoftware.gauntlet.Gauntlet;
import tech.jmacsoftware.gauntlet.init.CustomBlocks;
import tech.jmacsoftware.gauntlet.init.CustomEntities;
import tech.jmacsoftware.gauntlet.network.BlockPosPayload;
import tech.jmacsoftware.gauntlet.screenhandler.gravestone.GravestoneInventoryScreenHandler;

import java.util.Optional;

public class Gravestone extends Block implements BlockEntityProvider {

	private static final Identifier TEXTURE_BOTTOM = Gauntlet.id("block/gravestone_bottom");

	private static final Identifier TEXTURE_SIDE = Gauntlet.id("block/gravestone_side");

	private static final Identifier TEXTURE_TOP = Gauntlet.id("block/gravestone_top");

	public static final String ID = "gravestone";

	public static final VoxelShape DEFAULT_SHAPE = VoxelShapes
			.cuboid(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F)
			.simplify();

	public static final TextureMap TEXTURE_MAP = new TextureMap();

	public Gravestone(Settings settings) {

		super(settings);
		TEXTURE_MAP.put(TextureKey.BOTTOM, TEXTURE_BOTTOM);
		TEXTURE_MAP.put(TextureKey.SIDE, TEXTURE_SIDE);
		TEXTURE_MAP.put(TextureKey.TOP, TEXTURE_TOP);
	}

	public static Gravestone register(String name) {

		RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, Gauntlet.id(name));
		AbstractBlock.Settings settings = AbstractBlock.Settings.create()
				.registryKey(registryKey)
				.mapColor(MapColor.STONE_GRAY)
				.pistonBehavior(PistonBehavior.BLOCK)
				.requiresTool()
				.sounds(BlockSoundGroup.STONE)
				.strength(1.25F, 3600000.0F);
		Gravestone gravestone = new Gravestone(settings);
		return Registry.register(Registries.BLOCK, registryKey, gravestone);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

		if(!world.isClient) {
			if(world.getBlockEntity(pos) instanceof GravestoneEntity gravestoneEntity) {
				player.openHandledScreen(gravestoneEntity);
			}
			return ActionResult.SUCCESS_SERVER;
		}

		return ActionResult.SUCCESS;
	}

	@Override
	public BlockState onBreak(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player) {

		BlockState superBlockState = super.onBreak(world, blockPos, blockState, player);

		if (!world.isClient) {

			Optional<GravestoneEntity> gravestoneEntity = world.getBlockEntity(blockPos, CustomEntities.GRAVESTONE_ENTITY);

			if (gravestoneEntity.isPresent()) {

				SimpleInventory gravestoneInventory = gravestoneEntity.get().getInventory();
				for (int slotIndex = 0; slotIndex < 41; ++slotIndex) {

					if (!gravestoneInventory.getStack(slotIndex).isEmpty()) {
						world.spawnEntity(new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(),
								gravestoneInventory.getStack(slotIndex)));
					}
				}
			}
		}

		return superBlockState;
	}

	@Override
	@Nullable
	public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
		return CustomEntities.GRAVESTONE_ENTITY.instantiate(blockPos, blockState);
	}

	@Override
	protected MapCodec<Gravestone> getCodec() {
		return createCodec(Gravestone::new);
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
		return DEFAULT_SHAPE;
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	public static class GravestoneEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosPayload> {

		public static final Text DEFAULT_TITLE = Text.translatable("container." + Gauntlet.MOD_ID + ".unmarked_gravestone");

		private String title = DEFAULT_TITLE.getString();

		private final SimpleInventory inventory = new SimpleInventory(41) {

			@Override
			public void markDirty() {

				super.markDirty();
				update();
			}

			@Override
			public void onOpen(PlayerEntity player) {

				super.onOpen(player);
				update();
			}

			@Override
			public void onClose(PlayerEntity player) {

				super.onClose(player);
				update();
			}
		};

		public GravestoneEntity(BlockPos pos, BlockState state) {
			super(CustomEntities.GRAVESTONE_ENTITY, pos, state);
		}

		public static BlockEntityType<GravestoneEntity> register(String name, BlockEntityType<GravestoneEntity> gravestoneEntitytype) {

			RegistryKey<BlockEntityType<?>> registryKey = RegistryKey.of(RegistryKeys.BLOCK_ENTITY_TYPE, Gauntlet.id(name));
			return Registry.register(Registries.BLOCK_ENTITY_TYPE, registryKey, gravestoneEntitytype);
		}

		@Override
		public BlockPosPayload getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
			return new BlockPosPayload(this.pos);
		}

		@Override
		public Text getDisplayName() {
			return Text.of(this.title);
		}

		@Override
		@Nullable
		public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
			return new GravestoneInventoryScreenHandler(syncId, playerInventory, this);
		}

		@Override
		public void readNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {

			super.readNbt(nbtCompound, wrapperLookup);
			Inventories.readNbt(nbtCompound, this.inventory.getHeldStacks(), wrapperLookup);

			if (nbtCompound.contains("Title", NbtElement.STRING_TYPE)) {
				this.title = nbtCompound.getString("Title");
			}
		}

		@Override
		public void writeNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {

			nbtCompound.putString("Title", this.title);
			Inventories.writeNbt(nbtCompound, this.inventory.getHeldStacks(), wrapperLookup);
			super.writeNbt(nbtCompound, wrapperLookup);
		}

		@Nullable
		@Override
		public Packet<ClientPlayPacketListener> toUpdatePacket() {
			return BlockEntityUpdateS2CPacket.create(this);
		}

		@Override
		public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup wrapperLookup) {

			NbtCompound nbtCompound = super.toInitialChunkDataNbt(wrapperLookup);
			writeNbt(nbtCompound, wrapperLookup);
			return nbtCompound;
		}

		private void update() {

			markDirty();
			if (this.world != null) {
				world.updateListeners(this.pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
			}
		}

		public SimpleInventory getInventory() {
			return this.inventory;
		}

		public void setTitle(Text title) {
			if (title != null && title.getString() != null) {
				this.title = title.getString() + "'s Gravestone";
			}
		}
	}

	public static class GravestoneItem extends BlockItem {

		public GravestoneItem(Gravestone gravestone, Settings settings) {
			super(gravestone, settings);
		}

		public static GravestoneItem register(String name) {

			RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Gauntlet.id(name));
			Item.Settings settings = new Item.Settings().registryKey(registryKey);
			GravestoneItem gravestoneItem = new GravestoneItem(CustomBlocks.GRAVESTONE_BLOCK, settings);
			return Registry.register(Registries.ITEM, registryKey, gravestoneItem);
		}
	}
}