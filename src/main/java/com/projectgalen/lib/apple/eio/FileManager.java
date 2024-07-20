package com.projectgalen.lib.apple.eio;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: FileManager.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: July 02, 2024
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

import com.formdev.flatlaf.util.SystemInfo;
import com.projectgalen.lib.utils.Obj;
import com.projectgalen.lib.utils.errors.MethodNotFoundException;
import com.projectgalen.lib.utils.functions.SupplierEx;
import com.projectgalen.lib.utils.reflect.Reflect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Optional.ofNullable;

/**
 * A proxy for the class {@code com.apple.eawt.FullScreenUtilities}.
 *
 * <p>When running on Windows or Linux this class does nothing.</p>
 *
 * <p>In order to use this class, you must launch the Java application with the following JVM command-line parameters:</p>
 * <blockquote><pre>--add-opens java.desktop/com.apple.eawt=ALL-UNNAMED --add-opens java.desktop/com.apple.eawt.event=ALL-UNNAMED</pre></blockquote>
 *
 * <p>Provides functionality to query and modify Mac-specific file attributes. The methods in this class are based on Finder attributes. These attributes in turn are dependent on HFS and HFS+ file
 * systems. As such, it is important to recognize their limitation when writing code that must function well across multiple platforms.</p>
 *
 * <p>In addition to file name suffixes, Mac OS X can use Finder attributes like file {@code type} and {@code creator} codes to identify and handle files. These codes are unique 4-byte identifiers.
 * The file {@code type} is a string that describes the contents of a file. For example, the file type {@code APPL} identifies the file as an application and therefore executable. A file type of
 * {@code TEXT}  means that the file contains raw text. Any application that can read raw text can open a file of type {@code TEXT}. Applications that use proprietary file types might assign their
 * files a proprietary file {@code type} code.</p>
 *
 * <p>To identify the application that can handle a document, the Finder can look at the {@code creator}. For example, if a user double-clicks on a document with the {@code ttxt creator}, it opens up
 * in Text Edit, the application registered with the {@code ttxt creator} code. Note that the {@code creator} code can be set to any application, not necessarily the application that created it. For
 * example, if you use an editor to create an HTML document, you might want to assign a browser's {@code creator} code for the file rather than the HTML editor's {@code creator} code. Double-clicking
 * on the document then opens the appropriate browser rather than the HTML editor.</p>
 *
 * <p>If you plan to publicly distribute your application, you must register its creator and any proprietary file types with the Apple Developer Connection to avoid collisions with codes used by
 * other developers. You can register a codes online at the
 * <a target=_blank href=http://developer.apple.com/dev/cftype/>Creator Code Registration</a> site.</p>
 *
 * @since 1.4
 */
@SuppressWarnings("unused")
public final class FileManager {

    private static final Class<?> C = Obj.classForname("com.apple.eio.FileManager");
    /**
     * The default
     *
     * @since Java for Mac OS X 10.5 - 1.5
     * @since Java for Mac OS X 10.5 Update 1 - 1.6
     */
    public static final short kOnAppropriateDisk = -32767;
    /**
     * Read-only system hierarchy.
     *
     * @since Java for Mac OS X 10.5 - 1.5
     * @since Java for Mac OS X 10.5 Update 1 - 1.6
     */
    public static final short kSystemDomain      = -32766;
    /**
     * All users of a single machine have access to these resources.
     *
     * @since Java for Mac OS X 10.5 - 1.5
     * @since Java for Mac OS X 10.5 Update 1 - 1.6
     */
    public static final short kLocalDomain       = -32765;
    /**
     * All users configured to use a common network server has access to these resources.
     *
     * @since Java for Mac OS X 10.5 - 1.5
     * @since Java for Mac OS X 10.5 Update 1 - 1.6
     */
    public static final short kNetworkDomain     = -32764;
    /**
     * Read/write. Resources that are private to the user.
     *
     * @since Java for Mac OS X 10.5 - 1.5
     * @since Java for Mac OS X 10.5 Update 1 - 1.6
     */
    public static final short kUserDomain        = -32763;

