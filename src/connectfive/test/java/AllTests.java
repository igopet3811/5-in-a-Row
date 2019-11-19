package connectfive.test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
	ConnectFiveTest.class,
	PlayerTest.class,
	SocketTest.class
})

public class AllTests {
	
}