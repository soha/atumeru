-- For H2 Database
create table events (
  id bigserial not null primary key,
  title varchar(512) not null,
  start_date_time timestamp,
  end_date_time timestamp,
  created_at timestamp not null,
  updated_at timestamp not null
)
