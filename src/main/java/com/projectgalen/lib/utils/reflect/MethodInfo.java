package com.projectgalen.lib.utils.reflect;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: MethodInfo.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 30, 2024
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

import com.projectgalen.lib.utils.Obj;
import com.projectgalen.lib.utils.PGResourceBundle;
import com.projectgalen.lib.utils.errors.MethodInvocationException;
import com.projectgalen.lib.utils.errors.MethodNotFound;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Stores information about a declared method in a class. Later this information can be used to obtain the actual {@link Method} or to {@link #invoke(Object, Object...)} the method. Unlike the
 * {@link Class#getDeclaredMethod(String, Class[])} method this class will search for declared methods in the given class and any of it's {@link Class#getSuperclass() superclasses}.
 *
 * <p>Like the {@link Reflect} helper class this class will catch and wrap any exceptions inside an instance of {@link RuntimeException} in order to simplify coding since, at runtime,
 * most functions and their operations are already known. For applications where there is a possibility that a method actually does not exists or that an {@link IllegalAccessException} might occur
 * then use the actual methods {@link Class#getMethod(String, Class...)} and {@link Method#invoke(Object, Object...)}.</p>
 *
 * <p>This class implements {@link Comparable} so that instances of this class can be stored in {@link java.util.TreeSet}s or used as keys in {@link java.util.TreeMap}s</p>
 */
@SuppressWarnings({ "unused", "unchecked" })
public class MethodInfo implements Comparable<MethodInfo> {

    private static final PGResourceBundle    msgs  = new PGResourceBundle("com.projectgalen.lib.utils.messages");
    private static final Map<String, Method> CACHE = new TreeMap<>();

    private final @NotNull Class<?>             cls;
    private final @NotNull String               name;
    private final          boolean              isStatic;
    private final          Class<?> @NotNull [] types;
    private final @NotNull String               key;

    /**
     * Creates a new instance of {@link MethodInfo}.
     *
     * @param cls      The class in which the method exists.
     * @param name     The name of the method.
     * @param isStatic {@code true} if the method is static, {@code false} otherwise.
     * @param types    The types of each of the parameters of the method.
     *
     * @see Class#getMethod(String, Class[])
     */
    public MethodInfo(@NotNull Class<?> cls, @NotNull String name, boolean isStatic, Class<?> @NotNull ... types) {
        this.cls      = cls;
        this.name     = name;
        this.isStatic = isStatic;
        this.types    = types;
        this.key      = "%s %s%s(%s)".formatted(cls.getName(), (isStatic ? "static " : ""), name, Arrays.stream(types).map(Class::getName).collect(Collectors.joining(",")));
    }

    /**
     * Compares this object with the specified object for order.  Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for all {@code x} and {@code y}.  (This implies that {@code x.compareTo(y)} must
     * throw an exception if and only if {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive: {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0} implies that {@code signum(x.compareTo(z)) == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     *
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it from being compared to this object.
     */
    public @Override int compareTo(@NotNull MethodInfo o) {
        return (((this == o) || _equals(o)) ? 0 : key.compareTo(o.key));
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * <p>The {@code equals} method implements an equivalence relation on non-null object references:</p>
     *
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value {@code x}, {@code x.equals(x)} should return {@code true}.</li>
     * <li>It is <i>symmetric</i>: for any non-null reference values {@code x} and {@code y}, {@code x.equals(y)} should return {@code true} if and only if {@code y.equals(x)} returns {@code true}.</li>
     * <li>It is <i>transitive</i>: for any non-null reference values {@code x}, {@code y}, and {@code z}, if {@code x.equals(y)} returns {@code true} and {@code y.equals(z)} returns {@code true}, then {@code x.equals(z)} should return {@code true}.</li>
     * <li>It is <i>consistent</i>: for any non-null reference values {@code x} and {@code y}, multiple invocations of {@code x.equals(y)} consistently return {@code true} or consistently return {@code false}, provided no information used in {@code equals} comparisons on the objects is modified.</li>
     * <li>For any non-null reference value {@code x}, {@code x.equals(null)} should return {@code false}.</li>
     * </ul>
     *
     * <p>An equivalence relation partitions the elements it operates on into <i>equivalence classes</i>; all the members of an equivalence class are equal to each other. Members of an equivalence
     * class are substitutable for each other, at least for some purposes.</p>
     *
     * <p>The {@code equals} method for class {@code Object} implements the most discriminating possible equivalence relation on objects; that is, for any non-null reference values {@code x}
     * and {@code y}, this method returns {@code true} if and only if {@code x} and {@code y} refer to the same object ({@code x == y} has the value {@code true}).</p>
     *
     * <p>In other words, under the reference equality equivalence relation, each equivalence class only has a single element.</p>
     *
     * @param obj the reference object with which to compare.
     *
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     * @see #hashCode()
     * @see java.util.HashMap
     */
    public @Override boolean equals(Object obj) {
        return ((this == obj) || ((obj instanceof MethodInfo info) && _equals(info)));
    }

    /**
     * @return The {@link Class} that the method will be found in.
     */
    public @NotNull Class<?> getCls() {
        return cls;
    }

    /**
     * @return The {@link Method} represented by this object.
     *
     * @throws MethodNotFound if the method cannot be found in the associated {@link Class} or one of it's {@link Class#getSuperclass() superclasses}.
     */
    public @NotNull Method getMethod() {
        return Obj.requireNonNull(Reflect.getMethod(cls, isStatic, name, types), () -> new MethodNotFound(msgs.getString("msg.err.no_such_method").formatted(key)));
    }

    /**
     * @return The name of the method represented by this object.
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Returns the formal types of the parameters of the method represented by this object. <i>Altering the elements of the returned array does not change the elements of the array held by this
     * object.</i>
     *
     * @return The formal types of the parameters of the method represented by this object.
     */
    public Class<?> @NotNull [] getTypes() {
        // We're doing this because we don't want the contents of the array to change.
        return Arrays.copyOf(types, types.length);
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by {@link java.util.HashMap}.
     *
     * <p>The general contract of {@code hashCode} is:</p>
     *
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during an execution of a Java application, the {@code hashCode} method must consistently return the same integer, provided no information used in {@code equals} comparisons on the object is modified. This integer need not remain consistent from one execution of an application to another execution of the same application.</li>
     * <li>If two objects are equal according to the {@link #equals(Object) equals} method, then calling the {@code hashCode} method on each of the two objects must produce the same integer result.</li>
     * <li>It is <em>not</em> required that if two objects are unequal according to the {@link #equals(Object) equals} method, then calling the {@code hashCode} method on each of the two objects must produce distinct integer results.  However, the programmer should be aware that producing distinct integer results for unequal objects may improve the performance of hash tables.</li>
     * </ul>
     *
     * @return a hash code value for this object.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     * @see java.lang.System#identityHashCode
     */
    public @Override int hashCode() {
        return Objects.hash(cls, name, isStatic, Arrays.hashCode(types), key);
    }

    /**
     * Invokes the method.
     *
     * @param obj  The object the underlying method is invoked from.
     * @param args The arguments used for the method call.
     * @param <T>  The expected type of the returned result.
     *
     * @return The result of dispatching the method represented by this object on {@code obj} with parameters {@code args}.
     *
     * @throws MethodInvocationException   If either {@link IllegalAccessException} or {@link InvocationTargetException} are thrown.
     * @throws NullPointerException        If the specified object is null and the method is an instance method.
     * @throws ExceptionInInitializerError If the initialization provoked by this method fails.
     * @throws IllegalArgumentException    If the method is an instance method and the specified object argument is not an instance of the class or interface declaring the underlying method (or of a
     *                                     subclass or implementor thereof); if the number of actual and formal parameters differ; if an unwrapping conversion for primitive arguments fails; or if,
     *                                     after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type by a method invocation conversion.
     * @see Method#invoke(Object, Object...)
     */
    public <T> T invoke(Object obj, Object @NotNull ... args) {
        try { return (T)getMethod().invoke(obj, args); } catch(IllegalAccessException | InvocationTargetException e) { throw new MethodInvocationException(e); }
    }

    /**
     * @return {@code true} if this object represents a static method or {@code false} otherwise.
     */
    public boolean isStatic() {
        return isStatic;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    public @Override String toString() {
        return key;
    }

    private boolean _equals(@NotNull MethodInfo info) {
        return ((isStatic == info.isStatic) && Objects.equals(cls, info.cls) && Objects.equals(name, info.name) && Objects.deepEquals(types, info.types) && Objects.equals(key, info.key));
    }
}
