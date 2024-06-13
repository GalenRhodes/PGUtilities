package com.projectgalen.lib.json;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: JsonRect.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 05, 2024
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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY)
public class JsonRect extends Rectangle {

    public JsonRect()                                       { super(); }

    public JsonRect(@NotNull Dimension d)                   { super(d); }

    public JsonRect(@NotNull Point p)                       { super(p); }

    public JsonRect(@NotNull Point p, @NotNull Dimension d) { super(p, d); }

    public JsonRect(@NotNull Rectangle r)                   { super(r); }

    public JsonRect(int width, int height)                  { super(width, height); }

    public JsonRect(int x, int y, int width, int height)    { super(x, y, width, height); }

    public @Override String toString()                      { return "{ \"x\": %d; \"y\": %d; \"width\": %d; \"height\": %d; }".formatted(x, y, width, height); }
}
