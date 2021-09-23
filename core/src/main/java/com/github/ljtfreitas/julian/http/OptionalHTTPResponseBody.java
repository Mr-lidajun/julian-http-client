/*
 * Copyright (C) 2021 Tiago de Freitas Lima
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.ljtfreitas.julian.http;

import java.util.Optional;
import java.util.function.Function;

public class OptionalHTTPResponseBody implements HTTPResponseBody {

    private final HTTPStatus status;
    private final HTTPHeaders headers;
    private final HTTPResponseBody content;

    public OptionalHTTPResponseBody(HTTPStatus status, HTTPHeaders headers, HTTPResponseBody content) {
        this.status = status;
        this.headers = headers;
        this.content = content;
    }

    @Override
    public <T> Optional<T> deserialize(Function<byte[], T> fn) {
        return (status.readable() && hasContentLength()) ? content.deserialize(fn) : Optional.empty();
    }

    private boolean hasContentLength() {
        int length = headers.select("Content-Length").map(HTTPHeader::value).map(Integer::valueOf).orElse(-1);
        return length != 0;
    }
}
