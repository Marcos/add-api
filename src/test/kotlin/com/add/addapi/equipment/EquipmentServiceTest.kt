package com.add.addapi.equipment

import com.add.addapi.dnd5api.AttributeType.EQUIPMENT
import com.add.addapi.dnd5api.Equipment
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EquipmentServiceTest {

    @MockK(relaxed = true)
    private lateinit var apiRepository: ApiRepository

    @InjectMockKs
    private lateinit var equipmentService: EquipmentService

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `get equipments by indexes`() {
        val indexes = listOf("equipment")

        val expectedEquipments = listOf(mockk<Equipment>())
        every {
            apiRepository.getByIndexes(indexes, EQUIPMENT)
        } returns expectedEquipments

        val equipments = equipmentService.getByIndexes(indexes)

        assertThat(equipments).isEqualTo(expectedEquipments)
    }
}