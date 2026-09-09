package dev.overgrown.apoli.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;

public final class HudStatusStack {

    public static final int BASE_HEIGHT = 39;
    public static final int ROW_HEIGHT = 10;

    private HudStatusStack() {}

    public static void pause() {}

    public static void resume() {}

    public static int rightHeight() {
        Gui gui = tracked();
        return gui == null ? BASE_HEIGHT + vanillaRows() * ROW_HEIGHT : gui.rightHeight;
    }

    public static void reserveRight(int pixels) {
        Gui gui = tracked();
        if (gui != null) {
            gui.rightHeight += pixels;
        }
    }

    private static Gui tracked() {
        if (!ApoliClientConfig.get().hudAutoStack()) {
            return null;
        }
        return Minecraft.getInstance().gui;
    }

    private static int vanillaRows() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) {
            return 0;
        }
        int hearts = 0;
        if (player.getVehicle() instanceof LivingEntity vehicle && vehicle.showVehicleHealth()) {
            hearts = Math.min((int) (vehicle.getMaxHealth() + 0.5F) / 2, 30);
        }
        boolean vulnerable = mc.gameMode != null && mc.gameMode.canHurtPlayer();
        int rows = hearts > 0 ? (hearts + 9) / 10 : (vulnerable ? 1 : 0);
        if (vulnerable && (player.isEyeInFluid(FluidTags.WATER) || player.getAirSupply() < player.getMaxAirSupply())) {
            rows++;
        }
        return rows;
    }
}
