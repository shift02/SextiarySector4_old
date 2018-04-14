package shift.sextiarysector4.core.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import shift.sextiarysector4.core.capability.AdditionalPlayerData;

public class PacketPlayerData implements IMessage {
    
    private NBTTagCompound data;
    
    public PacketPlayerData() {
        
    }
    
    public PacketPlayerData(AdditionalPlayerData data) {
        
        NBTTagCompound nbt = data.serializeNBT();
        this.setData(nbt);
        
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        setData(ByteBufUtils.readTag(buf));
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, getData());
    }
    
    public NBTTagCompound getData() {
        return data;
    }
    
    public void setData(NBTTagCompound data) {
        this.data = data;
    }
    
}