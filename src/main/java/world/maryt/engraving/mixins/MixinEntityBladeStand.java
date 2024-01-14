package world.maryt.engraving.mixins;

import mods.flammpfeil.slashblade.entity.EntityBladeStand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import world.maryt.engraving.interfaces.IMixinEntityBladeStand;

@Mixin(value = EntityBladeStand.class, remap = false)
public abstract class MixinEntityBladeStand extends Entity implements IMixinEntityBladeStand {
    private MixinEntityBladeStand(World world) {
        super(world);
    }

    @Final
    @Shadow
    private static final DataParameter<ItemStack> WatchIndexBlade
            = EntityDataManager.createKey(MixinEntityBladeStand.class, DataSerializers.ITEM_STACK);
    @Final
    @Shadow
    private static final DataParameter<Integer> WatchIndexFlipState
            = EntityDataManager.createKey(MixinEntityBladeStand.class, DataSerializers.VARINT);
    @Final
    @Shadow
    private static final DataParameter<Integer> WatchIndexStandType
            = EntityDataManager.createKey(MixinEntityBladeStand.class, DataSerializers.VARINT);
    @Unique
    private static final DataParameter<ItemStack> engraving$originalFenceItem =
        EntityDataManager.createKey(MixinEntityBladeStand.class, DataSerializers.ITEM_STACK);
    public ItemStack engraving$getOriginalFenceItem(){
        return this.getDataManager().get(engraving$originalFenceItem);
    }
    public void engraving$setOriginalFenceItem(ItemStack stack) { this.getDataManager().set(engraving$originalFenceItem, stack); }

    /**
     * @author RisingInIris2017
     * @reason Add a new register entry for original fence item
     */
    @Overwrite
    protected void entityInit() {
        this.getDataManager().register(WatchIndexBlade, ItemStack.EMPTY);
        this.getDataManager().register(WatchIndexFlipState, 0);
        this.getDataManager().register(WatchIndexStandType, 0);
        this.getDataManager().register(engraving$originalFenceItem, ItemStack.EMPTY);
    }
    @Inject(
            method = "hitByEntity(Lnet/minecraft/entity/Entity;)Z",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Lmods/flammpfeil/slashblade/entity/EntityBladeStand;setDead()V"
            ),
            remap = false
    )
    private void inject_hitByEntity(Entity p_85031_1_, CallbackInfoReturnable<Boolean> cir) {
        World world = p_85031_1_.world;
        EntityItem droppedFenceItem = new EntityItem(world, this.posX, this.posY, this.posZ, this.engraving$getOriginalFenceItem());
        world.spawnEntity(droppedFenceItem);
    }
}
