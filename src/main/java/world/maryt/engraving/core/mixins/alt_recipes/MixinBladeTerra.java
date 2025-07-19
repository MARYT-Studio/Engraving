package world.maryt.engraving.core.mixins.alt_recipes;

import cn.mcmod.sakura.item.ItemLoader;
import cn.mcmod_mmf.mmlib.util.RecipesUtil;
import cn.mmf.lastsmith.blades.BladeLoader;
import cn.mmf.slashblade_addon.blades.BladeTerra;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import world.maryt.engraving.Engraving;
import world.maryt.engraving.config.EngravingConfig;

import static world.maryt.engraving.config.ConfigParser.getBlessed;

@Mixin(value = BladeTerra.class, remap = false)
public abstract class MixinBladeTerra {
    @Inject(
            method = "InitRecipes",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true)
    private void inject_initRecipes(LoadEvent.PostInitEvent event, CallbackInfo ci) {
        if (getBlessed() || EngravingConfig.alterRecipeRoukanBotania) {
            ItemStack nagasada = BladeLoader.getInstance().getCustomBlade("flammpfeil.slashblade.named.nagasada");
            NBTTagCompound nagasadaTag = ItemSlashBlade.getItemTagCompound(nagasada);
            nagasada.setTagCompound(nagasadaTag);
            ItemStack terraBlade = SlashBlade.getCustomBlade("flammpfeil.slashblade.named.terra");
            RecipesUtil.getInstance().addRecipe(
                    Engraving.MOD_ID,
                    "flammpfeil.slashblade.named.terra",
                    new RecipeAwakeBlade(
                            new ResourceLocation(Engraving.MOD_ID, "flammpfeil.slashblade.named.terra"),
                            terraBlade,
                            nagasada,
                            " MV",
                            "FV ",
                            "BD ",
                            'D', ItemLoader.SAKURA_DIAMOND,
                            'V', "vine",
                            'M', getBlessed() ? "killCountT1" : "gemDiamond",
                            'F', "leafSakura",
                            'B', nagasada
                    ));
        }
        if (getBlessed()) ci.cancel();
    }
}
