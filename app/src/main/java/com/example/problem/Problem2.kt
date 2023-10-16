package com.example.problem

import org.junit.Test
import junit.framework.TestCase.assertEquals


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
        }
    }
}