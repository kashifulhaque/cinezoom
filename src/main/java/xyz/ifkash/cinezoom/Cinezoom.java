package xyz.ifkash.cinezoom;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Cinezoom implements ClientModInitializer {

    // Bool to keep track of Zoom and smooth camera
    private static boolean isZoom = false;
    private static boolean isSmooth = false;

    // Key binding to trigger the zoom
    private static KeyBinding key;
    private static final int keyBind = GLFW.GLFW_KEY_Z;
    private static final InputUtil.Type type = InputUtil.Type.KEYSYM;

    // Get Minecraft instance
    private static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();

    // Member variables
    private static final String translationKey = "key.cinezoom.zoom";
    private static final String category = "category.cinezoom.zoom";
    private static final double zoomLevel = 20.0f;   // Optifine uses FOV 19 for it's zoom level

    @Override
    public void onInitializeClient() {
        key = new KeyBinding(translationKey, type, keyBind, category);
        KeyBindingHelper.registerKeyBinding(key);
    }

    public static boolean isSmoothCamEnabled() { return MINECRAFT.options.smoothCameraEnabled; }

    public static void enableSmoothCam() { MINECRAFT.options.smoothCameraEnabled = true; }

    public static void disableSmoothCam() { MINECRAFT.options.smoothCameraEnabled = false; }

    public static void resetSmoothCam() {
        if (isSmooth) enableSmoothCam();
        else disableSmoothCam();
    }

    public static void manageSmoothCam() {
        if(isZoomStarting()) {
            startZoom();
            enableSmoothCam();
        } else {
            stopZoom();
            disableSmoothCam();
        }
    }

    public static boolean isKeyPressed() { return key.isPressed(); }

    private static boolean isZoomStarting() { return isKeyPressed() && !isZoom; }

    private static boolean isZoomStopping() { return !isKeyPressed() && isZoom; }

    private static void startZoom() {
        isSmooth = isSmoothCamEnabled();
        isZoom = true;
    }

    private static void stopZoom() { isZoom = false; }

    public static double getLevel() { return zoomLevel; }
}
