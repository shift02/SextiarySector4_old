package shift.sextiarysector4.core;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.RecipeType;
import shift.sextiarysector4.core.common.crafting.FreezerRecipe;

public class SSCoreRecipeTypes {

    public static final RecipeType<FreezerRecipe> FREEZING = RecipeType.get(new ResourceLocation("freezing"), FreezerRecipe.class);

}
