package play.modules.cheese;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.XPath;

public class Customer {
    private String code;
    private String firstName;
    private String lastName;
    private String email;
    private List<Subscription> subscriptions = new ArrayList<Subscription>();
    private Service service;

    public Customer(Service service, Node node) {
        this.service = service;
        this.code = XPath.selectText("@code", node);
        this.firstName = XPath.selectText("firstName", node);
        this.lastName = XPath.selectText("lastName", node);
        this.email = XPath.selectText("email", node);
        List<Node> subscriptionNodes = XPath.selectNodes("subscriptions/subscription", node);
        for (Node subscriptionNode : subscriptionNodes) {
            this.subscriptions.add(new Subscription(subscriptionNode));
        }
    }

    public void subscribe(String plan, String ccNumber, String expiration) {
        String[] expirationSplit = expiration.split("/");
        int expireMo = Integer.parseInt(expirationSplit[0]);
        int expireYear = Integer.parseInt(expirationSplit[1]);
        subscribe(plan, new CreditCard(firstName, lastName, ccNumber, expireMo, expireYear));
    }

    public void subscribe(String plan, String ccNumber, int expireMo, int expireYear) {
        subscribe(plan, new CreditCard(firstName, lastName, ccNumber, expireMo, expireYear));
    }

    public void subscribe(String plan, CreditCard card) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("planCode", plan);
        params.put("ccFirstName", card.getFirstName());
        params.put("ccLastName", card.getLastName());
        params.put("ccNumber", card.getNumber());
        params.put("ccExpiration", card.getExpireMonth() + "/" + card.getExpireYear());
        if (card.getCode() != null)
            params.put("ccCardCode", card.getCode());
        if (card.getZip() != null)
            params.put("ccZip", card.getZip());
        service.post("/customers/edit-subscription/productCode/" + service.getProductCode() + "/code/" + code, params);
    }

    public void cancel() {
        service.get("/customers/cancel/productCode/" + service.getProductCode() + "/code/" + code); 
    }

    public String getCode() {
        return code;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public List<String> getPlans() {
        List<String> plans = new ArrayList<String>();
        for (Subscription sub : subscriptions) {
            if (sub.isCanceled())
                continue;
            plans.addAll(sub.getPlans());
        }
        return plans;
    }

    public boolean hasPlan(String plan) {
        return getPlans().contains(plan);
    }
}
