package com.yourname.farlandsfarther;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;

/**
 * Prevent the built-in clamping that keeps Entities within "safe" coordinates.
 * Replace or bypass the clamp to allow coordinates beyond 30M.
 *
 * *** WARNING: *** Use with care. Disabling clamps can cause odd behavior with other mods.
 */
@Mixin(ClientPlayerEntity.class)
public class EntityMovementMixin {

    /**
     * Example: redirect calls to MathHelper.clamp(...) used by movement code to a no-op.
     * The exact utility class and clamp method may differ by version/mapping.
     *
     * If the clamp method is `MathHelper.clamp(double, double, double)` you'll need to target it.
     */
    @Redirect(method = "updatePosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(DDD)D"))
    private double bypassClamp(double value, double min, double max) {
        // allow any value — but optionally enforce a very large finite boundary
        double hardLimit = 1e12; // still finite to avoid Infinity problems
        if (Double.isFinite(value) && Math.abs(value) < hardLimit) return value;
        // fallback: keep inside hard limit
        return Math.signum(value) * hardLimit;
    }
}
