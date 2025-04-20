package me.lauriichan.clovercrafts1.mixin.inject.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@Mixin(Player.class)
public abstract class NametagPlayerMixin {

    @Inject(method = "getName", at = @At(value = "RETURN"), cancellable = true)
    public void getName(CallbackInfoReturnable<Component> info) {
        ICuriosItemHandler inventory = CuriosApi.getCuriosInventory((Player) ((Object) this)).resolve().orElse(null);
        if (inventory == null) {
            info.cancel();
            return;
        }
        ICurioStacksHandler handler = inventory.getStacksHandler("nametag").orElse(null);
        if (handler == null || handler.getSlots() != 1) {
            info.cancel();
            return;
        }
        ItemStack itemStack = handler.getStacks().getStackInSlot(0);
        if (!itemStack.is(Items.NAME_TAG) || !itemStack.hasCustomHoverName()) {
            info.cancel();
            return;
        }
        info.setReturnValue(itemStack.getHoverName());
    }

}
