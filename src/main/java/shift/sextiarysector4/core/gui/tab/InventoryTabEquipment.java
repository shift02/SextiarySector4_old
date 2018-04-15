package shift.sextiarysector4.core.gui.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import shift.sextiarysector4.api.equipment.EquipmentType;
import shift.sextiarysector4.core.capability.EntityPlayerManager;
import shift.sextiarysector4.lib.item.ISSEquipment;

public abstract class InventoryTabEquipment extends AbstractTab {
    
    private static Minecraft mc = FMLClientHandler.instance().getClient();
    
    private EquipmentType type;
    private ItemStack item;
    
    public InventoryTabEquipment(EquipmentType type, ItemStack item) {
        this.type = type;
        this.item = item;
    }
    
    private ItemStack getSlotItem() {
        
        for (int i : type.getSlots()) {
            
            ItemStack slot = EntityPlayerManager.getEquipmentStats(mc.player).inventory.getStackInSlot(i);
            
            if (slot != null && slot.isItemEqual(item)) {
                return slot;
            }
            
        }
        
        return null;
    }
    
    @Override
    public void onTabClicked() {
        
        if (getSlotItem() == null) {
            return;
        }
        
        ((ISSEquipment) this.getItemStack().getItem()).onTabClicked(type, getSlotItem(), mc.player);
        
    }
    
    @Override
    public ItemStack getItemStack() {
        return item;
    }
    
    @Override
    public boolean shouldAddToList() {
        
        if (getSlotItem() == null) {
            return false;
        }
        
        return ((ISSEquipment) this.getItemStack().getItem()).shouldAddToList(type, getSlotItem(), mc.player);
    }
    
    @Override
    public String getTabName() {
        
        if (getSlotItem() == null) {
            return "player.tab." + "";
        }
        
        return "player.tab." + ((ISSEquipment) this.getItemStack().getItem()).getTabName(type, getSlotItem(), mc.player);
    }
    
}
