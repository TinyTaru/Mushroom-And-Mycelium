package tinytaru.mushroomsandmycelium.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;

import tinytaru.mushroomsandmycelium.MushroomAndMycelium;
import tinytaru.mushroomsandmycelium.item.armor.MushroomArmorMaterial;

public class ModItems {

    public static final Item MUSHROOM_HELMET = register(new ArmorItem(MushroomArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new Item.Settings()), "mushroom_helmet");
    public static final Item MUSHROOM_BOOTS = register(new ArmorItem(MushroomArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new Item.Settings()), "mushroom_boots");
    public static final Item MUSHROOM_LEGGINGS = register(new ArmorItem(MushroomArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new Item.Settings()), "mushroom_leggings");
    public static final Item MUSHROOM_CHESTPLATE = register(new ArmorItem(MushroomArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new Item.Settings()), "mushroom_chestplate");

    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(MushroomAndMycelium.MOD_ID, "item_group"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.SUSPICIOUS_SUBSTANCE))
            .displayName(Text.translatable("itemGroup.fabric_docs_reference"))
            .build();

    public static final FoodComponent SUSPICIOUS_FOOD_COMPONENT = new FoodComponent.Builder()
            .snack()
            .hunger(1)
            // The duration is in ticks, 20 ticks = 1 second
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 6 * 20, 1), 1.0f)
            .build();

    public static final Item SUSPICIOUS_SUBSTANCE = register(
            // Ignore the food component for now, we'll cover it later in the food section.
            new Item(new FabricItemSettings().food(SUSPICIOUS_FOOD_COMPONENT)),
            "suspicious_substance"
    );
    public static final Item MUSHROOM_LEATHER = register(
            // Ignore the food component for now, we'll cover it later in the food section.
            new Item(new FabricItemSettings()),
            "mushroom_leather"
    );
    public static final Item RAW_MUSHROOM_LEATHER = register(
            // Ignore the food component for now, we'll cover it later in the food section.
            new Item(new FabricItemSettings()),
            "raw_mushroom_leather"
    );
    public static final Item SPORES = register(
            new Item(new FabricItemSettings()),
            "spores"
    );

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = new Identifier(MushroomAndMycelium.MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }

        public static void initialize() {
            // Get the event for modifying entries in the ingredients group.
            // And register an event handler that adds our suspicious item to the ingredients group.
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                    .register((itemGroup) -> itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE));

            // Register the group.
            Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

// Register items to the custom item group.
            ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
                itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE);
                itemGroup.add(ModItems.MUSHROOM_HELMET);
                itemGroup.add(ModItems.MUSHROOM_CHESTPLATE);
                itemGroup.add(ModItems.MUSHROOM_LEGGINGS);
                itemGroup.add(ModItems.MUSHROOM_LEATHER);
                itemGroup.add(ModItems.MUSHROOM_BOOTS);
                itemGroup.add(ModItems.SPORES);


                // ...
            });

            // Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
            CompostingChanceRegistry.INSTANCE.add(ModItems.SUSPICIOUS_SUBSTANCE, 0.3f);
        }


}
