package shift.sextiarysector4.api.capability.template;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import shift.sextiarysector4.api.capability.CapabilityStaminaHandler;
import shift.sextiarysector4.api.capability.IStaminaHandler;

public class StaminaHandler implements IStaminaHandler, ICapabilityProvider {
    
    public int stamina;
    public float staminaSaturation;
    public float staminaExhaustion;
    
    public StaminaHandler(int s1, float s2, float s3) {
        this.stamina = s1;
        this.staminaSaturation = s2;
        this.staminaExhaustion = s3;
        
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (CapabilityStaminaHandler.STAMINA_HANDLER_CAPABILITY == null)
            return false;
        return capability == CapabilityStaminaHandler.STAMINA_HANDLER_CAPABILITY;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityStaminaHandler.STAMINA_HANDLER_CAPABILITY ? (T) this : null;
    }
    
    @Override
    public int getStamina() {
        return stamina;
    }
    
    @Override
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
    
    @Override
    public float getStaminaSaturation() {
        return staminaSaturation;
    }
    
    @Override
    public void setStaminaSaturation(float staminaSaturation) {
        this.staminaSaturation = staminaSaturation;
    }
    
    @Override
    public float getStaminaExhaustion() {
        return staminaExhaustion;
    }
    
    @Override
    public void setStaminaExhaustion(float staminaExhaustion) {
        this.staminaExhaustion = staminaExhaustion;
    }
    
}