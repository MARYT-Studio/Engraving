package world.maryt.engraving.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static world.maryt.engraving.Engraving.DEBUG;
import static world.maryt.engraving.Engraving.LOGGER;

@Mixin(value = ContainerRepair.class, remap = false)
public abstract class MixinContainerRepair {
    @Shadow
    @Final
    private EntityPlayer player;
    @Shadow
    @Final
    private World world;

    @Inject(
            method = "updateRepairOutput",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Lnet/minecraftforge/common/ForgeHooks;onAnvilChange(Lnet/minecraft/inventory/ContainerRepair;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/inventory/IInventory;Ljava/lang/String;I)Z",
                    remap = false
            )
    )
    // Record the *times* of AnvilUpdateEvent has fired as its ID
    private void inject_updateRepairOutput(CallbackInfo ci) {
        if (!player.getEntityData().hasKey("PlayerPersisted")) player.getEntityData().setTag("PlayerPersisted", new NBTTagCompound());
        NBTTagCompound persisted = player.getEntityData().getCompoundTag("PlayerPersisted");
        if (!world.isRemote) {
            if (!persisted.hasKey("AnvilUpdateEventId")) {
                persisted.setInteger("AnvilUpdateEventId", 1);
            } else {
                persisted.setInteger("AnvilUpdateEventId", persisted.getInteger("AnvilUpdateEventId") + 1);
            }
            if (DEBUG) LOGGER.info("AnvilUpdateEventId: {}", persisted.getInteger("AnvilUpdateEventId"));
        }
    }
}
