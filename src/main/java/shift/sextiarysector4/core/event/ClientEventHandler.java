package shift.sextiarysector4.core.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import shift.sextiarysector4.api.equipment.EquipmentType;
import shift.sextiarysector4.core.SextiarySector4;
import shift.sextiarysector4.core.capability.EntityPlayerManager;
import shift.sextiarysector4.core.capability.EquipmentStats;
import shift.sextiarysector4.lib.SSConfig;

public class ClientEventHandler {
    
    @SideOnly(Side.CLIENT)
    public static Minecraft mc = FMLClientHandler.instance().getClient();
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onConfigChanged(ConfigChangedEvent event) {
        
        if (event.getModID().equals(SextiarySector4.MODID)) {
            
            SSConfig.syncConfig();
        }
        
    }
    
    public static Entity boat = new Entity(null) {
        
        @Override
        public double getMountedYOffset() {
            return -0.5;
        }
        
        @Override
        protected void entityInit() {
            
        }
        
        @Override
        protected void readEntityFromNBT(NBTTagCompound compound) {
            
        }
        
        @Override
        protected void writeEntityToNBT(NBTTagCompound compound) {
            
        }
        
    };
    public static double y = 0;
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderPlayerEvent(net.minecraftforge.client.event.RenderPlayerEvent.Pre event) {
        
        if (!EntityPlayerManager.getAdditionalPlayerData(event.getEntityPlayer()).getSit().isSit)
            return;
        
        boat.world = event.getEntityPlayer().world;
        // boat.worldObj.spawnEntityInWorld(boat);
        event.getEntityPlayer().startRiding(boat);
        boat.rotationYaw = event.getEntityPlayer().rotationYaw;
        boat.rotationPitch = event.getEntityPlayer().rotationPitch;
        boat.prevRotationYaw = event.getEntityPlayer().prevRotationYaw;
        boat.prevRotationPitch = event.getEntityPlayer().prevRotationPitch;
        
        // boat.setPosition(event.getEntityPlayer().posX,
        // event.getEntityPlayer().posY - 1.0, event.getEntityPlayer().posZ);
        // boat.prevPosY = event.getEntityPlayer().posY - 1.0;
        y = event.getEntityPlayer().posY;
        event.getEntityPlayer().getMountedYOffset();
        // event.getEntityPlayer().prevPosY = event.getEntityPlayer().posY -
        // 1.3;
        // event.entityPlayer.posY -= 0.5;
        
        // event.getRenderer().getRenderManager().setRenderPosition(event.getEntityPlayer().posX,
        // y - 0.5, event.getEntityPlayer().posZ);
        
        event.getEntityPlayer().setLocationAndAngles(event.getEntityPlayer().posX, y - 0.5,
                event.getEntityPlayer().posZ, event.getEntityPlayer().rotationYaw,
                event.getEntityPlayer().rotationPitch);
        boat.setLocationAndAngles(event.getEntityPlayer().posX, y, event.getEntityPlayer().posZ,
                event.getEntityPlayer().rotationYaw, event.getEntityPlayer().rotationPitch);
        
        GlStateManager.pushMatrix();
        
        GlStateManager.translate(0, (float) -0.5625, 0);
        
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderPlayerEvent(RenderPlayerEvent.Post event) {
        
        if (!EntityPlayerManager.getAdditionalPlayerData(event.getEntityPlayer()).getSit().isSit)
            return;
        
        GlStateManager.popMatrix();
        
        event.getEntityPlayer().dismountRidingEntity();
        // event.getEntityPlayer().setPositionAndUpdate(event.getEntityPlayer().posX,
        // y, event.getEntityPlayer().posZ);
        event.getEntityPlayer().setLocationAndAngles(event.getEntityPlayer().posX, y, event.getEntityPlayer().posZ,
                event.getEntityPlayer().rotationYaw, event.getEntityPlayer().rotationPitch);
        // event.getEntityPlayer().prevPosY = y;
        // event.entityPlayer.posY = y;
        
    }
    
    @SubscribeEvent
    public void PreTextureStitchEvent(TextureStitchEvent.Pre event) {
        // Item
        for (EquipmentType type : EquipmentType.values()) {
            type.registerIcon(event.getMap());
        }
    }
    
    public NonNullList<ItemStack> eqList = NonNullList.<ItemStack> withSize(4, ItemStack.EMPTY);
    
    @SubscribeEvent
    public void doRenderPlayerEvent(RenderPlayerEvent.Pre event) {
        
        swapInv(event.getEntityPlayer());
        
        /*
        EntityPlayer player = event.getEntityPlayer();
        
        eqList = (NonNullList<ItemStack>) player.getArmorInventoryList();
        
        EquipmentStats e = EntityPlayerManager.getEquipmentStats(player);
        
        for (int i = 0; i < 4; i++) {
            if (!e.inventory.getStackInSlot(i).isEmpty()) {
                player.setItemStackToSlot(EntityEquipmentSlot.values()[5 - i], e.inventory.getStackInSlot(i));
            }
        }*/
        
    }
    
    @SubscribeEvent
    public void doRenderPlayerEvent(RenderPlayerEvent.Post event) {
        
        swapInv(event.getEntityPlayer());
        
        /*
        EntityPlayer player = event.getEntityPlayer();
        
        for (int i = 0; i < 4; i++) {
            player.setItemStackToSlot(EntityEquipmentSlot.values()[i + 2], eqList.get(i));
        }*/
    }
    
    ItemStackHandler localInv = new ItemStackHandler(4);
    
    private void swapInv(EntityPlayer p) {
        
        EquipmentStats e = EntityPlayerManager.getEquipmentStats(p);
        for (int i = 0; i < 4; i++) {
            if (!e.inventory.getStackInSlot(i).isEmpty()) localInv.insertItem(i, e.inventory.getStackInSlot(i), false);
        }
        
        NonNullList<ItemStack> armor = p.inventory.armorInventory;
        for (int i = 0; i < armor.size(); i++) {
            if (!e.inventory.getStackInSlot(i).isEmpty()) e.inventory.setInventorySlotContents(i, armor.get(3 - i));
        }
        
        for (int i = 0; i < localInv.getSlots(); i++) {
            if (!e.inventory.getStackInSlot(i).isEmpty()) armor.set(3 - i, localInv.extractItem(i, 1, false));
        }
        
    }
    
}
