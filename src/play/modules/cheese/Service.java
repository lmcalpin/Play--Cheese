package play.modules.cheese;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import play.Play;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.XPath;
import play.modules.cheese.util.XPathUtil;

public class Service {
    private String user;
    private String password;
    private String productCode;

    public static String ROOT = "https://cheddargetter.com/xml";

    public Service(String productCode) {
        this.user = (String) Play.configuration.get("cg.user");
        this.password = (String) Play.configuration.get("cg.password");
        this.productCode = productCode;
    }

    public Service(String user, String password, String productCode) {
        this.user = user;
        this.password = password;
        this.productCode = productCode;
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
        return new Customer(this, resp.getXml().getFirstChild());
    }

    public void deleteCustomer(String custCode) {
        get("/customers/delete/productCode/" + productCode + "/code/" + custCode);
    }

    public HttpResponse get(String api) {
        HttpResponse resp = WS.url(ROOT + api).authenticate(user, password).get();
        CheddarGetterException.validate(resp);
        return resp;
    }

    public Customer getCustomer(String custCode) {
        HttpResponse resp = get("/customers/get/productCode/" + productCode + "/code/" + custCode);
        Customer customer = new Customer(this, XPath.selectNode("/customers/customer", resp.getXml().getFirstChild()));
        return customer;
    }

    public List<Customer> getCustomers() {
        HttpResponse resp = get("/customers/get/productCode/" + productCode);
        Node root = resp.getXml().getFirstChild();
        List<Customer> customers = XPathUtil.selectList("customer", root, this, Customer.class);
        return customers;
    }

    public String getPassword() {
        return password;
    }

    public Plan getPricingPlan(String planCode) {
        HttpResponse resp = get("/plans/get/productCode/" + productCode + "/code/" + planCode);
        Plan plan = new Plan(this, XPath.selectNode("/plans/plan", resp.getXml().getFirstChild()));
        return plan;
    }

    public List<Plan> getPricingPlans() {
        HttpResponse resp = get("/plans/get/productCode/" + productCode);
        List<Plan> plans = XPathUtil.selectList("/plans/plan", resp.getXml().getFirstChild(), this, Plan.class);
        return plans;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getUser() {
        return user;
    }

    public HttpResponse post(String api, Map<String, Object> params) {
        HttpResponse resp = WS.url(ROOT + api).params(params).authenticate(user, password).post();
        CheddarGetterException.validate(resp);
        return resp;
    }
}
