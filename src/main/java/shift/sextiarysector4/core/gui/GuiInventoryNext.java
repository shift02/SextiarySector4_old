package shift.sextiarysector4.core.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import shift.sextiarysector4.core.SSCorePlayerTabs;
import shift.sextiarysector4.core.SextiarySector4;
import shift.sextiarysector4.core.container.ContainerPlayerNext;
import shift.sextiarysector4.core.gui.tab.TabManager;

public class GuiInventoryNext extends InventoryEffectRenderer {
    
    protected static final ResourceLocation field_147001_a = new ResourceLocation(SextiarySector4.MODID, "textures/guis/inventory_next.png");
    
    /** x size of the inventory window in pixels. Defined as  float, passed as int */
    private float xSizeFloat;
    /** y size of the inventory window in pixels. Defined as  float, passed as int. */
    private float ySizeFloat;
    private static final String __OBFID = "CL_00000761";
    
    public GuiInventoryNext(EntityPlayer p_i1094_1_) {
        super(new ContainerPlayerNext(p_i1094_1_.inventory, p_i1094_1_));
        this.allowUserInput = true;
    }
    
    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen() {
        if (this.mc.playerController.isInCreativeMode()) {
            //this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
    }
    
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        this.buttonList.clear();
        
        super.initGui();
        
        int cornerX = this.guiLeft;
        
        int cornerY = this.guiTop;
        
        if (TabManager.hasPotion(null)) //!mc.thePlayer.getActivePotionEffects().isEmpty())
        {
            cornerX = 160 + (this.width - this.xSize - 200) / 2;
        } else {
            cornerX = (this.width - this.xSize) / 2;
        }
        
        TabManager.updateTabValues(cornerX, cornerY, this.buttonList, SSCorePlayerTabs.ss, false);
        
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        //this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.drawDefaultBackground();
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        this.xSizeFloat = p_73863_1_;
        this.ySizeFloat = p_73863_2_;
        this.renderHoveredToolTip(p_73863_1_, p_73863_2_);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.field_147001_a);
        int k = this.guiLeft;
        int l = this.guiTop;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        drawEntityOnScreen(k + 51, l + 75, 30, k + 51 - this.xSizeFloat, l + 75 - 50 - this.ySizeFloat, this.mc.player);
    }
    
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float) Math.atan(mouseX / 40.0F) * 20.0F;
        ent.rotationYaw = (float) Math.atan(mouseX / 40.0F) * 40.0F;
        ent.rotationPitch = -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            //this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.player.getStatFileWriter()));
        }
        
        if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
        }
    }
}
