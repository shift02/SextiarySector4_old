package shift.sextiarysector4.api.capability;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import shift.sextiarysector4.api.capability.template.StaminaHandler;

public class CapabilityStaminaHandler {
    
    @CapabilityInject(IStaminaHandler.class)
    public static Capability<IStaminaHandler> STAMINA_HANDLER_CAPABILITY = null;
    
    public static void register() {
        CapabilityManager.INSTANCE.register(IStaminaHandler.class, new Capability.IStorage<IStaminaHandler>() {
            @Override
            public NBTBase writeNBT(Capability<IStaminaHandler> capability, IStaminaHandler instance,
                    EnumFacing side) {
                NBTTagCompound tags = new NBTTagCompound();
                tags.setInteger("Stamina", instance.getStamina());
                tags.setFloat("StaminaSaturation", instance.getStaminaSaturation());
                tags.setFloat("StaminaExhaustion", instance.getStaminaExhaustion());
                return tags;
            }
            
            @Override
            public void readNBT(Capability<IStaminaHandler> capability, IStaminaHandler instance, EnumFacing side,
                    NBTBase nbt) {
                
                NBTTagCompound tags = (NBTTagCompound) nbt;
                instance.setStamina(tags.getInteger("Stamina"));
                instance.setStaminaSaturation(tags.getFloat("StaminaSaturation"));
                instance.setStaminaExhaustion(tags.getFloat("StaminaExhaustion"));
                
            }
        }, new Callable<IStaminaHandler>() {
            @Override
            public IStaminaHandler call() throws Exception {
                return new StaminaHandler(0, 0, 0);
            }
        });
    }
    
}