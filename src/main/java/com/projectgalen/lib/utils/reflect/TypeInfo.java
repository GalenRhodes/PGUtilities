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

import com.projectgalen.lib.utils.PGArrays;
import com.projectgalen.lib.utils.PGProperties;
import com.projectgalen.lib.utils.PGResourceBundle;
import com.projectgalen.lib.utils.Str;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

/**
 * {@link TypeInfo} provides an easier way to get the type (along with types of generics) from {@link Field Fields} and {@link Method} return types and {@link Parameter Parameters}.
 */
@SuppressWarnings("unused")
public class TypeInfo {

    private static final PGResourceBundle msgs  = new PGResourceBundle("com.projectgalen.lib.utils.messages");
    private static final PGProperties     props = PGProperties.getProperties("settings.properties", PGProperties.class);

    private static final String ARRAY   = props.getProperty("code.type_info.array");
    private static final String AND     = props.getProperty("code.type_info.and");
    private static final String COMMA   = props.getProperty("code.type_info.comma");
    private static final String EXTENDS = props.getProperty("code.type_info.extends");
    private static final String FMT1    = props.getProperty("code.type_info.fmt1");
    private static final String FMT2    = props.getProperty("code.type_info.fmt2");
    private static final String FMT3    = props.getProperty("code.type_info.fmt3");
    private static final String FMT4    = props.getProperty("code.type_info.fmt4");
    private static final String GT      = props.getProperty("code.type_info.gt");
    private static final String Q       = props.getProperty("code.type_info.q");
    private static final String SUPER   = props.getProperty("code.type_info.super");

    private final Class<?>   rawType;
    private final boolean    isArray;
    private final boolean    isGeneric;
    private final boolean    isTypeVar;
    private final boolean    isWildCard;
    private final boolean    isSupers;
    private final boolean    isClass;
    private final TypeInfo[] bounds;
    private final String     name;
    private       String     _string_ = null;

    public TypeInfo(@NotNull Type type) {
        this(type, new Stack<>());
    }

    public TypeInfo(@NotNull Field field) {
        this(field.getGenericType(), new Stack<>());
    }

    public TypeInfo(@NotNull Method method) {
        this(method.getGenericReturnType(), new Stack<>());
    }

    public TypeInfo(@NotNull Parameter parameter) {
        this(parameter.getParameterizedType(), new Stack<>());
    }

    private TypeInfo(@NotNull Type type, @NotNull Stack<TypeVariable<?>> nested) {
        Class<?>   rawType    = Object.class;
        boolean    isArray    = false;
        boolean    isGeneric  = false;
        boolean    isTypeVar  = false;
        boolean    isWildCard = false;
        boolean    isSupers   = false;
        TypeInfo[] bounds     = new TypeInfo[0];
        String     name       = null;

        switch(type) {
            case Class<?> c -> {
                rawType = c;
                if(c.isArray()) {
                    isArray = true;
                    bounds  = new TypeInfo[] { new TypeInfo(c.getComponentType()) };
                    name    = FMT3.formatted(c.getComponentType().getName());
                }
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
                    try { bounds = Stream.of(tv.getBounds()).map(t -> new TypeInfo(t, nested)).toArray(TypeInfo[]::new); } finally { nested.pop(); }
                }
            }
            case WildcardType wt -> {
                Type[] lowb = wt.getLowerBounds();
                name       = Q;
                isWildCard = true;
                isSupers   = (lowb.length != 0);
                bounds     = Stream.of(isSupers ? lowb : wt.getUpperBounds()).map(t -> new TypeInfo(t, nested)).toArray(TypeInfo[]::new);
            }
            case GenericArrayType at -> {
                TypeInfo i = new TypeInfo(at.getGenericComponentType());

                isArray    = true;
                isGeneric  = i.isGeneric;
                isTypeVar  = i.isTypeVar;
                isWildCard = i.isWildCard;
                isSupers   = i.isSupers;
                bounds     = i.bounds;
                rawType    = Array.newInstance(i.rawType, 0).getClass();
                name       = FMT3.formatted(i.name);
            }
            default -> throw new IllegalArgumentException(msgs.getString("msg.err.type_info.unexpected_type").formatted(type.getClass().getName()));
        }

        this.isArray    = isArray;
        this.isGeneric  = isGeneric;
        this.isTypeVar  = isTypeVar;
        this.isWildCard = isWildCard;
        this.isSupers   = isSupers;
        this.bounds     = bounds;
        this.rawType    = rawType;
        this.name       = ofNullable(name).orElseGet(this.rawType::getName);
        this.isClass    = (!(isGeneric || isTypeVar || isWildCard));
    }

    public @NotNull Stream<TypeInfo> boundsStream() {
        return Stream.of(bounds);
    }

    public TypeInfo @NotNull [] getBounds() {
        return bounds;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull Class<?> getRawType() {
        return rawType;
    }

    public boolean isArray() {
        return isArray;
    }

    /**
     * <p>Returns {@code true} if the only bounds for a wildcard or type variable is {@link Object Object.class}.</p>
     *
     * <p>For example, if the generic bounds is expressed as simply {@code <?>} then this can be seen as simply {@code <Object>}.</p>
     *
     * @return {@code true} if the only bounds for a wildcard or type variable is {@link Object Object.class}.
     */
    public boolean isBoundsObject() {
        return ((bounds.length == 0) || ((bounds.length == 1) && bounds[0].isObject()));
    }

    public boolean isClass() {
        return isClass;
    }

    public boolean isGeneric() {
        return isGeneric;
    }

    public boolean isObject() {
        return (isClass && (rawType == Object.class));
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

    public @Override String toString() {
        synchronized(rawType) {
            if(_string_ == null) _string_ = (isGeneric ? strA() : (isTypeVar ? strD() : (isWildCard ? strB() : name)));
            return _string_;
        }
    }

    private String strA() {
        return boundsStream().map(TypeInfo::toString).collect(Collectors.joining(COMMA, FMT1.formatted(name), GT));
    }

    private String strB() {
        return (isBoundsObject() ? name : strC());
    }

    private String strC() {
        return boundsStream().map(TypeInfo::toString).collect(Collectors.joining(AND, FMT2.formatted((isArray ? Str.leftStr(name, -ARRAY.length()) : name), (isSupers ? SUPER : EXTENDS)), ""));
    }

    private String strD() {
        return (isBoundsObject() ? name : FMT4.formatted(name, strB()));
    }

    public static TypeInfo @NotNull [] getConstructorTypes(@NotNull Constructor<?> constructor) {
        return getTypeInfoList(constructor.getGenericParameterTypes());
    }

    public static TypeInfo @NotNull [] getMethodTypes(@NotNull Method method) {
        return PGArrays.prepend(new TypeInfo(method), getTypeInfoList(method.getGenericParameterTypes()));
    }

    private static TypeInfo @NotNull [] getTypeInfoList(Type[] types) {
        return Stream.of(types).map(TypeInfo::new).toArray(TypeInfo[]::new);
    }
}
