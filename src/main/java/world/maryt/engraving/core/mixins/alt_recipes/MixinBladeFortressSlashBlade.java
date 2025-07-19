package world.maryt.engraving.core.mixins.alt_recipes;

import cn.mcmod_mmf.mmlib.util.RecipesUtil;
import cn.mmf.lastsmith.blades.BladeLoader;
import cn.mmf.lastsmith.blades.compat.BladeFortressSlashBlade;
import cn.mmf.lastsmith.recipe.RecipeAwakeBladeTLS;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import world.maryt.engraving.config.EngravingConfig;

import static world.maryt.engraving.config.ConfigParser.getBlessed;

@Mixin(value = BladeFortressSlashBlade.class, remap = false)
public abstract class MixinBladeFortressSlashBlade {
    @Inject(
            method = "recipe",
            at = @At(
                    value = "INVOKE",
                    target = "Lcn/mcmod_mmf/mmlib/util/RecipesUtil;addRecipe(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/item/crafting/IRecipe;)V",
                    ordinal = 5,
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    private static void inject_recipe(CallbackInfo ci, ItemStack reqiredBlade, NBTTagCompound tag, ItemStack thaum, ItemStack element, ItemStack voidness, ItemStack crimson, ItemStack thaum_update, ItemStack element_update, ItemStack voidness_update, ItemStack crimson_update) {
        if (getBlessed() || EngravingConfig.alterRecipeFortress) {
            RecipesUtil.getInstance().addRecipe("lastsmith", "flammpfeil.slashblade.named.fortress.thaumium.update",
                    new RecipeAwakeBladeTLS(
                            new ResourceLocation("lastsmith", "flammpfeil.slashblade.named.fortress.thaumium.update"),
                            "bewitched_blade",
                            thaum_update, thaum,
                            " AS",
                            "TK8",
                            "BT ",
                            'S', Items.EXPERIENCE_BOTTLE,
                            'T', getBlessed() ? "killCountT1" : "gemDiamond",
                            'A', Items.GOLDEN_APPLE,
                            'K', SlashBlade.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1),
                            '8', "leafSakura",
                            'B', thaum
                    )
            );
            RecipesUtil.getInstance().addRecipe("lastsmith", "flammpfeil.slashblade.named.fortress.elemental.update",
                    new RecipeAwakeBladeTLS(
                            new ResourceLocation("lastsmith", "flammpfeil.slashblade.named.fortress.elemental.update"),
                            "bewitched_blade",
                            element_update, element,
                            " AS",
                            "TK8",
                            "BT ",
                            'S', Items.EXPERIENCE_BOTTLE,
                            'T', getBlessed() ? "killCountT1" : "gemDiamond",
                            'A', Items.GOLDEN_APPLE,
                            'K', SlashBlade.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1),
                            '8', "leafSakura",
                            'B', element
                    )
            );
            RecipesUtil.getInstance().addRecipe("lastsmith", "flammpfeil.slashblade.named.fortress.void.update",
                    new RecipeAwakeBladeTLS(
                            new ResourceLocation("lastsmith", "flammpfeil.slashblade.named.fortress.void.update"),
                            "bewitched_blade",
                            voidness_update, voidness,
                            " AS",
                            "TK8",
                            "BT ",
                            'S', Items.EXPERIENCE_BOTTLE,
                            'T', getBlessed() ? "killCountT1" : "gemDiamond",
                            'A', Items.GOLDEN_APPLE,
                            'K', SlashBlade.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1),
                            '8', "leafSakura",
                            'B', voidness
                    )
            );
            RecipesUtil.getInstance().addRecipe("lastsmith", "flammpfeil.slashblade.named.fortress.crimson.update",
                    new RecipeAwakeBladeTLS(
                            new ResourceLocation("lastsmith", "flammpfeil.slashblade.named.fortress.crimson.update"),
                            "bewitched_blade",
                            crimson_update, crimson,
                            " AS",
                            "TK8",
                            "BT ",
                            'S', Items.EXPERIENCE_BOTTLE,
                            'T', getBlessed() ? "killCountT1" : "gemDiamond",
                            'A', Items.GOLDEN_APPLE,
                            'K', SlashBlade.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1),
                            '8', "leafSakura",
                            'B', crimson
                    )
            );
        }
        if (getBlessed()) ci.cancel();
    }
}
