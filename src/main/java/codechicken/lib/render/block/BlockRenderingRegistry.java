package codechicken.lib.render.block;

import codechicken.lib.util.ReflectionManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by covers1624 on 8/09/2016.
 */
public class BlockRenderingRegistry {

    private static final Map<EnumBlockRenderType, ICCBlockRenderer> blockRendererList = new HashMap<EnumBlockRenderType, ICCBlockRenderer>();

    private static boolean initialized = false;

    static {
        init();
    }

    public static void init() {
        if (initialized) {
            BlockRendererDispatcher parentDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            Field field = ReflectionHelper.findField(Minecraft.class, "blockRenderDispatcher", "field_175618_aM", "aR");
            ReflectionManager.set(field, new CCBlockRendererDispatcher(parentDispatcher, Minecraft.getMinecraft().getBlockColors()));
            initialized = true;
        }
    }

    public static EnumBlockRenderType createRenderType(String name) {
        return EnumHelper.addEnum(EnumBlockRenderType.class, name, new Class[0]);
    }

    public static boolean canHandle(EnumBlockRenderType type) {
        return blockRendererList.containsKey(type);
    }

    public static void registerRenderer(EnumBlockRenderType type, ICCBlockRenderer renderer) {
        switch (type) {
            case INVISIBLE:
            case LIQUID:
            case ENTITYBLOCK_ANIMATED:
            case MODEL:
                throw new IllegalArgumentException("Invalid EnumBlockRenderType! " + type.name());
        }
        if (blockRendererList.containsKey(type)) {
            throw new IllegalArgumentException("Unable to register duplicate render type!" + type.name());
        }
        blockRendererList.put(type, renderer);
    }

    public static void renderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite) {
        ICCBlockRenderer renderer = blockRendererList.get(state.getRenderType());
        if (renderer != null) {
            state = state.getActualState(world, pos);
            renderer.handleRenderBlockDamage(world, pos, state, sprite, Tessellator.getInstance().getBuffer());
        }
    }

    public static boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer) {
        ICCBlockRenderer renderer = blockRendererList.get(state.getRenderType());
        if (renderer != null) {
            return renderer.renderBlock(world, pos, state, buffer);
        }
        return false;
    }

    public static void renderBlockBrightness(IBlockState state, float brightness) {
        ICCBlockRenderer renderer = blockRendererList.get(state.getRenderType());
        if (renderer != null) {
            renderer.renderBrightness(state, brightness);
        }
    }

}