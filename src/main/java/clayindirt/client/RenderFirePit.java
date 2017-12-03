package clayindirt.client;

import clayindirt.tile.TileFirePit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderFirePit extends TileEntitySpecialRenderer<TileFirePit> {

	@Override
	public void render(TileFirePit te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te == null) return;
		GlStateManager.pushMatrix();
		int i = te.getWorld().getCombinedLight(te.getPos().up(), 0);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i % 65536, i / 65536);

		GlStateManager.translate(x + 0.5, y, z + 0.5);

		GlStateManager.scale(0.5, 0.5, 0.5);
		
		GlStateManager.translate(-.3, 1, -.3);

		Minecraft.getMinecraft().getRenderItem().renderItem(te.getOutput(), TransformType.FIXED);
		
		GlStateManager.translate(.6, 0, .6);

		Minecraft.getMinecraft().getRenderItem().renderItem(te.getInput(), TransformType.FIXED);

		GlStateManager.glNormal3f(0, 0, 0);

		GlStateManager.popMatrix();
	}

}
