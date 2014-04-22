package openmods.greenham;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.AxisAlignedBB;
import openperipheral.api.*;
import cpw.mods.fml.common.Optional;

public class TileEntityDyeableBeacon extends TileEntityBeacon {

	private static final String OPEN_PERIPHERAL_CORE_MODID = "OpenPeripheralCore";

	private static final String TAG_COLOR = "Color";

	private Integer color;

	public TileEntityDyeableBeacon() {}

	public TileEntityDyeableBeacon(int color) {
		this.color = color;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey(TAG_COLOR)) color = tag.getInteger(TAG_COLOR);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		if (color != null) tag.setInteger(TAG_COLOR, color);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	public int getColor() {
		if (color == null) color = BlockDyeableBeacon.colorFromMeta(getBlockMetadata());
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@OnTick
	@Freeform
	@LuaCallable
	@Alias("setBeamColour")
	@Optional.Method(modid = OPEN_PERIPHERAL_CORE_MODID)
	public void setBeamColor(@Arg(type = LuaType.NUMBER, name = "color", description = "New color (RGB)") int color) {
		this.color = color;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		onInventoryChanged();
	}

	@OnTick
	@Freeform
	@LuaCallable
	@Alias("setBaseColour")
	@Optional.Method(modid = OPEN_PERIPHERAL_CORE_MODID)
	public void setBaseColor(@Arg(type = LuaType.NUMBER, name = "color", description = "New color (0-16)") int color,
			@Arg(type = LuaType.BOOLEAN, name = "resetBeam", description = "Should beam be reset to base color") @Optionals Boolean resetBeam) {
		final int meta = color & 0xF;
		final boolean shouldUpdateTile = resetBeam == Boolean.TRUE;

		if (shouldUpdateTile) setColor(BlockDyeableBeacon.colorFromMeta(meta));
		boolean updateFromMeta = worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 3);
		if (!updateFromMeta && shouldUpdateTile) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
