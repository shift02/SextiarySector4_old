package shift.sextiarysector4.core.asm.vanilla;

import net.minecraft.client.entity.EntityPlayerSP;
import shift.sextiarysector4.core.SSCoreProxy;
import shift.sextiarysector4.core.capability.EntityPlayerManager;

public class EntityPlayerSPMethod {
    
    public static int isSprinting(int i) {
        
        try {
            
            EntityPlayerSP p = (EntityPlayerSP) SSCoreProxy.getProxy().getClientPlayer();
            
            if (EntityPlayerManager.getStaminaStats(p).getStaminaLevel() == 0)
                return 0;
            
        } catch (Exception e) {
            
        }
        
        return 10;
        
    }
    
}