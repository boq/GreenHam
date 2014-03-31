package openmods.greenham;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityDyeableBeacon extends TileEntityBeacon {

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
}
