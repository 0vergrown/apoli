package dev.overgrown.apoli.mixin.tick;

import dev.overgrown.apoli.client.ClientTickRates;
import net.minecraft.client.renderer.GameRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GameRenderer.class)
@OnlyIn(Dist.CLIENT)
public abstract class GameRendererTickRateMixin {

    @ModifyVariable(
        method = "renderItemInHand(Lnet/minecraft/client/Camera;FLorg/joml/Matrix4f;)V",
        at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float apoli$slowedFirstPersonPartial(float partialTick) {
        return ClientTickRates.cameraPartial(partialTick);
    }
}
