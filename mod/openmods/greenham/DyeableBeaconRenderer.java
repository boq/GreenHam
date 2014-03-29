package openmods.greenham;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class DyeableBeaconRenderer implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		final int color = BlockDyeableBeacon.colorFromMeta(metadata);

		renderer.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
		renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.obsidian));
		renderFaces(renderer, block, 0xFFFFFF);

		renderer.setRenderBounds(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.875D, 0.8125D);
		renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.beacon));
		renderFaces(renderer, block, color);

		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.glass));
		renderFaces(renderer, block, 0xFFFFFF);

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		renderer.clearOverrideBlockTexture();
	}

	private static void renderFaces(RenderBlocks renderer, Block block, int color) {
		final Tessellator tessellator = Tessellator.instance;

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(color);
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, 0));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(color);
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, 0));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(color);
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, 0));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(color);
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, 0));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(color);
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, 0));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(color);
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, 0));
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if (!(block instanceof BlockDyeableBeacon)) return false;
		BlockDyeableBeacon beacon = (BlockDyeableBeacon)block;

		renderer.blockAccess = world;

		beacon.skipColor = true;
		renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.glass));
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.renderAllFaces = true;
		renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.obsidian));
		renderer.setRenderBounds(0.125D, 0.0062500000931322575D, 0.125D, 0.875D, 0.1875F, 0.875D);
		renderer.renderStandardBlock(block, x, y, z);

		beacon.skipColor = false;

		renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.beacon));
		renderer.setRenderBounds(0.1875D, 0.1875F, 0.1875D, 0.8125D, 0.875D, 0.8125D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.renderAllFaces = false;
		renderer.clearOverrideBlockTexture();
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return GreenHam.renderId;
	}

}
