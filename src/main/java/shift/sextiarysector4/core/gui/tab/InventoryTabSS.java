package shift.sextiarysector4.core.gui.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import shift.sextiarysector4.core.packet.PacketGuiId;
import shift.sextiarysector4.core.packet.SSPacketHandler;

public class InventoryTabSS extends AbstractTab {
    
    private static Minecraft mc = FMLClientHandler.instance().getClient();
    
    @Override
    public void onTabClicked() {
        
        SSPacketHandler.INSTANCE.sendToServer(new PacketGuiId(200));
        
        //mc.thePlayer.openGui(SextiarySector.instance, 200, mc.thePlayer.worldObj, (int)mc.thePlayer.posX, (int)mc.thePlayer.posY, (int)mc.thePlayer.posZ);
    }
    
    @Override
    public ItemStack getItemStack() {
        return new ItemStack(Items.CHAINMAIL_CHESTPLATE);
    }
    
    @Override
    public boolean shouldAddToList() {
        return true;
    }
    
    @Override
    public String getTabName() {
        return "player.tab.equipment";
    }
    
}
