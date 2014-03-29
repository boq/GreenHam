package openmods.greenham;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityDyeableBeacon extends TileEntityBeacon {

	private static final String TAG_COLOR = "Color";
	private static final int DEFAULT_COLOR = 0x00FF00;

	private int color = DEFAULT_COLOR;

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey(TAG_COLOR)) color = tag.getInteger(TAG_COLOR);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger(TAG_COLOR, color);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	public int color() {
		return color;
	}

}
