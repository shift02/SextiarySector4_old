package shift.sextiarysector4.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

public class SSCoreItems {

    public static Item animalOil;


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Register {

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            // register a new block here

            IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
            registry.register(new Item(new Item.Properties().group(ItemGroup.FOOD)).setRegistryName(SextiarySector4.MOD_ID, "animal_oil"));

        }

    }

}
