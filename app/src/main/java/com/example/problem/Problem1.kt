package com.example.problem

import android.util.Log
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.lang.Math.abs
import java.lang.Math.max
class Problem1Test {

    @Test
    fun case1() {
        val pobi = listOf(97, 98)
        val crong = listOf(197, 198)
        val result = 0
        val solution = Solution1(pobi, crong)
        val calculatedResult = solution.calculateScore(pobi,crong)
        assertEquals(result, calculatedResult)
    }

    @Test
    fun case2() {
        val pobi = listOf(131, 132)
        val crong = listOf(211, 212)
        val result = 1
        val solution = Solution1(pobi, crong)
        val calculatedResult = solution.calculateScore(pobi,crong)
        assertEquals(result, calculatedResult)
    }

    @Test
    fun case3() {
        val pobi = listOf(99, 102)
        val crong = listOf(211, 212)
        val result = -1
        val solution = Solution1(pobi, crong)
        val calculatedResult = solution.calculateScore(pobi,crong)
        assertEquals(result, calculatedResult)
    }
}
class Solution1(pobi: List<Int>, crong: List<Int>) {
    fun availableInput(page: List<Int>): Boolean {
        if (page.max() > 400 || page.min() > 400 || abs(page[0] - page[1]) != 1)
            return false
        if (page[0] % 2 != 1 || page[1] % 2 != 0)
            return false
        return true
    }

    fun plusScore(leftPage: Int): Int {
        var score: String = leftPage.toString()
        var calresult: Int = 0
        for (len in score){
            val digit=len.toString().toInt()
            calresult+=digit
        }
        return calresult
    }

    fun multipleScore(leftPage: Int): Int {
        var score: String = leftPage.toString()
        var calresult: Int = 1
        for (len in score){
            val digit=len.toString().toInt()
            calresult*=digit
        }
        return calresult
    }

    fun maxPlusScore(page: List<Int>): Int {
        return max(plusScore(page[0]), plusScore(page[1]))
    }

    fun maxMultipleScore(page: List<Int>): Int {
        return max(multipleScore(page[0]), multipleScore(page[1]))
    }
    fun resultScore(page:List<Int>):Int{
        return max(maxPlusScore(page),maxMultipleScore(page))
    }
    fun calculateScore(pobi: List<Int>,crong: List<Int>):Int{
        if(!availableInput(pobi)||!availableInput(crong))
            return -1
        if(resultScore(pobi)>resultScore(crong))
            return 1
        if(resultScore(pobi)<resultScore(crong))
            return 2
        else
            return 0
    }

}