create type upload_status as enum('FETCHED', 'UPLOADED', 'FAILED');

comment on type upload_status is '업로드 상태: 업로드 준비, 업로드 됨, 업로드 실패';

alter table tistory_post
    drop column if exists upload;

alter table tistory_post
    add column upload_status upload_status;

alter table tistory_post
    add column category varchar(256);

comment on column tistory_post.upload_status is '티스토리 업로드 상태: 인스타 글 가져옴 / 티스토리에 업로드 됨 / 업로드 실패 (tistory_upload_fail_log -> tistory_id 검색)';
