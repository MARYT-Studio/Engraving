package world.maryt.engraving.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraft.entity.Entity;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.events.IEventManager")
@ZenRegister
public class ExpandEventManager {
    public static final EventList<EntityStylishRankChangeEvent> elEntityStylishRankChange = new EventList<>();

    @ZenMethod
    public static IEventHandle onEntityStylishRankChange(IEventManager manager, IEventHandler<EntityStylishRankChangeEvent> ev) {
        return elEntityStylishRankChange.add(ev);
    }

    public static Integer handleEntityStylishRankChangeEvent(Entity entity, int amount, boolean isDrop) {
        if (ExpandEventManager.elEntityStylishRankChange.hasHandlers()) {
            EntityStylishRankChangeEvent event = new EntityStylishRankChangeEvent(entity, amount, isDrop);
            ExpandEventManager.elEntityStylishRankChange.publish(event);
            return event.getAmount();
        }
        return null;
    }
}
