package com.add.addapi.dnd5.equipment

import com.add.addapi.dnd5.api.ApiResource
import com.add.addapi.dnd5.api.Equipment
import com.add.addapi.dnd5.repositories.ApiRepository
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
            apiRepository.getByIndexes(indexes, "equipment", Equipment::class.java)
        } returns expectedEquipments

        val equipments = equipmentService.getByIndexes(indexes)

        assertThat(equipments).isEqualTo(expectedEquipments)
    }
}