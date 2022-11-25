package com.toteuch.compta.backend.utils.i18n;

public interface Labels {
    /**
     * Gets a localized label for the given key.
     *
     * @param key The key for the desired label.
     * @return The label for the given key.
     * @throws NullPointerException               If the given key is {@code null}
     * @throws java.util.MissingResourceException if no label can be found
     */
    String getString(String key);

    /**
     * Gets a localized label for the given key and arguments.
     *
     * @param key  The key for the desired label.
     * @param args Object(s) to format.
     * @return The label for the given key.
     * @throws NullPointerException               If the given key is {@code null}
     * @throws java.util.MissingResourceException If no label can be found
     * @throws IllegalArgumentException           If the pattern is invalid, or if an argument in the arguments array
     *                                            is not of the type expected by the format element(s) that use it.
     */
    String getString(String key, Object... args);
}
