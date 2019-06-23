package shift.sextiarysector4.lib;

import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SSLibEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @OnlyIn(Dist.CLIENT)
    public static void onTooltip(ItemTooltipEvent evt) {

        if (!Optional.ofNullable(evt.getEntityPlayer()).map(e -> e.world).map(e -> e.isRemote).orElse(false)) {
            return;
        }

        if (evt.getFlags().isAdvanced()) {
            NBTTagCompound tag = evt.getItemStack().getTag();

            if (tag != null) {

                evt.getToolTip().add(tag.toFormattedComponent(" ", 1));

            } else {
            }
        }
    }

}
