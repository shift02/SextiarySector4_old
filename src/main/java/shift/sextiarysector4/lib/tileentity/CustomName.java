package shift.sextiarysector4.lib.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * TileEntityに独自の名前を持たせるクラス
 */
public class CustomName implements INameable, INBTSerializable<NBTTagCompound> {

    private final TextComponentTranslation defaultName;

    private ITextComponent customName;

    public CustomName(String defaultName) {
        this.defaultName = new TextComponentTranslation(defaultName);
    }

    @Override
    @Nonnull
    public ITextComponent getName() {

        if (this.hasCustomName()) {
            return this.customName;
        }

        return defaultName;
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null;
    }

    @Override
    public ITextComponent getCustomName() {
        return this.customName;
    }

    public void setCustomName(@Nullable ITextComponent name) {
        this.customName = name;
    }

    //NBT関係
    @Override
    public NBTTagCompound serializeNBT() {

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("CustomName", ITextComponent.Serializer.toJson(this.getCustomName()));
        return nbt;

    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.contains("CustomName", 8)) {
            this.setCustomName(ITextComponent.Serializer.fromJson(nbt.getString("CustomName")));
        }
    }
}
