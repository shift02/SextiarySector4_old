package shift.sextiarysector4.core.asm.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import shift.sextiarysector4.api.SextiarySector4API;
import shift.sextiarysector4.lib.SSConfig;

public class FoodStatsMethod {
    
    public static void onExhaustion(EntityPlayer player, float amount) {
        
        if (SSConfig.statusStamina) {
            SextiarySector4API.addStaminaExhaustion(player, amount);
        } else {
            player.attackEntityFrom(DamageSource.STARVE, 1.0F);
        }
    }
}