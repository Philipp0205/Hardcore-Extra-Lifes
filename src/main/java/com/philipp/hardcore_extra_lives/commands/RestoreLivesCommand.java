package com.philipp.hardcore_extra_lives.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.philipp.hardcore_extra_lives.HardcoreExtraLivesMod;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class RestoreLivesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("restorelives")
                .then(CommandManager.literal("all").executes(RestoreLivesCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        System.out.println("Restoring lives command was run");
        HardcoreExtraLivesMod.getStorage().updateRemainingLives(3);
        return 1;
    }

//    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
//        dispatcher.register(CommandManager.literal("home")
//                .then(CommandManager.literal("return").executes(ReturnHomeCommand::run)));
//    }
//
//    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
//        IEntityDataSaver player = (IEntityDataSaver)context.getSource().getPlayer();
//
//        // not 0 means it contains SOMETHING
//        int[] homepos = player.getPersistentData().getIntArray("homepos");
//
//        if(homepos.length != 0) {
//            int[] playerPos = player.getPersistentData().getIntArray("homepos");
//            context.getSource().getPlayer().requestTeleport(playerPos[0], playerPos[1], playerPos[2]);
//
//            context.getSource().sendFeedback(new LiteralText("Player returned Home!"), true);
//            return 1;
//        }
//        else {
//            context.getSource().sendFeedback(new LiteralText("No Home Position has been Set!"), true);
//            return -1;
//        }
//    }
}
