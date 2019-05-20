package shift.sextiarysector4.lib.recipe;

import net.minecraft.item.ItemStack;

public class FreezerFuel {

    public static boolean isItemFuel(ItemStack stack) {
        return getItemFreezeTime(stack) > 0;
    }

    public static int getItemFreezeTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            return 200;
        }
    }

}
