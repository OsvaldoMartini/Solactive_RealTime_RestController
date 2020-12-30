package com.solactive;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.solactive.pojos.StatisticsDTO;
import com.solactive.pojos.TicksDTO;

/**
 * 
 */
@Service
public class ExecutorMultiRequests {

	/*
	 * variables Maps 
	 * mainMappedTick: ConcurrentHashMap to maintain the Main Ticks 
	 * grpByInstrument: ConcurrentHashMap to maintain the grouped Instruments
	 */
	public static Map<String, TicksDTO> mainMappedTick = new ConcurrentHashMap<>();
	public static Map<String, List<TicksDTO>> grpByInstrument = new ConcurrentHashMap<>();
	public static StatisticsDTO statisticsDTO = new StatisticsDTO();
	public static StatisticsContainer statisticsContainer = new StatisticsContainer();

	public ExecutorMultiRequests() {
	}

	@PostConstruct
	public void startServer() throws IOException {
		StatisticsUpdater priceUpdater = new StatisticsUpdater(mainMappedTick, grpByInstrument, statisticsContainer);
		priceUpdater.start();
	}

	
	/*
	 * Rules:
	 * Updates every last 1 second the Statistics
	 * The Instruments oldest than 60 Seconds are removed from the MainMap
	 * "mainMappedTick" ConcurrentHashMap maintain all the latest ticks
	 * "grpByInstrument" ConcurrentHashMap group by Instrument all the latest ticks
	 */
	public static class StatisticsUpdater extends Thread {
		private Map<String, TicksDTO> mappedTick;
		private Map<String, List<TicksDTO>> grpByInstrument;
		private StatisticsContainer statisticsContainer;

		public StatisticsUpdater(Map<String, TicksDTO> mappedTick, Map<String, List<TicksDTO>> grpByInstrument,
				StatisticsContainer statisticsContainer) {
			this.mappedTick = mappedTick;
			this.grpByInstrument = grpByInstrument;
			this.statisticsContainer = statisticsContainer;
		}

		@Override
		public void run() {
			while (true) {
				statisticsContainer.getLockObject().lock();

				try {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}

					final long rule60SecondsAgo = Instant.now().minus(Duration.ofSeconds(60)).toEpochMilli();

					grpByInstrument.clear();

					mappedTick.entrySet().removeIf(entry -> entry.getValue().getTimeStamp() < rule60SecondsAgo);

					grpByInstrument.putAll(
							mappedTick.values().stream().collect(Collectors.groupingBy(p -> p.getInstrument())));

					if (mappedTick.size() > 0) {
						executeCalculus(mappedTick, statisticsContainer);
					}else {
						statisticsContainer.setAvg(0);
						statisticsContainer.setMax(0);
						statisticsContainer.setMin(0);
						statisticsContainer.setCount(0);
					}

				} finally {
					statisticsContainer.getLockObject().unlock();
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}
		}
		
		
		private void executeCalculus(Map<String, TicksDTO> mappedTick, StatisticsContainer statisticsContainer) {
			if (mappedTick.size() > 0) {
				// Average amount of all tick prices in the last 60 seconds
				Double averagePrice = mappedTick.values().stream()
						.collect(Collectors.averagingDouble(p -> p.getPrice()));

				// lowest tick price in the last 60 seconds
				TicksDTO minPrice = mappedTick.values().stream()
						.collect(Collectors.minBy(Comparator.comparingDouble(TicksDTO::getPrice))).get();

				// highest tick price in the last 60 seconds
				TicksDTO maxPrice = mappedTick.values().stream()
						.collect(Collectors.maxBy(Comparator.comparingDouble(TicksDTO::getPrice))).get();


				statisticsContainer.setAvg(averagePrice);
				statisticsContainer.setMax(maxPrice.getPrice());
				statisticsContainer.setMin(minPrice.getPrice());
				statisticsContainer.setCount(mappedTick.values().size());
			}
		}

		final Comparator<TicksDTO> comparatorPrice = (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice());

	}

	public static class StatisticsContainer {
		private Lock lockObject = new ReentrantLock();

		private double avg;
		private double max;
		private double min;
		private long count;

		public Lock getLockObject() {
			return lockObject;
		}

		public double getAvg() {
			return avg;
		}

		public void setAvg(double avg) {
			this.avg = avg;
		}

		public double getMax() {
			return max;
		}

		void setMax(double max) {
			this.max = max;
		}

		public double getMin() {
			return min;
		}

		void setMin(double min) {
			this.min = min;
		}

		public long getCount() {
			return count;
		}

		void setCount(long count) {
			this.count = count;
		}

	}
}
