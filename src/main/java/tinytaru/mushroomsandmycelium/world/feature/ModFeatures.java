package tinytaru.mushroomsandmycelium.world.feature;

import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> FALLEN_LOG_WITH_MUSHROOMS = Registry.register(
            Registries.FEATURE,
            new Identifier("mushrooms-and-mycelium", "fallen_log_with_mushrooms"),
            new FallenLogWithMushroomsFeature(DefaultFeatureConfig.CODEC)
    );
}