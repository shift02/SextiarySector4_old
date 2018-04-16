/*
* 作成者: Shift02
* 作成日: 2016/03/11 - 17:49:53
*/
package shift.sextiarysector4.core.asm.vanilla;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.ItemStack;
import shift.sextiarysector4.core.capability.EntityPlayerManager;
import shift.sextiarysector4.core.capability.EquipmentStats;

public class InventoryPlayerMethod {
    
    public static ItemStack armorItemInSlot(int i, AbstractClientPlayer player) {
        
        EquipmentStats e = EntityPlayerManager.getEquipmentStats(player);
        
        if (e == null) return null;
        
        int index = 3 - i;
        
        ItemStack itemstack = e.inventory.getStackInSlot(index);
        
        if (itemstack != null) return itemstack;
        
        return player.inventory.armorItemInSlot(i);
        
    }
    
}
