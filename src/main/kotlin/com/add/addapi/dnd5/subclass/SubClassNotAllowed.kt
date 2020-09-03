package com.add.addapi.dnd5.subclass

import com.add.addapi.dnd5.api.MainClass

class SubClassNotAllowed(index: String, mainClass: MainClass) :
        RuntimeException("Subclass $index is not allowed for class $mainClass")
