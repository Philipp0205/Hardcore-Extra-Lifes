package com.philipp.hardcore_extra_lives.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.philipp.hardcore_extra_lives.HardcoreExtraLivesMod;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.text.Text;

public class DeathCountCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess
            commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("deathcount").executes(DeathCountCommand::run));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ServerStatHandler statHandler = player.getStatHandler();
//            int deaths = statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS));
            int deaths = statHandler.getStat(Stats.CUSTOM.getOrCreateStat(HardcoreExtraLivesMod.REMAINING_LIVES));
            player.sendMessage(Text.literal("Deaths: " + deaths));
//            player.increaseStat(Stats.DEATHS, 1);
        }
        return 0;
    }


}
