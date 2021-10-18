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

package com.github.ljtfreitas.julian.http.client;

import com.github.ljtfreitas.julian.http.HTTPRequestDefinition;

import java.util.Collection;
import java.util.function.Function;

import static java.util.Collections.unmodifiableCollection;

public class ComposedHTTPClient implements HTTPClient {

    private final HTTPClient client;
    private final Collection<Function<HTTPClient, HTTPClient>> constructors;

    public ComposedHTTPClient(HTTPClient client, Collection<Function<HTTPClient, HTTPClient>> constructors) {
        this.client = client;
        this.constructors = unmodifiableCollection(constructors);
    }

    @Override
    public HTTPClientRequest request(HTTPRequestDefinition request) {
        return constructors.stream().reduce(client, (a, b) -> b.apply(a), (a, b) -> b)
                .request(request);
    }
}
