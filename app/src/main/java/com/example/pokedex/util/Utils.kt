package com.example.pokedex.util

import java.util.Locale

class Utils {
    companion object {
        fun transformName(name: String): String {
            return name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
            }
        }
    }
}