## 1.코틀린에서 문자 관련 함수 정리
<br>👉🏻[jetbrains 전문 바로가기](https://blog.jetbrains.com/ko/kotlin/2021/04/kotlin-1-5-0-rc-released/ "string관련 kotlin함수 정리")
<br>◼이번에 사용한 함수와 자주 사용 할 것 같은 함수
<br>    1.isUpperCase()/isLowerCase()
<br>    이름 그대로 대문자인지 판별할 때 사용한다->Boolean타입으로 반환

<br>
<br>    2.isWhitespace()
<br>    공백 여부를 판별할 때 사용한다->Boolean타입로 반환
<br>    🔸isBlank()와의 차이
<br>    처음에는 isBlank()를 사용하였더니 내 의도와는 공백처리가 다른 것을 테스트 케이스로 확인할 수 있었다.
        isBlank()는 비어있거나 공백문자로만 구성된 경우에만 true를 반환하기 때문

<br>👉🏻[isBlank()와 isEmpty()의 차이가 궁금하다면](https://dwenn.tistory.com/117 "blank와 empty의 차이")
