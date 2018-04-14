package shift.sextiarysector4.core.packet;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import shift.sextiarysector4.core.SextiarySector4;

public class SSPacketHandler {
    
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE
            .newSimpleChannel(SextiarySector4.MODID);
    
    public static void init(FMLPreInitializationEvent event) {
        /*
         * Messageクラスの登録。 第一引数：IMessageHandlerクラス 第二引数：送るMessageクラス
         * 第三引数：登録番号。255個まで 第四引数：ClientとServerのどちらに送るか。送り先
         */
        INSTANCE.registerMessage(MessagePlayerDataHandler.class, PacketPlayerData.class, 0, Side.CLIENT);
        
    }
    
}
