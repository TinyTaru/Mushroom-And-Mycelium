package tinytaru.mushroomsandmycelium.world.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> FALLEN_LOG_WITH_MUSHROOMS_CONFIGURED =
            RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
                    new Identifier("mushrooms-and-mycelium", "fallen_log_with_mushrooms"));

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        context.register(
                FALLEN_LOG_WITH_MUSHROOMS_CONFIGURED,
                new ConfiguredFeature<>(
                        ModFeatures.FALLEN_LOG_WITH_MUSHROOMS,
                        DefaultFeatureConfig.INSTANCE
                )
        );
    }
}
