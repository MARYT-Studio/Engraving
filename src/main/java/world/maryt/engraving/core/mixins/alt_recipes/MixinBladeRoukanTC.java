package world.maryt.engraving.core.mixins.alt_recipes;

import cn.mcmod_mmf.mmlib.util.RecipesUtil;
import cn.mmf.lastsmith.blades.BladeLoader;
import cn.mmf.lastsmith.blades.compat.BladeRoukanTC;
import cn.mmf.lastsmith.event.RegisterSlashBladeRecipeEvent;
import cn.mmf.lastsmith.recipe.RecipeAwakeBladeTLS;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.*;
import world.maryt.engraving.Engraving;
import world.maryt.engraving.config.EngravingConfig;

import static world.maryt.engraving.config.ConfigParser.getBlessed;

@Mixin(value = BladeRoukanTC.class, remap = false)
public abstract class MixinBladeRoukanTC {
    @Shadow
    private static void recipe() {}

    /**
     * @author RisingInIris2017
     * @reason alternate Roukan TC crafting table recipes
     */
    @SubscribeEvent
    @Overwrite
    public static void onRecipeRegister(RegisterSlashBladeRecipeEvent event) {
        if (getBlessed()) {
            engraving$alterRecipe();
            return;
        }
        if (Loader.isModLoaded("thaumcraft")) {
            recipe();
        } else if (EngravingConfig.alterRecipeRoukanXF) {
            engraving$alterRecipe();
        }
    }


    @Unique
    private static void engraving$alterRecipe() {
        // Roukan Senpurin
        ItemStack roukan = BladeLoader.getInstance().getCustomBlade("flammpfeil.slashblade.named.roukan");
        NBTTagCompound roukanTag = ItemSlashBlade.getItemTagCompound(roukan);
        ItemSlashBlade.RepairCount.set(roukanTag, 10);
        roukan.setTagCompound(roukanTag);
        ItemStack roukanXF = BladeLoader.getInstance().getCustomBlade("flammpfeil.slashblade.named.roukan_xf");
        RecipesUtil.getInstance().addRecipe(
                Engraving.MOD_ID,
                "flammpfeil.slashblade.named.roukan_xf",
                new RecipeAwakeBladeTLS(
                        new ResourceLocation(Engraving.MOD_ID, "flammpfeil.slashblade.named.roukan_xf"),
                        "sakura_blade",
                        roukanXF,
                        roukan,
                        " FQ",
                        "SQ ",
                        "B  ",
                        'Q', "fullSakura",
                        'F', new ItemStack(Items.FEATHER),
                        'S', "leafSakura",
                        'B', roukan
                        ));


        // Hakurou Senpurin
        ItemStack hakurou = BladeLoader.getInstance().getCustomBlade("flammpfeil.slashblade.named.hakurou");
        NBTTagCompound hakurouTag = ItemSlashBlade.getItemTagCompound(hakurou);
        ItemSlashBlade.RepairCount.set(hakurouTag, 10);
        hakurou.setTagCompound(hakurouTag);
        ItemStack hakurouXF = BladeLoader.getInstance().getCustomBlade("flammpfeil.slashblade.named.hakurou_xf");
        RecipesUtil.getInstance().addRecipe(
                Engraving.MOD_ID,
                "flammpfeil.slashblade.named.hakurou_xf",
                new RecipeAwakeBladeTLS(
                        new ResourceLocation(Engraving.MOD_ID, "flammpfeil.slashblade.named.hakurou_xf"),
                        "sakura_blade",
                        hakurouXF,
                        hakurou,
                        " FQ",
                        "SQ ",
                        "B  ",
                        'Q', "fullSakura",
                        'F', new ItemStack(Items.FEATHER),
                        'S', "leafSakura",
                        'B', hakurou
                ));
    }
}
