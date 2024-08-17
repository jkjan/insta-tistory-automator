create table tistory_post

(
    tistory_id       serial
        constraint tistory_post_pk
            primary key,
    insta_id         char(63),
    upload_timestamp timestamp,
    tistory_url      varchar(2037)
);

comment on column tistory_post.insta_id is '인스타 api에서 가져온 id값';

comment on column tistory_post.upload_timestamp is 'tistory에 업로드 한 시간 (null일 경우 실패)';

comment on column tistory_post.tistory_url is '업로드 한 tistory 주소 (null일 경우 실패)';
