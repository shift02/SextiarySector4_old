package shift.sextiarysector4.core;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class SSCoreProxy implements IGuiHandler {
    
    @SidedProxy(modId = SextiarySector4.MODID)
    private static SSCoreProxy proxy;
    
    public static SSCoreProxy getProxy() {
        return proxy;
    }
    
    public EntityPlayer getClientPlayer() {
        return null;
    }
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
    
    @SideOnly(Side.SERVER)
    public static class ServerProxy extends SSCoreProxy {
    }
    
    @SideOnly(Side.CLIENT)
    public static class ClientProxy extends SSCoreProxy {
        
        @Override
        public EntityPlayer getClientPlayer() {
            return Minecraft.getMinecraft().player;
        }
        
    }
    
}
