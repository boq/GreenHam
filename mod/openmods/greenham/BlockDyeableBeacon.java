package openmods.greenham;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import openmods.greenham.ColorUtils.ColorMeta;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDyeableBeacon extends BlockBeacon {

	public BlockDyeableBeacon(int blockId) {
		super(blockId);
		setUnlocalizedName("beacon").setLightValue(1.0F).setTextureName("beacon");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDyeableBeacon();
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityDyeableBeacon(colorFromMeta(metadata));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;

		final InventoryPlayer inv = player.inventory;
		ItemStack held = inv.getCurrentItem();
		if (held != null) {
			ColorMeta color = ColorUtils.stackToColor(held);
			if (color != null) {
				int currentMeta = world.getBlockMetadata(x, y, z);
				int newMeta = colorToMeta(color);

				if (newMeta != currentMeta) {
					world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);

					TileEntity te = world.getBlockTileEntity(x, y, z);
					if (te instanceof TileEntityDyeableBeacon) {
						((TileEntityDyeableBeacon)te).setColor(color.rgb);
					}

					if (!player.capabilities.isCreativeMode && --held.stackSize <= 0) {
						inv.setInventorySlotContents(inv.currentItem, null);
					}
				}

				return true;
			}
		}

		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		this.blockIcon = registry.registerIcon(getTextureName());
		Block.beacon.registerIcons(registry);
	}

	@Override
	public int getRenderType() {
		return GreenHam.renderId;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(int id, CreativeTabs tab, List result) {
		for (ColorMeta meta : ColorUtils.getAllColors())
			result.add(new ItemStack(this, 1, meta.vanillaId));
	}

	public boolean skipColor;

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		if (skipColor) return 0xFFFFFF;

		int meta = world.getBlockMetadata(x, y, z);
		return colorFromMeta(meta);
	}

	public static int colorFromMeta(int meta) {
		ColorMeta color = ColorUtils.vanillaToColor(~meta & 0xF); // hey, don't look at me like that, I'm doing same thing as mojang
		return color != null? color.rgb : 0xFFFFFF;
	}

	public static int colorToMeta(ColorMeta meta) {
		return ~meta.vanillaId & 0xF;
	}
}
