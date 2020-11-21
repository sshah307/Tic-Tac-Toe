import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RPLSTest {
	
	static RPLS mygame;
	static Client p1;
	
	
	@BeforeAll
	static void init() {
		
		mygame = new RPLS();
		
	}
	
	@Test
	void test() { 
		
		assertEquals("RPLS", mygame.getClass().getName(), "Error in initialization"); //constructor test
	}
	
	@Test
	//client constructor test
	void test2() {
		
		p1 = new Client(d->{}, 5555, "127.0.1.10");
		assertNotNull(p1, "client constructor failed");		
	}
	
	@Test
	//server connection test
	void test3() {
		
		String ip = p1.server_address_;
		assertEquals(ip, "127.0.1.10", "server connection failed");		
	}
	
	@Test
	//port connection test
	void test4() {
		
		int port= p1.client_port;
		assertEquals(port, 5555, "port connection failed");		
	}
	
	@Test
	//get connection test
	void test5() {
		
		p1.setConnected(true);
		boolean s = p1.getConnected();
		assertEquals(s, true, "get Connection failed");		
	}
	
	@Test
	//set connection test
	void test6() {
		
		p1.setConnected(true);
		assertEquals(p1.getConnected(), true, "set Connection failed");
	}
	
}
