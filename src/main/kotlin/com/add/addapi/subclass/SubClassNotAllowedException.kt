package com.add.addapi.subclass

import com.add.addapi.dnd5api.MainClass

class SubClassNotAllowedException(index: String, mainClass: MainClass) :
        RuntimeException("Subclass $index is not allowed for class $mainClass")
