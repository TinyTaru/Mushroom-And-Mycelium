package tinytaru.mushroomsandmycelium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class InoculatedSawdustBlock extends Block {
    private static final String MUSHROOM_COUNT_KEY = "MushroomCount";
    private static final int MIN_MUSHROOMS_BEFORE_SPENT = 5;
    private static final int MAX_MUSHROOMS_BEFORE_SPENT = 10;
    private int mushroomCount = 0;
    private final int maxMushrooms;

    public InoculatedSawdustBlock(Settings settings) {
        super(settings);
        Random random = Random.create();
        this.maxMushrooms = MIN_MUSHROOMS_BEFORE_SPENT + random.nextInt(
            MAX_MUSHROOMS_BEFORE_SPENT - MIN_MUSHROOMS_BEFORE_SPENT + 1);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient) {
            // If we've reached max mushrooms, convert to spent sawdust
            if (mushroomCount >= maxMushrooms) {
                world.setBlockState(pos, ModBlocks.SPENT_SAWDUST_BLOCK.getDefaultState());
                return;
            }

            BlockPos abovePos = pos.up();
            // Check if the block above is air and the block below can sustain the mushroom
            if (world.isAir(abovePos) && canGrowMushroom(world, abovePos)) {
                // 50% chance to grow a mushroom
                if (random.nextFloat() < 0.5f) {
                    world.setBlockState(abovePos, ModBlocks.OYSTER_MUSHROOM.getDefaultState());
                    mushroomCount++;
                    // Mark the block for saving the updated count
                    world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
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

    @Override
    protected void appendProperties(net.minecraft.state.StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        // No additional block state properties needed as we're using NBT
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        // No need to manually clear ticks as they'll be cleaned up automatically
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
