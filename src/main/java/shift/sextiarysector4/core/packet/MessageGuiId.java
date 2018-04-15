package shift.sextiarysector4.core.packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import shift.sextiarysector4.core.SextiarySector4;

public class MessageGuiId implements IMessageHandler<PacketGuiId, IMessage> {
    
    @Override
    public IMessage onMessage(PacketGuiId message, MessageContext ctx) {
        
        EntityPlayerMP p = ctx.getServerHandler().player;
        
        //CustomPlayerData data = EntityPlayerManager.getCustomPlayerData(p);
        
        //data.loadNBTData(message.getData());
        
        //System.out.println("AAAAA" + message.getData().getInteger("gui"));
        
        int i = message.getData().getInteger("gui");
        
        p.openGui(SextiarySector4.instance, i, p.world, (int) p.posX, (int) p.posY, (int) p.posZ);
        
        return null;
        
    }
}
