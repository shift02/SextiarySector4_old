package shift.sextiarysector4.core.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import shift.sextiarysector4.api.equipment.EquipmentType;
import shift.sextiarysector4.api.equipment.IEquipment;
import shift.sextiarysector4.core.container.InventoryPlayerNext;

public class EquipmentStats {
    
    public final String NBT_ID = "ssequipment";
    
    public InventoryPlayerNext inventory;
    
    public InventoryPlayerNext dummy;
    
    public EquipmentStats() {
        
        inventory = new InventoryPlayerNext();
        dummy = new InventoryPlayerNext();
        
    }
    
    public void onUpdate(EntityPlayer entityPlayer) {
        
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            
            if (inventory.getStackInSlot(i) == null) continue;
            if (!(inventory.getStackInSlot(i).getItem() instanceof IEquipment)) continue;
            
            IEquipment e = (IEquipment) inventory.getStackInSlot(i).getItem();
            
            e.onUpdate(EquipmentType.getEquipmentTypeFromSlot(i), inventory.getStackInSlot(i), entityPlayer.world, entityPlayer, i);
            
        }
        
    }
    
    public boolean isPacket() {
        
        for (int i = 0; i < 4; i++) {
            
            if (!ItemStack.areItemStacksEqual(this.inventory.getStackInSlot(i), dummy.getStackInSlot(i))) {
                
                dummy.setInventorySlotContents(i, inventory.getStackInSlot(i).copy());
                return true;
                
            }
            
        }
        
        return false;
    }
    
    public void writeNBT(NBTTagCompound compound) {
        
        NBTTagCompound nbt = new NBTTagCompound();
        
        inventory.writeToNBT(nbt);
        
        compound.setTag(NBT_ID, nbt);
        
    }
    
    public void readNBT(NBTTagCompound compound) {
        
        if (compound.hasKey(NBT_ID)) {
            inventory.readFromNBT(compound.getCompoundTag(NBT_ID));
        }
        
    }
    
}
