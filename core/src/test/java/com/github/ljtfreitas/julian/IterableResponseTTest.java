package com.github.ljtfreitas.julian;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IterableResponseTTest {

	@Mock
	private Endpoint endpoint;
	
	private IterableResponseT<String> responseT = new IterableResponseT<>();

	@Nested
	class Predicates {
		
		@Test
		void supported() throws Exception {
			when(endpoint.returnType()).thenReturn(JavaType.parameterized(Iterable.class, String.class));

			assertTrue(responseT.test(endpoint));
		}
		
		@Test
		void unsupported() throws Exception {
			when(endpoint.returnType()).thenReturn(JavaType.valueOf(String.class));
	
			assertFalse(responseT.test(endpoint));
		}
	}

	@Nested
	class Adapted {
	
		@Test
		void parameterized() throws Exception {
			when(endpoint.returnType()).thenReturn(JavaType.parameterized(Iterable.class, String.class));

			assertEquals(JavaType.parameterized(Collection.class, String.class), responseT.adapted(endpoint));
		}

		@Test
		void simple() throws Exception {
			when(endpoint.returnType()).thenReturn(JavaType.object());

			assertEquals(JavaType.parameterized(Collection.class, Object.class), responseT.adapted(endpoint));
		}
	}

	@Test
	void compose(@Mock ResponseFn<Collection<String>, Collection<String>> fn, @Mock RequestIO<Collection<String>> request) throws Exception {
		Arguments arguments = Arguments.empty();

		when(request.comp(fn, arguments)).thenReturn(Promise.done(List.of("expected")));

		Iterable<String> iterable = responseT.comp(endpoint, fn).join(request, arguments);

		assertThat(iterable, hasItem("expected"));
	}
}
