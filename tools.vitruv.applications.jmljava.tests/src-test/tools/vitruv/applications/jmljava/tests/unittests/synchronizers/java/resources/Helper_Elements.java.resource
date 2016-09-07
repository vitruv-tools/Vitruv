import static a.b.c.NewImport.abc;
import static a.b.c.NewImport.*;
import a.b.c.*;
import a.b.c.NewImport;

public class ExampleClass {

	public String testMethod(int firstParam, String secondParam) throws RuntimeException;
	
	public void testMethod() throws IOException;

	private int[] otherField;

}

public class AnyClass extends ExampleClass implements SomeInterface {

	public String testMethod(int firstParam, String secondParam) throws RuntimeException;
	
}

public class DummyElements {

	private boolean modelMethod (char c) {
		return true;
	}
	
	int modelField;

}