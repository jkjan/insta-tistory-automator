alter table insta_post
    alter column insta_id type varchar(64) using insta_id::varchar(64);

alter table tistory_post
    alter column insta_id type varchar(64) using insta_id::varchar(64);

alter table tistory_post
    alter column tistory_url type varchar(2083) using tistory_url::varchar(2083);
