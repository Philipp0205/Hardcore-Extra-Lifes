package com.philipp.hardcore_extra_lives.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.philipp.hardcore_extra_lives.HardcoreExtraLivesMod;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RestoreLivesCommand2 {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess
            commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("restorelives")
                .then(CommandManager.literal("all"))
                .executes(RestoreLivesCommand2::run));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ServerStatHandler statHandler = player.getStatHandler();

            Stat<Identifier> stat = Stats.CUSTOM.getOrCreateStat(HardcoreExtraLivesMod.REMAINING_LIVES, StatFormatter.DEFAULT);
            statHandler.increaseStat(player, stat, 3);

            int deaths = statHandler.getStat(Stats.CUSTOM.getOrCreateStat(HardcoreExtraLivesMod.REMAINING_LIVES));
            player.sendMessage(Text.literal("Deaths: " + deaths));
        }
        return 0;
    }


}
