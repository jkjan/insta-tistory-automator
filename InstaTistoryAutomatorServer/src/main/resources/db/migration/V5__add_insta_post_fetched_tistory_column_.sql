alter table insta_post
    add column tistory_fetched boolean;

comment on column insta_post.tistory_fetched is 'tistory_post 테이블에 존재함';
