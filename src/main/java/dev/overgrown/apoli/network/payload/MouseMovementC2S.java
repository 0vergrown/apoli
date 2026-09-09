package dev.overgrown.apoli.network.payload;

import dev.overgrown.apoli.Apoli;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Mth;

public record MouseMovementC2S(float yaw, float pitch) implements CustomPacketPayload {
    public static final Type<MouseMovementC2S> TYPE = new Type<>(Apoli.id("mouse_movement"));

    public static final float MAX_DEGREES = 720.0F;

    public static final StreamCodec<RegistryFriendlyByteBuf, MouseMovementC2S> STREAM_CODEC = StreamCodec.of(
        (buf, payload) -> payload.write(buf),
        MouseMovementC2S::read);

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
    }

    public static MouseMovementC2S read(FriendlyByteBuf buf) {
        return new MouseMovementC2S(clamp(buf.readFloat()), clamp(buf.readFloat()));
    }

    private static float clamp(float value) {
        if (!Float.isFinite(value)) return 0.0F;
        return Mth.clamp(value, -MAX_DEGREES, MAX_DEGREES);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
