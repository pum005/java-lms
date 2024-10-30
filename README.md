# 학습 관리 시스템(Learning Management System)
## 진행 방법
* 학습 관리 시스템의 수강신청 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 리팩토링 요구사항
- 메소드 분리
  - 하나의 메소드는 파라미터 최대 2개 까지 허용
- 클래스 분리
  - 일급 컬렉션 + 원시값 문자열 포장 + 3개 이상의 인스턴스 변수를 가진 클래스는 사용하지 않는다.
- 인터페이스 분리
  - 클래스의 핵심 logic 을 인터페이스로 분리 or 요구사항이 자주 바뀌는 부분을 인터페이스로 분리한다.

## 기능 목록
- [x] 질문을 삭제 할수 있는지 검증하기
  - 질문한 사람이 자신인 경우 삭제 가능
  - 답변이 존재 x
    - 삭제 가능
  - 답변이 존재 o
    - 모든 답변자가 자신인 경우 삭제 가능

- [x] 질문 삭제 하기
  - 데이터를 삭제하기 보단, 상태를 삭제 상태로 변경한다.
  - 질문과 답변을 모두 삭제 상태로 변경한다.

- [x] 질문 삭제 이력 남기기
  - 질문과 답변에 대한 삭제 정보를 저장한다.

- [x] 강의 도메인 요구사항
  - [x] 시작일과 종료일을 가진다.
  - [x] 강의 썸네일 이미지 정보를 가진다.
  - [x] 강의 타입은 무료 혹은 유료이다.
    - 무료인 경우
      - 수강 인원 제한이 없다.
    - 유료인 경우
      - 수강 인원 제한이 있다.
      - 결제 금액과 수강료가 일치할 때, 수강 신청이 가능하다.
  - [x] 강의 상태는 3가지 상태를 가진다.
    - 준비중, 모집중, 종료

- [x] 강의 썸네일 이미지 유효성 검증하기
  - [x] 이미지 사이즈는 1MB 이하 이어야 한다.
  - [x] 이미지 타입은 gif, jpg, jpeg, png, svg 만 허용한다.
  - [x] width는 300 픽셀 이상 이어야 한다.
  - [x] height는 200 픽셀 이상 이어야 한다.
  - [x] width, height 비율은 3:2 이어야 한다.

- [x] 수강 신청 하기
  - [x] 무료인 경우
    - 조건 없이 수강신청이 가능하다.
  - [x] 유료인 경우
    - [x] 수강 인원이 남아 있을때, 수강신청이 가능하다.
    - [x] 강의 상태가 모집중 일때, 수강신청이 가능하다.
    - [x] 결제 금액과 수강료가 일치할 때, 수강 신청이 가능하다.