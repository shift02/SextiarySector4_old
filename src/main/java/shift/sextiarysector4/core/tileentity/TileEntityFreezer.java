package shift.sextiarysector4.core.tileentity;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkHooks;
import shift.sextiarysector4.core.SSCoreBlocks;
import shift.sextiarysector4.core.SextiarySector4;
import shift.sextiarysector4.core.block.BlockFreezer;
import shift.sextiarysector4.inventory.ContainerFreezer;
import shift.sextiarysector4.lib.ItemBox;
import shift.sextiarysector4.lib.recipe.FreezerFuel;
import shift.sextiarysector4.lib.tileentity.CustomName;
import shift.sextiarysector4.lib.util.NamespaceHelper;

public class TileEntityFreezer extends TileEntityLockable implements IFeatureBlock, ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickable {

    public static String GUI_ID = NamespaceHelper.getLocation(SextiarySector4.MOD_ID, "freezer");

    private ItemBox itemBox;

    //それぞれの方角からどのスロットにアクセスできるかの設定 0 材料, 1 燃料, 2 完成品
    private static final int[] SLOTS_TOP = new int[]{1};
    private static final int[] SLOTS_BOTTOM = new int[]{2, 0};
    private static final int[] SLOTS_SIDES = new int[]{0};

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

        this.customName = new CustomName("container.sextiarysector4.freezer");

    }

    //Freezer固有
    @Override
    public void tick() {

        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote) {

            System.out.println(this.cookTime + ":" + this.totalCookTime);

            ItemStack itemstack = this.itemBox.getStackInSlot(1);
            if (this.isBurning() || !itemstack.isEmpty() && !this.itemBox.getStackInSlot(0).isEmpty()) {
                IRecipe irecipe = this.getCurrentRecipe();
                if (!this.isBurning() && this.canSmelt(irecipe)) {
                    this.furnaceBurnTime = FreezerFuel.getItemFreezeTime(itemstack);
                    this.currentItemBurnTime = this.furnaceBurnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (itemstack.hasContainerItem()) {
                            this.itemBox.setInventorySlotContents(1, itemstack.getContainerItem());
                        } else if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                Item item1 = item.getContainerItem();
                                this.itemBox.setInventorySlotContents(1, item1 == null ? ItemStack.EMPTY : new ItemStack(item1));
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(irecipe)) {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime();
                        this.smeltItem(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(BlockFreezer.LIT, this.isBurning()), 3);
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    /**
     * Furnace isBurning
     */
    private boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    private int getCookTime() {
        FurnaceRecipe furnacerecipe = this.getCurrentRecipe();
        return furnacerecipe != null ? furnacerecipe.getCookingTime() : 200;
    }

    private boolean canSmelt(@Nullable IRecipe recipe) {
        if (!this.itemBox.getStackInSlot(0).isEmpty() && recipe != null) {
            ItemStack itemstack = recipe.getRecipeOutput();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.itemBox.getStackInSlot(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() < itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private void smeltItem(@Nullable IRecipe recipe) {
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack material = this.itemBox.getStackInSlot(0);
            ItemStack product = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.itemBox.getStackInSlot(2);
            if (itemstack2.isEmpty()) {
                this.itemBox.setInventorySlotContents(2, product.copy());
            } else if (itemstack2.getItem() == product.getItem()) {
                itemstack2.grow(product.getCount());
            }

            if (!this.world.isRemote) {
                this.canUseRecipe(this.world, (EntityPlayerMP) null, recipe);
            }

            if (material.getItem() == Blocks.WET_SPONGE.asItem() && !this.itemBox.getStackInSlot(1).isEmpty() && this.itemBox.getStackInSlot(1).getItem() == Items.BUCKET) {
                this.itemBox.setInventorySlotContents(1, new ItemStack(Items.WATER_BUCKET));
            }

            material.shrink(1);
        }
    }

    //Blockからの処理
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {

            if (player instanceof EntityPlayerMP && !(player instanceof FakePlayer)) {
                EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;

                NetworkHooks.openGui(entityPlayerMP, this, buf -> buf.writeBlockPos(pos));
            }


            return true;
        }
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

    public FurnaceRecipe getCurrentRecipe() {
        return this.world.getRecipeManager().getRecipe(this, this.world, net.minecraftforge.common.crafting.VanillaRecipeTypes.SMELTING);//this.world.getRecipeManager().getRecipe(this, this.world, SSCoreRecipeTypes.FREEZING);
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

        ItemStack itemstack = this.itemBox.getStackInSlot(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);

        if (index == 0 && !flag) {
            this.totalCookTime = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }

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
            return FreezerFuel.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
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
        return new ContainerFreezer(playerInventory, this);
    }

    @Override
    @Nonnull
    public String getGuiID() {
        return GUI_ID;
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

        if (direction == EnumFacing.DOWN && index == 0) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable EnumFacing facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP)
                return handlers[0].cast();
            else if (facing == EnumFacing.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    //NBT
    public void read(NBTTagCompound compound) {
        super.read(compound);

        this.itemBox = this.itemBox.newEmpty();
        this.itemBox.deserializeNBT(compound);

        this.furnaceBurnTime = compound.getInt("BurnTime");
        this.cookTime = compound.getInt("CookTime");
        this.totalCookTime = compound.getInt("CookTimeTotal");
        this.currentItemBurnTime = FreezerFuel.getItemFreezeTime(this.itemBox.getStackInSlot(1));
        int i = compound.getShort("RecipesUsedSize");

        for (int j = 0; j < i; ++j) {
            ResourceLocation resourcelocation = new ResourceLocation(compound.getString("RecipeLocation" + j));
            int k = compound.getInt("RecipeAmount" + j);
            this.recipeUseCounts.put(resourcelocation, k);
        }

        this.customName.deserializeNBT(compound);

    }

    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setInt("BurnTime", this.furnaceBurnTime);
        compound.setInt("CookTime", this.cookTime);
        compound.setInt("CookTimeTotal", this.totalCookTime);

        compound.merge(this.itemBox.serializeNBT());

        compound.setShort("RecipesUsedSize", (short) this.recipeUseCounts.size());
        int i = 0;

        for (Map.Entry<ResourceLocation, Integer> entry : this.recipeUseCounts.entrySet()) {
            compound.setString("RecipeLocation" + i, entry.getKey().toString());
            compound.setInt("RecipeAmount" + i, entry.getValue());
            ++i;
        }

        if (this.customName.hasCustomName()) {
            compound.merge(this.customName.serializeNBT());
        }

        return compound;
    }

}
