/*
 * MIT License
 *
 * Copyright (c) 2018 KubeMQ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kubemq.sdk.tools;

import com.google.protobuf.ByteString;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * A class that is responsible for Converting Byte[] to object and vice versa.
 */
public class Converter {

    /**
     * Byte Array to ByteString
     *
     * @param byteArray byteArray to convert into ByteString
     * @return com.google.protobuf.ByteString
     */
    static ByteString ToByteString(byte[] byteArray) {
        return ByteString.copyFrom(byteArray);
    }

    /**
     * Convert from byte array to object
     *
     * @param data byteArray to convert into Object
     * @return Object
     * @throws IOException            Signals that an I/O exception of some sort has occurred.
     * @throws ClassNotFoundException Thrown when an application tries to load in a class through its string name
     */
    public static Object FromByteArray(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try (ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }

    /**
     * Convert from object to byte array
     *
     * @param obj Object to convert into byte array
     * @return byte[]
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public static byte[] ToByteArray(Object obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutput out = null;
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            return bos.toByteArray();
        }
    }

    public static LocalDateTime FromUnixTime(long unitTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unitTime),
                TimeZone.getDefault().toZoneId());
    }

    public static long ToUnixTime(LocalDateTime timestamp) {
        return timestamp.atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli();
    }
}
