import org.junit.Test;

import com.huang.util.JettyUtil;



public class testJettyUtil {
	
	@Test
	public void testResource(){
		String key = "jetty.port";
		String value = JettyUtil.getValue(key);
		System.out.println(value);
	}
}
