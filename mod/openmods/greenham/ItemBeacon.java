package openmods.greenham;

import net.minecraft.item.ItemBlock;

public class ItemBeacon extends ItemBlock {

	public ItemBeacon(int itemId) {
		super(itemId);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
