/*
 * Copyright (C) 2021 Tiago de Freitas Lima
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.github.ljtfreitas.julian.http.auth;

import java.util.Base64;

public class BasicAuthentication implements Authentication {

    private static final String BASIC_SCHEMA = "Basic ";

    private final Credentials credentials;

    public BasicAuthentication(String username, String password) {
        this(new Credentials(username, password));
    }

    public BasicAuthentication(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public String content() {
        return basic();
    }

    private String basic() {
        return BASIC_SCHEMA + base64();
    }

    private String base64() {
        return Base64.getEncoder().encodeToString((credentials.username() + ":" + credentials.password()).getBytes());
    }

    @Override
    public String show() {
        return basic();
    }
}
