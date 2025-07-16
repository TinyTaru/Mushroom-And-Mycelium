package tinytaru.mushroomsandmycelium.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import tinytaru.mushroomsandmycelium.block.ModBlocks;
import tinytaru.mushroomsandmycelium.item.ModItems;

import java.util.List;

public class SporeDishBlock extends Block {
    public static final BooleanProperty FULL = BooleanProperty.of("full");
    public static final IntProperty FILLING = IntProperty.of("filling", 0, 1); // 0 = not filling, 1 = filling
    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 3, 13);
    private static final int FILLING_TIME_TICKS = 200 + (int)(Math.random() * 101); // 10-15 seconds (200-300 ticks)

    public SporeDishBlock() {
        super(FabricBlockSettings.create()
                .strength(1.0f)
                .nonOpaque()
                .sounds(BlockSoundGroup.GLASS)
                .ticksRandomly()
        );
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FULL, false)
                .with(FILLING, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FULL, FILLING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient) {
            if (state.get(FILLING) == 1 && !state.get(FULL)) {
                // Check if there's still an oyster mushroom nearby
                if (hasAdjacentOysterMushroom(world, pos)) {
                    world.setBlockState(pos, state.with(FULL, true).with(FILLING, 0), 3);
                    world.updateNeighbors(pos, this);
                } else {
                    // If oyster mushroom was removed, reset the filling state
                    world.setBlockState(pos, state.with(FILLING, 0), 3);
                }
            }
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isClient) return;
        
        // Only check if not already full and not already filling
        if (!state.get(FULL) && state.get(FILLING) == 0) {
            // Check if there's an adjacent oyster mushroom
            if (hasAdjacentOysterMushroom(world, pos)) {
                // Start the filling process
                world.setBlockState(pos, state.with(FILLING, 1), 3);
                world.scheduleBlockTick(pos, this, FILLING_TIME_TICKS);
            }
        } else if (state.get(FILLING) == 1 && !hasAdjacentOysterMushroom(world, pos)) {
            // If oyster mushroom was removed while filling, reset the filling state
            world.setBlockState(pos, state.with(FILLING, 0), 3);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient && !state.get(FULL) && state.get(FILLING) == 0) {
            // Check for adjacent oyster mushroom when placed
            neighborUpdate(state, world, pos, this, pos, notify);
        }
    }

    private boolean hasAdjacentOysterMushroom(World world, BlockPos pos) {
        for (Direction d : Direction.values()) {
            if (world.getBlockState(pos.offset(d)).getBlock() == ModBlocks.OYSTER_MUSHROOM) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        List<ItemStack> drops = super.getDroppedStacks(state, builder);
        if (state.get(FULL)) {
            drops.add(new ItemStack(ModItems.SPORES));
        }
        return drops;
    }
}