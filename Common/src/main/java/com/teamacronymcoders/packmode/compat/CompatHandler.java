package com.teamacronymcoders.packmode.compat;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.packmode.PackModeConstants;
import com.teamacronymcoders.packmode.platform.PackModeServices;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class CompatHandler {
    private static final List<Compat> compat = Lists.newArrayList();
    private static final Map<String, String> compatClasses = Maps.newHashMap();

    public static void tryActivate() {
        compat.addAll(compatClasses.entrySet().stream()
                .filter(entry -> PackModeServices.PLATFORM.isModLoaded(entry.getKey()))
                .map(Entry::getValue)
                .map(CompatHandler::loadCompat)
                .filter(Objects::nonNull).toList());
    }


    /**
     * Registers a new compat that is only loaded when the modid of the compat class is found.
     *
     * @param modid The modid to check for
     * @param className The className that extends {@link Compat}. Needs to override setup and have a 0 param public constructor.
     */

    public static void registerCompat(final String modid, final String className) {
        compatClasses.put(modid, className);
    }

    public static void registerCompat(final String modid, final Class<? extends Compat> clazz) {
        registerCompat(modid, clazz.getName());
    }

    public static Compat loadCompat(String compatClassString) {
        Compat compatInstance = null;
        try {
            Class compatClass = Class.forName(compatClassString);
            Object objectInstance = compatClass.newInstance();
            if (objectInstance instanceof Compat) {
                compatInstance = (Compat) objectInstance;
            }
            else throw new IllegalArgumentException("Class " + compatClass + " does not implement Compat!");
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            PackModeConstants.LOGGER.error(e);
        }
        return compatInstance;
    }

    public static void setup() {
        compat.forEach(compatModule -> {
           compatModule.setup();
           PackModeConstants.LOGGER.info("Setting up PackMode Compat Module with class: " + compatModule.getClass().getName());
        });

    }
}
