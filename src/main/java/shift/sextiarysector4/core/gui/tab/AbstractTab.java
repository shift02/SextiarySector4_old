package shift.sextiarysector4.core.gui.tab;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractTab {
    
    @SideOnly(Side.CLIENT)
    public abstract void onTabClicked();
    
    public abstract ItemStack getItemStack();
    
    @SideOnly(Side.CLIENT)
    public abstract String getTabName();
    
    @SideOnly(Side.CLIENT)
    public abstract boolean shouldAddToList();
    
}
