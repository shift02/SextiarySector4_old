package shift.sextiarysector4.core.container;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shift.sextiarysector4.api.equipment.EquipmentType;
import shift.sextiarysector4.api.equipment.IEquipment;

public class SlotEquipment extends Slot {
    
    private EquipmentType equipment;
    
    public SlotEquipment(EquipmentType type, IInventory p_i1824_1_, int p_i1824_2_, int x, int y) {
        super(p_i1824_1_, p_i1824_2_, x, y);
        equipment = type;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return stack.getItem() instanceof IEquipment && ((IEquipment) stack.getItem()).isItemValid(this.equipment, stack);
        
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer player) {
        
        return this.getStack() != null && ((IEquipment) this.getStack().getItem()).canTakeStack(equipment, this.getStack(), player);
    }
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getBackgroundSprite() {
        return equipment.getIcon();
    }
}