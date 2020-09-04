package com.add.addapi.dnd5api.subclass

import com.add.addapi.dnd5api.api.MainClass

class SubClassNotAllowedException(index: String, mainClass: MainClass) :
        RuntimeException("Subclass $index is not allowed for class $mainClass")
