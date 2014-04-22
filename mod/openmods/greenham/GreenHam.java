package openmods.greenham;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;

@Mod(modid = "GreenHam", name = "Green Ham", version = "@VERSION@")
@NetworkMod(serverSideRequired = true, clientSideRequired = false)
public class GreenHam {

	public static int renderId;

	public interface IProxy {
		public void preInit();
	}

	public static class CommonProxy implements IProxy {
		@Override
		public void preInit() {}
	}

	public static class ClientProxy implements IProxy {
		@Override
		public void preInit() {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDyeableBeacon.class, new TileEntityDyeableBeaconRenderer());
			renderId = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new DyeableBeaconRenderer());
		}
	}

	@SidedProxy(clientSide = "openmods.greenham.GreenHam$ClientProxy", serverSide = "openmods.greenham.GreenHam$CommonProxy")
	public static IProxy proxy;

	public static BlockDyeableBeacon beacon;

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		int id = Block.beacon.blockID;
		Block.blocksList[id] = null;
		beacon = new BlockDyeableBeacon(id);

		final Item itemBeacon = new ItemBeacon(beacon.blockID - 256);
		Item.itemsList[beacon.blockID] = itemBeacon;
		GameRegistry.registerItem(itemBeacon, "beacon_alternative");

		Map<String, Class<? extends TileEntity>> nameToClassMap = ReflectionHelper.getPrivateValue(TileEntity.class, null, "nameToClassMap", "field_70326_a");
		nameToClassMap.remove("Beacon");
		nameToClassMap.put("DyeableBeacon", TileEntityDyeableBeacon.class); // compat
		GameRegistry.registerTileEntity(TileEntityDyeableBeacon.class, "Beacon");
		proxy.preInit();
	}
}
