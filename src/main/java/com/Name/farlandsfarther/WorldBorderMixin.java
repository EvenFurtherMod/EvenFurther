package com.yourname.farlandsfarther.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.border.WorldBorder;

/**
 * Prevent vanilla world border sizing/clamping from interfering.
 * Some world border setters may be called during initialization; we can override size.
 */
@Mixin(WorldBorder.class)
public class WorldBorderMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        try {
            // set size enormously high — effectively disabling vanilla border clamp.
            ((WorldBorder)(Object)this).setSize(Double.MAX_VALUE / 4.0); // avoid Infinity
        } catch (Throwable t) {
            // ignore if not allowed on server-side init path
        }
    }
}
