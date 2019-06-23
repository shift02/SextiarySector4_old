package shift.sextiarysector4.lib.block;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import shift.sextiarysector4.core.tileentity.IFeatureBlock;

/**
 * SS4のほぼすべてのBlockの親クラス
 */
public class BlockSextiarySector4 extends Block {

    /**
     * TileEntityを作成するメソッド
     */
    protected BiFunction<IBlockState, IBlockReader, TileEntity> createTileEntityFunction;

    public BlockSextiarySector4(Properties properties) {
        super(properties);
    }

    /*
     *  TileEntity関係
     */
    public Block setCreateTileEntityFunction(BiFunction<IBlockState, IBlockReader, TileEntity> createTileEntityFunction) {
        this.createTileEntityFunction = createTileEntityFunction;
        return this;
    }

    public Block setCreateTileEntityFunction(Supplier<TileEntity> createTileEntityFunction) {
        this.createTileEntityFunction = (s, r) -> createTileEntityFunction.get();
        return this;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return createTileEntityFunction != null;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return createTileEntityFunction.apply(state, world);
    }

    /*
     *  Block -> TileEntityの橋渡しメソッド
     */

    /**
     * ブロックのクリック
     *
     * @param state
     * @param worldIn
     * @param pos
     * @param player
     * @param hand
     * @param side
     * @param hitX
     * @param hitY
     * @param hitZ
     * @return 動作をした場合はtrue
     */
    @Override
    @Deprecated
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (!this.hasTileEntity(state)) {
            return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof IFeatureBlock) {
            return ((IFeatureBlock) tileEntity).onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
        }

        return false;
    }

}
