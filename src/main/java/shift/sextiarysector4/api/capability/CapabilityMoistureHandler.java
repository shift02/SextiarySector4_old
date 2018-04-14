package shift.sextiarysector4.api.capability;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import shift.sextiarysector4.api.capability.template.MoistureHandler;

public class CapabilityMoistureHandler {
    
    @CapabilityInject(IMoistureHandler.class)
    public static Capability<IMoistureHandler> MOISTURE_HANDLER_CAPABILITY = null;
    
    public static void register() {
        CapabilityManager.INSTANCE.register(IMoistureHandler.class, new Capability.IStorage<IMoistureHandler>() {
            @Override
            public NBTBase writeNBT(Capability<IMoistureHandler> capability, IMoistureHandler instance,
                    EnumFacing side) {
                NBTTagCompound tags = new NBTTagCompound();
                tags.setInteger("Moisture", instance.getMoisture());
                tags.setFloat("MoistureSaturation", instance.getMoistureSaturation());
                tags.setFloat("MoistureExhaustion", instance.getMoistureExhaustion());
                return tags;
            }
            
            @Override
            public void readNBT(Capability<IMoistureHandler> capability, IMoistureHandler instance, EnumFacing side,
                    NBTBase nbt) {
                
                NBTTagCompound tags = (NBTTagCompound) nbt;
                instance.setMoisture(tags.getInteger("Moisture"));
                instance.setMoistureSaturation(tags.getFloat("MoistureSaturation"));
                instance.setMoistureExhaustion(tags.getFloat("MoistureExhaustion"));
                
            }
        }, new Callable<IMoistureHandler>() {
            @Override
            public IMoistureHandler call() throws Exception {
                return new MoistureHandler(0, 0, 0);
            }
        });
    }
    
}