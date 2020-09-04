package com.add.addapi.dnd5.spell

import com.add.addapi.character.exceptions.RequiredFieldException
import com.add.addapi.dnd5.api.MainClass
import com.add.addapi.dnd5.api.NamedAPIResource
import com.add.addapi.dnd5.api.Spell
import com.add.addapi.dnd5.api.SubClass
import com.add.addapi.dnd5.repositories.ApiRepository
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
internal class SpellServiceTest {

    @MockK(relaxed = true)
    private lateinit var apiRepository: ApiRepository

    @InjectMockKs
    private lateinit var spellService: SpellService

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `get spells by indexes`() {
        val indexes = listOf("spell")
        val mainClass = MainClass("wizard", "Wizard")
        val subClass = SubClass("son-of-gandalf", "Son of Gandalf")

        val expectedSpells = listOf(createSpell(
                classes = listOf(mainClass.toNamedAPIResource()),
                subClasses = listOf(subClass.toNamedAPIResource()))
        )
        every {
            apiRepository.getByIndexes(indexes, "spells", Spell::class.java)
        } returns expectedSpells

        val spells = spellService.getByIndexes(indexes, mainClass, subClass)

        assertThat(spells).isEqualTo(expectedSpells)
    }

    @Test
    fun `get spells by indexes when it is allowed only for main class`() {
        val indexes = listOf("spell")
        val mainClass = MainClass("wizard", "Wizard")
        val subClass = SubClass("son-of-gandalf", "Son of Gandalf")

        val expectedSpells = listOf(createSpell(
                classes = listOf(mainClass.toNamedAPIResource()),
                subClasses = emptyList())
        )
        every {
            apiRepository.getByIndexes(indexes, "spells", Spell::class.java)
        } returns expectedSpells

        val spells = spellService.getByIndexes(indexes, mainClass, subClass)

        assertThat(spells).isEqualTo(expectedSpells)
    }

    @Test
    fun `get spells by indexes when it is allowed only for sub class`() {
        val indexes = listOf("spell")
        val mainClass = MainClass("wizard", "Wizard")
        val subClass = SubClass("son-of-gandalf", "Son of Gandalf")

        val expectedSpells = listOf(createSpell(
                classes = emptyList(),
                subClasses = listOf(subClass.toNamedAPIResource()))
        )
        every {
            apiRepository.getByIndexes(indexes, "spells", Spell::class.java)
        } returns expectedSpells

        val spells = spellService.getByIndexes(indexes, mainClass, subClass)

        assertThat(spells).isEqualTo(expectedSpells)
    }

    @Test
    fun `get spells by indexes when it is not allowed`() {
        val indexes = listOf("spell")
        val mainClass = MainClass("wizard", "Wizard")
        val subClass = SubClass("son-of-gandalf", "Son of Gandalf")

        val expectedSpells = listOf(createSpell(
                classes = listOf(NamedAPIResource("otherClass", "otherClass", "otherClass")),
                subClasses = listOf(NamedAPIResource("otherSublClass", "otherSublClass", "otherSublClass")))
        )
        every {
            apiRepository.getByIndexes(indexes, "spells", Spell::class.java)
        } returns expectedSpells

        assertThrows<SpellNotAllowedException> {
            spellService.getByIndexes(indexes, mainClass, subClass)
        }
    }

    private fun createSpell(classes: List<NamedAPIResource>, subClasses: List<NamedAPIResource>): Spell {
        return Spell(
                index = "whirlpool",
                name = "Whirlpool",
                classes = classes,
                subclasses = subClasses
        )
    }
}