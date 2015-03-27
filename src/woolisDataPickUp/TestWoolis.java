package woolisDataPickUp;

public class TestWoolis {

	public TestWoolis() {
	}

	public static void main(String[] args) {
		System.out.println("Initializing program...");
		WoolisDataParsing dp = new WoolisDataParsing("http://www2.woolworthsonline.com.au/");
		System.out.println("Starting data pick up for Woolis. 'Finished' will be displayed when programm finish.");
		dp.WoolisDataPickUp();
		System.out.println("Finished");
	}

}
