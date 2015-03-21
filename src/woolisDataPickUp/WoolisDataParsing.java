package woolisDataPickUp;

import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import webConnection.WebConn;
import dbConnection.DbConn;

/**
 * Extract HTML elements from web, process HTML elements to string format, call DbConn to store WoolProduct entity
 * @author Xi
 *
 */
public class WoolisDataParsing {
	private ArrayList<String> categoryList;
	private ArrayList<WoolProduct> woolProducts;
	private WebConn webConn;
	private String baseUrl;
	private DbConn dbConn;

	/**
	 * Constructor, initializes object
	 * @param baseUrl
	 */
	public WoolisDataParsing(String baseUrl) {
		// TODO Auto-generated constructor stub
		this.baseUrl = baseUrl;
		this.dbConn = new DbConn();
		this.webConn = new WebConn();
		this.categoryList = new ArrayList<String>();
		this.woolProducts = new ArrayList<WoolProduct>();
	}
	
	/**
	 * Get different categories from HTML elements
	 * @return this.categoryList
	 */
	private ArrayList<String> getCategory() {
		webConn.getHTML(baseUrl);
		Elements categories = this.webConn.getHTML(baseUrl).getElementsByClass("navigation-link");
		for (Element category : categories) {
			this.categoryList.add(category.attr("href"));
		}
		return this.categoryList;
	}

	/**
	 * Main logic
	 * Processing HTML elements from WoolWorth website to string format, extracting product's description, price, Url
	 * and Stockcode, store them to woolis entity
	 */
	private void extractData() {
		getCategory();

		// Exact data from each category
		for (String cateUrl : this.categoryList) {
			String category = cateUrl.split("/Shop/Browse/")[1];
			System.out.println("retriving data: " + cateUrl);
			Document productHTML = this.webConn.getHTML("http://www2.woolworthsonline.com.au"
					+ cateUrl);

			// Store product and price for each page
			Elements productsPerPage = productHTML
					.getElementsByClass("name-container");
			Elements pricesPerPage = productHTML
					.getElementsByClass("price-container");

			// Store all products and prices in DOM format
			ArrayList<Elements> allProducts = new ArrayList<Elements>();
			ArrayList<Elements> allPrices = new ArrayList<Elements>();

			// Current category's products displayed directly.
			if (productsPerPage.size() != 0) {
				getAllProducts(cateUrl, category, productHTML, productsPerPage,
						pricesPerPage, allProducts, allPrices);
			} else {

				// Get sub-categories
				productHTML.getElementsByAttributeValueContaining("href",
						cateUrl + "/");

				productsPerPage = productHTML
						.getElementsByClass("name-container");
				pricesPerPage = productHTML
						.getElementsByClass("price-container");

				// To see if current category is promotional category which does not
				// contain any products
				if (productsPerPage.size() != 0) {
					getAllProducts(cateUrl, category, productHTML, productsPerPage,
							pricesPerPage, allProducts, allPrices);
				}
			}
			
			for (WoolProduct wp : this.woolProducts) {
				this.dbConn.insertWp(wp);
			}
			
			//Empty the wool list, in case of duplicate insertion
			this.woolProducts.clear();
		}

	}

	/**
	 * private method to facilitate the extracting process, read every HTML element of each category, added to allProducts list.
	 * then process HTML element to string, store string to WoolWorth entity
	 * Insert into database
	 * @param cateUrl
	 * @param category
	 * @param productHTML
	 * @param productsPerPage
	 * @param pricesPerPage
	 * @param allProducts
	 * @param allPrices
	 */
	private void getAllProducts(String cateUrl, String category,
			Document productHTML, Elements productsPerPage,
			Elements pricesPerPage, ArrayList<Elements> allProducts,
			ArrayList<Elements> allPrices) {
		
		//Add the first page to list, due to the first page is already loaded
		allProducts.add(productsPerPage);
		allPrices.add(pricesPerPage);
		
		//Index flag of how many pages for current category, some categories only have one page
		int lastIndex = 0;
		if (productHTML.getElementsByClass("page-number").size() != 0) {
			lastIndex = Integer.valueOf(productHTML
					.getElementsByClass("page-number").last().child(0)
					.text());
		}

		// Extract product HTML elements from different pages
		for (int i = 2; i <= lastIndex; i++) {
			productHTML = this.webConn.getHTML("http://www2.woolworthsonline.com.au"
					+ cateUrl + "?page=" + i);

			productsPerPage = productHTML
					.getElementsByClass("name-container");
			pricesPerPage = productHTML
					.getElementsByClass("price-container");
			allProducts.add(productsPerPage);
			allPrices.add(pricesPerPage);
		}
		
		//Insert data to database
		converToEntityFormat(category, allProducts, allPrices);
	}

	/**
	 * Convert HTML element's data to string format, and store it in woolis entity.
	 * @param category
	 * @param allProducts
	 * @param allPrices
	 */
	private void converToEntityFormat(String category, ArrayList<Elements> allProducts,
			ArrayList<Elements> allPrices) {
		
		//Woolwoth product entity
		WoolProduct wp;
		
		//Set fields for woolwoth entity
		for (int i = 0; i < allProducts.size(); i++) {
			for (int j = 0; j < allProducts.get(i).size(); j++) {
				wp = new WoolProduct();
				wp.setStockCode(allProducts.get(i).get(j).child(0)
						.attr("href").replaceAll("[^0-9]", ""));
				
				//Process name, name is contained in Url
				String desc = allProducts.get(i).get(j).child(0)
						.attr("href");
				desc = desc.split("name=")[1];
				wp.setDesc(desc);
				wp.setProductUrl("http://www2.woolworthsonline.com.au"
						+allProducts.get(i).get(j).child(0).attr("href"));
				wp.setPrice(allPrices.get(i).get(j).child(1).text());
				wp.setCategory(category);
				this.woolProducts.add(wp);
			}
		}
	}
	
	/**
	 * Encapsulate private methods, expose to user
	 */
	public void WoolisDataPickUp(){
		extractData();
	}
	


}
