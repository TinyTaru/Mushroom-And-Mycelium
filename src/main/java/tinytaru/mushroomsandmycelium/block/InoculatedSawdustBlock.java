package tinytaru.mushroomsandmycelium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class InoculatedSawdustBlock extends Block {
    public InoculatedSawdustBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient) {
            BlockPos abovePos = pos.up();
            // Check if the block above is air and the block below can sustain the mushroom
            if (world.isAir(abovePos) && canGrowMushroom(world, abovePos)) {
                // 50% chance to grow a mushroom
                if (random.nextFloat() < 0.5f) {
                    world.setBlockState(abovePos, ModBlocks.OYSTER_MUSHROOM.getDefaultState());
                }
            }
            // Schedule next growth attempt in 10-15 seconds (200-300 ticks)
            world.scheduleBlockTick(pos, this, random.nextBetween(200, 300));
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        // Schedule the first growth check
        if (!world.isClient) {
            world.scheduleBlockTick(pos, this, world.random.nextBetween(200, 300));
        }
    }

    private boolean canGrowMushroom(WorldView world, BlockPos pos) {
        BlockPos downPos = pos.down();
        BlockState downState = world.getBlockState(downPos);
        // Check if the block below can sustain the mushroom
        return downState.isSolidBlock(world, downPos);
    }
}
