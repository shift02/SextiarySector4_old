package shift.sextiarysector4.core.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import shift.sextiarysector4.core.packet.PacketPlayerData;
import shift.sextiarysector4.core.packet.SSPacketHandler;

public class AdditionalPlayerData implements IStorage<AdditionalPlayerData>, ICapabilitySerializable<NBTTagCompound> {
    
    @CapabilityInject(AdditionalPlayerData.class)
    private static final Capability<AdditionalPlayerData> ADDITIONAL_CAP = null;
    
    /** 水分 */
    private MoistureStats moisture;
    
    /** スタミナ */
    private StaminaStats stamina;
    
    /** 座る */
    private SitStats sit;
    
    public AdditionalPlayerData() {
        this.moisture = new MoistureStats();
        this.stamina = new StaminaStats();
        this.sit = new SitStats();
    }
    
    public void onUpdateEntity(EntityPlayer entityPlayer) {
        
        if (moisture.isPacket() || stamina.isPacket()) {
            SSPacketHandler.INSTANCE.sendTo(new PacketPlayerData(this), (EntityPlayerMP) entityPlayer);
        }
        
        this.moisture.onUpdate(entityPlayer);
        this.stamina.onUpdate(entityPlayer);
        
        this.sit.onUpdate(this, entityPlayer);
        
    }
    
    @Override
    public NBTTagCompound serializeNBT() {
        
        NBTTagCompound nbt = new NBTTagCompound();
        this.moisture.writeNBT(nbt);
        
        this.stamina.writeNBT(nbt);
        
        this.sit.writeNBT(nbt);
        
        return nbt;
    }
    
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.moisture.readNBT(nbt);
        
        this.stamina.readNBT(nbt);
        
        this.sit.readNBT(nbt);
        
    }
    
    public MoistureStats getMoisture() {
        return moisture;
    }
    
    private void setMoisture(MoistureStats moisture) {
        this.moisture = moisture;
    }
    
    public StaminaStats getStamina() {
        return stamina;
    }
    
    private void setStamina(StaminaStats stamina) {
        this.stamina = stamina;
    }
    
    public SitStats getSit() {
        return sit;
    }
    
    public void setSit(SitStats sit) {
        this.sit = sit;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == this.ADDITIONAL_CAP)
            return true;
        return false;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == ADDITIONAL_CAP ? ADDITIONAL_CAP.<T> cast(this) : null;
    }
    
    @Override
    public NBTBase writeNBT(Capability<AdditionalPlayerData> capability, AdditionalPlayerData instance,
            EnumFacing side) {
        return this.serializeNBT();
    }
    
    @Override
    public void readNBT(Capability<AdditionalPlayerData> capability, AdditionalPlayerData instance, EnumFacing side,
            NBTBase nbt) {
        this.deserializeNBT((NBTTagCompound) nbt);
        
    }
    
}