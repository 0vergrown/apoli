package dev.overgrown.apoli.mixin.disguise;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntityRenderer.class)
@OnlyIn(Dist.CLIENT)
public interface LivingEntityRendererAddLayerAccessor {

    @Invoker("addLayer")
    boolean apoli$addLayer(RenderLayer<?, ?> layer);
}
