package shift.sextiarysector4.core.gui.tab;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class InventoryTabVanilla extends AbstractTab {
    
    @Override
    public ItemStack getItemStack() {
        return new ItemStack(Blocks.CRAFTING_TABLE);
    }
    
    @Override
    public boolean shouldAddToList() {
        return true;
    }
    
    @Override
    public void onTabClicked() {
        TabManager.openInventoryGui();
    }
    
    @Override
    public String getTabName() {
        return "player.tab.vanilla";
    }
    
}
