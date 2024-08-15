create type media_type as enum('IMAGE', 'VIDEO', 'CAROUSEL_ALBUM');

create table insta_post
(
    insta_id          char(64) not null
        constraint insta_post_pk
            primary key,
    media_type        media_type,
    media_url         varchar(2083),
    permalink         varchar(2083),
    caption           text,
    timestamp         timestamp,
    fetched_timestamp timestamp
);

comment on table insta_post is 'instagram api에서 가져온 데이터';

comment on column insta_post.insta_id is 'instagram 게시물 고유 id';

comment on column insta_post.media_type is '미디어의 유형. IMAGE, VIDEO 또는 CAROUSEL_ALBUM일 수 있습니다.  ';

comment on column insta_post.media_url is '이미지 url (carousel_album일 경우 앞에 한 장만)';

comment on column insta_post.permalink is '인스타 게시글 url';

comment on column insta_post.caption is '게시글 본문, 해시태그 포함';

comment on column insta_post.timestamp is '게시 시간';

comment on column insta_post.fetched_timestamp is 'api를 통해 게시글을 가져온 시간';
