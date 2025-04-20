package me.lauriichan.clovercrafts1.mixin.inject.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.lauriichan.clovercrafts1.mixin.api.client.IPlayerInfo;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mixin(value = PlayerInfo.class)
public abstract class PlayerInfoMixin implements IPlayerInfo {

    @Unique
    private volatile boolean updateDisplayName = false;

    @Inject(method = "setTabListDisplayName(Lnet/minecraft/network/chat/Component;)V", at = @At("TAIL"))
    public void clovercrafts1$setTabListDisplayname(Component component, CallbackInfo info) {
        updateDisplayName = true;
    }

    @Override
    public boolean requiresDisplayNameUpdate() {
        if (updateDisplayName) {
            updateDisplayName = false;
            return true;
        }
        return false;
    }

}
