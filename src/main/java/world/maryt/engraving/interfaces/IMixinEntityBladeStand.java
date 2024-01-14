package world.maryt.engraving.interfaces;

import net.minecraft.item.ItemStack;

public interface IMixinEntityBladeStand {
    ItemStack engraving$getOriginalFenceItem();
    void engraving$setOriginalFenceItem(ItemStack stack);
}
