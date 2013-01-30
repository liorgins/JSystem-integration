import jsystem.framework.TestProperties;

import org.junit.Test;
import junit.framework.SystemTestCase4;

public class foo2 extends SystemTestCase4{
	@Test
	@TestProperties(name = "Report Success", paramsInclude = { "" })
	public void reportSuccesses() {
		report.report("Success");
	}
}
