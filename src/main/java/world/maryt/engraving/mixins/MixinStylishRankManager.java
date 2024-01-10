package world.maryt.engraving.mixins;

import mods.flammpfeil.slashblade.TagPropertyAccessor;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = StylishRankManager.class, remap = false)
public abstract class MixinStylishRankManager {

    @Shadow
    public static TagPropertyAccessor.TagPropertyIntegerWithRange RankPoint;

    @Shadow
    public static TagPropertyAccessor.TagPropertyLong LastRankPointUpdate;

    @Shadow
    public static NBTTagCompound getTag(Entity e) {
        return null;
    }

    @Shadow
    public static int getTotalRankPoint(Entity e){
        return 0;
    }

    /**
     * @author RisingInIris2017
     * @reason This method is the endpoint of rank points' adding.
     * There is little chance this method modified by other mods.
     */
    @Overwrite
    public static void addRankPoint(Entity e, int amount) {
        if (e != null) {
            if (!e.world.isRemote) {
                NBTTagCompound tag = getTag(e);
                int rankPoint = getTotalRankPoint(e);
                rankPoint += amount;
                RankPoint.set(tag, rankPoint);
                LastRankPointUpdate.set(tag, e.world.getTotalWorldTime());
            }
        }
    }
}
