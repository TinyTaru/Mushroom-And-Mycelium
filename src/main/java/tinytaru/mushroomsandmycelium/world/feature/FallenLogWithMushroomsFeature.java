package tinytaru.mushroomsandmycelium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import tinytaru.mushroomsandmycelium.block.ModBlocks;

import net.minecraft.util.math.random.Random;

public class FallenLogWithMushroomsFeature extends Feature<DefaultFeatureConfig> {
    public FallenLogWithMushroomsFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        WorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        // 1. Decide length and direction for the fallen log
        int logLength = 3 + random.nextInt(3); // 3–5 blocks
        boolean xAxis = random.nextBoolean();

        // 2. Place the fallen log (oak, horizontal)
        BlockState logState = Blocks.OAK_LOG
                .getDefaultState()
                .with(Properties.AXIS, xAxis ? Direction.Axis.X : Direction.Axis.Z);

        // Store log positions for mushroom placement
        BlockPos[] logPositions = new BlockPos[logLength];
        int placed = 0;

        for (int i = 0; i < logLength; i++) {
            BlockPos surfacePos = xAxis
                    ? origin.add(i, 0, 0)
                    : origin.add(0, 0, i);
            BlockPos placePos = surfacePos.up();
            BlockState surface = world.getBlockState(surfacePos);
            BlockState above = world.getBlockState(placePos);

            // Only place if the surface is grass or dirt, and above is air
            if ((surface.isOf(Blocks.GRASS_BLOCK) || surface.isOf(Blocks.DIRT))
                    && above.isAir()) {
                world.setBlockState(placePos, logState, 3);
                logPositions[placed++] = placePos;
            }
        }

        // No valid positions, fail
        if (placed == 0) return false;

        // 3. Place 1–3 oyster mushrooms on top of the log
        int mushroomCount = 1 + random.nextInt(3);
        for (int i = 0; i < mushroomCount; i++) {
            int offset = random.nextInt(placed);
            BlockPos mushroomPos = logPositions[offset].up();
            if (world.getBlockState(mushroomPos).isAir()) {
                world.setBlockState(mushroomPos, ModBlocks.OYSTER_MUSHROOM.getDefaultState(), 3);
            }
        }

        return true;
    }
}