package com.projectgalen.lib.utils.test;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Test.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 21, 2024
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

import com.projectgalen.lib.utils.annotations.Equals;
import com.projectgalen.lib.utils.reflect.TypeInfo;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Stack;

public class Test<Q, P> {

    public List<? extends CharSequence> list1;
    public List<? super CharSequence>   list2;
    public List<BigDecimal>             list3;
    public List<?>                      list4;
    public List<Q>                      list5;
    public List<Test<Q, P>>             list6;
    public List<Test<URL, Equals>>      list7;
    public String                       name1 = "Galen";

    public Test()                                      { }

    public <T extends Number & Comparable<T>> T foo1() { return null; }

    public <T extends Comparable<T>> T foo2()          { return null; }

    public <T> T foo3()                                { return null; }

    public <T extends Runnable> List<T> foo4()         { return null; }

    public int run(String... args) throws Exception {
        TypeInfo info1 = new TypeInfo(Test.class.getDeclaredField("list1"));
        TypeInfo info2 = new TypeInfo(Test.class.getDeclaredField("list2"));
        TypeInfo info3 = new TypeInfo(Test.class.getDeclaredField("list3"));
        TypeInfo info4 = new TypeInfo(Test.class.getDeclaredField("list4"));
        TypeInfo info5 = new TypeInfo(Test.class.getDeclaredField("list5"));
        TypeInfo info6 = new TypeInfo(Test.class.getDeclaredField("list6"));
        TypeInfo info7 = new TypeInfo(Test.class.getDeclaredField("list7"));
        TypeInfo info8 = new TypeInfo(Test.class.getDeclaredField("name1"));
        TypeInfo info9 = new TypeInfo(Test.class.getDeclaredMethod("foo1"));
        TypeInfo info10 = new TypeInfo(Test.class.getDeclaredMethod("foo2"));
        TypeInfo info11 = new TypeInfo(Test.class.getDeclaredMethod("foo3"));
        TypeInfo info12 = new TypeInfo(Test.class.getDeclaredMethod("foo4"));
//        debug(Test.class.getDeclaredField("list1"));
//        debug(Test.class.getDeclaredField("list2"));
//        debug(Test.class.getDeclaredField("list3"));
//        debug(Test.class.getDeclaredField("list4"));
//        debug(Test.class.getDeclaredField("list5"));
//        debug(Test.class.getDeclaredField("list6"));
//        debug(Test.class.getDeclaredField("list7"));
//        debug(Test.class.getDeclaredField("name1"));
//        debug(Test.class.getDeclaredMethod("foo1"));
//        debug(Test.class.getDeclaredMethod("foo2"));
//        debug(Test.class.getDeclaredMethod("foo3"));
//        debug(Test.class.getDeclaredMethod("foo4"));

        return 0;
    }

    private void debug(@NotNull Field f) {
        debug("Field", f.toString(), f.getGenericType());
    }

    private void debug(@NotNull Method m) {
        debug("Method", m.toString(), m.getGenericReturnType());
    }

    private void debug(@NotNull String constructType, @NotNull String constructName, @NotNull Type t) {
        debug(0, 0, new Stack<>(), constructType, constructName, t);
    }

    private void debug(int depth, int indent, Stack<TypeVariable<?>> nestedTypes, @NotNull String constructType, @NotNull String constructName, @NotNull Type t) {
        if(depth < 5) {
            String tab  = " ".repeat(indent);
            String fmt1 = "%s%%20s: %%s\n".formatted(tab);
            String fmt2 = "%s%%20s: ".formatted(tab);
            String bar  = "-".repeat(Math.max(0, 100 - indent));

            if(indent == 0) {
                System.out.printf("%s%s\n", tab, bar);
                System.out.printf(fmt1, constructType, constructName);
            }
            else System.out.printf("%s\n", bar);

            switch(t) {
                case Class<?> c -> {
                    System.out.printf(fmt1, "Class", c.getName());
                }
                case WildcardType wt -> {
                    System.out.printf(fmt1, "WildcardType", wt);
                    debug(depth, indent, nestedTypes, wt.getLowerBounds(), fmt2, "LowerBounds");
                    debug(depth, indent, nestedTypes, wt.getUpperBounds(), fmt2, "UpperBounds");
                }
                case ParameterizedType pt -> {
                    System.out.printf(fmt1, "ParameterizedType", "%s (%s)".formatted(pt, pt.getRawType()));
                    debug(depth, indent, nestedTypes, pt.getActualTypeArguments(), fmt2, "TypeArguments");
                }
                case TypeVariable<?> tv -> {
                    System.out.printf(fmt1, "TypeVariable", "%s (%s)".formatted(tv, tv.getTypeName()));
                    if(!nestedTypes.contains(tv)) {
                        nestedTypes.push(tv);
                        debug(depth, indent, nestedTypes, tv.getBounds(), fmt2, "Bounds");
                        nestedTypes.pop();
                    }
                }
                default -> { }
            }
        }
        else {
            System.out.print('\n');
        }
    }

    private void debug(int depth, int indent, Stack<TypeVariable<?>> nestedTypes, Type[] types, String fmt2, String desc) {
        if(depth < 4) {
            for(int i = 0; i < types.length; ++i) {
                System.out.printf(fmt2, "%s %d".formatted(desc, i + 1));
                debug(depth + 1, indent + 22, nestedTypes, "", "", types[i]);
            }
        }
    }

    public static void main(String... args) {
        try {
            System.exit(new Test<Integer, String>().run(args));
        }
        catch(Throwable t) {
            t.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
