package com.philipp.hardcore_extra_lives.mixin;


import com.philipp.hardcore_extra_lives.DeathStorage;
import com.philipp.hardcore_extra_lives.HardcoreExtraLivesMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
        storage.updateRemainingLives(-1);

        client.getServer();


//        ServerStatHandler statHandler = player.getStatHandler();
//        Stat<Identifier> stat = Stats.CUSTOM.getOrCreateStat(HardcoreExtraLivesMod.REMAINING_LIVES, StatFormatter.DEFAULT);
//        statHandler.increaseStat(player, stat, -1);
//
//        int remainingLives = statHandler.getStat(Stats.CUSTOM.getOrCreateStat(HardcoreExtraLivesMod.REMAINING_LIVES));
//
//        player.sendMessage(Text.literal("Remaining lives: " + remainingLives), false);

    }

}


