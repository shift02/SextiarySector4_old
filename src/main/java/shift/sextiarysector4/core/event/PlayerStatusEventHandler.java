package shift.sextiarysector4.core.event;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import shift.sextiarysector4.api.SextiarySector4API;
import shift.sextiarysector4.api.capability.CapabilityMoistureHandler;
import shift.sextiarysector4.api.capability.CapabilityStaminaHandler;
import shift.sextiarysector4.api.capability.IMoistureHandler;
import shift.sextiarysector4.api.capability.IStaminaHandler;
import shift.sextiarysector4.core.capability.EntityPlayerManager;
import shift.sextiarysector4.core.capability.MoistureStats;
import shift.sextiarysector4.core.capability.StaminaStats;

public class PlayerStatusEventHandler {
    
    /**バニラの食べ物を食べた時の動作*/
    /** 食べ物を食べた時の動作 */
    @SubscribeEvent
    public void onPlayerUseItemEvent(LivingEntityUseItemEvent.Finish event) {
        
        ItemStack item = event.getItem();
        EntityPlayer player = (EntityPlayer) event.getEntity();
        if (player.getEntityWorld().isRemote) return;
        
        if (!item.hasCapability(CapabilityMoistureHandler.MOISTURE_HANDLER_CAPABILITY, null)) return;
        
        IMoistureHandler m = item.getCapability(CapabilityMoistureHandler.MOISTURE_HANDLER_CAPABILITY, null);
        SextiarySector4API.playerManager.addMoistureStats(player, m.getMoisture(), m.getMoistureSaturation());
        SextiarySector4API.playerManager.addMoistureExhaustion(player, m.getMoistureExhaustion());
        
    }
    
    /**バニラの食べ物を食べた時の動作*/
    /** 食べ物を食べた時の動作 */
    @SubscribeEvent
    public void onPlayerUseItemStaminaEvent(LivingEntityUseItemEvent.Finish event) {
        
        ItemStack item = event.getItem();
        EntityPlayer player = (EntityPlayer) event.getEntity();
        if (player.getEntityWorld().isRemote) return;
        
        if (!item.hasCapability(CapabilityStaminaHandler.STAMINA_HANDLER_CAPABILITY, null)) return;
        
        IStaminaHandler m = item.getCapability(CapabilityStaminaHandler.STAMINA_HANDLER_CAPABILITY, null);
        SextiarySector4API.playerManager.addStaminaStats(player, m.getStamina(), m.getStaminaSaturation());
        SextiarySector4API.playerManager.addStaminaExhaustion(player, m.getStaminaExhaustion());
        
    }
    
