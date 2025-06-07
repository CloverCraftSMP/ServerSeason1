package me.lauriichan.clovercrafts1.mixin.inject.client.compat.emf;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.player.Player;
import traben.entity_model_features.models.animation.EMFAnimationEntityContext;

@Mixin(value = PlayerModel.class, priority = 1999)
public abstract class EMFPlayerModelFixMixin {

    @Shadow
    @Final
    private ModelPart cloak;

    @Inject(method = "renderCloak", at = @At("HEAD"), cancellable = true)
    private void fixEmf(final PoseStack poseStack, final VertexConsumer buffer, final int packedLight, final int packedOverlay,
        final CallbackInfo ci) {
        var entity = EMFAnimationEntityContext.getEMFEntity();
        if (entity == null || !(entity instanceof Player)) {
            this.cloak.render(poseStack, buffer, packedLight, packedOverlay);
            ci.cancel();
            return;
        }
    }

}
