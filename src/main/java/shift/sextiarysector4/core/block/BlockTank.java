package shift.sextiarysector4.core.block;


import java.util.function.BiFunction;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import shift.sextiarysector4.core.tileentity.IFeatureBlock;

/**
 * 液体を入れるタンク
 */
public class BlockTank extends Block {

    protected BiFunction<IBlockState, IBlockReader, TileEntity> createTileEntityFunction;

    public BlockTank(Properties properties) {
        super(properties);
    }

    public Block setCreateTileEntityFunction(BiFunction<IBlockState, IBlockReader, TileEntity> createTileEntityFunction) {
        this.createTileEntityFunction = createTileEntityFunction;
        return this;
    }

    public Block setCreateTileEntityFunction(Supplier<TileEntity> createTileEntityFunction) {
        this.createTileEntityFunction = (s, r) -> createTileEntityFunction.get();
        return this;
    }

    @Deprecated
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof IFeatureBlock) {
            return ((IFeatureBlock) tileEntity).onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
        }

        return false;
    }

    public boolean propagatesSkylightDown(IBlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Deprecated
    @Nonnull
    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.create(2 / 16D, 0 / 16D, 2 / 16D, 14 / 16D, 16 / 16D, 14 / 16D);
    }

    /**
     * ブロックが隣接している時に自分自身を透明にするかどうか
     *
     * @param state              自分自身
     * @param adjacentBlockState 隣接しているブロック
     * @param side               方角
     * @return trueの場合は透明化
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isSideInvisible(IBlockState state, IBlockState adjacentBlockState, EnumFacing side) {
        if (side.equals(EnumFacing.UP) || side.equals(EnumFacing.DOWN)) {
            return adjacentBlockState.getBlock() == this;
        }
        return false;
    }

    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return createTileEntityFunction.apply(state, world);
    }
}
