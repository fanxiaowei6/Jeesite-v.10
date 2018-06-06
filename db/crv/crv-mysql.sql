CREATE TABLE computer(id VARCHAR(64),-- 编号
			comp_id INT PRIMARY KEY ,-- 电脑编号
			comp_name VARCHAR(10),-- 电脑名称
			comp_type VARCHAR(20),-- 类型
			comp_describe VARCHAR(20),-- 描述
			create_by VARCHAR(64),-- 创建者
			create_date TIMESTAMP,-- 创建时间
			update_by VARCHAR(64),-- 更新着
			update_date TIMESTAMP,-- 更新时间
			remarks NVARCHAR(64),-- 备注信息
			del_flag VARCHAR(1) -- 删除标记
			);
			
			
			
###需求分析表

create table demand( id varchar(64),-- 编号
			de_name varchar(20),-- 需求名称
			de_type varchar(20),-- 类型
			de_detail varchar(255),-- 需求明细
			create_by VARCHAR(64),-- 创建者
			create_date TIMESTAMP,-- 创建时间
			update_by VARCHAR(64),-- 更新着
			update_date TIMESTAMP,-- 更新时间
			remarks NVARCHAR(64),-- 备注信息
			del_flag VARCHAR(1) -- 删除标记
)