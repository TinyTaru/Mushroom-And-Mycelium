package tinytaru.mushroomsandmycelium.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.gen.GenerationStep;
import tinytaru.mushroomsandmycelium.world.feature.ModPlacedFeatures;

public class ModWorldGen {
    public static void addFeatures() {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld().and(ctx ->
                        ctx.hasTag(BiomeTags.IS_FOREST)
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.FALLEN_LOG_WITH_MUSHROOMS_PLACED
        );
    }
}