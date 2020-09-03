package com.add.addapi.dnd5.mainclass

import com.add.addapi.dnd5.api.ApiResource
import com.add.addapi.dnd5.api.Equipment
import com.add.addapi.dnd5.api.MainClass
import com.add.addapi.dnd5.repositories.ApiRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.assertj.core.api.Assertions
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
            apiRepository.getByIndex(index, "classes", MainClass::class.java)
        } returns expectedMainClass

        val mainClass = mainClassService.getByIndex(index)

        assertThat(mainClass).isEqualTo(expectedMainClass)
    }
}