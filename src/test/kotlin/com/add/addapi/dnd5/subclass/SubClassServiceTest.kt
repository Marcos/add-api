package com.add.addapi.dnd5.subclass

import com.add.addapi.dnd5.api.MainClass
import com.add.addapi.dnd5.api.SubClass
import com.add.addapi.dnd5.repositories.ApiRepository
import com.add.addapi.dnd5.spell.SpellNotAllowedException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.assertj.core.api.Assertions
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
            apiRepository.getByIndex(index, "subclasses", SubClass::class.java)
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
            apiRepository.getByIndex(index, "subclasses", SubClass::class.java)
        } returns expectedSubClass

        assertThrows<SubClassNotAllowedException> {
            subClassService.getByIndex(index, mainClass)
        }
    }

}