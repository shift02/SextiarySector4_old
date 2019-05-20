package shift.sextiarysector4.lib.inventory;

import java.util.function.Function;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFuel extends Slot {

    public Function<ItemStack, Boolean> isItemValidFunction;

    public SlotFuel(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition, Function<ItemStack, Boolean> function) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.isItemValidFunction = function;
    }

    /**
     * trueの場合はアイテムをセットできる
     */
    public boolean isItemValid(ItemStack stack) {
        return this.isItemValidFunction.apply(stack);
    }

}