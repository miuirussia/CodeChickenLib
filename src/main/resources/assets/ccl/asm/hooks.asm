list n_IItemRenderer
GETSTATIC net/minecraft/client/renderer/tileentity/TileEntityItemStackRenderer.field_147719_a : Lnet/minecraft/client/renderer/tileentity/TileEntityItemStackRenderer;
ALOAD 1
INVOKEVIRTUAL net/minecraft/client/renderer/tileentity/TileEntityItemStackRenderer.func_179022_a (Lnet/minecraft/item/ItemStack;)V
GOTO LEND #end of if statement

list IItemRenderer
ALOAD 2
INSTANCEOF codechicken/lib/render/item/IItemRenderer
IFEQ LELSE
ALOAD 2
ALOAD 1
INVOKEINTERFACE codechicken/lib/render/item/IItemRenderer.renderItem (Lnet/minecraft/item/ItemStack;)V
GOTO LEND
LELSE

list i_renderMapFirstPerson
ALOAD 1
ICONST_0
INVOKESTATIC codechicken/lib/render/item/map/MapRenderRegistry.shouldHandle (Lnet/minecraft/item/ItemStack;Z)Z
IFEQ LELSE
ALOAD 1
ICONST_0
INVOKESTATIC codechicken/lib/render/item/map/MapRenderRegistry.handleRender (Lnet/minecraft/item/ItemStack;Z)V
RETURN
LELSE

list i_BlockStateLoader
ALOAD 0
GETSTATIC net/minecraft/client/renderer/block/model/ModelBlockDefinition.field_178333_a : Lcom/google/gson/Gson;
INVOKESTATIC codechicken/lib/model/loader/blockstate/CCBlockStateLoader.handleLoad (Ljava/io/Reader;Lcom/google/gson/Gson;)Lnet/minecraft/client/renderer/block/model/ModelBlockDefinition;
ARETURN

list n_foregroundHook
INVOKESTATIC net/minecraft/client/renderer/RenderHelper.func_74518_a ()V
ALOAD 0
ILOAD 1
ILOAD 2
INVOKEVIRTUAL net/minecraft/client/gui/inventory/GuiContainer.func_146979_b (II)V
INVOKESTATIC net/minecraft/client/renderer/RenderHelper.func_74520_c ()V

list i_foregroundHook
ALOAD 0
INVOKESTATIC codechicken/lib/gui/GuiDraw.handleForegroundRender (Lnet/minecraft/client/gui/inventory/GuiContainer;)V
