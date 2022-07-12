package com.philipp.hardcore_extra_lives;

import com.philipp.hardcore_extra_lives.commands.RestoreLivesCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardcoreExtraLivesMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("hardcore_extra_lives");

    private static DeathStorage storage;



    @Override
    public void onInitialize() {
        storage = new DeathStorage();
        LOGGER.info("Hello Fabric world!");

        // Commands
        CommandRegistrationCallback.EVENT.register(RestoreLivesCommand::register);
    }

    public static DeathStorage getStorage() {
        return storage;
    }
}
