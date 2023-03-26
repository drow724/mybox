# mybox

## ERD

### 논리 모델
![스크린샷 2023-03-26 오후 3 37 21](https://user-images.githubusercontent.com/76800974/227759932-5b904681-d9a8-4b78-849b-27c36430d5bc.png)

### 물리 모델
![스크린샷 2023-03-26 오후 3 37 48](https://user-images.githubusercontent.com/76800974/227759941-de9a8f2d-57c2-426a-906a-abda3782c3e5.png)

## DB Schema

### users
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

### folders
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

### files
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

## nGrinder

처음부터 cache를 염두해 두고 코드를 작성하였습니다.
다만 직접적인 성능향상을 확인해 보기 위해 cache를 사용하기 전 코드와 사용 후 코드를 비교하여 성능 측정을 진행해 보았습니다.

### folder 검색(cache 사용 X)
```java
public Flux<Folder> ls(String parentId, String username) {
	return folderRepository.findByparentIdAndUsername(parentId, username).map(f -> f.toDomain());
}
```

### file 검색(cache 사용 X)
```java
public Flux<File> findByParentId(String parentId, String username) {
	return fileRepository.findByParentIdAndUsername(parentId, username).map(FileEntity::toDomain);
}
```

### 성능 테스트

![스크린샷 2023-03-26 오후 7 50 51](https://user-images.githubusercontent.com/76800974/227770978-c50ac323-e01a-468d-a4e7-41c43cd8d3b2.png)

### folder 검색(cache 사용)
```java
public Flux<Folder> ls(String parentId, String username) {
	return template.opsForSet().members("folder" + parentId + username)
			.switchIfEmpty(folderRepository.findByparentIdAndUsername(parentId, username).map(f -> f.toDomain())
					.flatMap(file -> template.opsForSet()
							.add("folder" + file.getParentId() + file.getUsername(), file).thenReturn(file)));
}
```

### file 검색(cache 사용)
```java
public Flux<File> findByParentId(String parentId, String username) {
	return fileTemplate.opsForSet().members("file" + parentId + username)
			.switchIfEmpty(fileRepository.findByParentIdAndUsername(parentId, username).map(FileEntity::toDomain)
					.flatMap(file -> fileTemplate.opsForSet().add("file" + file.getParentId() + file.getUsername(), file)
							.thenReturn(file)));
}
```

### 성능 테스트

![스크린샷 2023-03-26 오후 7 51 03](https://user-images.githubusercontent.com/76800974/227771053-97ac5207-bdb0-40b1-8821-efe9d437b9c3.png)

수치 상으로 향상된것을 확인해 볼 수 있었습니다.
