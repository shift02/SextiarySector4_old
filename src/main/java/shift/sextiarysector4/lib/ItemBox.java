package shift.sextiarysector4.lib;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

/**
 * ItemStackの処理を楽にするラッパークラス
 */
public class ItemBox implements IInventory, Iterable<ItemStack> {

    private final NonNullList<ItemStack> itemStacks;
    private final int limit;

    public ItemBox(int size, int limit) {
        this.itemStacks = NonNullList.withSize(size, ItemStack.EMPTY);
        this.limit = limit;
    }

    @Override
    public int getSizeInventory() {
        return this.itemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.itemStacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int index) {
        return this.itemStacks.get(index);
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.itemStacks, index, count);
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.itemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        this.itemStacks.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return this.limit;
    }

    @Override
    public void markDirty() {
        throw new IllegalStateException();
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        throw new IllegalStateException();
    }

    @Override
    public void openInventory(@Nonnull EntityPlayer player) {
        throw new IllegalStateException();
    }

    @Override
    public void closeInventory(@Nonnull EntityPlayer player) {
        throw new IllegalStateException();
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        throw new IllegalStateException();
    }

    @Override
    public int getField(int id) {
        throw new IllegalStateException();
    }

    @Override
    public void setField(int id, int value) {
        throw new IllegalStateException();
    }

    @Override
    public int getFieldCount() {
        throw new IllegalStateException();
    }

    @Override
    public void clear() {
        this.itemStacks.clear();
    }

    @Override
    @Nonnull
    public ITextComponent getName() {
        throw new IllegalStateException();
    }

    @Override
    public boolean hasCustomName() {
        throw new IllegalStateException();
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        throw new IllegalStateException();
    }

    @Override
    @Nonnull
    public Iterator<ItemStack> iterator() {
        return this.itemStacks.iterator();
    }

}
