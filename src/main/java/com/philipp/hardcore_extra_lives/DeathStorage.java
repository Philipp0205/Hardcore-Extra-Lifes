package com.philipp.hardcore_extra_lives;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class DeathStorage {
    public static final Logger LOGGER = LogManager.getLogger();

    public static int REMAINING_LIVES = 3;

    private DeathMessageS2CPacket lastUsedPackage;

    private final File deathLogFile;

    public DeathStorage() {
        this.deathLogFile = FabricLoader.getInstance().getGameDir().resolve("death_log.dat").toFile();
        initRemainingLives();
    }

    private void initRemainingLives() {
        System.out.println("initRemainingLives 3");
        setRemainingLives(this.deathLogFile, 3);

    }


    /// Save the remaining lives to the file.
    public void setRemainingLives(File file, int remainingLives) {
        System.out.println("setRemainingLives " + remainingLives);
        final NbtCompound livesNbt = new NbtCompound();

        livesNbt.putInt("RemainingLives", remainingLives);

        try {
            NbtIo.write(livesNbt, file);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Failed to save remaining lives.");
        }
    }

    public void updateRemainingLives(int amount) {


        int remainingLives = getRemainingLives() + amount;

        System.out.println("decreaseRemainingLives: from " + getRemainingLives() + " to " + remainingLives);
        setRemainingLives(this.deathLogFile, remainingLives);
    }



    void saveRemainingLivesToFile(File file, int remainingLives) {
        NbtCompound nbtCompound = file.exists() ? loadNbtFromExistingFile(file) : createNewNbtCompound();
        nbtCompound.putInt("RemainingLives", remainingLives);

        try {
            NbtIo.write(nbtCompound, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns {@link NbtCompound} containing the number of remaining lives .
     * or creates it if file does not exist.
     *
     * @param file the file to read from
     * @return the NbtCompound containing the number of remaining lives
     */
    public int getRemainingLivesFromFile(File file) {
        NbtCompound nbtCompound = file.exists() ? loadNbtFromExistingFile(file) : createNewNbtCompound();

        int remainingLives = nbtCompound.getInt("RemainingLives");
        LOGGER.info("Remaining lives: " + remainingLives);

        return remainingLives;

    }

    /**
     * Load the remaining lives from  file.
     *
     * @param file the file that should be loaded
     * @return nbt compound with the remaining lives
     */
    private NbtCompound loadNbtFromExistingFile(File file) {
        NbtCompound deathNbt;

        try {
            deathNbt = NbtIo.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return deathNbt;
    }

    /**
     * Create new {@link NbtCompound} with the remaining lives and set initial value {@link #REMAINING_LIVES}
     *
     * @return the nbt compound with the remaining lives.
     */
    private NbtCompound createNewNbtCompound() {
        NbtCompound deathNbt = new NbtCompound();
        deathNbt.putInt("RemainingLives", REMAINING_LIVES);
        return deathNbt;
    }

    public int getRemainingLives() {
        return this.getRemainingLivesFromFile(this.deathLogFile);
    }
}
