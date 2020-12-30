package com.solactive.controller;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solactive.ExecutorMultiRequests;
import com.solactive.pojos.TicksDTO;

@RestController
public class StatisticsController {

	private static DecimalFormat df2 = new DecimalFormat("#.##");

	@Autowired
	ExecutorMultiRequests executor;

	/*
	 * To Handle all requests for the paths: 
	 * 
	 * RULES: 
	 * "/statistics"
	 * 1) averagePrice for all ticks in the last 60 seconds 
	 * 1) lowest tick price in the last 60 seconds 
	 * 2) highest tick price in the last 60 seconds 
	 * 3) count total of ticks in the last 60 seconds
	 */
	@RequestMapping(path = "/statistics", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<?> getStatisticsAllInstruments() {
		if (ExecutorMultiRequests.statisticsContainer.getLockObject().tryLock()) {
			try {

				// I need to finish the Statistics calculation before send the response
				executor.statisticsDTO.setAvg(Double.parseDouble(df2.format(executor.statisticsContainer.getAvg())));
				executor.statisticsDTO.setMax(Double.parseDouble(df2.format(executor.statisticsContainer.getMax())));
				executor.statisticsDTO.setMin(Double.parseDouble(df2.format(executor.statisticsContainer.getMin())));
				executor.statisticsDTO.setCount(executor.statisticsContainer.getCount());

			} finally {
				executor.statisticsContainer.getLockObject().unlock();
			}
		}
		return new ResponseEntity<Object>(executor.statisticsDTO, HttpStatus.OK);

	}

	 /* 
	  * To Handle For Especific Instrument: 
	  * "/statistics/{instrument}"
	  * 1) averagePrice for a specific Instrument in the last 60 seconds 
	  * 1) lowest tick price for a specific Instrument in the last 60 seconds 
	  * 2) highest tick price for a specific Instrument in the last 60 seconds 
	  * 3) count total of ticks for a specific Instrument in the last 60 seconds
	  */
	@RequestMapping(value = { "/statistics/{instrument}" })
	public ResponseEntity<?> getStatisticsByInstrument(@PathVariable String instrument) {
		// If have an Instrument in the request
		// Calculates average, maxPrice, minPrice, count from the specific instrument
		if (instrument != null) {

			List<TicksDTO> pricesOfInstr = executor.grpByInstrument.get(instrument);

			if (pricesOfInstr != null) {

				Double averagePrice = pricesOfInstr.stream().collect(Collectors.averagingDouble(p -> p.getPrice()));

				// lowest tick price in the last 60 seconds
				TicksDTO minPrice = pricesOfInstr.stream()
						.collect(Collectors.minBy(Comparator.comparingDouble(TicksDTO::getPrice))).get();

				// highest tick price in the last 60 seconds
				TicksDTO maxPrice = pricesOfInstr.stream()
						.collect(Collectors.maxBy(Comparator.comparingDouble(TicksDTO::getPrice))).get();

				// DecimalFormat, default is RoundingMode.HALF_EVEN
				executor.statisticsDTO
						.setAvg(averagePrice != null ? Double.parseDouble(df2.format(averagePrice)) : 0.00);
				executor.statisticsDTO
						.setMax(maxPrice != null ? Double.parseDouble(df2.format(maxPrice.getPrice())) : 0.00);
				executor.statisticsDTO
						.setMin(minPrice != null ? Double.parseDouble(df2.format(minPrice.getPrice())) : 0.00);
				executor.statisticsDTO.setCount(pricesOfInstr.size());
			} else {
				executor.statisticsDTO.setAvg(0.00);
				executor.statisticsDTO.setMax(0.00);
				executor.statisticsDTO.setMin(0.00);
				executor.statisticsDTO.setCount(0);
			}
		}
		return new ResponseEntity<Object>(executor.statisticsDTO, HttpStatus.OK);
	}

}
