package com.add.addapi.dnd5api.subclass

import com.add.addapi.dnd5api.api.AttributeType.SUBCLASS
import com.add.addapi.dnd5api.api.MainClass
import com.add.addapi.dnd5api.api.SubClass
import com.add.addapi.dnd5api.repositories.ApiRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SubClassServiceTest {

    @MockK(relaxed = true)
    private lateinit var apiRepository: ApiRepository

    @InjectMockKs
    private lateinit var subClassService: SubClassService

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `get main class by index when it is allowed for class`() {
        val index = "son-of-gandalf"
        val subClass = SubClass("son-of-gandalf", "Son of Gandalf")
        val mainClass = MainClass("wizard", "Wizard", listOf(subClass.toNamedAPIResource()))
        val expectedSubClass = mockk<SubClass>()
        every {
            apiRepository.getByIndex(index, SUBCLASS)
        } returns expectedSubClass

        val actualSubClass = subClassService.getByIndex(index, mainClass)

        assertThat(actualSubClass).isEqualTo(expectedSubClass)
    }

    @Test
    fun `get main class by index when it is not allowed for class`() {
        val index = "not-son-of-gandalf"
        val subClass = SubClass("son-of-gandalf", "Son of Gandalf")
        val mainClass = MainClass("wizard", "Wizard", listOf(subClass.toNamedAPIResource()))
        val expectedSubClass = mockk<SubClass>()
        every {
            apiRepository.getByIndex(index, SUBCLASS)
        } returns expectedSubClass

        assertThrows<SubClassNotAllowedException> {
            subClassService.getByIndex(index, mainClass)
        }
    }

}