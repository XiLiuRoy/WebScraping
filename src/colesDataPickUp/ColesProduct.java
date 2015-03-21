/**
 * 
 */
package colesDataPickUp;

/**
 * Coles Prodcut entity, encapsulate required fields
 * @author Xi
 * 
 */
public class ColesProduct {

	private String productId;
	private String itemId;
	private String partNumber;
	private String name;
	private String catEntryId;
	private String price;

	/**
	 * 
	 */
	public ColesProduct() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the catEntryId
	 */
	public String getCatEntryId() {
		return catEntryId;
	}

	/**
	 * @param catEntryId the catEntryId to set
	 */
	public void setCatEntryId(String catEntryId) {
		this.catEntryId = catEntryId;
	}
	
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	
}
