package world.maryt.engraving;

import net.minecraftforge.fml.common.Mod;

@Mod(modid = Engraving.MOD_ID, name = Engraving.NAME, version = Engraving.VERSION, dependencies = Engraving.DEPENDENCIES)
public class Engraving {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String NAME = "Engraving";
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "after:slashblade;required-after:crafttweaker;required-after:mixinbooter";
}
