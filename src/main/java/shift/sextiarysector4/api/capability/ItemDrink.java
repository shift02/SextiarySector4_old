package shift.sextiarysector4.api.capability;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import shift.sextiarysector4.api.capability.template.MoistureHandler;

/**
 * 飲み物のサンプル
 * @author Shift02ss
 *
 */
public class ItemDrink extends Item {
    
    public int moisture;
    public float moistureSaturation;
    public float moistureExhaustion;
    
    public ItemDrink(int m1, float m2, float m3) {
        this.moisture = m1;
        this.moistureSaturation = m2;
        this.moistureExhaustion = m3;
    }
    
    @Override
    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        
        stack.shrink(1);
        
        //--stack.stackSize;
        
        return stack;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }
    
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
            EnumHand hand) {
        playerIn.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new MoistureHandler(moisture, moistureSaturation, moistureExhaustion);
    }
    
}
