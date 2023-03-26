# mybox

## Requirements
- Gradle
- Java 17

## Git branch strategy
- Github Flow

# nGrinder

처음부터 cache를 염두해 두고 코드를 작성하였습니다.
다만 직접적인 성능향상을 확인해 보기 위해 cache를 사용하기 전 코드와 사용 후 코드를 비교하여 성능 측정을 진행해 보았습니다.

## folder 검색(cache 사용 X)
```java
public Flux<Folder> ls(String parentId, String username) {
	return folderRepository.findByparentIdAndUsername(parentId, username).map(f -> f.toDomain());
}
```

## file 검색(cache 사용 X)
```java
public Flux<File> findByParentId(String parentId, String username) {
	return fileRepository.findByParentIdAndUsername(parentId, username).map(FileEntity::toDomain);
}
```

## 성능 테스트

![스크린샷 2023-03-26 오후 7 50 51](https://user-images.githubusercontent.com/76800974/227770978-c50ac323-e01a-468d-a4e7-41c43cd8d3b2.png)

## folder 검색(cache 사용)
```java
public Flux<Folder> ls(String parentId, String username) {
	return template.opsForSet().members("folder" + parentId + username)
			.switchIfEmpty(folderRepository.findByparentIdAndUsername(parentId, username).map(f -> f.toDomain())
					.flatMap(file -> template.opsForSet()
							.add("folder" + file.getParentId() + file.getUsername(), file).thenReturn(file)));
}
```

## file 검색(cache 사용)
```java
public Flux<File> findByParentId(String parentId, String username) {
	return fileTemplate.opsForSet().members("file" + parentId + username)
			.switchIfEmpty(fileRepository.findByParentIdAndUsername(parentId, username).map(FileEntity::toDomain)
					.flatMap(file -> fileTemplate.opsForSet().add("file" + file.getParentId() + file.getUsername(), file)
							.thenReturn(file)));
}
```

## 성능 테스트

![스크린샷 2023-03-26 오후 7 51 03](https://user-images.githubusercontent.com/76800974/227771053-97ac5207-bdb0-40b1-8821-efe9d437b9c3.png)

수치 상으로 향상된것을 확인해 볼 수 있었습니다.


# 기술적으로 고민한(고생한) 부분들 혹은 개선해본 내용

평소에 Web-flux에 관심이 많았고 지난 챌린지에서 톡톡한 학습 효과를 보았기 때문에, 이번 챌린지를 신청하게 되었습니다.
회사에 재직중이기에 다른 참여자들보다 쏟는 시간이 달라서 아쉽긴 하지만, web-flux로 맘 잡고 사이드 프로젝트를 진행해보자는 마인드로 시작했습니다.
설계 초기 단계에 zip다운로드를 고려하지 못하고, S3에 file을 folder 구분 없이 저장하여 후반에 설계 변경할 시간이 없어서 구현하지 못한게 아쉬었습니다.
그리고 시간이 좀 더 있었다면, folder 수정, file 수정 등등 좀 더 단단한 어플리케이션을 만들지 못한 것도 아쉽게 느껴졌습니다.

또한 최근에 hexagonal-architecture에 대하여 알게 되어, 그것도 적용해 보았는데 제대로 이해하여 구현한 것 같지 않아 아쉽습니다.

Swagger를 web-flux 스택에서 구현하는데 애를 먹었습니다. 결국 구현하지 못하여 결국 API 문서를 markdown으로 작성하였는데 굉장히 아쉽게 생각합니다.

배민 개발자분께서 refresh token은 access token이 만료되지 않은 시점에서 access token을 재발급 받으려 한다면, 만료시켜야 한다는 이야기를 들어서, 구현해보고 싶었는데 메인 도메인 기능을 구현하는데 시간을 쏟느라 구현해 보지 못하여 아쉽습니다.

# 아키텍쳐
![무제 drawio](https://user-images.githubusercontent.com/76800974/227771999-549234d5-5676-4e0a-864a-dcf4bc2c527f.png)

영속성은 MongoDB를 이용하고 Redis는 캐시 용도로 사용합니다.
파일 저장은 Amazon S3를 이용합니다.

# CI/CD

## CI
- url : http://146.56.38.5:8080/
  - id : guest
  - pw : guest123
  - project : timeDeal

## CD
github webhook

# API 목록

- 회원
    - 회원가입 
        - url : /user/join 
        - method : post
        request
        ```json
        {
            "username" : "drow724",
            "password" : "abcd1234",
	        "roles" : ["ROLE_USER"]
        }
        ```
        response
        ```json
        {
            "username": "drow724",
            "password": "password",
	        "enabled" : true,
            "roles": [
        	"ROLE_USER"
    	    ]
        }
        ```
    - 로그인
        - url : /user/login 
        - method : post
        request
        ```json
        {
            "username" : "drow724",
            "password" : "abcd1234"
        }
        ```
        response
        ```json
        {
    		"username": "drow724",
    		"password": "!!aa119562",
    		"tokenPresenter": {
        		"accessToken": "eyJhbGciOiJIUzI1NiJ9....",
        		"refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
    		},
    		"rank": "NORMAL",
   		 	"all": 31457280,
   		 	"current": 0
        }
        ```
    - 토큰 리프레쉬
        - url : /token/refresh 
        - method : post
        request
        ```json
		{
		    "refreshToken" : "eyJhbGciOiJIUzI1NiJ9..---"
		}
        ```
        response
        ```json
		{
		    "accessToken": "",
		    "refreshToken": ""
		}
        ```
- 폴더
    - 저장 
        - url : /folder 
        - method : post
        request
        ```json
        {
    		"name" : "ffff21ffff",
    		"username" : "drow724",
    		"parentId" : "root"
        }
        ```
        response
        ```json
        {
		    "id": "641fede6cf00fc67ca909ac6",
		    "name": "ffff21ffff",
		    "username": "drow724",
		    "parentId": "root"
        }
        ```
    - 삭제
        - url : /folder 
        - method : delete
        ```json
		{
		    "id": "641fe06059e6303dd0b86d11",
		    "name": "조직구조.xlsx",
		    "username": "drow724",
		    "parentId": "root",
		    "file": null
		}
        ```
    - 조회 (파일 포함)
        - url : /product/{path}
        - method : get
        response
        ```json
        [
		    {
			"id": "641fede4cf00fc67ca909ac5",
			"name": "조직구조.xlsx",
			"username": "drow724",
			"parentId": "root",
			"type": "file"
		    },
		    {
			"id": "641fede6cf00fc67ca909ac6",
			"name": "ffff21ffff",
			"username": "drow724",
			"parentId": "root",
			"type": "folder"
		    }
		]
        ```
- 파일
    - 저장
        - url : /file 
        - method : post
        request
        ```json
		{
		    "name" : "조직구조.xlsx",
		    "username" : "drow724",
		    "parentId" : "root",
		    "file" : []
        	}
        ```
        response
        ```json
		{
		    "id": "641fede4cf00fc67ca909ac5",
		    "name": "조직구조.xlsx",
		    "username": "drow724",
		    "parentId": "root",
		    "file": null
		}
        ```
    - 다운로드
        - url : /file
        - method : get

# ERD

## 논리 모델
![스크린샷 2023-03-26 오후 3 37 21](https://user-images.githubusercontent.com/76800974/227759932-5b904681-d9a8-4b78-849b-27c36430d5bc.png)

## 물리 모델
![스크린샷 2023-03-26 오후 3 37 48](https://user-images.githubusercontent.com/76800974/227759941-de9a8f2d-57c2-426a-906a-abda3782c3e5.png)

# DB Schema

## users
```json
{
  "fields": [
    {
      "name": "_id",
      "path": "_id",
      "count": 1,
      "types": [
        {
          "name": "ObjectId",
          "bsonType": "ObjectId",
          "path": "_id",
          "count": 1,
          "values": [
            "641fee0dcf00fc67ca909ac7"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "ObjectId",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "_class",
      "path": "_class",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "_class",
          "count": 1,
          "values": [
            "com.mybox.adpaters.persistance.entity.UserEntity"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "current",
      "path": "current",
      "count": 1,
      "types": [
        {
          "name": "Long",
          "bsonType": "Long",
          "path": "current",
          "count": 1,
          "values": [
            {
              "low": 0,
              "high": 0,
              "unsigned": false
            }
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "Long",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "enabled",
      "path": "enabled",
      "count": 1,
      "types": [
        {
          "name": "Boolean",
          "bsonType": "Boolean",
          "path": "enabled",
          "count": 1,
          "values": [
            true
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "Boolean",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "password",
      "path": "password",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "password",
          "count": 1,
          "values": [
            "!!aa119562"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "rank",
      "path": "rank",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "rank",
          "count": 1,
          "values": [
            "NORMAL"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "roles",
      "path": "roles",
      "count": 1,
      "types": [
        {
          "name": "Array",
          "bsonType": "Array",
          "path": "roles",
          "count": 1,
          "types": [
            {
              "name": "String",
              "bsonType": "String",
              "path": "roles",
              "count": 1,
              "values": [
                "ROLE_USER"
              ],
              "total_count": 0,
              "probability": 1,
              "unique": 1,
              "has_duplicates": false
            }
          ],
          "lengths": [
            1
          ],
          "total_count": 1,
          "probability": 1,
          "average_length": 1
        }
      ],
      "total_count": 1,
      "type": "Array",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "username",
      "path": "username",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "username",
          "count": 1,
          "values": [
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    }
  ],
  "count": 1
}
```

## folders
```json
{
  "fields": [
    {
      "name": "_id",
      "path": "_id",
      "count": 1,
      "types": [
        {
          "name": "ObjectId",
          "bsonType": "ObjectId",
          "path": "_id",
          "count": 1,
          "values": [
            "641fede6cf00fc67ca909ac6"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "ObjectId",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "_class",
      "path": "_class",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "_class",
          "count": 1,
          "values": [
            "com.mybox.adpaters.persistance.entity.FolderEntity"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "name",
      "path": "name",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "name",
          "count": 1,
          "values": [
            "ffff21ffff"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "parentId",
      "path": "parentId",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "parentId",
          "count": 1,
          "values": [
            "root"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "username",
      "path": "username",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "username",
          "count": 1,
          "values": [
            "drow724"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    }
  ],
  "count": 1
}
```

## files
```json
{
  "fields": [
    {
      "name": "_id",
      "path": "_id",
      "count": 1,
      "types": [
        {
          "name": "ObjectId",
          "bsonType": "ObjectId",
          "path": "_id",
          "count": 1,
          "values": [
            "641fede4cf00fc67ca909ac5"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "ObjectId",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "_class",
      "path": "_class",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "_class",
          "count": 1,
          "values": [
            "com.mybox.adpaters.persistance.entity.FileEntity"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "name",
      "path": "name",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "name",
          "count": 1,
          "values": [
            "조직구조.xlsx"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "parentId",
      "path": "parentId",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "parentId",
          "count": 1,
          "values": [
            "root"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    },
    {
      "name": "username",
      "path": "username",
      "count": 1,
      "types": [
        {
          "name": "String",
          "bsonType": "String",
          "path": "username",
          "count": 1,
          "values": [
            "drow724"
          ],
          "total_count": 0,
          "probability": 1,
          "unique": 1,
          "has_duplicates": false
        }
      ],
      "total_count": 1,
      "type": "String",
      "has_duplicates": false,
      "probability": 1
    }
  ],
  "count": 1
}
```
