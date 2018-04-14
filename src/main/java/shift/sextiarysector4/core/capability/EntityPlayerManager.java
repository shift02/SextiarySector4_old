package shift.sextiarysector4.core.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import shift.sextiarysector4.api.IPlayerManager;
import shift.sextiarysector4.core.SextiarySector4;
import shift.sextiarysector4.core.packet.PacketPlayerData;
import shift.sextiarysector4.core.packet.SSPacketHandler;

public class EntityPlayerManager implements IPlayerManager {
    
    // private final static String kye = "sextiarysector";
    private final static ResourceLocation KYE = new ResourceLocation(SextiarySector4.MODID, "AdditionalPlayerData");
    
    @CapabilityInject(AdditionalPlayerData.class)
    private static final Capability<AdditionalPlayerData> ADDITIONAL_CAP = null;
    
    private final static int MAX_MOISTURE_LEVEL = 20;
    private final static int MAX_PREVMOISTURE_LEVEL = 20;
    
    private final static int MAX_STAMINA_LEVEL = 100;
    private final static int MAX_PREV_STAMINA_LEVEL = 20;
    
    /*
     * public static final Map<String, MoistureStats> moistureMap = new
     * HashMap<String, MoistureStats>(); public static final Map<String,
     * StaminaStats> staminaMap = new HashMap<String, StaminaStats>();
     *
     * public static final Map<String, Integer> lastMoistureLevel = new
     * HashMap<String, Integer>(); public static final Map<String, Boolean>
     * wasThirsty = new HashMap<String, Boolean>();
     *
     * public static final Map<String, Integer> lastStaminaLevel = new
     * HashMap<String, Integer>(); public static final Map<String, Boolean>
     * wasTired = new HashMap<String, Boolean>();
     *
     */
    
    public static EntityPlayerManager instance = new EntityPlayerManager();
    
    private EntityPlayerManager() {
        
    }
    
    @Override
    public void addMoistureStats(EntityPlayer entityPlayer, int par1, float par2) {
        if (!entityPlayer.world.isRemote) {
            
            getMoistureStats(entityPlayer).addStats(entityPlayer, par1, par2);
            // Achievement
            // entityPlayer.addStat(SSAchievement.moisture, 1);
        }
    }
    
    @Override
    public void addStaminaStats(EntityPlayer entityPlayer, int par1, float par2) {
        if (!entityPlayer.world.isRemote) {
            getStaminaStats(entityPlayer).addStats(entityPlayer, par1, par2);
        }
    }
    
    @Override
    public void addMoistureExhaustion(EntityPlayer entityPlayer, float par1) {
        if (!entityPlayer.world.isRemote) {
            getMoistureStats(entityPlayer).addExhaustion(entityPlayer, par1);
        }
    }
    
    @Override
    public void addStaminaExhaustion(EntityPlayer entityPlayer, float par1) {
        if (!entityPlayer.world.isRemote) {
            getStaminaStats(entityPlayer).addExhaustion(entityPlayer, par1);
        }
    }
    
    @Override
    public int getMoistureLevel(EntityPlayer entityPlayer) {
        return getMoistureStats(entityPlayer).getMoistureLevel();
    }
    
    @Override
    public int getStaminaLevel(EntityPlayer entityPlayer) {
        return getStaminaStats(entityPlayer).getStaminaLevel();
    }
    
    // 触らない
    
    // tick
    @SubscribeEvent
    public void LivingUpdateEvent(LivingUpdateEvent event) {
        if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer) {
            
            onUpdateEntity((EntityPlayer) event.getEntityLiving());
            
        }
        
    }
    
    /** プレイヤーのアップデート処理 */
    public static void onUpdateEntity(EntityPlayer entityPlayer) {
        
        getAdditionalPlayerData(entityPlayer).onUpdateEntity(entityPlayer);
        
    }
    
    /** Playerのデータの登録 */
    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> evt) {
        if (evt.getObject() instanceof EntityPlayer) {
            EntityPlayerManager.register(evt, (EntityPlayer) evt.getObject());
        }
    }
    
    public static void register(AttachCapabilitiesEvent<Entity> evt, EntityPlayer entityPlayer) {
        evt.addCapability(KYE, new AdditionalPlayerData());
    }
    
    public static AdditionalPlayerData getAdditionalPlayerData(EntityPlayer entityPlayer) {
        return entityPlayer.getCapability(ADDITIONAL_CAP, null);
    }
    
    public static MoistureStats getMoistureStats(EntityPlayer entityPlayer) {
        return getAdditionalPlayerData(entityPlayer).getMoisture();
        
    }
    
    public static StaminaStats getStaminaStats(EntityPlayer entityPlayer) {
        
        return getAdditionalPlayerData(entityPlayer).getStamina();
        
    }
    
    // GUI用
    public static int getPrevMoistureLevel(EntityPlayer entityPlayer) {
        
        return getMoistureStats(entityPlayer).getMoistureLevel() / (MAX_MOISTURE_LEVEL / MAX_PREVMOISTURE_LEVEL);
        
    }
    
    public static int getPrevStaminaLevel(EntityPlayer entityPlayer) {
        
        return getStaminaStats(entityPlayer).getStaminaLevel() / (MAX_STAMINA_LEVEL / MAX_PREV_STAMINA_LEVEL);
        
    }
    
    @SubscribeEvent
    /* ワールドに入った時に呼ばれるイベント。 */
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer) {
            
            SSPacketHandler.INSTANCE.sendTo(
                    new PacketPlayerData(this.getAdditionalPlayerData((EntityPlayer) event.getEntity())),
                    (EntityPlayerMP) event.getEntity());
            
            // this.sendOtherPlayer((EntityPlayer) event.getEntity());
            
        }
    }
    
    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        // プレイヤーがディメンション間を移動したときの処理
        
        if (!event.player.world.isRemote) {
            SSPacketHandler.INSTANCE.sendTo(new PacketPlayerData(this.getAdditionalPlayerData(event.player)),
                    (EntityPlayerMP) event.player);
            
            //this.sendOtherPlayer(event.player);
        }
        
    }
    
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        // プレイヤーがリスポーンした時の処理
        // System.out.println("onPlayerRespawn");
        if (!event.player.world.isRemote) {
            
            SSPacketHandler.INSTANCE.sendTo(new PacketPlayerData(this.getAdditionalPlayerData(event.player)),
                    (EntityPlayerMP) event.player);
            
            //this.sendOtherPlayer(event.player);
        }
        
    }
    
    public void sendOtherPlayer(EntityPlayer player) {
        //他のプレイヤーに送る
        PacketPlayerData d = new PacketPlayerData(this.getAdditionalPlayerData(player));
        d.getData().setString("uuid", player.getUniqueID().toString());
        SSPacketHandler.INSTANCE.sendToAllAround(d,
                new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64));
    }
    
}
