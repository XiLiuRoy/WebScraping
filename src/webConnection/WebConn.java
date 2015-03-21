package webConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Establish connection to the designated web, perform web related operations
 * @author Xi
 *
 */
public class WebConn {

	public WebConn() {
	}
	
	/**
	 * Used Jsoup for Woolis website
	 * @param url
	 * @return doc contains HTML elements
	 */
	public Document getHTML(String url){
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * Jsourp does not support ajax and javascript, have to change to use htmlUnit to pull data from Coles
	 * @param url
	 */
	public ArrayList<Document> getHTMLUnit(String url){
		
		// Configuration for htmlunit, so that it support Ajax calls
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
	    webClient.getOptions().setCssEnabled(false);
	    webClient.getOptions().setUseInsecureSSL(true);
	    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());

	    
	    ArrayList<Document> docs = new ArrayList<Document>();
	    Document doc = null;
		try {
			HtmlPage page = webClient.getPage(url);
			HtmlElement div;
			
			//Get products doc for all pages, currently 5 pages are used for test purpose
			//while (page.getFirstByXPath("//a[@title='Next page of results']")!=null){
			int i=0;
			while (i<5){
				i++;
				doc = Jsoup.parse(page.asXml());
				div = page.getFirstByXPath("//a[@title='Next page of results']");
			    page = div.click();
			    webClient.waitForBackgroundJavaScript(10000);
				docs.add(doc);
			}
		    
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return docs;

	}
	
	

}
