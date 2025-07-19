package world.maryt.engraving.core.mixins.alt_recipes;

import cn.mcmod.sakura.item.ItemLoader;
import cn.mcmod_mmf.mmlib.util.RecipesUtil;
import cn.mmf.lastsmith.blades.BladeLoader;
import cn.mmf.lastsmith.blades.compat.BladeRoukanBotania;
import cn.mmf.lastsmith.event.RegisterSlashBladeRecipeEvent;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import world.maryt.engraving.Engraving;
import world.maryt.engraving.config.EngravingConfig;

import static world.maryt.engraving.config.ConfigParser.getBlessed;

@Mixin(value = BladeRoukanBotania.class, remap = false)
public abstract class MixinBladeRoukanBotania {
    @Shadow
    private static void recipe() {}

    /**
     * @author RisingInIris2017
     * @reason alternate Roukan Botania crafting table recipes
     */
    @SubscribeEvent
    @Overwrite
    public static void onRecipeRegister(RegisterSlashBladeRecipeEvent event) {
        if (getBlessed()) {
            engraving$alterRecipe();
            return;
        }
        if (Loader.isModLoaded("botania")) {
            recipe();
        } else if (EngravingConfig.alterRecipeRoukanBotania) {
            engraving$alterRecipe();
        }
    }

    @Unique
    private static void engraving$alterRecipe() {
        ItemStack nagasada = BladeLoader.getInstance().getCustomBlade("flammpfeil.slashblade.named.nagasada");
        NBTTagCompound nagasadaTag = ItemSlashBlade.getItemTagCompound(nagasada);
        nagasada.setTagCompound(nagasadaTag);
        ItemStack roukanBot = BladeLoader.getInstance().getCustomBlade("flammpfeil.slashblade.named.roukan_bot");
        RecipesUtil.getInstance().addRecipe(
                Engraving.MOD_ID,
                "flammpfeil.slashblade.named.roukan_bot",
                new RecipeAwakeBlade(
                        new ResourceLocation(Engraving.MOD_ID, "flammpfeil.slashblade.named.roukan_bot"),
                        roukanBot,
                        nagasada,
                        " MK",
                        "FV ",
                        "B  ",
                        'K', ItemLoader.SAKURAKATANA,
                        'V', "vine",
                        'M', getBlessed() ? "killCountT1" : "gemDiamond",
                        'F', "fullSakura",
                        'B', nagasada
                ));
    }
}
