package world.maryt.engraving.mixins;

import mods.flammpfeil.slashblade.TagPropertyAccessor;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import world.maryt.engraving.Engraving;
import world.maryt.engraving.events.ExpandEventManager;

import java.util.Set;

@Mixin(value = StylishRankManager.class, remap = false)
public abstract class MixinStylishRankManager {

    @Shadow
    public static TagPropertyAccessor.TagPropertyIntegerWithRange RankPoint;

    @Shadow
    public static TagPropertyAccessor.TagPropertyLong LastRankPointUpdate;

    @Shadow
    public static Set<String> ignoreDamageTypes;

    @Shadow
    public static int RankRange;

    @Shadow
    public static NBTTagCompound getTag(Entity e) {
        return null;
    }

    @Shadow
    public static int getTotalRankPoint(Entity e){
        return 0;
    }

    @Unique
    private static String engraving$reason = "";

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
                Integer newAmount = ExpandEventManager.handleEntityStylishRankChangeEvent(e, amount, engraving$reason);
                if (newAmount == null) {
                    rankPoint += amount;
                } else {
                    rankPoint += newAmount;
                }
                RankPoint.set(tag, rankPoint);
                LastRankPointUpdate.set(tag, e.world.getTotalWorldTime());
            }
        }
    }


    /**
     * @author RisingInIris2017
     * @reason Rank Point drop occurring when player get hurt also need to be controlled.
     */
    @Overwrite
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void LivingHurtEvent(LivingHurtEvent e){
        if(e.isCanceled()) return;
        if(e.getEntity() == null) return;
        if(!(e.getEntity() instanceof EntityPlayer)) return;
        if(e.getSource().isUnblockable() && e.getSource().getTrueSource() != null) return;

        String type = e.getSource().getDamageType();
        if (Engraving.Blessed && Engraving.bypassDamageTypes.contains(type)) return;

        //反射攻撃ではないこと
        if(e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof EntityLivingBase){
            EntityLivingBase attacker = (EntityLivingBase)e.getSource().getTrueSource();

            if(attacker.getRevengeTimer() == attacker.ticksExisted)
                return;
        }

        if(ignoreDamageTypes.contains(type)) return;

        NBTTagCompound tag = getTag(e.getEntity());

        long lastUpdate = LastRankPointUpdate.get(tag);
        long now = e.getEntity().world.getTotalWorldTime();

        int time = RankRange * 2;
        if (Engraving.Blessed) {
            int difficulty = e.getEntity().getEntityWorld().getWorldInfo().getDifficulty().getDifficultyId();
            if (difficulty == 1) {
                time = Engraving.stylishRankDropTimeEasy;
            }
            if (difficulty == 2) {
                time = Engraving.stylishRankDropTimeNormal;
            }
            if (difficulty == 3) {
                time = Engraving.stylishRankDropTimeHard;
            }
        }


        if (time < now - lastUpdate) {
            engraving$reason = "DropLongTime";
            addRankPoint(e.getEntity(), -RankPoint.get(tag));
        } else {
            engraving$reason = "DropShortTime";
            addRankPoint(e.getEntity(), -RankRange * 2);
        }
        engraving$reason = "";
        LastRankPointUpdate.set(tag, now);
    }
}
