package com.github.ljtfreitas.julian;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListIteratorResponseTTest {

	@Mock
	private Endpoint endpoint;
	
	private final ListIteratorResponseT<String> responseT = new ListIteratorResponseT<>();

	@Nested
	class Predicates {
		
		@Test
		void supported() {
			when(endpoint.returnType()).thenReturn(JavaType.parameterized(ListIterator.class, String.class));

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
			when(endpoint.returnType()).thenReturn(JavaType.parameterized(ListIterator.class, String.class));

			assertEquals(JavaType.parameterized(List.class, String.class), responseT.adapted(endpoint));
		}

		@Test
		void simple() {
			when(endpoint.returnType()).thenReturn(JavaType.object());

			assertEquals(JavaType.parameterized(List.class, Object.class), responseT.adapted(endpoint));
		}
	}

	@Test
	void compose(@Mock ResponseFn<List<String>, List<String>> fn, @Mock RequestIO<List<String>> request) {
		Arguments arguments = Arguments.empty();

		when(fn.run(request, arguments)).thenReturn(Promise.done(List.of("expected")));

		ListIterator<String> listIterator = responseT.bind(endpoint, fn).join(request, arguments);

		assertThat(() -> listIterator, hasItem("expected"));
	}
}
