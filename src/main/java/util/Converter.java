package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Mohamed T. KASSAR
 */


public class Converter {

	public static Date localTimeToDate(LocalTime localTime) {
		return Date.from(localTime.atDate(LocalDate.of(2000, 1, 1)).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalTime dateToLocalTime(Date date) {
		return date.toInstant().atZone(ZoneId.of("GMT+01:00")).toLocalTime();
	}

	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.of("GMT+01:00")).toInstant());
	}

	public static LocalDateTime dateToLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.of("GMT+01:00")).toLocalDateTime();
	}
	
}
