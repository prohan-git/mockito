/*
 * Copyright (c) 2018 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockitousage;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JunitJupiterTest {

    @Mock
    private Function<Integer, String> rootMock;

    @Test
    void ensureMockCreationWorks() {
        assertThat(rootMock).isNotNull();
    }

    @Test
    void can_set_stubs_on_initialized_mock() {
        Mockito.when(rootMock.apply(10)).thenReturn("Return");
        assertThat(rootMock.apply(10)).isEqualTo("Return");
    }

    @Nested
    class NestedTestWithExtraMock {
        @Mock Runnable nestedMock;

        @Test
        void nestedMockCreated() {
            assertThat(nestedMock).isNotNull();
        }

        @Test
        void rootMockCreated() {
            assertThat(rootMock).isNotNull();
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
        // ^^ duplicate registration should be ignored by JUnit
        // see http://junit.org/junit5/docs/current/user-guide/#extensions-registration-inherita
    class DuplicateExtensionOnNestedTest {

        @Mock
        Object nestedMock;

        @Test
        void ensureMocksAreCreatedForNestedTests() {
            assertThat(nestedMock).isNotNull();
        }
    }

    @Nested
    class NestedWithoutExtraMock {
        @Test
        // mock is initialized by mockito session
        void shouldWeCreateMocksInTheParentContext() {
            assertThat(rootMock).isNotNull();
        }
    }
}
