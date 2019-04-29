package shift.sextiarysector4.core.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockRailDiamondCrossing extends BlockRailBase {

    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;


    public BlockRailDiamondCrossing(Properties properties) {
        super(true, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SHAPE, RailShape.NORTH_SOUTH));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(SHAPE);
    }

    @Override
    @Nonnull
    public IProperty<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    @Nonnull
    public RailShape getRailDirection(IBlockState state, IBlockReader world, BlockPos pos, @Nullable EntityMinecart cart) {

        if (cart == null) {
            return RailShape.NORTH_SOUTH;
        }
        float rotationYaw = cart.prevRotationYaw;

        rotationYaw = rotationYaw + 360F;
        rotationYaw = rotationYaw % 180F;

        if (45 < rotationYaw && rotationYaw <= 135) {
            return RailShape.NORTH_SOUTH;
        } else {
            return RailShape.EAST_WEST;
        }

    }

    public boolean canMakeSlopes(IBlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }

}