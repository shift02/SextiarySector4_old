package shift.sextiarysector4.core;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import shift.sextiarysector4.api.SextiarySector4API;
import shift.sextiarysector4.api.capability.CapabilityMoistureHandler;
import shift.sextiarysector4.api.capability.CapabilityStaminaHandler;
import shift.sextiarysector4.core.capability.AdditionalPlayerData;
import shift.sextiarysector4.core.capability.EntityPlayerManager;
import shift.sextiarysector4.core.gui.tab.TabManager;
import shift.sextiarysector4.core.packet.SSPacketHandler;
import shift.sextiarysector4.lib.SSLib;

//@formatter:off
@Mod(
		modid = SextiarySector4.MODID,
		name = "SextiarySector4 Core",
		version = SSLib.VERSION)
//@formatter:on
public class SextiarySector4 {
    public static final String MODID = "sextiarysector4";
    public static final String VERSION = "1.0.0";
    
    @Mod.Instance(SextiarySector4.MODID)
    public static SextiarySector4 instance;
    
    //@SidedProxy(modId = MODID, clientSide = "shift.sextiarysector4.core.proxy.ClientProxy", serverSide = "shift.sextiarysector4.core.proxy.CommonProxy")
    //public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // some example code
        System.out.println("DIRT BLOCK >> " + Blocks.DIRT.getUnlocalizedName());
        
        // 登録
        CapabilityManager.INSTANCE.register(AdditionalPlayerData.class, new AdditionalPlayerData(),
                AdditionalPlayerData.class);
        
        SextiarySector4API.playerManager = EntityPlayerManager.instance;
        
        SSPacketHandler.init(event);
        
        SSCoreEvents.preInit(event);
        
        CapabilityMoistureHandler.register();
        CapabilityStaminaHandler.register();
        
        SSCoreItems.preInit(event);
        
        TabManager.initTabManager();
        
        SSCorePlayerTabs.initTabs();
        
        SSCoreProxy.getProxy().fmlPreInit();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, SSCoreProxy.getProxy());
        
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        
    }
    
}
