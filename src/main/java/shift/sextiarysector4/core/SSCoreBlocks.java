package shift.sextiarysector4.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import shift.sextiarysector4.core.block.BlockTank;

@ObjectHolder(SextiarySector4.MOD_ID)
public class SSCoreBlocks {

    @ObjectHolder("animal_oil_block")
    public static Block animalOilBlock;

    @ObjectHolder("tank")
    public static Block tank;


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Register {

        @SubscribeEvent
        public static void onBlockRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here

            IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();
            registry.register(new Block(Block.Properties.create(Material.CLAY).hardnessAndResistance(3.0F, 3.0F)).setRegistryName(SextiarySector4.MOD_ID, "animal_oil_block"));

            registry.register(new BlockTank(Block.Properties.create(Material.GLASS).hardnessAndResistance(3.0F, 3.0F)).setRegistryName(SextiarySector4.MOD_ID, "tank"));


        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            // register a new block here

            IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
            registry.register(new ItemBlock(animalOilBlock, new Item.Properties().group(ItemGroup.FOOD)).setRegistryName(SextiarySector4.MOD_ID, "animal_oil_block"));

            registry.register(new ItemBlock(tank, new Item.Properties().group(ItemGroup.FOOD)).setRegistryName(SextiarySector4.MOD_ID, "tank"));

        }


    }

}
