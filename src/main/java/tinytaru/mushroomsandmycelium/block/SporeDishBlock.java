package tinytaru.mushroomsandmycelium.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import tinytaru.mushroomsandmycelium.block.ModBlocks;
import tinytaru.mushroomsandmycelium.item.ModItems;

import java.util.Collections;
import java.util.List;

public class SporeDishBlock extends Block {
    public static final BooleanProperty FULL = BooleanProperty.of("full");
    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 3, 13);

    public SporeDishBlock() {
        super(FabricBlockSettings.create()
                .strength(1.0f)
                .nonOpaque()
                .sounds(BlockSoundGroup.GLASS)
        );
        this.setDefaultState(this.stateManager.getDefaultState().with(FULL, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FULL);
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
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        List<ItemStack> drops = super.getDroppedStacks(state, builder);
        if (state.get(FULL)) {
            drops.add(new ItemStack(ModItems.SPORES));
        }
        return drops;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isClient) return;
        // Only ever flip from empty → full; never back to empty
        if (!state.get(FULL)) {
            // if any adjacent is an oyster mushroom, become full
            for (Direction d : Direction.values()) {
                if (world.getBlockState(pos.offset(d)).getBlock() == ModBlocks.OYSTER_MUSHROOM) {
                    world.setBlockState(pos, state.with(FULL, true), 3);
                    return;
                }
            }
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient) {
            neighborUpdate(state, world, pos, this, pos, notify);
        }
    }
}