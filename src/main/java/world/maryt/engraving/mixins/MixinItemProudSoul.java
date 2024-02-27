package world.maryt.engraving.mixins;

import mods.flammpfeil.slashblade.entity.EntityBladeStand;
import mods.flammpfeil.slashblade.entity.EntityGrimGripKey;
import mods.flammpfeil.slashblade.item.ItemProudSoul;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import world.maryt.engraving.interfaces.IMixinEntityBladeStand;

@Mixin(value = ItemProudSoul.class, remap = false)
public abstract class MixinItemProudSoul extends Item {
    /**
     * @author RisingInIris2017
     * @reason Make Proudsoul transform any fence into blade stand
     */
    @SuppressWarnings("NullableProblems")
    @Overwrite
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        Item targetBlockItem = Item.getItemFromBlock(block);

        // Prevent getOreID() to blocks with no item form, and game won't crash.
        if (targetBlockItem == Items.AIR) return super.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);

        ItemStack targetBlockItemStack = new ItemStack(targetBlockItem, 1, block.damageDropped(state));
        int[] targetBlockItemOreDictIDList = OreDictionary.getOreIDs(targetBlockItemStack);
        boolean isFenceMatched = targetBlockItemOreDictIDList.length == 1 && OreDictionary.getOreName(targetBlockItemOreDictIDList[0]).equals("fenceWood");
        boolean isQuartzMatched = targetBlockItemOreDictIDList.length == 1 && OreDictionary.getOreName(targetBlockItemOreDictIDList[0]).equals("blockQuartz");

        if(!world.isRemote && isFenceMatched && player.isSneaking()){
            world.setBlockToAir(pos);
            EntityBladeStand e = new EntityBladeStand(world);
            e.setStandType(stack.getMetadata());
            e.setPositionAndRotation(pos.getX() + 0.5 ,pos.getY() + 0.5 ,pos.getZ() + 0.5,Math.round(player.rotationYaw / 45.0f) * 45.0f + 180.0f,e.rotationPitch);
            ((IMixinEntityBladeStand)e).engraving$setOriginalFenceItem(targetBlockItemStack);
            world.spawnEntity(e);
            return EnumActionResult.SUCCESS;
        }else if(stack.getMetadata() == 4 //crystal
                && stack.hasTagCompound() && stack.getTagCompound().hasKey("GPX")
                && isQuartzMatched
                && player.isSneaking()) {

            NBTTagCompound tag = stack.getTagCompound();
            int x = tag.getInteger("GPX");
            int y = tag.getInteger("GPY");
            int z = tag.getInteger("GPZ");

            BlockPos gripPos = new BlockPos(x, y, z);

            if (30 > gripPos.distanceSq(pos))
                return EnumActionResult.FAIL;

            //world.setBlockToAir(pos);
            if (!world.isRemote) {
                EntityGrimGripKey e = new EntityGrimGripKey(world);
                e.setPositionAndRotation(
                        pos.getX() + 0.5 + side.getFrontOffsetX(),
                        pos.getY() + 0.5 + side.getFrontOffsetY(),
                        pos.getZ() + 0.5 + side.getFrontOffsetZ(), e.rotationYaw, e.rotationPitch);
                e.setGrimGripPos(gripPos);
                world.spawnEntity(e);
            }

            stack.shrink(1);

            return EnumActionResult.SUCCESS;
        }else if(stack.getMetadata() == 4 //crystal
                && !world.isRemote
                && player.isSneaking()){

            stack.setTagInfo("GPX", new NBTTagInt(pos.getX() + side.getFrontOffsetX()));
            stack.setTagInfo("GPY", new NBTTagInt(pos.getY() + side.getFrontOffsetY()));
            stack.setTagInfo("GPZ", new NBTTagInt(pos.getZ() + side.getFrontOffsetZ()));

            return EnumActionResult.SUCCESS;
        } else {
            return super.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
        }
    }
}
