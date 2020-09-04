package com.add.addapi.spell

import com.add.addapi.attribute.responses.AttributeListResponse
import com.add.addapi.attribute.services.AttributeService
import com.add.addapi.subclass.SubClassService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/spells")
class SpellController(
        val spellService: SpellService
) {
    @GetMapping("/{mainClassIndex}/{subClassIndex}")
    fun listSpeels(
            @PathVariable("mainClassIndex") mainClassIndex: String,
            @PathVariable("subClassIndex") subClassIndex: String
    ): AttributeListResponse {
        return spellService.list(mainClassIndex, subClassIndex)
    }

}