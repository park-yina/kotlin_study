#새로 알게된 사항 정리

## 1. 코틀린에서의 스택

-코틀린에서는 원래 지원하지 않음. 따라서 java의 스택을 import해서 쓰거나 ArrayList로 구현해야함. 
    -관련 링크 참조:https://kmight0518.tistory.com/78

-c++에서는 stack.top()을 사용했는데 코틀린에서는 관련 기능이 없나 당황했었음.
    -코틀린에서는 stack.peak()을 사용하여 top()의 값을 읽어온다.

## 2. to_string()과 joinToString()

-joinToString("원소간 들어갈 문자열"):컬렉션의 요소를 문자열로 결합
    -ex)Problem2의 return을 to_string()사용시 [b,r,o,w,n]이 출력됨
    -joinToString()을 사용하여야 원하는 return값인 "brown"반환 가능
