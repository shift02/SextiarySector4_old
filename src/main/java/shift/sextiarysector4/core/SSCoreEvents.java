package shift.sextiarysector4.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import shift.sextiarysector4.api.capability.VanillaFoodHandler;
import shift.sextiarysector4.core.capability.EntityPlayerManager;
import shift.sextiarysector4.core.event.ClientEventHandler;
import shift.sextiarysector4.core.event.CommonEventHandler;
import shift.sextiarysector4.core.event.HUDEventHandler;
import shift.sextiarysector4.core.event.PlayerStatusEventHandler;

public class SSCoreEvents {
    
    public static void preInit(FMLPreInitializationEvent event) {
        
        MinecraftForge.EVENT_BUS.register(EntityPlayerManager.instance);
        
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
        
        MinecraftForge.EVENT_BUS.register(new VanillaFoodHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerStatusEventHandler());
        // FMLCommonHandler.instance().bus().register(EntityPlayerManager.instance);
        
        if (event.getSide().isClient())
            MinecraftForge.EVENT_BUS.register(new HUDEventHandler());
        
        if (event.getSide().isClient()) {
            ClientEventHandler e = new ClientEventHandler();
            MinecraftForge.EVENT_BUS.register(e);
            // FMLCommonHandler.instance().bus().register(e);
        }
        
    }
    
}
