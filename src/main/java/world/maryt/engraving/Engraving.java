package world.maryt.engraving;

import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import world.maryt.engraving.commands.AttackTypesCommand;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod(modid = Engraving.MOD_ID, name = Engraving.NAME, version = Engraving.VERSION, dependencies = Engraving.DEPENDENCIES)
public class Engraving {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String NAME = "Engraving";
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "after:slashblade;required-after:crafttweaker;required-after:mixinbooter";

    public static boolean Blessed = false;

    public static int stylishRankDropTimeEasy = 200;
    public static int stylishRankDropTimeNormal = 200;
    public static int stylishRankDropTimeHard = 200;

    public static List<String> bypassDamageTypes = new ArrayList<>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent preEvent) {
        Configuration config = new Configuration(new File(Loader.instance().getConfigDir(), "engraving.cfg"));
        try {
            config.load();

            {
                Property property = config.get(Configuration.CATEGORY_GENERAL, "Blessed", Engraving.Blessed);
                property.setComment("");
                Engraving.Blessed = property.getBoolean();
                property.setShowInGui(true);
            }
            // Time length between player updates Stylish Rank points last time and player get damaged
            // Different difficulties, different length.
            // Integer, "tick" as unit.
            {
                Property property = config.get(Configuration.CATEGORY_GENERAL, "stylishRankDropTimeEasy", Engraving.stylishRankDropTimeEasy);
                property.setComment("When this option is enabled, the remaining options take effect. You usually do not need to set these configuration options.");
                Engraving.stylishRankDropTimeEasy = property.getInt();
                property.setShowInGui(true);
            }
            {
                Property property = config.get(Configuration.CATEGORY_GENERAL, "stylishRankDropTimeNormal", Engraving.stylishRankDropTimeNormal);
                property.setComment("Time length between player updates Stylish Rank points last time and player get damaged (under NORMAL difficulty.). Integer, \"tick\" as unit.");
                Engraving.stylishRankDropTimeNormal = property.getInt();
                property.setShowInGui(true);
            }
            {
                Property property = config.get(Configuration.CATEGORY_GENERAL, "stylishRankDropTimeHard", Engraving.stylishRankDropTimeHard);
                property.setComment("Time length between player updates Stylish Rank points last time and player get damaged (under HARD difficulty.). Integer, \"tick\" as unit.");
                Engraving.stylishRankDropTimeHard = property.getInt();
                property.setShowInGui(true);
            }
            {
                Property property = config.get(Configuration.CATEGORY_GENERAL, "bypassDamageTypes", new String[]{});
                property.setComment("Damage Types that player can bypass Stylish Rank drop when get these types of damage.");
                Engraving.bypassDamageTypes = Arrays.asList(property.getStringList());
                property.setShowInGui(true);
            }
        } finally {
            config.save();
        }
    }

    @Mod.EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        CTChatCommand.registerCommand(new AttackTypesCommand());
    }
}
