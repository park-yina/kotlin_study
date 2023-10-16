package com.example.problem

import org.junit.Test
import junit.framework.TestCase.assertEquals


class Problem2 {
    @Test
    fun case1() {
        val cryptogram = "browoanoommnaon"
        val result = Problem2Solve().deleteDuplication(cryptogram)
        assertEquals("brown", result)
    }

    @Test
    fun case2() {
        val cryptogram = "zyelleyz"
        val result = Problem2Solve().deleteDuplication(cryptogram)
        assertEquals("", result)
    }

    inner class Problem2Solve {
        fun deleteDuplication(cryptogram: String): String {
            var resultString=cryptogram.toSet()
            if(resultString.isNullOrEmpty())
                resultString.plus("")
            return resultString.toString()
        }
    }
}