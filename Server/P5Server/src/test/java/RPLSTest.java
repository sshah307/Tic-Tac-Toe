import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RPLSTest {
	
	
static RPLS mygame;
static Server srv;
	
	@BeforeAll
	static void init() {
		
		mygame = new RPLS();
		
	}
	
	
	@Test
	void test() { 
		
		assertEquals("RPLS", mygame.getClass().getName(), "Error in initialization"); //constructor test
		
	}
	
	@Test
	//server constructor test
	void test2() { 
		
        srv = new Server(d->{}, 5555);
        assertNotNull(srv, "server constructor failed");
	}
	
	@Test
	//port number test
	void test3() { 
		
		assertEquals( srv.port_number, 5555, "port number failed");
	}

	
}