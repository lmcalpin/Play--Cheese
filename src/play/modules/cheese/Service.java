package play.modules.cheese;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
        HttpResponse resp = get("/customers/get/productCode/" + productCode + "/code/" + custCode);
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

    public Customer addCustomer(String custCode, String firstName, String lastName, String email, String plan) {
        return addCustomer(custCode, firstName, lastName, email, plan, null);
    }

    public Customer addCustomer(String custCode, String firstName, String lastName, String email, String plan, CreditCard card) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", custCode);
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("email", email);
        params.put("subscription[planCode]", plan);
        if (card != null) {
            params.put("subscription[ccFirstName]", card.getFirstName());
            params.put("subscription[ccLastName]", card.getLastName());
            params.put("subscription[ccNumber]", card.getNumber());
            params.put("subscription[ccExpiration]", card.getExpireMonth() + "/" + card.getExpireYear());
            if (card.getCode() != null)
                params.put("subscription[ccCardCode]", card.getCode());
            if (card.getZip() != null)
                params.put("subscription[ccZip]", card.getZip());
        }
        HttpResponse resp = post("/customers/new/productCode/" + productCode, params);
        return parseCustomer(resp);
    }

    public List<Plan> getPricingPlans() {
        HttpResponse resp = get("/plans/get/productCode/" + productCode);
        List<Node> planNodes = XPath.selectNodes("/plans/plan", resp.getXml().getFirstChild());
        List<Plan> plans = new ArrayList<Plan>();
        for (Node planNode : planNodes) {
            Plan plan = new Plan(this, planNode);
            plans.add(plan);
        }
        return plans;
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

    public HttpResponse get(String api) {
        HttpResponse resp = WS.url(ROOT + api).authenticate(user, password).get();
        CheddarGetterException.validate(resp);
        return resp;
    }

    public HttpResponse post(String api, Map<String, Object> params) {
        HttpResponse resp = WS.url(ROOT + api).params(params).authenticate(user, password).post();
        CheddarGetterException.validate(resp);
        return resp;
    }
}
