package dev.overgrown.apoli.codec;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import org.jetbrains.annotations.Nullable;

public final class ApoliOps {

    private static volatile @Nullable HolderLookup.Provider live;
    private static volatile @Nullable HolderLookup.Provider loading;

    private ApoliOps() {}

    public static void setRegistries(@Nullable HolderLookup.Provider provider) {
        live = provider;
    }

    public static void setLoadingRegistries(@Nullable HolderLookup.Provider provider) {
        loading = provider;
    }

    public static @Nullable HolderLookup.Provider registries() {
        HolderLookup.Provider provider = live;
        return provider != null ? provider : loading;
    }

    public static <T> DynamicOps<T> of(DynamicOps<T> base) {
        if (base instanceof RegistryOps) return base;
        HolderLookup.Provider provider = registries();
        return provider == null ? base : provider.createSerializationContext(base);
    }

    public static <T> Dynamic<T> attach(Dynamic<T> data) {
        DynamicOps<T> ops = of(data.getOps());
        return ops == data.getOps() ? data : new Dynamic<>(ops, data.getValue());
    }
}
