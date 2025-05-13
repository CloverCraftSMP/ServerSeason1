package me.lauriichan.clovercrafts1.mixin.inject.common.compat.crittersandcompanions;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.eterdelta.crittersandcompanions.extension.ISilkLeashState;
import com.github.eterdelta.crittersandcompanions.handler.PlayerHandler;
import com.github.eterdelta.crittersandcompanions.registry.CACItems;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;

@Mixin(value = PlayerHandler.class, remap = false)
public class PlayerHandlerMixin {

    @Inject(target = @Desc(value = "onPlayerEntityInteract", args = {
        Entity.class,
        UseOnContext.class
    }, ret = InteractionResult.class), cancellable = true, at = @At("HEAD"))
    private static void clovercrafts1$onPlayerEntityInteract(Entity target, UseOnContext context,
        CallbackInfoReturnable<InteractionResult> info) {
        if (!context.getLevel().isClientSide() || !(context.getPlayer() instanceof ISilkLeashState playerState) || !(target instanceof LivingEntity entity) || context.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        ItemStack itemStack = context.getPlayer().getItemInHand(PlayerHandler.getOppositeHand(context.getHand()));
        if (itemStack.is(CACItems.SILK_LEAD.get())) {
            info.setReturnValue(null);
            return;
        }
        itemStack = context.getItemInHand();
        if (itemStack.is(CACItems.SILK_LEAD.get()) || itemStack.is(Items.LEAD)
            || (!playerState.getLeashingEntities().isEmpty() && !playerState.getLeashingEntities().contains(entity))) {
            return;
        }
        info.setReturnValue(null);
    }

}
