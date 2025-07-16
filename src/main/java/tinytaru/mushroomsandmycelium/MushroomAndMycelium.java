package tinytaru.mushroomsandmycelium;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinytaru.mushroomsandmycelium.block.ModBlocks;
import tinytaru.mushroomsandmycelium.item.ModItems;

// --- Add these imports:
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import tinytaru.mushroomsandmycelium.world.ModWorldGen;
import tinytaru.mushroomsandmycelium.world.feature.FallenLogWithMushroomsFeature;

public class MushroomAndMycelium implements ModInitializer {
	public static final String MOD_ID = "mushrooms-and-mycelium";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		// Initialize items
		ModItems.initialize();
		// Initialize blocks
		ModBlocks.initialize();

		// Register base worldgen feature (for JSON worldgen)
		Registry.register(
				Registries.FEATURE,
				new Identifier(MOD_ID, "fallen_log_with_mushrooms"),
				new FallenLogWithMushroomsFeature(DefaultFeatureConfig.CODEC)
		);

		// This is now optional with JSON worldgen, unless you're still adding features by code:
		ModWorldGen.addFeatures();
	}
}