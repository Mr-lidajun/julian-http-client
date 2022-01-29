package com.github.ljtfreitas.julian;

import com.github.ljtfreitas.julian.Endpoint.Parameter;
import com.github.ljtfreitas.julian.Endpoint.Parameters;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromiseSubscriberCallbackResponseTTest {

    @Mock
    private Endpoint endpoint;

    private final PromiseSubscriberCallbackResponseT<String> responseT = new PromiseSubscriberCallbackResponseT<>();

    @Nested
    class Predicates {

        @Test
        void supported() {
            when(endpoint.parameters()).thenReturn(Parameters.create(Parameter.callback(0, "subscriber",
                    JavaType.parameterized(Promise.Subscriber.class, String.class, Exception.class))));
            when(endpoint.returnType()).thenReturn(JavaType.none());

            assertTrue(responseT.test(endpoint));
        }

        @Test
        void unsupported() {
            when(endpoint.returnType()).thenReturn(JavaType.valueOf(String.class));

            assertFalse(responseT.test(endpoint));
        }
    }

    @Nested
    class Adapted {

        @Test
        void parameterized() {
            when(endpoint.parameters()).thenReturn(Parameters.create(Parameter.callback(0, "success",
                    JavaType.parameterized(Promise.Subscriber.class, String.class, Exception.class))));

            assertEquals(JavaType.parameterized(Promise.class, String.class, Exception.class), responseT.adapted(endpoint));
        }

        @Test
        void unsupported() {
            when(endpoint.parameters()).thenReturn(Parameters.empty());

            assertEquals(JavaType.parameterized(Promise.class, Object.class, Exception.class), responseT.adapted(endpoint));
        }
    }

    @Test
    void compose(@Mock Endpoint endpoint, TestReporter reporter) throws InterruptedException {
        Promise<Response<String, ? extends Exception>, Exception> response = Promise.done(new DoneResponse<>("it works!"));

        Promise.Subscriber<String, Exception> subscriber = new Promise.Subscriber<>() {

            @Override
            public void success(String item) {
                reporter.publishEntry("success: '" + item + "' on " + Thread.currentThread());
            }

            @Override
            public void failure(Exception failure) {
                reporter.publishEntry("failure");
                fail(failure.getMessage());
            }

            @Override
            public void done() {
                reporter.publishEntry("done");
            }
        };

        when(endpoint.parameters()).thenReturn(Parameters.create(Parameter.callback(0, "subscriber",
                JavaType.parameterized(Promise.Subscriber.class, String.class, Exception.class))));

        Arguments arguments = Arguments.create(subscriber);

        ObjectResponseT<String> objectResponseT = new ObjectResponseT<>();

        PromiseResponseT<String> promiseResponseT = new PromiseResponseT<>();
        ResponseFn<String, Promise<String, ?>> publisherFn = promiseResponseT.bind(endpoint, objectResponseT.bind(endpoint, null));

        responseT.bind(endpoint, publisherFn).join(response, arguments);

        Thread.sleep(1500);
    }
}