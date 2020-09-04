package com.add.addapi.spell

import com.add.addapi.attribute.services.AttributeService
import com.add.addapi.dnd5api.AttributeType.SPELL
import com.add.addapi.dnd5api.MainClass
import com.add.addapi.dnd5api.NamedAPIResource
import com.add.addapi.dnd5api.Spell
import com.add.addapi.dnd5api.SubClass
import com.add.addapi.dnd5api.repositories.ApiRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SpellServiceTest {

    @MockK(relaxed = true)
    private lateinit var attributeService: AttributeService

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
            apiRepository.getByIndexes(indexes, SPELL)
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
            apiRepository.getByIndexes(indexes, SPELL)
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
            apiRepository.getByIndexes(indexes, SPELL)
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
            apiRepository.getByIndexes(indexes, SPELL)
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