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

import java.util.function.Function;
import java.util.function.Predicate;

import com.github.ljtfreitas.julian.Response;

class EmptyHTTPResponse<T> implements HTTPResponse<T> {

	private final HTTPStatus status;
	private final HTTPHeaders headers;

	EmptyHTTPResponse(HTTPStatus status, HTTPHeaders headers) {
		this.status = status;
		this.headers = headers;
	}

	@Override
	public HTTPStatus status() {
		return status;
	}

	@Override
	public HTTPHeaders headers() {
		return headers;
	}

	@Override
	public T body() {
		return null;
	}

	@Override
	public <R> Response<R> map(Function<? super T, R> fn) {
		return new DefaultHTTPResponse<>(status, headers, fn.apply(null));
	}
	
	@Override
	public <E extends Exception> Response<T> recover(Class<? super E> p, Function<? super E, T> fn) {
		return this;
	}
	
	@Override
	public <E extends Exception> Response<T> recover(Function<? super E, T> fn) {
		return this;
	}
	
	@Override
	public Response<T> recover(HTTPStatusCode code, Function<HTTPResponse<T>, T> fn) {
		return this;
	}
	
	@Override
	public <E extends Exception> Response<T> recover(Predicate<? super E> p, Function<? super E, T> fn) {
		return this;
	}
}