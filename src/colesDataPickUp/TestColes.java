/**
 * 
 */
package colesDataPickUp;


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
		System.out.println("Initializing program...");
		ColesDataParsing cdp = new ColesDataParsing("http://shop.coles.com.au/online/national");
		System.out.println("Starting data pick up for Coles. 'Finished' will be displayed when programm finish.");
		cdp.ColesDataPickUp();
		System.out.println("Finished");
	}

}
