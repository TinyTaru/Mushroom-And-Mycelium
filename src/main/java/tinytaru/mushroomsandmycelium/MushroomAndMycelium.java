package tinytaru.mushroomsandmycelium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinytaru.mushroomsandmycelium.block.ModBlocks;
import tinytaru.mushroomsandmycelium.event.ModEvents;
import tinytaru.mushroomsandmycelium.item.ModItems;
import tinytaru.mushroomsandmycelium.world.ModWorldGen;
import tinytaru.mushroomsandmycelium.world.feature.FallenLogWithMushroomsFeature;

public class MushroomAndMycelium implements ModInitializer {
    public static final String MOD_ID = "mushrooms-and-mycelium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    // Item Group
    public static final ItemGroup MUSHROOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.MUSHROOM_BLOCK))
            .displayName(Text.translatable("itemGroup." + MOD_ID + ".mushroom_group"))
            .entries((context, entries) -> {
                entries.add(ModBlocks.MUSHROOM_BLOCK);
                entries.add(ModBlocks.OYSTER_MUSHROOM);
                entries.add(ModBlocks.SPORE_DISH);
                entries.add(ModBlocks.SAWDUST_BLOCK);
                entries.add(ModBlocks.INOCULATED_SAWDUST_BLOCK);
                entries.add(ModItems.SPORES);
            })
            .build();

    @Override
    public void onInitialize() {
        // Initialize blocks and items
        ModBlocks.initialize();
        ModItems.initialize();
        
        // Register item group
        Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "mushroom_group"), MUSHROOM_ITEM_GROUP);
        
        // Register base worldgen feature (for JSON worldgen)
        Registry.register(
                Registries.FEATURE,
                new Identifier(MOD_ID, "fallen_log_with_mushrooms"),
                new FallenLogWithMushroomsFeature(DefaultFeatureConfig.CODEC)
        );

        // This is now optional with JSON worldgen, unless you're still adding features by code:
        ModWorldGen.addFeatures();
        
        // Register events
        ModEvents.registerEvents();
        
        LOGGER.info("Mushrooms and Mycelium initialized!");
    }
}