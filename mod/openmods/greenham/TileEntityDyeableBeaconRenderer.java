package openmods.greenham;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class TileEntityDyeableBeaconRenderer extends TileEntitySpecialRenderer {

	private static final double INNER_BEAM_RADIUS = 0.2D;
	private static final ResourceLocation BEACON_TEXTURE = new ResourceLocation("textures/entity/beacon_beam.png");

	public void renderTileEntityBeaconAt(TileEntityDyeableBeacon beacon, double x, double y, double z, float partialTickTime) {
		final float f1 = beacon.func_82125_v_();

		if (f1 <= 0) return;

		bindTexture(BEACON_TEXTURE);

		final float rotationTime = beacon.getWorldObj().getTotalWorldTime() + partialTickTime;

		final float f3 = -rotationTime * 0.2F - MathHelper.floor_float(-rotationTime * 0.1F);
		renderInnerBeam(x, y, z, rotationTime, f1, f3, beacon.color());
		renderOuterBeam(x, y, z, f1, f3, beacon.color());

	}

	private static void renderInnerBeam(double x, double y, double z, double rotationTime, double f1, double f3, int color) {
		final double rotationPhase = rotationTime * -0.0375;

		final double x1 = 0.5 + Math.cos(rotationPhase + 1.0 / 4.0 * Math.PI) * INNER_BEAM_RADIUS;
		final double y1 = 0.5 + Math.sin(rotationPhase + 1.0 / 4.0 * Math.PI) * INNER_BEAM_RADIUS;

		final double x2 = 0.5 + Math.cos(rotationPhase + 3.0 / 4.0 * Math.PI) * INNER_BEAM_RADIUS;
		final double y2 = 0.5 + Math.sin(rotationPhase + 3.0 / 4.0 * Math.PI) * INNER_BEAM_RADIUS;

		final double x3 = 0.5 + Math.cos(rotationPhase + 5.0 / 4.0 * Math.PI) * INNER_BEAM_RADIUS;
		final double y3 = 0.5 + Math.sin(rotationPhase + 5.0 / 4.0 * Math.PI) * INNER_BEAM_RADIUS;

		final double x4 = 0.5 + Math.cos(rotationPhase + 7.0 / 4.0 * Math.PI) * INNER_BEAM_RADIUS;
		final double y4 = 0.5 + Math.sin(rotationPhase + 7.0 / 4.0 * Math.PI) * INNER_BEAM_RADIUS;

		final double maxY = 256.0F * f1;
		final double minY = 0;

		final double minV = -1.0F + f3;
		final double maxV = maxY * (0.5D / INNER_BEAM_RADIUS) + minV;

		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_I(color, 32);
		tessellator.addVertexWithUV(x + x2, y + maxY, z + y2, 1.0D, maxV);
		tessellator.addVertexWithUV(x + x2, y + minY, z + y2, 1.0D, minV);
		tessellator.addVertexWithUV(x + x1, y + minY, z + y1, 0.0D, minV);
		tessellator.addVertexWithUV(x + x1, y + maxY, z + y1, 0.0D, maxV);
		tessellator.addVertexWithUV(x + x4, y + maxY, z + y4, 1.0D, maxV);
		tessellator.addVertexWithUV(x + x4, y + minY, z + y4, 1.0D, minV);
		tessellator.addVertexWithUV(x + x3, y + minY, z + y3, 0.0D, minV);
		tessellator.addVertexWithUV(x + x3, y + maxY, z + y3, 0.0D, maxV);
		tessellator.addVertexWithUV(x + x1, y + maxY, z + y1, 1.0D, maxV);
		tessellator.addVertexWithUV(x + x1, y + minY, z + y1, 1.0D, minV);
		tessellator.addVertexWithUV(x + x4, y + minY, z + y4, 0.0D, minV);
		tessellator.addVertexWithUV(x + x4, y + maxY, z + y4, 0.0D, maxV);
		tessellator.addVertexWithUV(x + x3, y + maxY, z + y3, 1.0D, maxV);
		tessellator.addVertexWithUV(x + x3, y + minY, z + y3, 1.0D, minV);
		tessellator.addVertexWithUV(x + x2, y + minY, z + y2, 0.0D, minV);
		tessellator.addVertexWithUV(x + x2, y + maxY, z + y2, 0.0D, maxV);

		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		tessellator.draw();
	}

	private static void renderOuterBeam(double x, double y, double z, double f1, double f3, int color) {
		final double maxY = 256.0 * f1;
		final double minY = 0;

		final double minV = -1.0 + f3;
		final double maxV = 256.0F * f1 + minV;

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_I(color, 32);
		tessellator.addVertexWithUV(x + 0.2, y + maxY, z + 0.2, 1.0, maxV);
		tessellator.addVertexWithUV(x + 0.2, y + minY, z + 0.2, 1.0, minV);
		tessellator.addVertexWithUV(x + 0.8, y + minY, z + 0.2, 0.0, minV);
		tessellator.addVertexWithUV(x + 0.8, y + maxY, z + 0.2, 0.0, maxV);
		tessellator.addVertexWithUV(x + 0.8, y + maxY, z + 0.8, 1.0, maxV);
		tessellator.addVertexWithUV(x + 0.8, y + minY, z + 0.8, 1.0, minV);
		tessellator.addVertexWithUV(x + 0.2, y + minY, z + 0.8, 0.0, minV);
		tessellator.addVertexWithUV(x + 0.2, y + maxY, z + 0.8, 0.0, maxV);
		tessellator.addVertexWithUV(x + 0.8, y + maxY, z + 0.2, 1.0, maxV);
		tessellator.addVertexWithUV(x + 0.8, y + minY, z + 0.2, 1.0, minV);
		tessellator.addVertexWithUV(x + 0.8, y + minY, z + 0.8, 0.0, minV);
		tessellator.addVertexWithUV(x + 0.8, y + maxY, z + 0.8, 0.0, maxV);
		tessellator.addVertexWithUV(x + 0.2, y + maxY, z + 0.8, 1.0, maxV);
		tessellator.addVertexWithUV(x + 0.2, y + minY, z + 0.8, 1.0, minV);
		tessellator.addVertexWithUV(x + 0.2, y + minY, z + 0.2, 0.0, minV);
		tessellator.addVertexWithUV(x + 0.2, y + maxY, z + 0.2, 0.0, maxV);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTickTime) {
		this.renderTileEntityBeaconAt((TileEntityDyeableBeacon)te, x, y, z, partialTickTime);
	}

}
