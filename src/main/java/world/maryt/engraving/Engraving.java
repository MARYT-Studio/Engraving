package world.maryt.engraving;

import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import world.maryt.engraving.commands.AttackTypesCommand;

@Mod(modid = Engraving.MOD_ID, name = Engraving.MOD_NAME, version = Engraving.VERSION, dependencies = Engraving.DEPENDENCIES)
public class Engraving {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "after:slashblade;after:mm-lib;after:lastsmith;required-after:crafttweaker;required-after:mixinbooter";

    @Mod.EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        CTChatCommand.registerCommand(new AttackTypesCommand());
    }
}
