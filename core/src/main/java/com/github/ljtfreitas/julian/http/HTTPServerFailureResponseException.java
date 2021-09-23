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

import static com.github.ljtfreitas.julian.http.HTTPStatusCode.BAD_GATEWAY;
import static com.github.ljtfreitas.julian.http.HTTPStatusCode.GATEWAY_TIMEOUT;
import static com.github.ljtfreitas.julian.http.HTTPStatusCode.HTTP_VERSION_NOT_SUPPORTED;
import static com.github.ljtfreitas.julian.http.HTTPStatusCode.INTERNAL_SERVER_ERROR;
import static com.github.ljtfreitas.julian.http.HTTPStatusCode.NOT_IMPLEMENTED;
import static com.github.ljtfreitas.julian.http.HTTPStatusCode.SERVICE_UNAVAILABLE;

public class HTTPServerFailureResponseException extends HTTPFailureResponseException {

	private static final long serialVersionUID = 1L;

	HTTPServerFailureResponseException(HTTPStatusCode status, HTTPHeaders headers, String body) {
		super(status, headers, body);
	}

	public static class InternalServerError extends HTTPServerFailureResponseException {

		private static final long serialVersionUID = 1L;

		public InternalServerError(HTTPHeaders headers, String body) {
			super(INTERNAL_SERVER_ERROR, headers, body);
		}
	}

	public static class NotImplemented extends HTTPServerFailureResponseException {

		private static final long serialVersionUID = 1L;

		public NotImplemented(HTTPHeaders headers, String body) {
			super(NOT_IMPLEMENTED, headers, body);
		}
	}

	public static class BadGateway extends HTTPServerFailureResponseException {

		private static final long serialVersionUID = 1L;

		public BadGateway(HTTPHeaders headers, String body) {
			super(BAD_GATEWAY, headers, body);
		}
	}

	public static class ServiceUnavailable extends HTTPServerFailureResponseException {

		private static final long serialVersionUID = 1L;

		public ServiceUnavailable(HTTPHeaders headers, String body) {
			super(SERVICE_UNAVAILABLE, headers, body);
		}
	}

	public static class GatewayTimeout extends HTTPServerFailureResponseException {

		private static final long serialVersionUID = 1L;

		public GatewayTimeout(HTTPHeaders headers, String body) {
			super(GATEWAY_TIMEOUT, headers, body);
		}
	}

	public static class HTTPVersionNotSupported extends HTTPServerFailureResponseException {

		private static final long serialVersionUID = 1L;

		public HTTPVersionNotSupported(HTTPHeaders headers, String body) {
			super(HTTP_VERSION_NOT_SUPPORTED, headers, body);
		}
	}
}
