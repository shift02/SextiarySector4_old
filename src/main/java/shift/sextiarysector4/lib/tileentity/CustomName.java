package shift.sextiarysector4.lib.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * TileEntityに独自の名前を持たせるクラス
 */
public class CustomName implements INameable {

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
}
