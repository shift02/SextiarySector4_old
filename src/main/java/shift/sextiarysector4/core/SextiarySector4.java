package shift.sextiarysector4.core;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import shift.sextiarysector4.lib.SSLib;

//@formatter:off
@Mod(
		modid = SextiarySector4.MODID,
		name = "SextiarySector4 Core",
		version = SSLib.VERSION)
//@formatter:on
public class SextiarySector4
{
    public static final String MODID = "sextiarysector4";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
}
