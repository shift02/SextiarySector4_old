package shift.sextiarysector4.api.event.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

public class PlayerMoistureEvent extends PlayerEvent {
    
    public final int moistureLevel;
    public final float moistureSaturationLevel;
    
    public PlayerMoistureEvent(EntityPlayer player, int level, float saturation) {
        super(player);
        this.moistureLevel = level;
        this.moistureSaturationLevel = saturation;
    }
    
    @Cancelable
    public static class Add extends PlayerMoistureEvent {
        
        public final int addMoistureLevel;
        public final float addMoistureSaturationLevel;
        
        public int newAddMoistureLevel;
        public float newAddMoistureSaturationLevel;
        
        public Add(EntityPlayer player, int level, float saturation, int addMoistureLevel,
                float addMoistureSaturationLevel) {
            super(player, level, addMoistureLevel);
            this.addMoistureLevel = addMoistureLevel;
            this.addMoistureSaturationLevel = addMoistureSaturationLevel;
            this.newAddMoistureLevel = addMoistureLevel;
            this.newAddMoistureSaturationLevel = addMoistureSaturationLevel;
        }
    }
    
    @Cancelable
    public static class Exhaustion extends PlayerMoistureEvent {
        
        public final float exhaustionLevel;
        
        public float newExhaustionLevel;
        
        public Exhaustion(EntityPlayer player, int level, float saturation, float exhaustionLevel) {
            super(player, level, saturation);
            this.exhaustionLevel = level;
            this.newExhaustionLevel = exhaustionLevel;
        }
    }
    
}