    @SubscribeEvent
    public void onPlayerUseItemFoodEvent(LivingEntityUseItemEvent.Finish event) {
        
        ItemStack item = event.getItem();
        EntityPlayer player = (EntityPlayer) event.getEntity();
        if (player.getEntityWorld().isRemote)
            return;
        
        // 水入りビン
        if (item.getItem() == Items.POTIONITEM && item.getItemDamage() == 0) {
            
            player.addExhaustion(4.5f);
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0));
        }
        
        // 魚
        if (item.getItem() == Items.FISH) {
            
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0));
            
        }
        
    }
    
    /**
     * 水分関係
     */
    
    // ブロック破壊
    @SubscribeEvent
    public void onBlockBreakEvent(BreakEvent event) {
        
        if (event.getWorld().isRemote) {
            return;
        }
        
        EntityPlayer player = event.getPlayer();
        
        float i = 0.025f;
        
        if (BiomeDictionary.hasType(event.getWorld().getBiome(event.getPos()), Type.SANDY)) {
            i *= 4;
        }
        if (event.getWorld().getBlockState(event.getPos()).getMaterial() == Material.SAND) {
            i *= 2;
        }
        
        SextiarySector4API.addMoistureExhaustion(player, i);
        
    }
    
    // ダメージ
    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent event) {
        
        if (event.getEntityLiving().world.isRemote || !(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        
        SextiarySector4API.addMoistureExhaustion(player, event.getAmount() * 0.2f);
        
    }
    
    //攻撃
    @SubscribeEvent
    public void onAttackEntityEvent(AttackEntityEvent event) {
        
        if (event.getEntityLiving().world.isRemote || !(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        
        SextiarySector4API.addMoistureExhaustion(player, 0.23f);
        
    }
    
    //ジャンプ
    @SubscribeEvent
    public void onLivingJump2(LivingJumpEvent event) {
        
        if (!(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        
        if (event.getEntityLiving().world.isRemote) {
            return;
        }
        
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        
        float i = 1;
        
        if (BiomeDictionary.hasType(player.world.getBiome(player.getPosition()), Type.SANDY)) {
            i = 2;
        }
        
        if (player.isSprinting()) {
            SextiarySector4API.addMoistureExhaustion(player, 0.1f * i);
        } else {
            SextiarySector4API.addMoistureExhaustion(player, 0.05f * i);
        }
        
    }
    
    /**
     * スタミナ関係
     */
    
    // ブロック破壊
    @SubscribeEvent
    public void onBlockBreakEventS(BreakEvent event) {
        
        if (event.getWorld().isRemote) {
            return;
        }
        
        EntityPlayer player = event.getPlayer();
        
        float i = 0.65f;
        
        if (BiomeDictionary
                .hasType(
                        event.getWorld().getBiome(event.getPos()),
                        Type.SANDY)) {
            i *= 4;
        }
        if (event.getWorld().getBlockState(event.getPos()).getMaterial() == Material.SAND) {
            i *= 2;
        }
        
        SextiarySector4API.addStaminaExhaustion(player, i);
        
    }
    
    // ダメージ
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingHurtEvent2(LivingHurtEvent event) {
        
        if (event.getEntityLiving().world.isRemote || !(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        
        if (event.isCanceled()) return;
        
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        
        if (event.getAmount() < 0) return;
        
        float d = this.applyArmorCalculations(player, event.getSource(), event.getAmount());
        
        SextiarySector4API.addStaminaExhaustion(player, d * 1.2f);
        
    }
    
    protected float applyArmorCalculations(EntityPlayer player, DamageSource p_70655_1_, float p_70655_2_) {
        
        if (!p_70655_1_.isUnblockable()) {
            int i = 25 - player.getTotalArmorValue();
            float f1 = p_70655_2_ * i;
            p_70655_2_ = f1 / 25.0F;
        }
        
        return p_70655_2_;
        
    }
    
    // ジャンプ
    @SubscribeEvent
    public void onLivingJump(LivingJumpEvent event) {
        
        if (!(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        
        if (event.getEntityLiving().world.isRemote) {
            return;
        }
        
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        
        float i = 1;
        
        if (BiomeDictionary.hasType(player.world.getBiome(player.getPosition()), Type.SANDY)) {
            i = 2;
        }
        
        if (player.isSprinting()) {
            SextiarySector4API.addStaminaExhaustion(player, 2.2f * i);
        } else {
            SextiarySector4API.addStaminaExhaustion(player, 0.5f * i);
        }
        
    }
    
    //攻撃
    @SubscribeEvent
    public void onAttackEntityEvent2(AttackEntityEvent event) {
        
        if (event.getEntityLiving().world.isRemote || !(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        
        SextiarySector4API.addStaminaExhaustion(player, 1.4f);
        
    }
    
    //ベットで回復
    @SubscribeEvent
    public void LivingSleepingEvent(PlayerWakeUpEvent event) {
        
        if (event.getEntityPlayer().world.isRemote) {
            return;
        }
        
        if (!(event.getEntityPlayer() instanceof EntityPlayer)) {
            return;
        }
        
        if (event.updateWorld()) return;
        
        if (!event.getEntityLiving().world.isRemote) {
            
            EntityPlayer player = event.getEntityPlayer();
            
            if (EntityPlayerManager.getMoistureStats(player).getMoistureLevel() > 4 && player.getFoodStats().getFoodLevel() > 4) {
                EntityPlayerManager.getStaminaStats(player).addStats(player, 100, 20.0f);
            } else {
                EntityPlayerManager.getStaminaStats(player).addStats(player, 40, 0.0f);
            }
            player.getFoodStats().addExhaustion(19.3f);
            SextiarySector4API.addMoistureExhaustion(player, 20.3f);
            
        }
        
    }
    
    //Special Thanks ,Furia
    @SubscribeEvent
    public void playerUpdateEvent(TickEvent.PlayerTickEvent event) {
        this.playerNoMoveEvent(event.player);
        this.playerSittingEvent(event.player);
        //this.playerSpaEvent(event.player);
    }
    
    private void playerNoMoveEvent(EntityPlayer player) {
        
        if (player.world.getTotalWorldTime() % 120 != 0) return;
        
        if (player.world.rand.nextBoolean()) return;
        
        if (player.isSneaking()) return;
        
        StaminaStats stats = EntityPlayerManager.getStaminaStats(player);
        if (stats == null) return;
        
        if (!stats.needStamina()) return;
        
        MoistureStats moistStats = EntityPlayerManager.getMoistureStats(player);
        if (moistStats == null) return;
        
        if (moistStats.getMoistureLevel() < 1) return;
        
        if ((int) player.lastTickPosX == (int) player.posX
                && (int) player.lastTickPosY == (int) player.posY
                && (int) player.lastTickPosZ == (int) player.posZ
                && player.motionX == 0
                && player.motionY == 0
                && player.motionZ == 0) {
            
            if (!player.world.isRemote) {
                stats.addStats(player, 1, 0.1f);
                moistStats.addExhaustion(player, 0.05f);
                player.addExhaustion(0.1f);
            }
            generateRandomParticles(player, EnumParticleTypes.VILLAGER_HAPPY);
            
        }
    }
    
    private void playerSittingEvent(EntityPlayer player) {
        
        if (player.world.getTotalWorldTime() % 70 != 0)
            return;
        
        if (!player.isRiding() && !EntityPlayerManager.getAdditionalPlayerData(player).getSit().isSit) return;
        
        StaminaStats stats = EntityPlayerManager.getStaminaStats(player);
        if (stats == null) return;
        
        if (!stats.needStamina()) return;
        
        MoistureStats moistStats = EntityPlayerManager.getMoistureStats(player);
        if (moistStats == null) return;
        
        if (moistStats.getMoistureLevel() < 10)
            return;
        
        if (player.lastTickPosX == player.posX
                && player.lastTickPosY == player.posY
                && player.lastTickPosZ == player.posZ
                && player.motionX == 0
                && player.motionY == 0
                && player.motionZ == 0) {
            
            stats.addStats(player, 1, 0.0f);
            moistStats.addExhaustion(player, 1.4f);
            player.addExhaustion(0.05f);
            this.generateRandomParticles(player, EnumParticleTypes.VILLAGER_HAPPY);
        }
        
    }
    
    //    private void playerSpaEvent(EntityPlayer player) {
    //
    //        if (player.worldObj.getTotalWorldTime() % 200 != 0)
    //            return;
    //
    //        StaminaStats stats = EntityPlayerManager.getStaminaStats(player);
    //        if (stats == null)
    //            return;
    //
    //        if (!stats.needStamina())
    //            return;
    //
    //        //spaEffect
    //        if (!player.isInWater())
    //            return;
    //
    //        int i = MathHelper.floor_double(player.posX);
    //        int j = MathHelper.floor_double(player.boundingBox.minY);
    //        int k = MathHelper.floor_double(player.posZ);
    //
    //        for (int offset = 0; offset <= 1; offset++) {
    //            Block spaBlock = player.worldObj.getBlock(i, j + offset, k);
    //            int meta = player.worldObj.getBlockMetadata(i, j + offset, k);
    //            if (spaBlock == null) continue;
    //
    //            if (ModuleHotSprings.isHotSprings(spaBlock, meta)) {
    //                player.addPotionEffect(new PotionEffect(SSPotions.hotSprings.getId(), 400, 2));
    //                generateRandomParticles(player, "happyVillager");
    //                break;
    //            }
    //        }
    //    }
    
    private void generateRandomParticles(EntityPlayer player, EnumParticleTypes particleName) {
        for (int i = 0; i < 5; ++i) {
            double d0 = player.getRNG().nextGaussian() * 0.02D;
            double d1 = player.getRNG().nextGaussian() * 0.02D;
            double d2 = player.getRNG().nextGaussian() * 0.02D;
            player.world.spawnParticle(particleName,
                    player.posX + player.getRNG().nextFloat() * player.width * 2.0F - player.width,
                    player.posY + 0.2D + player.getRNG().nextFloat() * player.height,
                    player.posZ + player.getRNG().nextFloat() * player.width * 2.0F - player.width,
                    d0, d1, d2);
        }
    }
    
}
