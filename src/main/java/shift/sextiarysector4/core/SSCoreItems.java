package shift.sextiarysector4.core;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shift.sextiarysector4.api.SextiarySector4API;
import shift.sextiarysector4.api.capability.ItemDrink;

public class SSCoreItems {
    
    public static Item drinkingWaterBottle;
    public static String MODID = SextiarySector4.MODID;
    
    public static void preInit(FMLPreInitializationEvent event) {
        
        drinkingWaterBottle = new ItemDrink(4, 4, 0);
        drinkingWaterBottle.setCreativeTab(SextiarySector4API.TabSSCore);
        drinkingWaterBottle.setMaxStackSize(1).setUnlocalizedName("as." + "drinking_water_bottle");
        ForgeRegistries.ITEMS.register(drinkingWaterBottle.setRegistryName(MODID, "DrinkingWaterBottle"));
        if (event.getSide().isClient()) {
            ModelLoader.setCustomModelResourceLocation(drinkingWaterBottle, 0,
                    new ModelResourceLocation(MODID + ":" + "bottle_drinking_water", "inventory"));
        }
        
        GameRegistry.addSmelting(new ItemStack(Items.POTIONITEM), new ItemStack(drinkingWaterBottle), 0.1f);
        
    }
    
}
