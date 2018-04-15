package shift.sextiarysector4.core.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import shift.sextiarysector4.api.equipment.EquipmentType;
import shift.sextiarysector4.api.equipment.IEquipment;
import shift.sextiarysector4.lib.container.ItemBox;

public class InventoryPlayerNext implements IInventory {
    
    ItemBox items = new ItemBox("Base", 20, 1);
    
    //private final EntityPlayer player;
    
    public InventoryPlayerNext() {
        //this.player = player;
    }
    
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return items.decrStackSize(index, count);
    }
    
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return items.removeStackFromSlot(index);
    }
    
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        items.setInventorySlotContents(index, stack);
    }
    
    @Override
    public int getSizeInventory() {
        return items.getSizeInventory();
    }
    
    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    @Override
    public ItemStack getStackInSlot(int index) {
        return items.getStackInSlot(index);
    }
    
    @Override
    public String getName() {
        return "container.inventory";
    }
    
    @Override
    public boolean hasCustomName() {
        return false;
    }
    
    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public int getInventoryStackLimit() {
        return this.getInventoryStackLimit();
    }
    
    @Override
    public void markDirty() {
        this.items.onInventoryChanged();
    }
    
    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }
    
    @Override
    public void openInventory(EntityPlayer player) {
        this.markDirty();
    }
    
    @Override
    public void closeInventory(EntityPlayer player) {
        this.markDirty();
    }
    
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }
    
    @Override
    public int getField(int id) {
        return 0;
    }
    
    @Override
    public void setField(int id, int value) {
    }
    
    @Override
    public int getFieldCount() {
        return 0;
    }
    
    @Override
    public void clear() {
        
        items.clear();
        
    }
    
    //Other
    public void dropAllItems(EntityPlayer player) {
        int i;
        
        for (i = 0; i < this.items.getSizeInventory(); ++i) {
            
            ItemStack item = this.items.getStackInSlot(i);
            
            if (item.isEmpty()) continue;
            
            if (item.getItem() instanceof IEquipment) {
                
                if (!((IEquipment) item.getItem()).canDrop(EquipmentType.getEquipmentTypeFromSlot(i), item, player)) continue;
                player.dropItem(item, true, false);
                this.items.setInventorySlotContents(i, ItemStack.EMPTY);
                
            } else {
                player.dropItem(item, true, false);
                this.items.setInventorySlotContents(i, ItemStack.EMPTY);
            }
            
        }
        
    }
    
    //NBT
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        this.items.writeToNBT(nbt);
        ;
        return nbt;
    }
    
    public void readFromNBT(NBTTagCompound nbt) {
        this.items.readFromNBT(nbt);
    }
    
}
