package me.lauriichan.clovercrafts1.mixin.inject.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import me.lauriichan.clovercrafts1.mixin.api.client.IPlayerInfo;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mixin(RemotePlayer.class)
public abstract class RemotePlayerMixin extends AbstractClientPlayer {

    public RemotePlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void clovercrafts1$tick(CallbackInfo info) {
        if (!((IPlayerInfo) getPlayerInfo()).requiresDisplayNameUpdate()) {
            return;
        }
        ((RemotePlayer) ((Object) this)).refreshDisplayName();
    }

}
