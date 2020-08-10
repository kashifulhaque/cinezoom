package xyz.ifkash.cinezoom.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.ifkash.cinezoom.Cinezoom;

@Mixin(GameRenderer.class)
@Environment(EnvType.CLIENT)
public class CinezoomMixin {
    private void init(CallbackInfo info) { System.out.println("CinezoomMixin: initialized."); }

    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("HEAD"), cancellable = true)
    public void getLevel(CallbackInfoReturnable<Float> info) {
        if(Cinezoom.isKeyPressed()) info.setReturnValue(Cinezoom.getLevel());
        Cinezoom.manageSmoothCam();
    }
}
