// TODO: Make this class disable when SJAP 1.7.0+ is present, or let config of SJAP control this.


package world.maryt.engraving.core.mixins.tweaks;

import com.google.common.collect.Maps;
import mods.flammpfeil.slashblade.item.ItemProudSoul;
import mods.flammpfeil.slashblade.item.crafting.RecipeBladeSoulUpgrade;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nonnull;
import java.util.Map;

@Mixin(value = RecipeBladeSoulUpgrade.class, remap = false)
public abstract class MixinRecipeBladeSoulUpgrade extends ShapedOreRecipe {
    private MixinRecipeBladeSoulUpgrade(ResourceLocation loc, ItemStack result, Object... recipe) {
        super(loc, result, recipe);
    }

    /**
     * @author RisingInIris2017
     * @reason Remove unnecessary match check, move the logic into getCraftingResult.
     */
    @Overwrite
    public boolean matches(InventoryCrafting inv, World world) {
        return super.matches(inv, world);
    }

    /**
     * @author RisingInIris2017
     * @reason Let proudsoul with different enchantments be crafted into item without enchantment
     * By doing so player's inventory will not be stuffed with proudsoul pieces with unnecessary enchantments
     */
    @Overwrite
    @Nonnull
    public ItemStack getCraftingResult(InventoryCrafting crafting) {
        ItemStack result = super.getCraftingResult(crafting);

        Map<Enchantment,Integer> all = Maps.newHashMap();

        for(int index = 0; index < crafting.getSizeInventory(); index++) {
            ItemStack stack = crafting.getStackInSlot(index);
            if(stack.isEmpty()) continue;
            if(!(stack.getItem() instanceof ItemProudSoul)) continue;
            Map<Enchantment,Integer> emap = EnchantmentHelper.getEnchantments(stack);
            // If any soul material has empty enchantment,
            // materials MUST either are all with empty enchantment, or have two types of enchantments (empty and another one).
            // so we break the loop out here.
            if(emap.isEmpty()) {
                return result;
            }
            all.putAll(emap);
        }
        if (all.size() <= 1) {
            EnchantmentHelper.setEnchantments(all,result);
        }
        return result;
    }
}