    /**
     * Converts an OSType (e.g. "macs" from {@literal <CarbonCore/Folders.h>}) into an int.
     *
     * @param type the 4 character type to convert.
     *
     * @return an int representing the 4 character value
     *
     * @since Java for Mac OS X 10.5 - 1.5
     * @since Java for Mac OS X 10.5 Update 1 - 1.6
     */
    public static int OSTypeToInt(String type) {
        return ofNullable(foo3(() -> (int)_OSTypeToInt.m.invoke(null, type))).orElse(0);
    }

    /**
     * Locates a folder of a particular type. Mac OS X recognizes certain specific folders that have distinct purposes. For example, the user's desktop or temporary folder. These folders have
     * corresponding codes. Given one of these codes, this method returns the path to that particular folder. Certain folders of a given type may appear in more than one domain. For example, although
     * there is only one {@code root} folder, there are multiple {@code pref} folders. If this method is called to find the {@code pref} folder, it will return the first one it finds, the user's
     * preferences folder in {@code ~/Library/Preferences}. To explicitly locate a folder in a certain domain use {@code findFolder(short domain, int folderType)} or
     * {@code findFolder(short domain, int folderType, boolean createIfNeeded)}.
     *
     * @return the path to the folder searched for
     *
     * @throws FileNotFoundException If the file was not found.
     * @since 1.4
     */
    public static String findFolder(int folderType) throws FileNotFoundException {
        return foo1(() -> (String)_findFolder1.m.invoke(null, folderType));
    }

    /**
     * Locates a folder of a particular type, within a given domain. Similar to {@code findFolder(int folderType)} except that the domain to look in can be specified. Valid values for {@code domain}
     * include:
     * <dl>
     * <dt>user</dt>
     * <dd>The User domain contains resources specific to the user who is currently logged in</dd>
     * <dt>local</dt>
     * <dd>The Local domain contains resources shared by all users of the system but are not needed for the system
     * itself to run.</dd>
     * <dt>network</dt>
     * <dd>The Network domain contains resources shared by users of a local area network.</dd>
     * <dt>system</dt>
     * <dd>The System domain contains the operating system resources installed by Apple.</dd>
     * </dl>
     *
     * @return the path to the folder searched for
     *
     * @throws FileNotFoundException If the file was not found.
     * @since 1.4
     */
    public static String findFolder(short domain, int folderType) throws FileNotFoundException {
        return foo1(() -> (String)_findFolder2.m.invoke(null, domain, folderType));
    }

    /**
     * Locates a folder of a particular type within a given domain and optionally creating the folder if it does not exist. The behavior is similar to {@code findFolder(int folderType)} and
     * {@code findFolder(short domain, int folderType)} except that it can create the folder if it does not already exist.
     *
     * @param createIfNeeded set to {@code true}, by setting to {@code false} the behavior will be the same as {@code findFolder(short domain, int folderType, boolean createIfNeeded)}
     *
     * @return the path to the folder searched for
     *
     * @throws FileNotFoundException If the file was not found.
     * @since 1.4
     */
    public static String findFolder(short domain, int folderType, boolean createIfNeeded) throws FileNotFoundException {
        return foo1(() -> (String)_findFolder3.m.invoke(null, domain, folderType, createIfNeeded));
    }

    /**
     * Obtains the file {@code creator} code for a file or folder.
     *
     * @throws IOException If an I/O error occurs.
     * @since 1.4
     */
    public static int getFileCreator(String filename) throws IOException {
        return ofNullable(foo2(() -> (int)_getFileCreator.m.invoke(null, filename))).orElse(0);
    }

    /**
     * Obtains the file {@code type} code for a file or folder.
     *
     * @throws IOException If an I/O error occurs.
     * @since 1.4
     */
    public static int getFileType(String filename) throws IOException {
        return ofNullable(foo2(() -> (int)_getFileType.m.invoke(null, filename))).orElse(0);
    }

