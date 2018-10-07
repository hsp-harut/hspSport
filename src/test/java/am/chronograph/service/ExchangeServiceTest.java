package am.chronograph.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import am.chronograph.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class ExchangeServiceTest extends BaseTest{
	
	@Autowired
	private ExchangeRateService exchangeService;
	
	@Test
	public void testGetRates(){
		Double rate = exchangeService.getRate("USD", "AMD");
		Assert.assertTrue(rate > 0.0);
		rate = exchangeService.getRate("USD", "AMD");
	}
	
	@Test
	public void testFailRates(){
		double rate = exchangeService.getRate("MOZAMBIK", "ESHUTYUN");
		Assert.assertEquals(1.0, rate, 0.1);
		rate = exchangeService.getRate("USD", "AMD");
	}
	
	@Test
	public void testEvictRates(){
		Double rate = exchangeService.getRate("USD", "AMD");
		Assert.assertTrue(rate > 0.0);
		rate = exchangeService.getRate("USD", "AMD");
		exchangeService.evictRates();
	}
}
