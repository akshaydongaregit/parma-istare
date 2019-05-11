-- date : 11/05/2019
-- created in cg

--create table #tables_list (name varchar(100));
--create table #result (name varchar(100));

--declare @name varchar(100);
--declare @present int;

----replace \r : ') , \n : ('
--insert into #tables_list(name) values 
--('table-name') ;

--while exists ( select name from #tables_list )
--begin
--	select top 1 @name = name from #tables_list ; 
--	delete from #tables_list where name = @name;
	
--	--search

--	select @present = 0 ;

--	SELECT @present = 
--       CASE
--              WHEN cols.col like '%BU%'
--              THEN 1
--			  ELSE 0 
--       END
--	FROM (SELECT COLUMN_NAME
--			FROM INFORMATION_SCHEMA.COLUMNS
--			WHERE TABLE_NAME = @name ) cols (col)

--		if @present = 1 
--		begin 
--			insert into #result values (@name);
--		end

--end

--select * from #result;

--drop table #tables_list ;
--drop table #result;


create table #tables_list (name varchar(100));
create table #result (name varchar(100));

declare @name varchar(100);
declare @present int;

--replace \r : ') , \n : ('
insert into #tables_list(name) values 
('table-name') ;
				   
while exists ( select name from #tables_list )
begin
	select top 1 @name = name from #tables_list ; 
	delete from #tables_list where name = @name;
	
	--search

	select @present = 0 ;

	SELECT @present = 
       CASE when EXISTS ( select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS
							WHERE TABLE_NAME = @name and ( COLUMN_NAME in ('a') or column_name like '%b%' ) )
              THEN 1
			  ELSE 0 
       END

		if @present = 1 
		begin 
			insert into #result values (@name);
		end

end

select * from #result;

drop table #tables_list ;
drop table #result;
