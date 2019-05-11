package shift.sextiarysector4.core.tileentity;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import shift.sextiarysector4.core.SSCoreBlocks;
import shift.sextiarysector4.core.SextiarySector4;
import shift.sextiarysector4.lib.ItemBox;
import shift.sextiarysector4.lib.tileentity.CustomName;
import shift.sextiarysector4.lib.util.NamespaceHelper;

public class TileEntityFreezer extends TileEntityLockable implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickable {

    private ItemBox itemBox;

    //それぞれの方角からどのスロットにアクセスできるかの設定
    private static final int[] SLOTS_TOP = new int[]{0};
    private static final int[] SLOTS_BOTTOM = new int[]{2, 1};
    private static final int[] SLOTS_SIDES = new int[]{1};

    private int furnaceBurnTime;

    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;


    private CustomName customName;

    //経験値を反映させるための記録
    private final Map<ResourceLocation, Integer> recipeUseCounts = Maps.newHashMap();

    public TileEntityFreezer() {
        super(SSCoreBlocks.tileEntityFreezer);

        this.itemBox = new ItemBox(3, 64);

        this.customName = new CustomName("container.freezer");

    }

    //Freezer固有
    @Override
    public void tick() {

    }


    public static boolean isItemFuel(ItemStack stack) {
        return true;//getItemBurnTime(stack) > 0;
    }

    //レシピ関係
    @Override
    public void fillStackedContents(@Nonnull RecipeItemHelper helper) {
        for (ItemStack itemstack : this.itemBox) {
            helper.accountStack(itemstack);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe recipe) {

        if (recipe == null) {
            return;
        }

        if (this.recipeUseCounts.containsKey(recipe.getId())) {
            this.recipeUseCounts.put(recipe.getId(), this.recipeUseCounts.get(recipe.getId()) + 1);
        } else {
            this.recipeUseCounts.put(recipe.getId(), 1);
        }
    }

    @Nullable
    @Override
    public IRecipe getRecipeUsed() {
        return null;
    }

    public Map<ResourceLocation, Integer> getRecipeUseCounts() {
        return this.recipeUseCounts;
    }


    //Inventory
    @Override
    public int getSizeInventory() {
        return this.itemBox.getSizeInventory();
    }

    @Override
    public boolean isEmpty() {
        return this.itemBox.isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int index) {
        return this.itemBox.getStackInSlot(index);
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int index, int count) {
        return this.itemBox.decrStackSize(index, count);
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int index) {
        return this.itemBox.removeStackFromSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        this.itemBox.setInventorySlotContents(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return this.itemBox.getInventoryStackLimit();
    }

    /**
     * GUIにアクセスできるかどうか
     *
     * @param player GUIを開くプレイヤー
     * @return trueの場合はGUIが開く
     */
    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void openInventory(@Nonnull EntityPlayer player) {

    }

    @Override
    public void closeInventory(@Nonnull EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.itemBox.getStackInSlot(1);
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 4;
    }

    @Override
    public void clear() {
        this.itemBox.clear();
    }

    @Override
    @Nonnull
    public Container createContainer(@Nonnull InventoryPlayer playerInventory, @Nonnull EntityPlayer playerIn) {
        return null;
    }

    @Override
    @Nonnull
    public String getGuiID() {
        return NamespaceHelper.getLocation(SextiarySector4.MOD_ID, "freezer");
    }

    @Override
    @Nonnull
    public ITextComponent getName() {
        return this.customName.getName();
    }

    @Override
    public boolean hasCustomName() {
        return this.customName.hasCustomName();
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return this.customName.getCustomName();
    }

    public void setCustomName(@Nullable ITextComponent name) {
        this.customName.setCustomName(name);
    }

    //Sideインベントリ
    @Override
    @Nonnull
    public int[] getSlotsForFace(@Nonnull EnumFacing side) {

        switch (side) {
            case DOWN:
                return SLOTS_BOTTOM;
            case UP:
                return SLOTS_TOP;
            default:
                return SLOTS_SIDES;
        }

    }

    @Override
    public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nullable EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull EnumFacing direction) {

        if (direction == EnumFacing.DOWN && index == 1) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }
}