package com.solactive.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.solactive.tests.RandomDateTimes;

/**
 * @author Crunchify.com
 * 
 */

public class InstrumentsGenerator {

	/**
	 * BaseInstrument: enum.
	 */
	private enum BaseInstrument {
		DISNEY_US("DISNEY.US"), EBAY_US("EBAY.US"), EXXON_US("EXXON.US"), FB_US("FB.US"), FERRARI_US("FERRARI.US"),
		FORD_US("FORD.US"), GM_US("GM.US"), GOOGLE_US("GOOGLE.US"), IBM_US("IBM.US"), INTEL_US("INTEL.US"),
		MSFT_US("MSFT.US"), NFLX_US("NFLX.US"), PEPSI_US("PEPSI.US"), SNAP_US("SNAP.US"), SBUX_US("SBUX.US"),
		TESLA_US("TESLA.US"), TWTR_US("TWTR.US"), WMT_US("WMT.US"), LMT_US("LMT.US"), APA_US("APA.US"), IBM_N("IBM.N");

		/**
		 * Pick a random value of the Instrument enum.
		 * 
		 * @return a random Log.
		 */
		public static BaseInstrument getRandomLog() {
			Random random = new Random();
			return values()[random.nextInt(values().length)];
		}

		private String instrument;

		private BaseInstrument(String instrument) {
			this.instrument = instrument;
		}

		@Override
		public String toString() {
			return instrument;
		}

	}

	public static void main(String[] args) {

		Instant hundredYearsAgo = Instant.now().minus(Duration.ofDays(100 * 365));
		Instant tenDaysAgo = Instant.now().minus(Duration.ofDays(10));

		System.out.println("hundredYearsAgo instant : " + hundredYearsAgo.getEpochSecond());
		System.out.println("tenDaysAgo instant : " + tenDaysAgo.getEpochSecond());

		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00
		// GMT represented by this Date object.
		long epochTime = hundredYearsAgo.getEpochSecond();

		int x = 20000;
		Instant startDate = Instant.now().minus(Duration.ofMinutes(2));
		Instant endDate = Instant.now().plus(Duration.ofMinutes(2));

		DecimalFormat df2 = new DecimalFormat("0.0");
		double min = 142.00;
		double max = 359.99;
		double rand = new Random().nextDouble();
		String result = df2.format(min + (rand * (max - min))) + "0";
		System.out.println(result);

		System.out.println("instrument,price,timestamp");
		while (x > 0) {
			rand = new Random().nextDouble();
			result = df2.format(min + (rand * (max - min))) + "0";

			Instant random = RandomDateTimes.between(startDate, endDate);
			System.out.println(BaseInstrument.getRandomLog().instrument + "," + result);// + "," + random.getEpochSecond());
			x--;
		}

		log("Current Time in Epoch: " + epochTime);

		RandomDate rd = new RandomDate(LocalDate.of(1900, 1, 1), LocalDate.of(2010, 1, 1));
		System.out.println(rd.nextDate());
		System.out.println(rd.nextDate()); // birthdays ad infinitum

		Date today = Calendar.getInstance().getTime();

		// Constructs a SimpleDateFormat using the given pattern
		SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");

		// format() formats a Date into a date/time string.
		String currentTime = crunchifyFormat.format(today);
		log("Current Time = " + currentTime);

		try {

			// parse() parses text from the beginning of the given string to produce a date.
			Date date = crunchifyFormat.parse(currentTime);

			// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00
			// GMT represented by this Date object.
			epochTime = date.getTime();

			log("Current Time in Epoch: " + epochTime);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Local ZoneID
		ZoneId defaultZoneId = ZoneId.systemDefault();
		log("defaultZoneId: " + defaultZoneId);

		Date date = new Date();

		// Default Zone: UTC+0
		Instant instant = date.toInstant();
		System.out.println("instant : " + instant);

		// Local TimeZone
		LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
		System.out.println("localDateTime : " + localDateTime);

	}

	// Simple logging
	private static void log(String string) {
		System.out.println(string);

	}

	public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
		long startEpochDay = startInclusive.toEpochDay();
		long endEpochDay = endExclusive.toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

		return LocalDate.ofEpochDay(randomDay);
	}

}

class RandomDate {
	private final LocalDate minDate;
	private final LocalDate maxDate;
	private final Random random;

	public RandomDate(LocalDate minDate, LocalDate maxDate) {
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.random = new Random();
	}

	public LocalDate nextDate() {
		int minDay = (int) minDate.toEpochDay();
		int maxDay = (int) maxDate.toEpochDay();
		long randomDay = minDay + random.nextInt(maxDay - minDay);
		return LocalDate.ofEpochDay(randomDay);
	}

	@Override
	public String toString() {
		return "RandomDate{" + "maxDate=" + maxDate + ", minDate=" + minDate + '}';
	}
}
