package com.nestis.adidas.fliteTrakr.helper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.nestis.adidas.fliteTrakr.model.strategy.BelowPriceStrategy;
import com.nestis.adidas.fliteTrakr.model.strategy.CheapestStrategy;
import com.nestis.adidas.fliteTrakr.model.strategy.PriceStrategy;
import com.nestis.adidas.fliteTrakr.model.strategy.QuestionStrategy;
import com.nestis.adidas.fliteTrakr.model.strategy.StopsStrategy;

public class ASTQuestionHelperTest {
	
	@Test
	public void shouldGetAPriceStrategy() throws Exception {
		QuestionStrategy qStrategy = ASTQuestionHelper.getQuestionStrategy("What is the price of the connection NUE-FRA-LHR?");
		assertTrue(qStrategy instanceof PriceStrategy);
	}
	
	@Test
	public void shouldGetACheapestStrategy() throws Exception {
		QuestionStrategy qStrategy = ASTQuestionHelper.getQuestionStrategy("What is the cheapest connection from NUE to AMS?");
		assertTrue(qStrategy instanceof CheapestStrategy);		
	}
	
	@Test
	public void shouldGetABelowPriceStrategy() throws Exception {
		QuestionStrategy qStrategy = ASTQuestionHelper.getQuestionStrategy("Find all conenctions from NUE to LHR below 170Euros!");
		assertTrue(qStrategy instanceof BelowPriceStrategy);		
	}
	
	@Test
	public void shouldGetAStopsStrategy() throws Exception {
		QuestionStrategy qStrategy = ASTQuestionHelper.getQuestionStrategy("How many different connections with maximum 3 stops exists between NUE and FRA?");
		assertTrue(qStrategy instanceof StopsStrategy);		
	}
	
	@Test
	public void shouldGetAnException() {
		Executable exceptionExec = () -> ASTQuestionHelper.getQuestionStrategy("Exception");
		assertThrows(Exception.class, exceptionExec);
	}
}
