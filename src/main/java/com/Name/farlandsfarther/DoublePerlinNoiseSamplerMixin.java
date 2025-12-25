package com.yourname.farlandsfarther;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;

/**
 * Reintroduce legacy floating-point-ish behaviour to recreate the classic Far Lands pattern.
 *
 * This ModifyVariable hook multiplies certain sample offsets by a large factor or
 * intentionally introduces limited-precision rounding to replicate the historical overflow.
 *
 * NOTE: this is a best-effort approach — you may need to tune constants to match exact beta behavior.
 */
@Mixin(DoublePerlinNoiseSampler.class)
public class DoublePerlinNoiseSamplerMixin {

    /**
     * Example: multiply the x/z sampling offsets by a large factor to force catastrophic
     * aliasing and precision artifacts similar to original Far Lands math.
     *
     * The actual DoublePerlinNoiseSampler internals vary across versions; if the target method
     * or parameter index is different in your mappings, adapt the mixin selector accordingly.
     */
    @ModifyVariable(method = "sample", at = @At("HEAD"), ordinal = 0)
    private double reintroduceLegacyOffsetX(double original) {
        // Multiply and quantize to simulate lower-precision accumulation (tunable).
        double scaled = original * 1e6; // large scale to break pattern
        // emulate limited precision by forcing to float precision then back to double:
        float f = (float) scaled;
        return (double) f / 1e6;
    }

    @ModifyVariable(method = "sample", at = @At("HEAD"), ordinal = 1)
    private double reintroduceLegacyOffsetY(double original) {
        // minor changes in Y usually affect caves vs surface; keep smaller
        float f = (float) original;
        return (double) f;
    }
}
