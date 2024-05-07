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

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
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
    public PGProperties(@NotNull Function<PGProperties, Boolean> loader, long reloadPeriod, TimeUnit timeUnit, Properties defaults) {
        super(defaults);
        if(!loader.apply(this)) throw new RuntimeException(msgs.getString("msg.err.prop_load_failure"));

        if(reloadPeriod > 0) {
            this.loader       = loader;
            this.reloadPeriod = reloadPeriod;
            this.timeUnit     = ofNullable(timeUnit).orElse(TimeUnit.MILLISECONDS);
            this.executor     = Executors.newSingleThreadScheduledExecutor();
            this.executor.scheduleAtFixedRate(() -> { }, this.reloadPeriod, this.reloadPeriod, this.timeUnit);
        }
        else {
            this.loader       = null;
            this.reloadPeriod = -1;
            this.timeUnit     = null;
            this.executor     = null;
        }
    }

    public PGProperties(InputStream inputStream, Properties defaults) {
        this(DEFAULT_LOADER, -1, null, defaults);
        requireNonNull(inputStream, msgs.getString("msg.err.input_stream_null"));
        try(inputStream) { load(inputStream); } catch(IOException e) { throw new RuntimeException(e.getMessage(), e); }
    }

    public PGProperties(InputStream inputStream) {
        this(inputStream, null);
    }

    public PGProperties(@NotNull File file, long reloadPeriod, TimeUnit timeUnit, Properties defaults) {
        this(p -> p.loadFromInputStream(getInputStream(file)), reloadPeriod, timeUnit, defaults);
    }

    public PGProperties(@NotNull File file, long reloadPeriod, TimeUnit timeUnit) {
        this(file, reloadPeriod, timeUnit, null);
    }

    public PGProperties(@NotNull File file, Properties defaults) {
        this(file, -1, null, defaults);
    }

    public PGProperties(@NotNull File file) {
        this(file, -1, null, null);
    }

    public PGProperties(@NotNull String name, @NotNull Class<?> refClass, long reloadPeriod, TimeUnit timeUnit, Properties defaults) {
        this(p -> p.loadFromInputStream(getInputStream(name, refClass)), reloadPeriod, timeUnit, defaults);
    }

    public PGProperties(@NotNull String name, @NotNull Class<?> refClass, long reloadPeriod, TimeUnit timeUnit) {
        this(name, refClass, reloadPeriod, timeUnit, null);
    }

    public PGProperties(@NotNull String name, @NotNull Class<?> refClass, Properties defaults) {
        this(name, refClass, -1, null, defaults);
    }

    public PGProperties(@NotNull String name, @NotNull Class<?> refClass) {
        this(name, refClass, -1, null, null);
    }

    public long getReloadPeriod() {
        return reloadPeriod;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
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

    private void reload() {
        Map<Object, Object> t = new TreeMap<>(this);
        clear();
        try {
            if(!loader.apply(this)) restore(t);
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

    private static @NotNull InputStream getInputStream(@NotNull String name, @NotNull Class<?> refClass) {
        return requireNonNull(refClass.getResourceAsStream(name), () -> msgs.getString("msg.err.rsrc_not_found").formatted(name));
    }

    private static @NotNull InputStream getInputStream(@NotNull File file) {
        try { return new FileInputStream(file); } catch(IOException e) { throw new RuntimeException(e.getMessage(), e); }
    }
}
