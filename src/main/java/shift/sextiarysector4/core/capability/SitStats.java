package shift.sextiarysector4.core.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import shift.sextiarysector4.core.packet.PacketPlayerData;
import shift.sextiarysector4.core.packet.SSPacketHandler;

public class SitStats {
    
    public boolean jump = false;
    
    public boolean prevSit = false;
    
    public boolean isSit = false;
    
    public void onUpdate(AdditionalPlayerData data, EntityPlayer entityPlayer) {
        
        if (prevSit != isSit) {
            
            isSit = prevSit;
            
            SSPacketHandler.INSTANCE.sendTo(new PacketPlayerData(data), (EntityPlayerMP) entityPlayer);
            
            EntityPlayerManager.instance.sendOtherPlayer(entityPlayer);
            
        }
        
    }
    
    public void readNBT(NBTTagCompound par1NBTTagCompound) {
        
        isSit = par1NBTTagCompound.getBoolean("sit");
        
    }
    
    public void writeNBT(NBTTagCompound par1NBTTagCompound) {
        
        par1NBTTagCompound.setBoolean("sit", isSit);
        
    }
}
