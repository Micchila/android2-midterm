package com.example.wealthtracker

object StudentIdentity {
    const val FULL_NAME = "Nikoloz"
    const val SURNAME = "Michilashvili"
    const val BIRTH_DATE = "24/01/2005"
    val BIRTH_DAY_OF_MONTH: Int
        get() = BIRTH_DATE.substringBefore("/").toIntOrNull() ?: 1

    private val vowels = setOf('a', 'e', 'i', 'o', 'u', 'ა', 'ე', 'ი', 'ო', 'უ')

    fun isSurnameStartsWithVowel(): Boolean {
        val first = SURNAME.trim().firstOrNull()?.lowercaseChar() ?: return false
        return first in vowels
    }
}