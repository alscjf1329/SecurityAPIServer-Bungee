# SecurityAPIServer-Bungee

## 개요
SecurityAPIServer-Bungee는 Minecraft Bungee 서버 플러그인으로, 웹 백엔드에서 Minecraft 유저 인증을 위한 기능을 제공합니다.

## 기능
### 인증 코드 관련 기능
- **인증 코드 발급 명령어**: 사용자에게 개인 인증 코드를 발급합니다.
- **인증 코드 확인 API**: 시스템에서 발급된 인증 코드의 유효성을 확인합니다.

## 적용 방법

### 플러그인 적용 방법
- Bungee 서버 내 `plugins` 디렉토리에 `SecurityAPIServer-Bungee.jar` 파일 존재해야 합니다. 
- SecurityAPIServer-Bukkit 플러그인이 함께 적용되어야 합니다.
  - [SecurityAPIServer-Bukkit 플러그인](https://github.com/alscjf1329/SecurityAPIServer-Bukkit)
- Bungee 서버 내 `plugins/libs` 디렉토리에 존재해야 하는 라이브러리: 
  - `json-20231013` 이상의 라이브러리
    - [JSON 라이브러리 - Maven Repository](https://mvnrepository.com/artifact/org.json/json)
  - `projectlombok` 1.18.30이상의 라이브러리
    - [Project Lombok 라이브러리 - Public Doc](https://projectlombok.org/download)

### `config.yml` 설정 파일
- 플러그인을 적용한 후 서버를 시작하면 `plugins/SecurityAPIServer-Bungee` 디렉토리에 `config.yml` 설정 파일이 생성됩니다.
```yaml
server:
  port: 15001     # 서버 포트
  log_flag: true  # 서버 정보 출력 여부
  path:           # 서버 api endpoints 설정 
    generateUserTokenPath: /authenticate/token
token:
  length: 6       # 인증코드 자리수
  max_authentication_attempt_count: 5   # 최대 인증 횟수
  expiration(sec): 180  # 인증 코드 유지 시간
```

## 적용 확인 테스트
### [Postman URL](https://www.postman.com/maintenance-astronaut-53396501/workspace/minecraft-api/request/25507989-76a5a2d5-e397-4798-ab16-e88623ecf65c?ctx=documentation)

- `/token` 커맨드 <br>
![인증코드 발급.png](./img/인증코드%20발급.png)<br>
위 사진처럼 인증코드가 발급됨

- 해당 `849305` 인증 코드 <br>
  POST `localhost:15001/authenticate/token` 요청 보낸 결과 <br>
![인증 성공.png](./img/인증%20성공.png)<br>
위 사진에서 다음을 확인할 수 있다:
  + isValid : 인증 결과
  + remainingAttempts : 남은 인증 횟수


## ERROR
- `config.yml` 파일 초기화를 원할 경우 파일을 삭제하고 Bungee 서버를 다시 시작합니다.