package com.projectgalen.lib.utils;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: PGResouceBundle.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: April 30, 2024
//
// Copyright © 2024 Project Galen. All rights reserved.
//
// Permission to use, copy, modify, and distribute this software for any purpose with or without fee is hereby granted, provided
// that the above copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR
// CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
// NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
// ================================================================================================================================

import com.projectgalen.lib.utils.text.Macros;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public class PGResourceBundle extends ResourceBundle {

    private final Map<String, String> cache = Collections.synchronizedMap(new TreeMap<>());
    private final ResourceBundle      bundle;

    public PGResourceBundle(@NotNull String baseName) {
        bundle = ResourceBundle.getBundle(baseName);
    }

    public PGResourceBundle(@NotNull String baseName, @NotNull Control control) {
        bundle = ResourceBundle.getBundle(baseName, control);
    }

    public PGResourceBundle(@NotNull String baseName, @NotNull Locale locale) {
        bundle = ResourceBundle.getBundle(baseName, locale);
    }

    public PGResourceBundle(@NotNull String baseName, @NotNull Control control, @NotNull Locale locale) {
        bundle = ResourceBundle.getBundle(baseName, locale, control);
    }

    public PGResourceBundle(@NotNull String baseName, @NotNull Locale targetLocale, @NotNull Module module) {
        bundle = ResourceBundle.getBundle(baseName, targetLocale, module);
    }

    public PGResourceBundle(@NotNull String baseName, @NotNull Locale locale, @NotNull ClassLoader loader) {
        bundle = ResourceBundle.getBundle(baseName, locale, loader);
    }

    public PGResourceBundle(@NotNull String baseName, @NotNull Locale targetLocale, @NotNull ClassLoader loader, @NotNull Control control) {
        bundle = ResourceBundle.getBundle(baseName, targetLocale, loader, control);
    }

    /**
     * Returns an enumeration of the keys.
     *
     * @return an {@code Enumeration} of the keys contained in this {@code ResourceBundle} and its parent bundles.
     */
    public @NotNull @Override Enumeration<String> getKeys() {
        return bundle.getKeys();
    }

    /**
     * Gets an object for the given key from this resource bundle. Returns null if this resource bundle does not contain an object for the given key.
     *
     * @param key the key for the desired object
     *
     * @return the object for the given key, or null
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    protected @Override Object handleGetObject(@NotNull String key) {
        return _getObject(key).map(v -> cache.computeIfAbsent(key, __ -> Macros.expand(v, this::_getObject))).orElse(null);
    }

    private @NotNull Optional<String> _getObject(@NotNull String key) {
        try { return Optional.of(bundle.getString(key)); } catch(MissingResourceException e) { return Optional.empty(); }
    }
}
