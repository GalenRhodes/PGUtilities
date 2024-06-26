package com.projectgalen.lib.utils.generate;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Functions.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 26, 2024
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

import com.projectgalen.lib.utils.PGProperties;
import com.projectgalen.lib.utils.PGResourceBundle;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Functions {
    public static final @NotNull PGResourceBundle msgs  = new PGResourceBundle("com.projectgalen.lib.utils.generate.vmessages");
    public static final @NotNull PGProperties     props = PGProperties.getProperties("vsettings.properties", Functions.class);

    public static final boolean[]  special = {/*@f0*/
        false, // boolean
        false, // char
        false, // byte
        false, // short
        true,  // int
        true,  // long
        false, // float
        true,  // double
    };
    public static final String[][] data    = {
        { "boolean", "Boolean", "Boolean",   "", "", "", },
        { "char",    "Char",    "Character", "", "", "", },
        { "byte",    "Byte",    "Byte",      "", "", "", },
        { "short",   "Short",   "Short",     "", "", "", },
        { "int",     "Int",     "Integer",   "", "", "", },
        { "long",    "Long",    "Long",      "", "", "", },
        { "float",   "Float",   "Float",     "", "", "", },
        { "double",  "Double",  "Double",    "", "", "", },
    };/*@f1*/

    public static final @NotNull         SimpleDateFormat SDF_DAY     = new SimpleDateFormat(props.getProperty("fmt.day"));
    public static final @NotNull         SimpleDateFormat SDF_MONTH   = new SimpleDateFormat(props.getProperty("fmt.month"));
    public static final @NotNull         SimpleDateFormat SDF_YEAR    = new SimpleDateFormat(props.getProperty("fmt.year"));
    public static final @NotNull         SimpleDateFormat SDF_DATE    = new SimpleDateFormat(props.getProperty("fmt.date"));
    public static final @NotNull @NonNls String           KEY_DATE    = props.getProperty("velocity.key.date");
    public static final @NotNull @NonNls String           KEY_DAY     = props.getProperty("velocity.key.day");
    public static final @NotNull @NonNls String           KEY_MONTH   = props.getProperty("velocity.key.month");
    public static final @NotNull @NonNls String           KEY_YEAR    = props.getProperty("velocity.key.year");
    public static final @NotNull @NonNls String           KEY_PACKAGE = props.getProperty("velocity.key.package");
    public static final @NotNull @NonNls String           PATH        = "com/projectgalen/lib/utils/functions/primitives";

    private final @NotNull VelocityContext _context = new VelocityContext();

    public Functions() {
        Date date = Calendar.getInstance().getTime();
        _context.put(KEY_DATE, SDF_DATE.format(date));
        _context.put(KEY_YEAR, SDF_YEAR.format(date));
        _context.put(KEY_MONTH, SDF_MONTH.format(date));
        _context.put(KEY_DAY, SDF_DAY.format(date));

        String pfx = "velocity.prop.";
        props.stringPropertyNames().stream().filter(n -> n.startsWith(pfx)).forEach(n -> _context.put(n.substring(pfx.length()), props.getProperty(n)));
    }

    public int run(String... args) throws Exception {
        x2y(getPath("x2y"));
        toX(getPath("tox"));
        toXbi(getPath("toxbi"));
        return 0;
    }

    private @NotNull File getFile(@NotNull String path, @NotNull String className) {
        String filename = "%s.java".formatted(className);
        File   file     = new File("src/main/java/" + path, filename);

        file.getParentFile().mkdirs();
        _context.put("filename", filename);
        return file;
    }

    private @NotNull String getPath(@NotNull String subPath) {
        String path = "%s/%s".formatted(PATH, subPath);
        _context.put(KEY_PACKAGE, path.replace("/", "."));
        return path;
    }

    private @NotNull String setClassName(@NotNull String className) {
        _context.put("className", className);
        return className;
    }

    private void toX(String path) throws IOException {
        for(int i = 0; i < data.length; ++i) {
            if(!special[i]) {
                String[] type      = data[i];
                String   className = setClassName("To%sFunction".formatted(type[1]));
                File     file      = getFile(path, className);

                _context.put("type", type[0]);
                _context.put("typeCap", type[1]);
                _context.put("typeWrap", type[2]);

                try(Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                    Velocity.getTemplate("com/projectgalen/lib/utils/generate/toX.vm").merge(_context, w);
                }
            }
        }
    }

    private void toXbi(String path) throws IOException {
        for(int i = 0; i < data.length; ++i) {
            if(!special[i]) {
                String[] type      = data[i];
                String   className = setClassName("To%sBiFunction".formatted(type[1]));
                File     file      = getFile(path, className);

                _context.put("type", type[0]);
                _context.put("typeCap", type[1]);
                _context.put("typeWrap", type[2]);

                try(Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                    Velocity.getTemplate("com/projectgalen/lib/utils/generate/toXbi.vm").merge(_context, w);
                }
            }
        }
    }

    private void x2y(@NotNull String path) throws IOException {
        for(int i = 0; i < data.length; ++i) {
            for(int j = 0; j < data.length; ++j) {
                if(!(special[i] && special[j])) {
                    String[] from      = data[i];
                    String[] to        = data[j];
                    String   className = setClassName((i == j) ? "%sUnaryOperator".formatted(from[1]) : "%sTo%sFunction".formatted(from[1], to[1]));
                    File     file      = getFile(path, className);

                    _context.put("toType", to[0]);
                    _context.put("fromType", from[0]);
                    _context.put("toTypeCap", to[1]);
                    _context.put("fromTypeCap", from[1]);
                    _context.put("toTypeWrap", to[2]);
                    _context.put("fromTypeWrap", from[2]);

                    try(Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                        Velocity.getTemplate("com/projectgalen/lib/utils/generate/XtoY.vm").merge(_context, w);
                    }
                }
            }
        }
    }

    public static void main(String... args) {
        try {
            System.exit(new Functions().run(args));
        }
        catch(Throwable t) {
            t.printStackTrace(System.err);
            System.exit(1);
        }
    }

    static {
        Velocity.init(PGProperties.getProperties("velocity.properties", Functions.class));
    }
}
