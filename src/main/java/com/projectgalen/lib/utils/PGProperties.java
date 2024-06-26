package com.projectgalen.lib.utils;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: PGProperties.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 07, 2024
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
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@SuppressWarnings({ "FieldCanBeLocal", "unused" })
public class PGProperties extends Properties {

    private static final PGResourceBundle                msgs           = new PGResourceBundle("com.projectgalen.lib.utils.messages");
    private static final Function<PGProperties, Boolean> DEFAULT_LOADER = p -> true;

    private final Function<PGProperties, Boolean> loader;
    private final long                            reloadPeriod;
    private final TimeUnit                        timeUnit;
    private final ScheduledExecutorService        executor;

    public PGProperties(Properties defaults) {
        this(DEFAULT_LOADER, -1, null, defaults);
    }

    public PGProperties() {
        this(DEFAULT_LOADER, -1, null, null);
    }

    /**
     * Create a new instance of {@link PGProperties} that reloads it's values at a set interval.
     *
     * @param loader       The {@link Function} lambda that will do both the initial load and subsequent reloads.
     * @param reloadPeriod The interval period. A value less than 1 means the properties will not be reloaded after the initial load.
     * @param timeUnit     The units for the value of <code>reloadPeriod</code>. The default is {@link TimeUnit#MILLISECONDS}.
     * @param defaults     The defaults
     */
    private PGProperties(@NotNull Function<PGProperties, Boolean> loader, long reloadPeriod, TimeUnit timeUnit, Properties defaults) {
        super(defaults);
        if(!loader.apply(this)) throw new RuntimeException(msgs.getString("msg.err.prop_load_failure"));
        expandMacros();

        if(reloadPeriod > 0) {
            this.loader       = loader;
            this.reloadPeriod = reloadPeriod;
            this.timeUnit     = ofNullable(timeUnit).orElse(TimeUnit.MILLISECONDS);
            this.executor     = Executors.newSingleThreadScheduledExecutor();
            this.executor.scheduleAtFixedRate(this::reload, this.reloadPeriod, this.reloadPeriod, this.timeUnit);
        }
        else {
            this.loader       = null;
            this.reloadPeriod = -1;
            this.timeUnit     = null;
            this.executor     = null;
        }
    }

    public byte getByte(@NotNull @NonNls String key, byte defaultValue) {
        try { return Byte.parseByte(getProperty(key)); }
        catch(Exception ignored) { return defaultValue; }
    }

    public byte getByte(@NotNull @NonNls String key) {
        return getByte(key, (byte)0);
    }

    public double getDouble(@NotNull @NonNls String key, double defaultValue) {
        try { return Double.parseDouble(getProperty(key)); }
        catch(Exception ignored) { return defaultValue; }
    }

    public double getDouble(@NotNull @NonNls String key) {
        return getDouble(key, 0);
    }

    public float getFloat(@NotNull @NonNls String key, float defaultValue) {
        try { return Float.parseFloat(getProperty(key)); }
        catch(Exception ignored) { return defaultValue; }
    }

    public float getFloat(@NotNull @NonNls String key) {
        return getFloat(key, (float)0);
    }

    public int getInt(@NotNull @NonNls String key, int defaultValue) {
        try { return Integer.parseInt(getProperty(key)); }
        catch(Exception ignored) { return defaultValue; }
    }

    public int getInt(@NotNull @NonNls String key) {
        return getInt(key, 0);
    }

    public long getLong(@NotNull @NonNls String key, long defaultValue) {
        try { return Long.parseLong(getProperty(key)); }
        catch(Exception ignored) { return defaultValue; }
    }

    public long getLong(@NotNull @NonNls String key) {
        return getLong(key, 0);
    }

    public long getReloadPeriod() {
        return reloadPeriod;
    }

    public short getShort(@NotNull @NonNls String key, short defaultValue) {
        try { return Short.parseShort(getProperty(key)); }
        catch(Exception ignored) { return defaultValue; }
    }

