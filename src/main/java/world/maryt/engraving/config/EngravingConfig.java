package world.maryt.engraving.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import world.maryt.engraving.Engraving;

@Config(modid = Engraving.MOD_ID)
@Mod.EventBusSubscriber(modid = Engraving.MOD_ID)
public class EngravingConfig {
	private final static String config = Engraving.MOD_ID + ".config.";

	// Keep this config variable for debug usage.
	// To see if current version has features binding to this, comment the annotation.
	@SuppressWarnings("unused")
	@Config.LangKey(config + "debug")
	@Config.Comment("Enable this only for debug purposes.")
	public static boolean DEBUG = false;

	@Config.RequiresMcRestart
	@Config.LangKey(config + "working_mode")
	@Config.Comment("Keep it as \"Default\" and DON'T TOUCH")
	public static String WorkingMode = "Default";

	@Config.LangKey(config + "stylish_rank_drop_time_easy")
	@Config.Comment("Time length between player updates Stylish Rank points last time and player get damaged (under EASY difficulty.). Integer, \"tick\" as unit.")
	public static int stylishRankDropTimeEasy = 200;

	@Config.LangKey(config + "stylish_rank_drop_time_normal")
	@Config.Comment("Time length between player updates Stylish Rank points last time and player get damaged (under NORMAL difficulty.). Integer, \"tick\" as unit.")
	public static int stylishRankDropTimeNormal = 200;

	@Config.LangKey(config + "stylish_rank_drop_time_hard")
	@Config.Comment("Time length between player updates Stylish Rank points last time and player get damaged (under HARD difficulty.). Integer, \"tick\" as unit.")
	public static int stylishRankDropTimeHard = 200;

	@Config.LangKey(config + "bypass_damage_types")
	@Config.Comment("Damage Types that player can bypass Stylish Rank drop when get these types of damage.")
	public static String[] bypassDamageTypes = new String[]{};

	@Config.RequiresMcRestart
	@Config.LangKey(config + "alter_recipe_roukan_xf")
	@Config.Comment("Give Roukan Senpurin (Thaumcraft Compat) a crafting table recipe and no need for advancement & TC Research.")
	public static boolean alterRecipeRoukanXF = false;
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Engraving.MOD_ID)) {
			ConfigManager.sync(Engraving.MOD_ID, Config.Type.INSTANCE);
		}
	}
}
