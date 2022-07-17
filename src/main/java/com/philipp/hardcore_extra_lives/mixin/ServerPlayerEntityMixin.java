package com.philipp.hardcore_extra_lives.mixin;

import com.mojang.authlib.GameProfile;
import com.philipp.hardcore_extra_lives.HardcoreExtraLivesMod;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    @Shadow
    ServerStatHandler statHandler;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }


    @Inject(method = "onDeath", at = @At("TAIL"))
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        this.increaseStat(HardcoreExtraLivesMod.REMAINING_LIVES, -1);

        int lives = statHandler.getStat(Stats.CUSTOM.getOrCreateStat(HardcoreExtraLivesMod.REMAINING_LIVES));
        this.sendMessage(Text.literal("Remaining server lives: " + lives));

        if (lives >= 0) {
            System.out.println("Revive player");
            this.setHealth(1.0F);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
            this.world.sendEntityStatus(this, (byte)35);
        }
    }


}
