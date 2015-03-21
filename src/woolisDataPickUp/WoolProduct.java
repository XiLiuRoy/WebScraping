package woolisDataPickUp;

/**
 * Encapsulate required fields for Woolis product
 * @author Xi
 *
 */
public class WoolProduct {
	private String productUrl;
	private String stockCode;
	private String desc;
	private String price;
	private String category;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public WoolProduct() {
		// TODO Auto-generated constructor stub
	}
	
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

}
