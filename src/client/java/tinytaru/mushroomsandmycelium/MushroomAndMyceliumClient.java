package tinytaru.mushroomsandmycelium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import tinytaru.mushroomsandmycelium.block.ModBlocks;

public class MushroomAndMyceliumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Register transparent/cutout render layers
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OYSTER_MUSHROOM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPORE_DISH, RenderLayer.getCutout());
	}
}