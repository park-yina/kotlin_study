package com.example.problem

import org.junit.Test
import junit.framework.TestCase.assertEquals
import java.util.Stack
class Problem2 {
    @Test
    fun case1() {
        var cryptogram = "browoanoommnaon"
        val result = Problem2Solve().checkDuplication(cryptogram)
        assertEquals("brown", result)
    }

    @Test
    fun case2() {
        var cryptogram = "zyelleyz"
        val result = Problem2Solve().checkDuplication(cryptogram)
        assertEquals("", result)
    }

    inner class Problem2Solve {
        fun checkDuplication(cryptogram: String): String {
            var result = Stack<String>()
            for (index in 0 until cryptogram.length) {
                if (result.empty())
                    result.push(cryptogram[0].toString())
                if (result.peek() != cryptogram[index].toString()) {
                    result.push(cryptogram[index].toString())
                } else {
                    result.pop()
                }
            }
            return result.joinToString("")
        }
    }
}
