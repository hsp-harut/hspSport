package am.chronograph.service;

public interface ExchangeRateService {
	
	public enum Currency {
		AMD,
		BYR,
		GEL,
		USD,
		EUR,
		CHF,
		RUB,
		GBP
	}
	
	Double getRate(String from, String to);
	
	void evictRates();
}
