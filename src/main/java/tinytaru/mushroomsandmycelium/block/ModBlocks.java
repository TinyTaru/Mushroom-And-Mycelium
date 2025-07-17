package tinytaru.mushroomsandmycelium.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import tinytaru.mushroomsandmycelium.MushroomAndMycelium;
import tinytaru.mushroomsandmycelium.item.ModItems;


public class ModBlocks {

    public static final Block MUSHROOM_BLOCK = register(
            new Block(AbstractBlock.Settings.create().strength(1.0f).sounds(BlockSoundGroup.GRASS)),
            "mushroom_block",
            true

    );
    public static final Block SAWDUST_BLOCK = register(
            new Block(AbstractBlock.Settings.create().strength(1.0f).sounds(BlockSoundGroup.GRASS)),
            "sawdust_block",
            true

    );
    public static final Block SPORE_DISH = register(
            new SporeDishBlock(),     // ← your custom class!
            "spore_dish",
            true
    );
    public static final Block INOCULATED_SAWDUST_BLOCK = register(
            new Block(AbstractBlock.Settings.create().strength(1.0f).sounds(BlockSoundGroup.FUNGUS)),
            "inoculated_sawdust_block",
            true
    );
    public static final Block OYSTER_MUSHROOM = register(
            new FlowerBlock(
                    StatusEffects.SATURATION, // This status effect is just a placeholder; required by FlowerBlock.
                    0,                        // Duration of effect in ticks, 0 if you don't want an effect.
                    AbstractBlock.Settings.create()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .offset(AbstractBlock.OffsetType.XZ)
            ),
            "oyster_mushroom",
            true
    );

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = new Identifier(MushroomAndMycelium.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {
        // This is where you'd call `register(...)` for each of your custom blocks
        ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(ModBlocks.MUSHROOM_BLOCK.asItem());
            itemGroup.add(ModBlocks.OYSTER_MUSHROOM.asItem());
            itemGroup.add(ModBlocks.SPORE_DISH.asItem());
            itemGroup.add(ModBlocks.INOCULATED_SAWDUST_BLOCK.asItem());
        });
    }
}