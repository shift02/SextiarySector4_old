package shift.sextiarysector4.api.capability.template;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import shift.sextiarysector4.api.capability.CapabilityMoistureHandler;
import shift.sextiarysector4.api.capability.IMoistureHandler;

/**
 * デフォルトの実装
 * @author Shift02ss
 */
public class MoistureHandler implements IMoistureHandler, ICapabilityProvider {
    
    public int moisture;
    public float moistureSaturation;
    public float moistureExhaustion;
    
    public MoistureHandler(int m1, float m2, float m3) {
        this.moisture = m1;
        this.moistureSaturation = m2;
        this.moistureExhaustion = m3;
        
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (CapabilityMoistureHandler.MOISTURE_HANDLER_CAPABILITY == null)
            return false;
        return capability == CapabilityMoistureHandler.MOISTURE_HANDLER_CAPABILITY;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityMoistureHandler.MOISTURE_HANDLER_CAPABILITY ? (T) this : null;
    }
    
    @Override
    public int getMoisture() {
        return this.moisture;
    }
    
    @Override
    public float getMoistureSaturation() {
        return this.moistureSaturation;
    }
    
    @Override
    public float getMoistureExhaustion() {
        return this.moistureExhaustion;
    }
    
    @Override
    public void setMoisture(int m) {
        this.moisture = m;
    }
    
    @Override
    public void setMoistureSaturation(float m) {
        this.moistureSaturation = m;
    }
    
    @Override
    public void setMoistureExhaustion(float m) {
        this.moistureExhaustion = m;
    }
    
}
