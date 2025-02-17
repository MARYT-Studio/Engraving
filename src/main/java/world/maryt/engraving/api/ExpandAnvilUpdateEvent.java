package world.maryt.engraving.api;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.PlayerAnvilUpdateEvent;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenExpansion("crafttweaker.event.PlayerAnvilUpdateEvent")
@ZenRegister
public class ExpandAnvilUpdateEvent {
    // Timestamp
    @ZenMethod
    public static long getTimeStamp(PlayerAnvilUpdateEvent event) {
        return java.time.Instant.now().getEpochSecond();
    }
}
