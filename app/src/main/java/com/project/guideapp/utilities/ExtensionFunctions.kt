package com.project.guideapp.utilities

import java.util.*

class ExtensionFunctions {
    fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null
}