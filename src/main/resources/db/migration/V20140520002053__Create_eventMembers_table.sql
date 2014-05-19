-- For H2 Database
create table event_members (
  id bigserial not null primary key,
  event_id bigint not null,
  member_id bigint not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
