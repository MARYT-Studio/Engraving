package world.maryt.engraving.api;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemDefinition;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.item.IItemDefinition")
@ZenRegister
public class ExpandItemDefinition {
    // SlashBlade checker
    @ZenMethod
    public static boolean isSlashBlade(IItemDefinition definition) {
        return definition.getInternal() instanceof ItemSlashBlade;
    }

    // Vanilla item class checker
    @ZenMethod
    public static boolean isTools(IItemDefinition definition, boolean isAxeInclude) {
        return (isAxeInclude && (definition.getInternal() instanceof ItemTool || definition.getInternal() instanceof ItemHoe)) ||
                (!isAxeInclude && !(definition.getInternal() instanceof ItemAxe) &&  (definition.getInternal() instanceof ItemTool || definition.getInternal() instanceof ItemHoe));
    }

    // All Items extend from ItemSword will return true, including SlashBlades, HAC Scythes.
    @ZenMethod
    public static boolean isWeapons(IItemDefinition definition, boolean isAxeInclude) {
        return (isAxeInclude && (definition.getInternal() instanceof ItemSword || definition.getInternal() instanceof ItemAxe)) ||
                (!isAxeInclude && definition.getInternal() instanceof ItemSword);
    }

    // TODO: isArmor
}
