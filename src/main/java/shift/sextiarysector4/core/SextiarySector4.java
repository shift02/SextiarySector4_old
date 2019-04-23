package shift.sextiarysector4.core;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import shift.sextiarysector4.core.client.renderer.tileentity.TileEntityTankRenderer;
import shift.sextiarysector4.core.tileentity.TileEntityTank;

@Mod(SextiarySector4.MOD_ID)
public class SextiarySector4 {

    public static final String MOD_ID = "sextiarysector4";

    public SextiarySector4() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

        // do something that can only be done on the client
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class, new TileEntityTankRenderer());

    }

}
