package com.philipp.hardcore_extra_lives.mixin;


import com.philipp.hardcore_extra_lives.DeathStorage;
import com.philipp.hardcore_extra_lives.HardcoreExtraLivesMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "onDeathMessage", at = @At("TAIL"))
    public void onClientDeath(DeathMessageS2CPacket packet, CallbackInfo ci) {
        DeathStorage storage = HardcoreExtraLivesMod.getStorage();
        storage.updateRemainingLives(- 1);

        int remainingLives = storage.getRemainingLives();
        System.out.println("DeathLog: " + packet.getMessage().getContent().toString());

        if (client.player != null) {
            client.player.sendMessage(Text.literal("Player died! Remaining lives: " + remainingLives), false);
        }

    }

}


