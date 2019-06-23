package shift.sextiarysector4.core.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import shift.sextiarysector4.core.SSCoreBlocks;

public class TileEntityTank extends TileEntity implements IFeatureBlock {

    public TileEntityTank() {
        super(SSCoreBlocks.tileEntityTank);
    }

    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        return false;

    }

}
