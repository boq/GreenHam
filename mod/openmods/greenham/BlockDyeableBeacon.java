package openmods.greenham;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDyeableBeacon extends BlockBeacon {

	public BlockDyeableBeacon(int blockId) {
		super(blockId);
		setUnlocalizedName("beacon").setLightValue(1.0F).setTextureName("beacon");
	}

	@Override
	public TileEntity createNewTileEntity(World par1World) {
		return new TileEntityDyeableBeacon();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		this.blockIcon = registry.registerIcon(getTextureName());
		Block.beacon.registerIcons(registry);
	}
}
