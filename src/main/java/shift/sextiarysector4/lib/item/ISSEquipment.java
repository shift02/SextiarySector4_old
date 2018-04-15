package shift.sextiarysector4.lib.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import shift.sextiarysector4.api.equipment.EquipmentType;

public interface ISSEquipment {
    
    String getTabName(EquipmentType equipment, ItemStack stack, EntityPlayer player);
    
    boolean shouldAddToList(EquipmentType equipment, ItemStack stack, EntityPlayer player);
    
    void onTabClicked(EquipmentType equipment, ItemStack stack, EntityPlayer player);
    
}
