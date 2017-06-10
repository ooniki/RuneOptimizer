package logger;

public class Logger {

	static public int level = 2;
	
	static public void print(int level, String message) {
		if (level <= Logger.level)
			System.out.println(message);
	}
	
	static public void error(int level, String message) {
		if (level <= Logger.level)
			System.err.println(message);
	}
	
}
