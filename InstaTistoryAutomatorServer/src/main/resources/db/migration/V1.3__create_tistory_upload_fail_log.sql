create table tistory_upload_fail_log
(
    log_id          serial
        constraint tistory_upload_fail_log_pk
            primary key,
    tistory_id      integer,
    reason          text,
    retry_timestamp timestamp
);

comment on table tistory_upload_fail_log is 'tistory 업로드 실패 기록';

comment on column tistory_upload_fail_log.reason is '실패한 이유';

comment on column tistory_upload_fail_log.retry_timestamp is '재시도 시간';
