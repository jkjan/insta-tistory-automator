alter table tistory_post
    add title varchar(256);

comment on column tistory_post.title is '제목';

alter table tistory_post
    add content text;

comment on column tistory_post.content is '내용(html)';

alter table tistory_post
    add tags varchar(256)[];

comment on column tistory_post.tags is '태그 리스트';

alter table tistory_post
    add uploaded boolean;

comment on column tistory_post.uploaded is '업로드 여부';