package com.projectgalen.lib.json;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: JSON.java
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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jetbrains.annotations.NotNull;

import static com.projectgalen.lib.json.JSON.Options.*;

@SuppressWarnings("unused")
public final class JSON {
    private static final Options[] DEFAULT_OPTIONS = { EnumsUsingToString, Formatted, IncludeNonNull, AutoClose, IgnoreUnknownProps, UnwrapSingleElementArrays };

    private JSON() { }

    public static @NotNull ObjectMapper getMapper() {
        return getMapper(DEFAULT_OPTIONS);
    }

    public static @NotNull ObjectMapper getMapper(Options @NotNull ... options) {
        ObjectMapper mapper = new ObjectMapper();

        for(Options opt : options) {
            switch(opt) {
                case Formatted -> mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                case IncludeNonNull -> mapper.setSerializationInclusion(Include.NON_NULL);
                case AutoClose -> mapper.configure(SerializationFeature.CLOSE_CLOSEABLE, true);
                case IgnoreUnknownProps -> mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                case EnumsUsingToString -> {
                    mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
                    mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
                }
                case UnwrapSingleElementArrays -> {
                    mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
                    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                }
            }
        }

        return mapper;
    }

    public enum Options {
        EnumsUsingToString, Formatted, IncludeNonNull, AutoClose, IgnoreUnknownProps, UnwrapSingleElementArrays
    }
}
