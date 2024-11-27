package tech.jmacsoftware.gauntlet.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

import tech.jmacsoftware.gauntlet.Gauntlet;

public record BlockPosPayload(BlockPos blockPos) implements CustomPayload {

	public static final Id<BlockPosPayload> ID = new Id<>(Gauntlet.id("block_pos"));
	public static final PacketCodec<RegistryByteBuf, BlockPosPayload> PACKET_CODEC =
			PacketCodec.tuple(BlockPos.PACKET_CODEC, BlockPosPayload::blockPos, BlockPosPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return null;
	}
}
