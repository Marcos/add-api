package com.add.addapi.race

import com.add.addapi.dnd5api.AttributeType.RACE
import com.add.addapi.dnd5api.Race
import com.add.addapi.dnd5api.repositories.ApiRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RaceServiceTest {

    @MockK(relaxed = true)
    private lateinit var apiRepository: ApiRepository

    @InjectMockKs
    private lateinit var raceService: RaceService

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `get race by index`() {
        val index = "race"

        val expectedRace = mockk<Race>()
        every {
            apiRepository.getByIndex(index, RACE)
        } returns expectedRace

        val race = raceService.getByIndex(index)

        Assertions.assertThat(race).isEqualTo(expectedRace)
    }
}