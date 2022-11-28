package com.teamacronymcoders.packmode.compat;

/**
 * <p>
 * Register using {@link CompatHandler#registerCompat(String, Class)}
 * </p>
 *
 *
 * Extending classes MUST have a public, no argument constructor
 */
public abstract class Compat {
    public abstract void setup();
}
