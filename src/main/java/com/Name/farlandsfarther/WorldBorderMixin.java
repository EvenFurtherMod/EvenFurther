package com.yourname.farlandsfarther.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class EntityMovementMixin {
    @Redirect(method = "updatePosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(DDD)D"))
    private double bypassClamp(double value, double min, double max) {
        double hardLimit = 1e12;
        if (Double.isFinite(value) && Math.abs(value) < hardLimit) return value;
        return Math.signum(value) * hardLimit;
    }
}
