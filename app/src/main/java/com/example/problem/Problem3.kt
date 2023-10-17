package com.example.problem

import org.junit.Test
import junit.framework.TestCase.assertEquals

class Problem3 {
        @Test
        fun test1(){
            val number=13
            val result=4
            assertEquals(result,SolvePromble3().countClap(number))
        }
        @Test
        fun test2(){
            val number=33
            val result=14
            assertEquals(result,SolvePromble3().countClap(number))
        }
    //369 가독성 수정하기
    inner class SolvePromble3{
        fun countClap(number:Int):Int{
            var result:Int=0
            var inputNum:Int=number
            for(clap in 1..inputNum){
                //0이 아니라 1부터 시작하는 이유는 나누기를 할 때 0이 되지 않게 하기 위해서
                var nowNumber:Int=clap
                while(nowNumber!=0) {
                    if (nowNumber % 10 == 3 || nowNumber % 10 == 6 || nowNumber % 10 == 9)
                        result++
                    nowNumber/=10
                }
            }
            return result
        }
    }
}