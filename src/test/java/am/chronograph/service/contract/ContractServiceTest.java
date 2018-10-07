package am.chronograph.service.contract;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import am.chronograph.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class ContractServiceTest extends BaseTest {
	
	@Autowired
	private ContractService contractService;	
	
	@Before
	public void init(){		
	}
				
	@Test
	public void testCreateUpdateProcess(){
		contractService.updateContractsStatus();
		Assert.assertTrue(true);
	}	
}