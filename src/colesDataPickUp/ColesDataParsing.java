/**
 * 
 */
package colesDataPickUp;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dbConnection.DbConn;
import webConnection.WebConn;


/**
 * Main class for parsing Coles HTML elements
 * process HTML elements to entity format, call DbConn to store Coles entity
 * @author Xi
 *
 */
public class ColesDataParsing {

	private ArrayList<String> categoryList;
	private ArrayList<ColesProduct> colesProducts;
	private WebConn webConn;
	private String baseUrl;
	private DbConn dbConn;

	/**
	 * Constructor 
	 */
	public ColesDataParsing(String baseUrl) {
		// TODO Auto-generated constructor stub
		this.baseUrl = baseUrl;
		this.webConn = new WebConn();
		this.categoryList = new ArrayList<String>();
		this.colesProducts = new ArrayList<ColesProduct>();
		this.dbConn = new DbConn();
	}

	/**
	 * Get different categories from HTML elements
	 * @return this.categoryList
	 */
	private ArrayList<String> getCategory() {
		webConn.getHTML(baseUrl);
		Elements categories = this.webConn.getHTML(baseUrl).getElementsByAttributeValue("title", "Browse all items");
		for (Element category : categories) {
			this.categoryList.add(category.attr("href"));
		}
		return this.categoryList;
	}

	/**
	 * Main logic, extract data from website, convert HTML elements to string format
	 * Store string format into CoelsEntity
	 */
	private void extractData(){
		getCategory();

		//Extract product's HTML element from each catetory
		for (String cateUrl : this.categoryList){
			
			System.out.println("Extracting data from : "+cateUrl);
			
			// Store all products and prices in DOM format
			ArrayList<Elements> allProducts = new ArrayList<Elements>();
			ArrayList<Elements> allPrices = new ArrayList<Elements>();

			// Add current page's data
			ArrayList<Document> productHTMLs = new ArrayList<Document>();

			//Get product HTML elements by using htmlunit
			productHTMLs = this.webConn.getHTMLUnit(cateUrl);

			// Store product and price for each page
			Elements productsPerPage;
			Elements pricesPerPage;
			for (Document productHTMLPerPage : productHTMLs){

				// Extracting data entry and price per page
				productsPerPage = productHTMLPerPage.getElementsByAttributeValueContaining("data-catentry", "catEntry_");
				pricesPerPage = productHTMLPerPage.getElementsByClass("purchasing");

				// Add current page's data
				allProducts.add(productsPerPage);
				allPrices.add(pricesPerPage);
			}

			// Processing extracted HTML elements to string format, save into Coles entity
			// Insert into database
			converToEntityFormat(allProducts, allPrices);

			// Empty the list
			this.categoryList.clear();
		}
	}

	/**
	 * Processing extracted HTML elements to string format, save into Coles entity
	 * Insert into database
	 * @param allProducts
	 * @param allPrices
	 */
	private void converToEntityFormat(ArrayList<Elements> allProducts,
			ArrayList<Elements> allPrices) {
		ColesProduct cp;

		for (int i=0; i < allProducts.size();i++){
			for (int j=0; j < allProducts.get(i).size(); j++){
				cp = new ColesProduct();
				try {
					JSONObject cpJObject = new JSONObject(allProducts.get(i).get(j).attr("data-refresh"));
					cp.setCatEntryId(cpJObject.getString("catEntryId"));
					cp.setItemId(cpJObject.getString("itemId"));
					cp.setName(cpJObject.getString("catEntryName"));
					cp.setPartNumber(cpJObject.getString("partNumber"));
					cp.setProductId(cpJObject.getString("productId"));

					// Some of products has special offers, like buy 2 for $3, need to extract unit price
					if (allPrices.get(i).get(j).getElementsByClass("price").size()!=0)
						cp.setPrice(allPrices.get(i).get(j).getElementsByClass("price").first().text());
					else
						cp.setPrice(allPrices.get(i).get(j).getElementsByClass("std-price").first().text());
					this.colesProducts.add(cp);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Insert Coles entity to DB
		for (ColesProduct cpTemp : this.colesProducts){
			this.dbConn.insertCp(cpTemp);
		}
	}

	/**
	 * Encapsulate private methods, expose to user
	 */
	public void ColesDataPickUp(){
		extractData();
	}

}
