USE messages
GO

if (exists(select * from sys.views where name = 'used_lanes_check')) 
BEGIN
  DROP view dbo.used_lanes_check;
END

GO
create view used_lanes_check AS
  select 
	max(lane1) as lane1,
	max(lane2) as lane2,
	max(lane3) as lane3,
	max(lane4) as lane4,
	max(lane5) as lane5,
	max(lane6) as lane6,
	max(lane7) as lane7,
	max(lane8) as lane8,
	max(lane9) as lane9,
	max(lane10) as lane10,
	min(used_lanes) as used_lanes_min,
	max(used_lanes) as used_lanes_max,
	CONCAT(max(lane1), max(lane2), max(lane3), max(lane4), max(lane5), max(lane6), max(lane7), max(lane8), max(lane9), max(lane10)) as used_lanes_calculated,
    event, 
	heat
  from (select 
	event, 
	heat, 
    (CASE WHEN lane = 1 THEN '+' ELSE '-' END) as lane1 ,
    (CASE WHEN lane = 2 THEN '+' ELSE '-' END) as lane2 ,
    (CASE WHEN lane = 3 THEN '+' ELSE '-' END) as lane3 ,
    (CASE WHEN lane = 4 THEN '+' ELSE '-' END) as lane4 ,
    (CASE WHEN lane = 5 THEN '+' ELSE '-' END) as lane5 ,
    (CASE WHEN lane = 6 THEN '+' ELSE '-' END) as lane6 ,
    (CASE WHEN lane = 7 THEN '+' ELSE '-' END) as lane7 ,
    (CASE WHEN lane = 8 THEN '+' ELSE '-' END) as lane8 ,
    (CASE WHEN lane = 9 THEN '+' ELSE '-' END) as lane9 ,
    (CASE WHEN lane =10 THEN '+' ELSE '-' END) as lane10,
	REPLACE(used_lanes, ' ', '-') as used_lanes
	from ares_message
	where message_type not in ('ReadyToStart', 'OfficialEnd', 'PreviousRaceResults', 'UnknownValue7') and kind_of_time not in ('Start')
	) x group by event, heat;
GO
