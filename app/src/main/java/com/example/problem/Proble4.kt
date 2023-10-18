package com.example.problem

import org.junit.Test
import junit.framework.TestCase.assertEquals
import java.lang.Math.abs

class Problem4 {
    @Test
    fun case1() {
        val input: String = "I love you"
        val result: String = "R olev blf"
        assertEquals(result, Problem4Solve().convert(input))

    }

    @Test
    fun case2() {
        //제공되지 않은 테스트 케이스 외(예외 사항 관련 테스트)
        val input: String = "!"
        val result: String = ""
        assertEquals(result, Problem4Solve().convert(input))
    }

    @Test
    fun case3() {
        //공백은 변환 대상에 포함되므로 공백만 입력되어도 변환 잘되나 확인해보아야
        val input: String =" "
        val result: String =" "
        assertEquals(result, Problem4Solve().convert(input))
    }

    inner class Problem4Solve {
        //알파벳 여부를 판별하고 몇번째인지 구해서 변환해야하니까 아스키코드 활용하는 게 좋을듯
        var convertResult: String = ""
        var result: String = ""
        fun convert(word: String): String {
            for (index in 0 until word.length) {
                if (word[index].isUpperCase()) {
                    result += ('A' - (word[index] - 'Z')).toString()
                }
                if(word[index].isWhitespace())
                    result+=word[index].toString()
                if (word[index].isLowerCase()) {
                    result += ('a' - (word[index] - 'z')).toString()
                }
            }
            return result
        }
    }
}