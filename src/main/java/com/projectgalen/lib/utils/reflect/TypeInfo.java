package com.projectgalen.lib.utils.reflect;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: TypeInfo.java
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

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class TypeInfo {

    private final Class<?>   rawType;
    private final boolean    isGeneric;
    private final boolean    isTypeVar;
    private final boolean    isWildCard;
    private final boolean    isSupers;
    private final TypeInfo[] bounds;
    private final String     name;

    public TypeInfo(@NotNull Type type) {
        this(type, new Stack<>());
    }

    public TypeInfo(@NotNull Field field) {
        this(field.getGenericType());
    }

    public TypeInfo(@NotNull Method method) {
        this(method.getGenericReturnType());
    }

    public TypeInfo(@NotNull Parameter parameter) {
        this(parameter.getParameterizedType());
    }

    private TypeInfo(@NotNull Type type, @NotNull Stack<TypeVariable<?>> nested) {
        Class<?>   rawType    = Object.class;
        boolean    isGeneric  = false;
        boolean    isTypeVar  = false;
        boolean    isWildCard = false;
        boolean    isSupers   = false;
        TypeInfo[] bounds     = new TypeInfo[0];
        String     name       = null;

        switch(type) {
            case Class<?> c -> {
                rawType = c;
            }
            case ParameterizedType pt -> {
                isGeneric = true;
                rawType   = (Class<?>)pt.getRawType();
                bounds    = Stream.of(pt.getActualTypeArguments()).map(t -> new TypeInfo(t, nested)).toArray(TypeInfo[]::new);
            }
            case TypeVariable<?> tv -> {
                name      = tv.getName();
                isTypeVar = true;

                if(!nested.contains(tv)) {
                    nested.push(tv);
                    try {
                        bounds = Stream.of(tv.getBounds()).map(t -> new TypeInfo(t, nested)).toArray(TypeInfo[]::new);

                        if(bounds.length == 1) {
                            TypeInfo info = bounds[0];

                            if(info.rawType != Object.class) {
                                rawType    = info.rawType;
                                name       = info.name;
                                isGeneric  = info.isGeneric;
                                isWildCard = info.isWildCard;
                                isTypeVar  = info.isTypeVar;
                                bounds     = info.bounds;
                            }
                        }
                    }
                    finally {
                        nested.pop();
                    }
                }
            }
            case WildcardType wt -> {
                Type[]   lowb = wt.getLowerBounds();
                boolean  f    = (lowb.length != 0);
                TypeInfo info = new TypeInfo(f ? lowb[0] : wt.getUpperBounds()[0], nested);

                isSupers   = f;
                rawType    = info.rawType;
                name       = info.name;
                isGeneric  = info.isGeneric;
                isWildCard = info.isWildCard;
                isTypeVar  = info.isTypeVar;
                bounds     = info.bounds;
            }
            default -> {
            }
        }

        this.isGeneric  = isGeneric;
        this.isTypeVar  = isTypeVar;
        this.isWildCard = isWildCard;
        this.isSupers   = isSupers;
        this.bounds     = bounds;
        this.rawType    = rawType;
        this.name       = Optional.ofNullable(name).orElseGet(this.rawType::getName);
    }

    public TypeInfo[] getBounds() {
        return bounds;
    }

    public String getName() {
        return name;
    }

    public Class<?> getRawType() {
        return rawType;
    }

    public boolean isGeneric() {
        return isGeneric;
    }

    public boolean isSupers() {
        return isSupers;
    }

    public boolean isTypeVar() {
        return isTypeVar;
    }

    public boolean isWildCard() {
        return isWildCard;
    }
}
