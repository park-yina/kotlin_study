package com.example.problem

import org.junit.Test
import junit.framework.TestCase.assertEquals
import java.util.Stack


<<<<<<< HEAD
class Problem2{
 inner class Problem2Test{
     @Test
     fun case1(){
         val cryptogram="browoanoommnaon"
         val result=Problem2Solve().deleteDuplication(cryptogram)
         assertEquals(cryptogram,result)
     }
     fun case2(){
         val cryptogram="zyelleyz"
         val result=Problem2Solve().deleteDuplication(cryptogram)
         assertEquals(cryptogram,result)
     }
 }
    inner class Problem2Solve{
        fun deleteDuplication(cryptogram:String):String{
            var resultString= setOf<String>(cryptogram)
            if(resultString.isNullOrEmpty())
                resultString.plus("")
            return resultString.toString()
=======
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
        fun checkDuplication(cryptogram: String):String {
            var result=Stack<String>()
            for(index in 0 until cryptogram.length){
                if(result.empty())
                    result.push(cryptogram[0].toString())
                if(result.peek()!=cryptogram[index].toString()) {
                    result.push(cryptogram[index].toString())
                }
                else{
                    result.pop()
                }
            }
            return result.joinToString("")
>>>>>>> 302ab1d (2번 문제 새로 알게된 사항 정리)
        }
    }
}