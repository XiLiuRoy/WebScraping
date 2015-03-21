/**
 * 
 */
package colesDataPickUp;

import webConnection.WebConn;

/**
 * @author Xi
 *
 */
public class TestColes {

	/**
	 * 
	 */
	public TestColes() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ColesDataParsing cdp = new ColesDataParsing("http://shop.coles.com.au/online/national");
		cdp.ColesDataPickUp();
	}

}
