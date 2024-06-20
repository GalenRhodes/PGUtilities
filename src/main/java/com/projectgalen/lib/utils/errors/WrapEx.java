package com.projectgalen.lib.utils.errors;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: WrapEx.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 18, 2024
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

import com.projectgalen.lib.utils.functions.ConsumerEx;
import com.projectgalen.lib.utils.functions.FunctionEx;
import com.projectgalen.lib.utils.functions.RunnableEx;
import com.projectgalen.lib.utils.functions.SupplierEx;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class WrapEx extends RuntimeException {

    public static final @NotNull Runnable                                                DEFAULT_FINALLY = () -> { };
    public static final @NotNull Function<? super Exception, ? extends RuntimeException> DEFAULT_WRAPPER = WrapEx::wrap;

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public WrapEx() { }

    /**
     * Constructs a new runtime exception with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of
     * {@code cause}).  This constructor is useful for runtime exceptions that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (T {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.4
     */
    public WrapEx(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message. The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public WrapEx(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.  <p>Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this
     * runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).  (T {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.4
     */
    public WrapEx(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause.  (T {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     *
     * @since 1.7
     */
    public WrapEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Creates and returns an instance of {@link Consumer<T>} based on the provided instance of {@link ConsumerEx} passed in the parameter {@code consumer}. Any checked exception that is throw in the
     * method {@link ConsumerEx#accept(T)} will be wrapped in an instance of the unchecked exception returned by the method {@link Function#apply(Object)} passed in the parameter {@code wrapper}.
     *
     * @param consumer A function the might throw a checked exception.
     * @param wrapper  A function that will wrap any caught exception in an instance of {@link RuntimeException} or one of it's subclasses.
     * @param fin      An instance of {@link Runnable} that will be run before the execution of {@code consumer} returns.
     * @param <T>      The type of the parameter that will be passed to {@code consumer}.
     * @param <E>      The type of the exception that will be returned from {@code wrapper} if a checked exception is thrown by {@code consumer}.
     *
     * @return a new instance of {@link Consumer} that executes {@link ConsumerEx#accept(T)} catching any thrown checked exceptions and wrapping them in a {@link RuntimeException} or one of it's
     */
    public static <T, E extends RuntimeException> @NotNull Consumer<T> accept(@NotNull ConsumerEx<T, ? extends Exception> consumer,
                                                                              @NotNull Runnable fin,
                                                                              @NotNull Function<? super Exception, ? extends E> wrapper) {
        return t -> {
            try {
                consumer.accept(t);
            }
            catch(RuntimeException e) {
                throw e;
            }
            catch(Exception e) {
                throw wrapper.apply(e);
            }
            finally {
                fin.run();
            }
        };
    }

    /**
     * Creates and returns an instance of {@link Consumer<T>} based on the provided instance of {@link ConsumerEx<T>} passed in the parameter {@code consumer}. Any checked exception that is throw in
     * the method {@link ConsumerEx#accept(T)} will be wrapped in an instance of the unchecked exception {@link WrapEx}.
     *
     * @param consumer A function the might throw a checked exception.
     * @param fin      An instance of {@link Runnable} that will be run before the execution of {@code consumer} returns.
     * @param <T>      The type of the parameter that will be passed to {@code consumer}.
     *
     * @return a new instance of {@link Consumer<T>} that executes {@link ConsumerEx#accept(T)} catching any thrown checked exceptions and wrapping them in an instance of the unchecked exception
     * {@link WrapEx}.
     */
    public static <T> @NotNull Consumer<T> accept(@NotNull ConsumerEx<T, ? extends Exception> consumer, @NotNull Runnable fin) {
        return accept(consumer, fin, DEFAULT_WRAPPER);
    }

    /**
     * Creates and returns an instance of {@link Consumer<T>} based on the provided instance of {@link ConsumerEx<T>} passed in the parameter {@code consumer}. Any checked exception that is throw in
     * the method {@link ConsumerEx#accept(T)} will be wrapped in an instance of the unchecked exception {@link WrapEx}.
     *
     * @param consumer A function the might throw a checked exception.
     * @param <T>      The type of the parameter that will be passed to {@code consumer}.
     *
     * @return a new instance of {@link Consumer<T>} that executes {@link ConsumerEx#accept(T)} catching any thrown checked exceptions and wrapping them in an instance of the unchecked exception
     * {@link WrapEx}.
     */
    public static <T> @NotNull Consumer<T> accept(@NotNull ConsumerEx<T, ? extends Exception> consumer) {
        return accept(consumer, DEFAULT_FINALLY, DEFAULT_WRAPPER);
    }

    /**
     * Creates and returns an instance of {@link Consumer} based on the provided instance of {@link ConsumerEx} passed in the parameter {@code consumer}. Any checked exception that is throw in the
     * method {@link ConsumerEx#accept(T)} will be wrapped in an instance of the unchecked exception returned by the method {@link Function#apply(Object)} passed in the parameter {@code wrapper}.
     *
     * @param consumer A function the might throw a checked exception.
     * @param wrapper  A function that will wrap any caught exception in an instance of {@link RuntimeException} or one of it's subclasses.
     * @param <T>      The type of the parameter that will be passed to {@code consumer}.
     * @param <E>      The type of the exception that will be returned from {@code wrapper} if a checked exception is thrown by {@code consumer}.
     *
     * @return a new instance of {@link Consumer} that executes {@link ConsumerEx#accept(T)} catching any thrown checked exceptions and wrapping them in a {@link RuntimeException} or one of it's
     * subclasses.
     */
    public static <T, E extends RuntimeException> @NotNull Consumer<T> accept(@NotNull ConsumerEx<T, ? extends Exception> consumer, @NotNull Function<? super Exception, ? extends E> wrapper) {
        return accept(consumer, DEFAULT_FINALLY, wrapper);
    }

    /**
     * Creates and returns an instance of {@link Function} based on the provided instance of {@link FunctionEx} passed in the parameter {@code func}. Any checked exception that is throw in the method
     * {@link FunctionEx#apply(T)} will be wrapped in an instance of the unchecked exception returned by the method {@link Function#apply(Object)} passed in the parameter {@code wrapper}.
     *
     * @param func    A function the might throw a checked exception.
     * @param wrapper A function that will wrap any caught exception in an instance of {@link RuntimeException} or one of it's subclasses.
     * @param fin     An instance of {@link Runnable} that will be run before the execution of {@code func} returns.
     * @param <T>     The type of the parameter that will be passed to {@code func}.
     * @param <R>     The type of the value returned from {@code func}.
     * @param <E>     The type of the exception that will be returned from {@code wrapper} if a checked exception is thrown by {@code func}.
     *
     * @return a new instance of {@link Function} that executes {@link FunctionEx#apply(T)} catching any thrown checked exceptions and wrapping them in a {@link RuntimeException} or one of it's
     * subclasses.
     */
    public static <T, R, E extends RuntimeException> @NotNull Function<T, R> apply(@NotNull FunctionEx<T, R, ? extends Exception> func,
                                                                                   @NotNull Runnable fin,
                                                                                   @NotNull Function<? super Exception, ? extends E> wrapper) {
        return t -> {
            try {
                return func.apply(t);
            }
            catch(RuntimeException e) {
                throw e;
            }
            catch(Exception e) {
                throw wrapper.apply(e);
            }
            finally {
                fin.run();
            }
        };
    }

    /**
     * Creates and returns an instance of {@link Function} based on the provided instance of {@link FunctionEx} passed in the parameter {@code func}. Any checked exception that is throw in the method
     * {@link FunctionEx#apply(T)} will be wrapped in an instance of the unchecked exception returned by the method {@link Function#apply(Object)} passed in the parameter {@code wrapper}.
     *
     * @param func    A function the might throw a checked exception.
     * @param wrapper A function that will wrap any caught exception in an instance of {@link RuntimeException} or one of it's subclasses.
     * @param <T>     The type of the parameter that will be passed to {@code func}.
     * @param <R>     The type of the value returned from {@code func}.
     * @param <E>     The type of the exception that will be returned from {@code wrapper} if a checked exception is thrown by {@code func}.
     *
     * @return a new instance of {@link Function} that executes {@link FunctionEx#apply(T)} catching any thrown checked exceptions and wrapping them in a {@link RuntimeException} or one of it's
     * subclasses.
     */
    public static <T, R, E extends RuntimeException> @NotNull Function<T, R> apply(@NotNull FunctionEx<T, R, ? extends Exception> func, @NotNull Function<? super Exception, ? extends E> wrapper) {
        return apply(func, DEFAULT_FINALLY, wrapper);
    }

    /**
     * Creates and returns an instance of {@link Function} based on the provided instance of {@link FunctionEx} passed in the parameter {@code func}. Any checked exception that is throw in the method
     * {@link FunctionEx#apply(T)} will be wrapped in an instance of the unchecked exception {@link WrapEx}.
     *
     * @param func A function the might throw a checked exception.
     * @param fin  An instance of {@link Runnable} that will be run before the execution of {@code func} returns.
     * @param <T>  The type of the parameter that will be passed to {@code func}.
     * @param <R>  The type of the value returned from {@code func}.
     *
     * @return a new instance of {@link Function} that executes {@link FunctionEx#apply(T)} catching any thrown checked exceptions and wrapping them in an instance of the unchecked exception
     * {@link WrapEx}.
     */
    public static <T, R> @NotNull Function<T, R> apply(@NotNull FunctionEx<T, R, ? extends Exception> func, @NotNull Runnable fin) {
        return apply(func, fin, DEFAULT_WRAPPER);
    }

    /**
     * Creates and returns an instance of {@link Function} based on the provided instance of {@link FunctionEx} passed in the parameter {@code func}. Any checked exception that is throw in the method
     * {@link FunctionEx#apply(T)} will be wrapped in an instance of the unchecked exception {@link WrapEx}.
     *
     * @param func A function the might throw a checked exception.
     * @param <T>  The type of the parameter that will be passed to {@code func}.
     * @param <R>  The type of the value returned from {@code func}.
     *
     * @return a new instance of {@link Function} that executes {@link FunctionEx#apply(T)} catching any thrown checked exceptions and wrapping them in an instance of the unchecked exception
     * {@link WrapEx}.
     */
    public static <T, R> @NotNull Function<T, R> apply(@NotNull FunctionEx<T, R, Exception> func) {
        return apply(func, DEFAULT_FINALLY, DEFAULT_WRAPPER);
    }

    public static <T> @NotNull Supplier<T> fGet(@NotNull SupplierEx<T, ? extends Exception> supplier) {
        return () -> get(supplier, DEFAULT_FINALLY, DEFAULT_WRAPPER);
    }

    public static <T> @NotNull Supplier<T> fGet(@NotNull SupplierEx<T, ? extends Exception> supplier, @NotNull Runnable fin) {
        return () -> get(supplier, fin, DEFAULT_WRAPPER);
    }

    public static <T, E extends RuntimeException> @NotNull Supplier<T> fGet(@NotNull SupplierEx<T, ? extends Exception> supplier, @NotNull Function<? super Exception, ? extends E> wrapper) {
        return () -> get(supplier, DEFAULT_FINALLY, wrapper);
    }

    public static <T, E extends RuntimeException> @NotNull Supplier<T> fGet(@NotNull SupplierEx<T, ? extends Exception> supplier,
                                                                            @NotNull Runnable fin,
                                                                            @NotNull Function<? super Exception, ? extends E> wrapper) {
        return () -> get(supplier, fin, wrapper);
    }

    /**
     * Creates and returns an instance of {@link Runnable} that wraps the execution of an instance {@link RunnableEx<Exception>} catching any thrown checked exception and wrapping them into an
     * instance of {@link WrapEx}.
     *
     * @param runner The instance of {@link RunnableEx<Exception>}.
     *
     * @return A new instance of {@link Runnable}.
     */
    public static @NotNull Runnable fRun(@NotNull RunnableEx<? extends Exception> runner) {
        return () -> run(runner, DEFAULT_FINALLY, DEFAULT_WRAPPER);
    }

    /**
     * Creates and returns an instance of {@link Runnable} that wraps the execution of an instance {@link RunnableEx<Exception>} catching any thrown checked exception and wrapping them into an
     * instance of {@link WrapEx}.
     *
     * @param runner The instance of {@link RunnableEx<Exception>}.
     * @param fin    An instance of {@link Runnable} that will always be executed before {@link RunnableEx#run()} finishes.
     *
     * @return A new instance of {@link Runnable}.
     */
    public static @NotNull Runnable fRun(@NotNull RunnableEx<? extends Exception> runner, @NotNull Runnable fin) {
        return () -> run(runner, fin, DEFAULT_WRAPPER);
    }

    /**
     * Creates and returns an instance of {@link Runnable} that wraps the execution of an instance {@link RunnableEx<Exception>} catching any thrown checked exception and wrapping them into an
     * instance of the unchecked exception returned by the method {@link Function#apply(Object)} passed in the parameter {@code wrapper}.
     *
     * @param runner  The instance of {@link RunnableEx<Exception>}.
     * @param wrapper A function that will wrap any caught exception in an instance of {@link RuntimeException} or one of it's subclasses.
     * @param <E>     The type of the exception that will be used to wrap any checked exception thrown by the lambda.
     *
     * @return A new instance of {@link Runnable}.
     */
    public static <E extends RuntimeException> @NotNull Runnable fRun(@NotNull RunnableEx<? extends Exception> runner, @NotNull Function<? super Exception, ? extends E> wrapper) {
        return () -> run(runner, DEFAULT_FINALLY, wrapper);
    }

    /**
     * Creates and returns an instance of {@link Runnable} that wraps the execution of an instance {@link RunnableEx<Exception>} catching any thrown checked exception and wrapping them into an
     * instance of the unchecked exception returned by the method {@link Function#apply(Object)} passed in the parameter {@code wrapper}.
     *
     * @param runner  The instance of {@link RunnableEx<Exception>}.
     * @param fin     An instance of {@link Runnable} that will always be executed before {@link RunnableEx#run()} finishes.
     * @param wrapper A function that will wrap any caught exception in an instance of {@link RuntimeException} or one of it's subclasses.
     * @param <E>     The type of the exception that will be used to wrap any checked exception thrown by the lambda.
     *
     * @return A new instance of {@link Runnable}.
     */
    public static <E extends RuntimeException> @NotNull Runnable fRun(@NotNull RunnableEx<? extends Exception> runner,
                                                                      @NotNull Runnable fin,
                                                                      @NotNull Function<? super Exception, ? extends E> wrapper) {
        return () -> run(runner, fin, wrapper);
    }

    /**
     * Executes the given lambda {@code supplier} and returns the value returned by that lambda. Also, wraps any checked exception thrown by the lambda {@code supplier} into an instance of
     * {@link WrapEx}, which is a superclass of {@link RuntimeException} and throws that instead.
     *
     * @param supplier The lambda that gets executed.
     * @param <T>      The type that gets returned from the lambda.
     *
     * @return The value returned by the lambda {@code supplier}.
     */
    public static <T> T get(@NotNull SupplierEx<T, ? extends Exception> supplier) {
        return get(supplier, DEFAULT_FINALLY, DEFAULT_WRAPPER);
    }

    /**
     * Executes the given lambda {@code supplier} and returns the value returned by that lambda. Also, wraps any checked exception thrown by the lambda into a subclass of {@link RuntimeException} as
     * determined by the lambda {@code wrapper}.
     *
     * @param supplier The lambda that gets executed.
     * @param wrapper  A lambda that wraps any checked exception thrown by the lambda {@code supplier} into a subclass of {@link RuntimeException}.
     * @param <T>      The type that gets returned from the lambda {@code supplier}.
     * @param <E>      The type of the exception that will be used to wrap any checked exception thrown by the lambda {@code supplier}.
     *
     * @return The value returned by the lambda {@code supplier}.
     */
    public static <T, E extends RuntimeException> T get(@NotNull SupplierEx<T, ? extends Exception> supplier, @NotNull Function<? super Exception, ? extends E> wrapper) {
        return get(supplier, DEFAULT_FINALLY, wrapper);
    }

    /**
     * Executes the given lambda {@code supplier} and returns the value returned by that lambda. Also, wraps any checked exception thrown by the lambda {@code supplier} into an instance of
     * {@link WrapEx}, which is a superclass of {@link RuntimeException} and throws that instead. Finally, the lambda {@code fin} is executed before this method exits whether or not an exception is
     * thrown.
     *
     * @param supplier The lambda that gets executed.
     * @param fin      The lambda that gets executed before this method exits.
     * @param <T>      The type that gets returned from the lambda {@code supplier}.
     *
     * @return The value returned by the lambda {@code supplier}.
     */
    public static <T> T get(@NotNull SupplierEx<T, ? extends Exception> supplier, @NotNull Runnable fin) {
        return get(supplier, fin, DEFAULT_WRAPPER);
    }

    /**
     * Executes the given lambda {@code supplier} and returns the value returned by that lambda. Also, wraps any checked exception thrown by the lambda into a subclass of {@link RuntimeException} as
     * determined by the lambda {@code wrapper}. Finally, the lambda {@code fin} is executed before this method exits whether or not an exception is thrown.
     *
     * @param supplier The lambda that gets executed.
     * @param fin      The lambda that gets executed before this method exits.
     * @param wrapper  A lambda that wraps any checked exception thrown by the lambda {@code supplier} into a subclass of {@link RuntimeException}.
     * @param <T>      The type that gets returned from the lambda {@code supplier}.
     * @param <E>      The type of the exception that will be used to wrap any checked exception thrown by the lambda {@code supplier}.
     *
     * @return The value returned by the lambda {@code supplier}.
     */
    public static <T, E extends RuntimeException> T get(@NotNull SupplierEx<T, ? extends Exception> supplier, @NotNull Runnable fin, @NotNull Function<? super Exception, ? extends E> wrapper) {
        try {
            return supplier.get();
        }
        catch(RuntimeException e) {
            throw e;
        }
        catch(Exception e) {
            throw wrapper.apply(e);
        }
        finally {
            fin.run();
        }
    }

    public static void run(@NotNull RunnableEx<? extends Exception> runner) {
        run(runner, DEFAULT_FINALLY, DEFAULT_WRAPPER);
    }

    public static <E extends RuntimeException> void run(@NotNull RunnableEx<? extends Exception> runner, @NotNull Function<? super Exception, ? extends E> wrapper) {
        run(runner, DEFAULT_FINALLY, wrapper);
    }

    public static void run(@NotNull RunnableEx<? extends Exception> runner, @NotNull Runnable fin) {
        run(runner, fin, DEFAULT_WRAPPER);
    }

    public static <E extends RuntimeException> void run(@NotNull RunnableEx<? extends Exception> runner, @NotNull Runnable fin, @NotNull Function<? super Exception, ? extends E> wrapper) {
        try {
            runner.run();
        }
        catch(RuntimeException e) {
            throw e;
        }
        catch(Exception e) {
            throw wrap(e);
        }
        finally {
            fin.run();
        }
    }

    public static WrapEx wrap(@NotNull Throwable t) {
        return ((t instanceof WrapEx ex) ? ex : new WrapEx(t.getMessage(), t));
    }
}
