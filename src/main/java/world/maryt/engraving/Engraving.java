package world.maryt.engraving;

import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import world.maryt.engraving.commands.AttackTypesCommand;

@Mod(modid = Engraving.MOD_ID, name = Engraving.NAME, version = Engraving.VERSION, dependencies = Engraving.DEPENDENCIES)
public class Engraving {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String NAME = "Engraving";
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "after:slashblade;required-after:crafttweaker;required-after:mixinbooter";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        CTChatCommand.registerCommand(new AttackTypesCommand());
    }
}
