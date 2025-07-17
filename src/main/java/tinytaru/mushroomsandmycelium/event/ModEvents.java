package tinytaru.mushroomsandmycelium.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tinytaru.mushroomsandmycelium.block.ModBlocks;
import tinytaru.mushroomsandmycelium.item.ModItems;

public class ModEvents {
    public static void registerEvents() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (world.isClient) return ActionResult.PASS;

            ItemStack stack = player.getStackInHand(hand);
            BlockPos pos = hit.getBlockPos();
            BlockState state = world.getBlockState(pos);

            // Check if right-clicking a sawdust block with spores
            if (stack.isOf(ModItems.SPORES) && state.isOf(ModBlocks.SAWDUST_BLOCK)) {
                // Replace with inoculated sawdust block
                world.setBlockState(pos, ModBlocks.INOCULATED_SAWDUST_BLOCK.getDefaultState());

                // Consume one spore if not in creative mode
                if (!player.isCreative()) {
                    stack.decrement(1);
                }

                // Play a sound
                world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE,
                    SoundCategory.BLOCKS, 1.0f, 1.0f);

                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });
    }
}
