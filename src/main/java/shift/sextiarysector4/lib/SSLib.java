package shift.sextiarysector4.lib;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//@formatter:off
@Mod(
		modid = SSLib.MODID,
		name = "SextiarySector4 Lib",
		version = SSLib.VERSION,
		guiFactory = "shift.sextiarysector4.lib.config.SSConfigGuiFactory")
//@formatter:on
public class SSLib {
    
    public static final String MODID = "sextiarysector4lib";
    public static final String VERSION = "$version";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        
        SSConfig.syncConfig();
        
        SSCreativeTabs.initCreativeTabs();
        
    }
    
}
