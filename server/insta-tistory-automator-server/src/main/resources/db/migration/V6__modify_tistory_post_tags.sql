alter table tistory_post
    drop column tags;

alter table tistory_post
    add column tags varchar(512);

comment on column tistory_post.tags is '태그 리스트';
