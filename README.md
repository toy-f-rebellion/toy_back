#토이프로젝트에서 제작한 api 모음 및 사용 방법에 대한 안내

##1. AuthController

###1-1. 회원가입 POST : /api/auth/signUp
request
{
  "email": "user3@email.com",
  "name": "유저3",
  "password": "user3password",
  "nickname": "user3",
  "phoneNum": "010-3333-3333",
  "gender": "남성",
  "age": 20,
  "agreement": true
}
response
{
    "result": true,
    "message": "SignUp success!",
    "data": null
}
회원가입 성공 여부만 message로 알려주며, 따로 반환하는 데이터는 없다.

###1-2. 로그인 POST : /api/auth/signIn
request
{
    "userEmail" : "user3@email.com",
    "userPassword" : "user3password"
}
response
{
    "result": true,
    "message": "Sign In Success",
    "data": {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyM0BlbWFpbC5jb20iLCJpYXQiOjE2OTM0MDMyMDQsImV4cCI6MTY5MzQwNjgwNH0.P4TPvbG5zJjYv3RJqu_EMGAg6cCFi8TU6KGZ3-F4jiGZSdeHqKLDzQ-qrIJv_VFJZ7pf7s7rBvKHQY3om-QbBA",
        "exprTime": 3600000,
        "user": {
            "id": 4,
            "email": "user3@email.com",
            "name": "유저3",
            "password": "",
            "nickname": "user3",
            "phoneNum": "010-3333-3333",
            "gender": "남성",
            "age": 20,
            "agreement": true
        }
    }
}
로그인이 되면 유저 객체에 대한 로그인 정보가 담긴 jwt 토큰이 생성됨
이 토큰은 1시간 후에 만료됨 (exprTime)
프론트에서는 다른 서비스에서 request 시 이 토큰을 헤더 정보에 포함시켜서 이후의 로직을 처리하면 된다.
기존에 있는 휴대폰 번호, 이메일이 이미 가입된 정보이면 가입이 불가하도록 구현해놨다. (이메일의 경우 중복 체크 단계에서 통과되지만, 휴대폰 번호는 중복 확인 과정이 없기 때문에 가입 시 이를 판단하게 된다.)

###1-3. 이메일 중복 체크 POST : /api/auth/emailCheck
request
{
    "check" : "use1@email.com"
}
response
{
    "result": false,
    "message": "Existed Email",
    "data": null
}

or 

{
    "result": true,
    "message": "can use this email",
    "data": null
}
true/false 여부로 가입 가능 여부를 판단 가능하다.

###1-4. 닉네임 중복 체크 POST : /api/auth/nicknameCheck
request
{
    "check" : "user1"
}
response
{
    "result": false,
    "message": "Existed nickname",
    "data": null
}

or 

{
    "result": true,
    "message": "can use this nickname",
    "data": null
}
true/false 여부로 닉네임 사용 가능 여부를 판단 가능하다.
중복 체크나 존재 여부 관련해서 해당 dto를 같이 쓰도록 구현했기 때문에 request 시 변수가 "check" 임에 유의하자.


##2. DiaryController

###2-1. 일기 작성 POST : /api/diary/create
request

header
    Key : Authorization
    Value : Bearer + 로그인 시 받은 Token
Body
   {
    "diaryDetail" : "오늘은 알고리즘 스터디를 했다. 너무 재밌었다.",
    "addDate" : "2023-08-27"
    }
response

{
    "result": true,
    "message": "Complete Create Diary",
    "data": "기쁨"
}
일기를 작성하면 일기 저장 로직이 구현되어 있는 DiaryService.createDiary 부분에서 감정 평가에 대한 FastApi를 호출 후 diaryDetail 내용에 대한 감정평가까지 이뤄질 예정.
diaryName 이라는 변수는 이메일 + 날짜를 조합한 고유 아이디 값을 생성하여 저장한다. (ex. user3@email.com 2023-08-30)
일기가 저장된 후 일기에 대한 감정 결과만 반환한다.

###2-2 날짜 별 일기 조회 GET : /api/diary/view
request
header
    Key : Authorization
    Value : Bearer + 로그인 시 받은 Token
http://localhost:8080/api/diary/view?addDate=2023-08-27
addDate=날짜 를 통해 해당 날짜의 일기를 조회한다.

body는 사용하지 않으며 url로 쿼리를 전송하는 Get 방식

response

{
    "result": true,
    "message": "Find Diary",
    "data": {
        "addDate": "2023-08-27",
        "diaryDetail": "오늘은 알고리즘 스터디를 했다. 너무 재밌었다.",
        "emotion": "기쁨"
    }
}
해당 날짜의 일기, 일기 내용, 감정이 반환된다.
못 찾으면 false 반환됨

###2-3. 일기 수정 PUT : /api/diary/edit
request

header
    Key : Authorization
    Value : Bearer + 로그인 시 받은 Token
Body
{
    "diaryDetail" : "사실 오늘은 늦잠자서 모각코 오전에 지각을 했다. 아차차 ㅎㅎㅎㅎ",
    "addDate" : "2023-01-01"
}
response

{
    "result": true,
    "message": "edit Successful",
    "data": "기쁨"
}
PUT 요청을 해야하는 것에 주의하기 !!
###2-4. 일기 삭제 DELETE : /api/diary/delete
request
header
    Key : Authorization
    Value : Bearer + 로그인 시 받은 Token
http://localhost:8080/api/diary/delete?addDate=2023-01-01
addDate=날짜 를 통해 해당 날짜의 일기를 조회한다.

body는 사용하지 않으며 url로 쿼리를 전송하는 방식

response

{
    "result": true,
    "message": "Delete Successful",
    "data": "2023-01-01"
}

or

{
    "result": false,
    "message": "user didn't write diary",
    "data": null
}
해당 날짜의 일기를 삭제하고 성공 여부를 반환한다.
