package play.modules.cheese;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import play.Logger;
import play.Play;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.XPath;

public class Service {
	private String user;
	private String password;
	private String productCode;

	public static String ROOT = "https://cheddargetter.com/xml";

	public Service(String user, String password, String productCode) {
		this.user = user;
		this.password = password;
		this.productCode = productCode;
	}
	
	public Service(String productCode) {
		this.user = (String) Play.configuration.get("cg.user");
		this.password = (String) Play.configuration.get("cg.password");
		this.productCode = productCode;
	}

	public Customer getCustomer(String custCode) {
		HttpResponse resp = WS.url(
				ROOT + "/customers/get/productCode/" + productCode + "/code/"
						+ custCode).authenticate(user, password).get();
        CheddarGetterException.validate(resp);
		return parseCustomer(resp);
	}

	private Customer parseCustomer(HttpResponse resp) {
		Document doc = resp.getXml();
		Element root = doc.getDocumentElement();
		Node customer = XPath.selectNode("//customer", root);
		if (customer == null)
			return null;
		return new Customer(this, customer);
	}
	
	public Customer addCustomer(String custCode, String firstName, String lastName, String email) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code", custCode);
		params.put("firstName", firstName);
		params.put("lastName", lastName);
		params.put("email", email);
		params.put("subscription[planCode]", "FREE");
		HttpResponse resp = WS.url(ROOT + "/customers/new/productCode/" + productCode).params(params).authenticate(user, password).post();
        CheddarGetterException.validate(resp);
		return parseCustomer(resp);
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getProductCode() {
		return productCode;
	}
}
