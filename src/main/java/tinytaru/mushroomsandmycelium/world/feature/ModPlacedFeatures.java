package tinytaru.mushroomsandmycelium.world.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> FALLEN_LOG_WITH_MUSHROOMS_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                    new Identifier("mushrooms-and-mycelium", "fallen_log_with_mushrooms"));

    public static void bootstrap(Registerable<PlacedFeature> context) {
        context.register(
                FALLEN_LOG_WITH_MUSHROOMS_PLACED,
                new PlacedFeature(
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE)
                                .getOrThrow(ModConfiguredFeatures.FALLEN_LOG_WITH_MUSHROOMS_CONFIGURED),
                        List.of(
                                CountPlacementModifier.of(1), // 1 per chunk; adjust as needed
                                SquarePlacementModifier.of(),
                                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                BiomePlacementModifier.of()
                        )
                )
        );
    }
}