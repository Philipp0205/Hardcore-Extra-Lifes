package com.philipp.hardcore_extra_lives;

import com.philipp.hardcore_extra_lives.commands.DeathCountCommand;
import com.philipp.hardcore_extra_lives.commands.RestoreLivesCommand;
import com.philipp.hardcore_extra_lives.commands.RestoreLivesCommand2;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardcoreExtraLivesMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("hardcore_extra_lives");
    public static final String MOD_ID =  "hardcore_extra_lives";

    private static DeathStorage storage;

    public static final Identifier REMAINING_LIVES = new Identifier(MOD_ID, "remaining_lives");

    @Override
    public void onInitialize() {
        storage = new DeathStorage();
        LOGGER.info("Hello Fabric world!");

        // Commands
//        CommandRegistrationCallback.EVENT.register(RestoreLivesCommand::register);
        CommandRegistrationCallback.EVENT.register(DeathCountCommand::register);
        CommandRegistrationCallback.EVENT.register(RestoreLivesCommand2::register);

        Registry.register(Registry.CUSTOM_STAT, "remaining_lives", REMAINING_LIVES);
    }

    public static DeathStorage getStorage() {
        return storage;
    }


}
