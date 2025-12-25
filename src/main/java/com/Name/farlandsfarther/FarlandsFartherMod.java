package com.yourname.farlandsfarther;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FarlandsFartherMod implements ModInitializer {
    public static final String MODID = "farlandsfarther";
    public static final Logger LOGGER = LoggerFactory.getLogger("farlandsfarther");

    @Override
    public void onInitialize() {
        LOGGER.info("FarlandsFarther initializing — A+B: restoring Far Lands and enabling extended coordinates.");
        // All heavy-lifting is done with mixins. We can register some runtime toggles here later, adam.
    }
}
