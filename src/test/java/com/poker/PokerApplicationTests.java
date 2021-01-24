package com.poker;

import com.poker.controller.PokerRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PokerApplicationTests {

    @Autowired
    private PokerRestController controller;

	@Test
	void contextLoads() throws Exception {
        Assertions.assertNotNull(controller);
	}

}