    public short getShort(@NotNull @NonNls String key) {
        return getShort(key, (short)0);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    private void expandMacro(@NotNull Entry<Object, Object> e) {
        e.setValue(Macros.expand2(e.getValue().toString(), this::getProperty));
    }

    private void expandMacros() {
        entrySet().stream().filter(this::isStringEntry).forEach(this::expandMacro);
    }

    private boolean isStringEntry(@NotNull Entry<Object, Object> e) {
        return ((e.getKey() instanceof String) && (e.getValue() instanceof String));
    }

    private boolean loadFromInputStream(@NotNull InputStream inputStream) {
        try(inputStream) {
            load(inputStream);
            return true;
        }
        catch(IOException e) {
            System.err.printf(msgs.getString("msg.err.prop_load_error"), e);
            return false;
        }
    }

    private synchronized void reload() {
        Map<Object, Object> t = new LinkedHashMap<>(this);
        clear();
        try {
            if(loader.apply(this)) {
                expandMacros();
            }
            else {
                restore(t);
            }
        }
        catch(Exception e) {
            restore(t);
            System.err.printf(msgs.getString("msg.err.prop_load_error"), e);
        }
        finally {
            t.clear();
        }
    }

    private void restore(Map<Object, Object> t) {
        clear();
        putAll(t);
    }

    /**
     * Create a new instance of {@link PGProperties} that reloads it's values at a set interval.
     *
     * @param loader       The {@link Function} lambda that will do both the initial load and subsequent reloads.
     * @param reloadPeriod The interval period. A value less than 1 means the properties will not be reloaded after the initial load.
     * @param timeUnit     The units for the value of <code>reloadPeriod</code>. The default is {@link TimeUnit#MILLISECONDS}.
     * @param defaults     The defaults
     *
     * @return A new instance of PGProperties.
     */
    public static @NotNull PGProperties getProperties(@NotNull Function<PGProperties, Boolean> loader, long reloadPeriod, TimeUnit timeUnit, Properties defaults) {
        return new PGProperties(loader, reloadPeriod, timeUnit, defaults);
    }

    public static @NotNull PGProperties getProperties(InputStream inputStream, Properties defaults) {
        return new PGProperties(p -> _getProperties(inputStream, p), -1, null, defaults);
    }

    public static @NotNull PGProperties getProperties(InputStream inputStream) {
        return getProperties(inputStream, null);
    }

    public static @NotNull PGProperties getProperties(@NotNull File file, long reloadPeriod, TimeUnit timeUnit, Properties defaults) {
        return new PGProperties(p -> p.loadFromInputStream(getInputStream(file)), reloadPeriod, timeUnit, defaults);
    }

    public static @NotNull PGProperties getProperties(@NotNull File file, long reloadPeriod, TimeUnit timeUnit) {
        return getProperties(file, reloadPeriod, timeUnit, null);
    }

    public static @NotNull PGProperties getProperties(@NotNull File file, Properties defaults) {
        return getProperties(file, -1, null, defaults);
    }

    public static @NotNull PGProperties getProperties(@NotNull File file) {
        return getProperties(file, -1, null, null);
    }

    public static @NotNull PGProperties getProperties(@NotNull @NonNls String name, @NotNull Class<?> refClass, long reloadPeriod, TimeUnit timeUnit, Properties defaults) {
        return new PGProperties(p -> p.loadFromInputStream(getInputStream(name, refClass)), reloadPeriod, timeUnit, defaults);
    }

    public static @NotNull PGProperties getProperties(@NotNull @NonNls String name, @NotNull Class<?> refClass, long reloadPeriod, TimeUnit timeUnit) {
        return getProperties(name, refClass, reloadPeriod, timeUnit, null);
    }

    public static @NotNull PGProperties getProperties(@NotNull @NonNls String name, @NotNull Class<?> refClass, Properties defaults) {
        return getProperties(name, refClass, -1, null, defaults);
    }

    public static @NotNull PGProperties getProperties(@NotNull @NonNls String name, @NotNull Class<?> refClass) {
        return getProperties(name, refClass, -1, null, null);
    }

    private static boolean _getProperties(InputStream inputStream, @NotNull PGProperties p) {
        requireNonNull(inputStream, msgs.getString("msg.err.input_stream_null"));
        try(inputStream) { p.load(inputStream); } catch(IOException e) { throw new RuntimeException(e.toString(), e); }
        return true;
    }

    private static @NotNull InputStream getInputStream(@NotNull @NonNls String name, @NotNull Class<?> refClass) {
        return requireNonNull(refClass.getResourceAsStream(name), () -> msgs.getString("msg.err.rsrc_not_found").formatted(name));
    }

    private static @NotNull InputStream getInputStream(@NotNull File file) {
        try { return new FileInputStream(file); } catch(IOException e) { throw new RuntimeException(e.toString(), e); }
    }
}
