package woolisDataPickUp;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Initializing program...");
		WoolisDataParsing dp = new WoolisDataParsing("http://www2.woolworthsonline.com.au/");
		System.out.println("Starting data pick up for Woolis. 'Finished' will be displayed when programm finish.");
		dp.WoolisDataPickUp();
		System.out.println("Finished");
	}

}
