package world.maryt.engraving.api;

import cn.mmf.lastsmith.item.ItemSlashBladeTLS;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemDefinition;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.item.IItemDefinition")
@ModOnly("lastsmith")
@ZenRegister
public class ExpandItemDefinitionTLS {
    // SlashBladeTLS checker
    @ZenMethod
    public static boolean isSlashBladeTLS(IItemDefinition definition) {
        return definition.getInternal() instanceof ItemSlashBladeTLS;
    }
}
