package shift.sextiarysector4.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.FMLPlayMessages;
import shift.sextiarysector4.core.client.gui.GuiFreezer;
import shift.sextiarysector4.core.tileentity.TileEntityFreezer;

public class SSCoreGuiHandler {

    public static GuiScreen openGui(FMLPlayMessages.OpenContainer openContainer) {

        BlockPos pos = openContainer.getAdditionalData().readBlockPos();

        if (TileEntityFreezer.GUI_ID.equals(openContainer.getId().toString())) {
            return new GuiFreezer(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos));
        }

        return null;
    }

}
