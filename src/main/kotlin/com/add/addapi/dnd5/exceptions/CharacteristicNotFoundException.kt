package com.add.addapi.dnd5.exceptions

import com.add.addapi.enums.Characteristics
import java.lang.RuntimeException

class CharacteristicNotFoundException(index: String, characteristic: Characteristics) : RuntimeException("Index $index not found in ${characteristic.resource}")