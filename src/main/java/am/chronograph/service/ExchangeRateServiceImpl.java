package am.chronograph.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
class ExchangeRateServiceImpl implements ExchangeRateService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
	
	@Override
	@Cacheable("rates")
	public Double getRate(String from, String to) {
		LOGGER.info("Loading rates for {} {}", from, to);
		try {
			URL url = new URL("http://quote.yahoo.com/d/quotes.csv?f=l1&s=" + from + to + "=X");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = reader.readLine();
			reader.close();
			if (line.length() > 0) {
				return Double.parseDouble(line);
			}
		} catch (IOException | NumberFormatException ex) {
			LOGGER.error("Error while loadng currency exchange rates. {}", ex);
		}

		return 1.0;
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.service.ExchangeRateService#evictRates()
	 */
	@Override
	@CacheEvict(cacheNames="rates", allEntries = true)
	@Scheduled(initialDelay = 1000 * 3600 * 6, fixedDelay = 1000 * 3600 * 6)
	public void evictRates() {
		LOGGER.info("Evicting all the cache entries.");
	}
}	
