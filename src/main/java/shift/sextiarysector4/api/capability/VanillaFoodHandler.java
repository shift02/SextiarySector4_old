package shift.sextiarysector4.api.capability;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shift.sextiarysector4.api.SextiarySector4API;
import shift.sextiarysector4.api.capability.template.MoistureHandler;
import shift.sextiarysector4.api.capability.template.StaminaHandler;

/**
 * バニラのアイテムにステータスを追加するためのHandler。<br>
 * 既存のアイテムに追加するためのサンプル
 * 
 * @author Shift02ss
 *
 */
public class VanillaFoodHandler {
    
    public ResourceLocation moisture = new ResourceLocation(SextiarySector4API.MODID, "IMoistureHandler");
    public ResourceLocation stamina = new ResourceLocation(SextiarySector4API.MODID, "IStaminaHandler");
    
    /**バニラの食べ物に水分とスタミナのステータスを追加*/
    @SubscribeEvent
    public void addStatus(AttachCapabilitiesEvent<ItemStack> evt) {
        
        Item item = evt.getObject().getItem();
        ItemStack food = evt.getObject();
        if (food == null)
            return;
        if (item == null)
            return;
        
        //水入り瓶
        if (item == Items.POTIONITEM && item.getDamage(food) == 0) {
            
            evt.addCapability(moisture, new MoistureHandler(3, 0, 0));
        }
        
        //きのこシチュー
        if (item == Items.MUSHROOM_STEW) {
            
            evt.addCapability(moisture, new MoistureHandler(2, 2, 0));
            
        }
        
        //鳥
        if (item == Items.CHICKEN) {
            evt.addCapability(moisture, new MoistureHandler(1, 0, 0));
        }
        
        if (item == Items.COOKED_CHICKEN) {
            
            evt.addCapability(moisture, new MoistureHandler(0, 0, 7.2f));
            evt.addCapability(stamina, new StaminaHandler(4, 2, 0));
        }
        
        //豚
        if (item == Items.PORKCHOP) {
            
            evt.addCapability(moisture, new MoistureHandler(1, 0, 0));
        }
        
        if (item == Items.COOKED_PORKCHOP) {
            
            evt.addCapability(moisture, new MoistureHandler(0, 0, 8.2f));
            evt.addCapability(stamina, new StaminaHandler(5, 1, 0));
        }
        
        //牛
        if (item == Items.BEEF) {
            
            evt.addCapability(moisture, new MoistureHandler(1, 0, 0));
        }
        
        if (item == Items.COOKED_BEEF) {
            
            evt.addCapability(moisture, new MoistureHandler(0, 0, 10.2f));
            evt.addCapability(stamina, new StaminaHandler(7, 4, 0));
            
        }
        
        //魚
        if (item == Items.FISH) {
            
            evt.addCapability(moisture, new MoistureHandler(4, 2, 0));
            
        }
        
        if (item == Items.COOKED_FISH) {
            
            evt.addCapability(moisture, new MoistureHandler(0, 0, 5.2f));
            evt.addCapability(stamina, new StaminaHandler(6, 2, 0));
            
        }
        
        //リンゴ
        if (item == Items.APPLE) {
            
            evt.addCapability(moisture, new MoistureHandler(1, 0.3f, 0));
            
        }
        
        //スイカ
        if (item == Items.MELON) {
            
            evt.addCapability(moisture, new MoistureHandler(1, 1.0f, 0));
            
        }
        
        //パン
        if (item == Items.BREAD) {
            
            evt.addCapability(moisture, new MoistureHandler(0, 0, 5.2f));
            evt.addCapability(stamina, new StaminaHandler(6, 4, 0));
        }
        
        //牛乳
        if (item == Items.MILK_BUCKET) {
            
            evt.addCapability(moisture, new MoistureHandler(4, 4.0f, 0));
            
        }
        
    }
    
}
