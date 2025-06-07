package me.lauriichan.clovercrafts1.mixin.inject.client.compat.ae2;

import java.util.List;
import java.util.Locale;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import appeng.core.localization.GuiText;
import appeng.core.localization.Tooltips;
import appeng.items.storage.SpatialStorageCellItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

@Mixin(value = SpatialStorageCellItem.class)
public abstract class SpatialStorageCellItemMixin {

    @Shadow
    @Final
    private static String TAG_PLOT_ID;
    @Shadow
    @Final
    private static String TAG_PLOT_SIZE;

    @Shadow
    @Final
    private int maxRegion;

    @Overwrite
    public void appendHoverText(ItemStack stack, Level level, List<Component> lines, TooltipFlag advancedTooltips) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(TAG_PLOT_ID, Tag.TAG_INT)) {
            lines.add(Tooltips.of(GuiText.Unformatted).withStyle(ChatFormatting.ITALIC));
            lines.add(Tooltips.of(GuiText.SpatialCapacity, maxRegion, maxRegion, maxRegion));
            return;
        }
        var plotId = tag.getInt(TAG_PLOT_ID);

        // Add a serial number to allows players to keep different cells apart
        // Try to make this a little more flavorful.
        String serialNumber = String.format(Locale.ROOT, "SP-%04d", plotId);
        lines.add(Tooltips.of(GuiText.SerialNumber, serialNumber));

        if (tag != null && tag.contains(TAG_PLOT_SIZE, Tag.TAG_LONG)) {
            BlockPos size = BlockPos.of(tag.getLong(TAG_PLOT_SIZE));
            lines.add(Tooltips.of(GuiText.StoredSize, size.getX(), size.getY(), size.getZ()));
        }
    }

}
