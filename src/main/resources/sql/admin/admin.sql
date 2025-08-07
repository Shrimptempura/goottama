-- 관리자
CREATE TABLE system_log (
	system_log_id	bigint	NOT NULL,
	system_log_level	varchar(100)	NULL,
	system_log_message	varchar(2000)	NULL,
	system_log_occurred_at	timestamp	NULL,
	system_log_stack_trace	varchar(2000)	NULL
);

-- 관리자
CREATE TABLE user_activity (
	user_activity_id	bigint	NOT NULL,
	user_id	bigint	NULL,
	user_activity_type	varchar(100)	NULL,
	user_activity_time	timestamp	NULL,
	user_activity_target	varchar(100)	NULL,
	user_activity_details	varchar(2000)	NULL
);

-- 관리자
CREATE TABLE notices (
	notices_id	int	NOT NULL,
	notices_title	varchar(500)	NULL,
	notices_is_pinned	boolean	NULL,
	notices_created_at	timestamp	NULL,
	notices_content	varchar(2000)	NULL
);

-- 관리자
CREATE TABLE sanctions (
	sanctions_id	int	NOT NULL,
	user_id	bigint	NULL,
	sanctions_types	varchar(100)	NULL,
	sanctions_start_date	timestamp	NULL,
	sanctions_end_date	timestamp	NULL,
	sanctions_reason	varchar(2000)	NULL,
	admin_account_id	int	NULL,
	sanctions_created_at	timestamp	NULL
);

-- 관리자
CREATE TABLE admin_actions (
	admin_actions_id	int	NOT NULL,
	admin_actions_type	varchar(100)	NULL,
	admin_actions_target	varchar(100)	NULL,
	admin_actions_time	timestamp	NULL,
	admin_actions_details	varchar(2000)	NULL
);