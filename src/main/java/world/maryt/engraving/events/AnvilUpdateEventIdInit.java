package world.maryt.engraving.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class AnvilUpdateEventIdInit {
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        if (player.world.isRemote) return;
        if (!player.getEntityData().hasKey("PlayerPersisted")) player.getEntityData().setTag("PlayerPersisted", new NBTTagCompound());
        player.getEntityData().getCompoundTag("PlayerPersisted").setInteger("AnvilUpdateEventId", 0);
    }
}