    /**
     * Obtains the path to the current application's NSBundle, may not return a valid path if Java was launched from the command line.
     *
     * @return full pathname of the NSBundle of the current application executable.
     *
     * @since Java for Mac OS X 10.5 Update 1 - 1.6
     * @since Java for Mac OS X 10.5 Update 2 - 1.5
     */
    public static String getPathToApplicationBundle() {
        return foo3(() -> (String)_getPathToAppBndl.m.invoke(null));
    }

    /**
     * @return full pathname for the resource identified by a given name.
     *
     * @throws FileNotFoundException If the file was not found.
     * @since 1.4
     */
    public static String getResource(String resourceName) throws FileNotFoundException {
        return foo1(() -> (String)_getResource1.m.invoke(null, resourceName));
    }

    /**
     * @return full pathname for the resource identified by a given name and located in the specified bundle subdirectory.
     *
     * @throws FileNotFoundException If the file was not found.
     * @since 1.4
     */
    public static String getResource(String resourceName, String subDirName) throws FileNotFoundException {
        return foo1(() -> (String)_getResource2.m.invoke(null, resourceName, subDirName));
    }

    /**
     * Returns the full pathname for the resource identified by the given name and file extension and located in the specified bundle subdirectory.
     * <p>
     * If extension is an empty string or null, the returned pathname is the first one encountered where the file name exactly matches name.
     * <p>
     * If subpath is null, this method searches the top-level nonlocalized resource directory (typically Resources) and the top-level of any language-specific directories. For example, suppose you
     * have a modern bundle and specify "Documentation" for the subpath parameter. This method would first look in the Contents/Resources/Documentation directory of the bundle, followed by the
     * Documentation subdirectories of each language-specific .lproj directory. (The search order for the language-specific directories corresponds to the user's preferences.) This method does not
     * recurse through any other subdirectories at any of these locations. For more details see the AppKit NSBundle documentation.
     *
     * @return full pathname for the resource identified by the given name and file extension and located in the specified bundle subdirectory.
     *
     * @throws FileNotFoundException If the file was not found.
     * @since 1.4
     */
    public static String getResource(String resourceName, String subDirName, String type) throws FileNotFoundException {
        return foo1(() -> (String)_getResource3.m.invoke(null, resourceName, subDirName, type));
    }

    /**
     * Moves the specified file to the Trash
     *
     * @param file the file
     *
     * @return returns true if the NSFileManager successfully moved the file to the Trash.
     *
     * @throws FileNotFoundException If the file was not found.
     * @since Java for Mac OS X 10.6 Update 1 - 1.6
     * @since Java for Mac OS X 10.5 Update 6 - 1.6, 1.5
     */
    public static boolean moveToTrash(final File file) throws FileNotFoundException {
        return ofNullable(foo1(() -> (boolean)_moveToTrash.m.invoke(null, file))).orElse(false);
    }

    /**
     * Opens the path specified by a URL in the appropriate application for that URL. HTTP URL's ({@code http://}) open in the default browser as set in the Internet pane of System Preferences. File
     * ({@code file://}) and FTP URL's ({@code ftp://}) open in the Finder. Note that opening an FTP URL will prompt the user for where they want to save the downloaded file(s).
     *
     * @param url the URL for the file you want to open, it can either be an HTTP, FTP, or file url
     *
     * @throws IOException If an I/O error occurs.
     * @since 1.4
     * @deprecated this functionality has been superseded by java.awt.Desktop.browse() and java.awt.Desktop.open()
     */
    public static @Deprecated void openURL(String url) throws IOException {
        foo2(() -> _openURL.m.invoke(null, url));
    }

    /**
     * Reveals the specified file in the Finder
     *
     * @param file the file to reveal
     *
     * @return returns true if the NSFileManager successfully revealed the file in the Finder.
     *
     * @throws FileNotFoundException If the file was not found.
     * @since Java for Mac OS X 10.6 Update 1 - 1.6
     * @since Java for Mac OS X 10.5 Update 6 - 1.6, 1.5
     */
    public static boolean revealInFinder(final File file) throws FileNotFoundException {
        return ofNullable(foo1(() -> (boolean)_revealInFinder.m.invoke(null, file))).orElse(false);
    }

