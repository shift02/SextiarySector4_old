package shift.sextiarysector4.core;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class SSCoreItemGroups {

    public static ItemGroup CORE = new ItemGroup("sextiarysector4.core") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(SSCoreBlocks.tank);
        }
    };

}
