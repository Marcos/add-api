package com.add.addapi.dnd5api.mainclass

import com.add.addapi.dnd5api.api.AttributeType.MAINCLASS
import com.add.addapi.dnd5api.api.MainClass
import com.add.addapi.dnd5api.repositories.ApiRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MainClassServiceTest {

    @MockK(relaxed = true)
    private lateinit var apiRepository: ApiRepository

    @InjectMockKs
    private lateinit var mainClassService: MainClassService

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `get main class by index`() {
        val index = "mainclass"

        val expectedMainClass = mockk<MainClass>()
        every {
            apiRepository.getByIndex(index, MAINCLASS)
        } returns expectedMainClass

        val mainClass = mainClassService.getByIndex(index)

        assertThat(mainClass).isEqualTo(expectedMainClass)
    }
}