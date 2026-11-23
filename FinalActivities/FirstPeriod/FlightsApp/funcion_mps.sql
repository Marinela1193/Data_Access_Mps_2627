-- FUNCTION: public.fn_showflights_mps(character varying, character varying, date)

-- DROP FUNCTION IF EXISTS public.fn_showflights_mps(character varying, character varying, date);

CREATE OR REPLACE FUNCTION public.fn_showflights_mps(
	fn_origin character varying,
	fn_destination character varying,
	fn_date date)
    RETURNS TABLE(flight_code character varying, source_id integer, destination_id integer, departure_time time without time zone, arrival_time time without time zone, duration integer, airline_id integer, distance integer, plane_id integer, free_seats integer, travel_date date) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
BEGIN
    RETURN QUERY
    SELECT 
        f.flight_code::VARCHAR, 
        f.source, 
        f.destination, 
        f.departure, 
        f.arrival, 
        f.duration, 
        f.airlineid, 
        f.distance, 
        f.plane, 
        p.total_seats,
        COUNT(* )t.ticket_number
    FROM flights f
    INNER JOIN airports a_origin ON f.source = a_origin.code
    INNER JOIN airports a_destin ON f.destination = a_destin.code
    INNER JOIN planes p ON f.plane = p.plane_id
    LEFT JOIN tickets t ON f.flight_code = t.flight_code 
        AND t.date_of_travel = fn_date 
        AND t.date_of_cancellation IS NULL
    WHERE a_origin.iata = fn_origin
      AND a_destin.iata = fn_destination
    GROUP BY 
        f.flight_code, 
        f.source, 
        f.destination, 
        f.departure, 
        f.arrival, 
        f.duration, 
        f.airlineid, 
        f.distance, 
        f.plane, 
        p.total_seats;
END;
$BODY$;

ALTER FUNCTION public.fn_showflights_mps(character varying, character varying, date)
    OWNER TO postgres;

