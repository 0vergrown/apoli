package dev.overgrown.apoli.tick;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public final class TickRateVanilla {
    public static final boolean SUPPORTED = true;

    private TickRateVanilla() {}

    public static int rate(@Nullable MinecraftServer server) {
        if (server == null) return 20;
        return Math.max(1, Math.round(server.tickRateManager().tickrate()));
    }

    public static float exactRate(@Nullable MinecraftServer server) {
        return server == null ? 20.0F : server.tickRateManager().tickrate();
    }

    public static void setRate(MinecraftServer server, float rate) {
        ServerTickRateManager manager = server.tickRateManager();
        float clamped = Mth.clamp(rate, 1.0F, TickRates.MAX_RATE);
        if (Math.abs(manager.tickrate() - clamped) < 1.0E-4F) return;
        manager.setTickRate(clamped);
    }

    public static void setFrozen(MinecraftServer server, boolean frozen) {
        ServerTickRateManager manager = server.tickRateManager();
        if (manager.isFrozen() == frozen) return;
        manager.setFrozen(frozen);
    }

    public static void step(MinecraftServer server, int ticks) {
        ServerTickRateManager manager = server.tickRateManager();
        if (ticks <= 0) manager.stopStepping();
        else manager.stepGameIfPaused(ticks);
    }

    public static void sprint(MinecraftServer server, int ticks) {
        ServerTickRateManager manager = server.tickRateManager();
        if (ticks <= 0) manager.stopSprinting();
        else manager.requestGameToSprint(ticks);
    }

    public static boolean frozen(@Nullable MinecraftServer server) {
        return server != null && server.tickRateManager().isFrozen();
    }

    public static boolean sprinting(@Nullable MinecraftServer server) {
        return server != null && server.tickRateManager().isSprinting();
    }

    public static boolean stepping(@Nullable MinecraftServer server) {
        return server != null && server.tickRateManager().isSteppingForward();
    }
}