    /**
     * Sets the file {@code creator} code for a file or folder.
     *
     * @throws IOException If an I/O error occurs.
     * @since 1.4
     */
    public static void setFileCreator(String filename, int creator) throws IOException {
        foo2(() -> _setFileCreator.m.invoke(null, filename, creator));
    }

    /**
     * Sets the file {@code type} code for a file or folder.
     *
     * @throws IOException If an I/O error occurs.
     * @since 1.4
     */
    public static void setFileType(String filename, int type) throws IOException {
        foo2(() -> _setFileType.m.invoke(null, filename, type));
    }

    /**
     * Sets the file {@code type} and {@code creator} codes for a file or folder.
     *
     * @throws IOException If an I/O error occurs.
     * @since 1.4
     */
    public static void setFileTypeAndCreator(String filename, int type, int creator) throws IOException {
        foo2(() -> _setFileTpCrtr.m.invoke(null, filename, type, creator));
    }

    private static <T> @Nullable T foo1(@NotNull SupplierEx<T, ? extends Exception> supplier) throws FileNotFoundException {
        if(SystemInfo.isMacOS) {
            try {
                return supplier.get();
            }
            catch(Exception e) {
                if((e instanceof InvocationTargetException) && (e.getCause() instanceof FileNotFoundException io)) throw io;
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return null;
    }

    private static <T> @Nullable T foo2(@NotNull SupplierEx<T, ? extends Exception> supplier) throws IOException {
        if(SystemInfo.isMacOS) {
            try {
                return supplier.get();
            }
            catch(Exception e) {
                if((e instanceof InvocationTargetException) && (e.getCause() instanceof IOException io)) throw io;
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return null;
    }

    private static <T> @Nullable T foo3(@NotNull SupplierEx<T, ? extends Exception> supplier) {
        if(SystemInfo.isMacOS) {
            try {
                return supplier.get();
            }
            catch(Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return null;
    }

    private static final class _OSTypeToInt {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "OSTypeToInt", String.class), MethodNotFoundException::new);
    }

    private static final class _findFolder1 {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "findFolder", int.class), MethodNotFoundException::new);
    }

    private static final class _findFolder2 {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "findFolder", short.class, int.class), MethodNotFoundException::new);
    }

    private static final class _findFolder3 {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "findFolder", short.class, int.class, boolean.class), MethodNotFoundException::new);
    }

    private static final class _getFileCreator {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "getFileCreator", String.class), MethodNotFoundException::new);
    }

    private static final class _getFileType {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "getFileType", String.class), MethodNotFoundException::new);
    }

    private static final class _getPathToAppBndl {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "getPathToApplicationBundle"), MethodNotFoundException::new);
    }

    private static final class _getResource1 {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "getResource", String.class), MethodNotFoundException::new);
    }

    private static final class _getResource2 {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "getResource", String.class, String.class), MethodNotFoundException::new);
    }

    private static final class _getResource3 {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "getResource", String.class, String.class, String.class), MethodNotFoundException::new);
    }

    private static final class _moveToTrash {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "moveToTrash", File.class), MethodNotFoundException::new);
    }

    private static final class _openURL {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "openURL", String.class), MethodNotFoundException::new);
    }

    private static final class _revealInFinder {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "revealInFinder", File.class), MethodNotFoundException::new);
    }

    private static final class _setFileCreator {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "setFileCreator", String.class, int.class), MethodNotFoundException::new);
    }

    private static final class _setFileTpCrtr {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "setFileTypeAndCreator", String.class, int.class, int.class), MethodNotFoundException::new);
    }

    private static final class _setFileType {
        private static final Method m = Obj.requireNonNull(Reflect.getMethod(C, true, "setFileType", String.class, int.class), MethodNotFoundException::new);
    }
}
