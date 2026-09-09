package dev.overgrown.apoli.network.payload;

import dev.overgrown.apoli.Apoli;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record TickRateS2C(int entityId, int rate, int baseRate) implements CustomPacketPayload {
    public static final Type<TickRateS2C> TYPE = new Type<>(Apoli.id("tick_rate"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TickRateS2C> STREAM_CODEC = StreamCodec.of(
        (buf, payload) -> payload.write(buf),
        TickRateS2C::read);

    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(entityId);
        buf.writeVarInt(rate + 1);
        buf.writeVarInt(baseRate);
    }

    public static TickRateS2C read(FriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        int rate = buf.readVarInt() - 1;
        int baseRate = Math.max(1, buf.readVarInt());
        return new TickRateS2C(entityId, rate, baseRate);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
