package shift.sextiarysector4.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockAnimalOil extends Block {
    public BlockAnimalOil(Properties properties) {
        super(properties);
    }

    public boolean isFireSource(IBlockState state, IBlockReader world, BlockPos pos, EnumFacing side) {
        return true;
    }

}
