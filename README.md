# spring-boot-batch

```
스케쥴 관리

1. 쿠버네티스 cronjob 기능
2. spring scheduler

위 기능 중 2번 기능 사용
```

# DDL
```
batch 관련 테이블 생성

-- user 테이블 생성 
create table "user" (
  user_id varchar(60) primary key,
  user_name varchar(60)
);

-- user for loop insert
begin;

do $$
begin
for i in 1..10 loop
RAISE NOTICE 'Iterator: %', i;
insert into "user" 
("user_id", "user_name") 
VALUES 
(i, '홍길동'||i);
end loop;
end;
$$;

select 
*
from 
"user" u ;

commit;
```


# batch 구현
```
1. tasklet 방식
2. chunk 방식
3. scheduling 비동기 처리
```

# chunk 지향 처리
<img width="80%" src="https://user-images.githubusercontent.com/104915851/167434908-b3519529-255f-4a98-bae9-41ccb2828bff.png"/>