package shift.sextiarysector4.core;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.RecipeSerializers;
import shift.sextiarysector4.core.common.crafting.FreezerRecipe;

public class SSCoreRecipeSerializers {

    public static IRecipeSerializer<FreezerRecipe> FREEZING;

    public static void init() {
        FREEZING = RecipeSerializers.register(new FreezerRecipe.Serializer());
    }

}
