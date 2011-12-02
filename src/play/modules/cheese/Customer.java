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
        List<Node> subscriptionNodes = XPath.selectNodes(
                "subscriptions/subscription", node);
        for (Node subscriptionNode : subscriptionNodes) {
            this.subscriptions.add(new Subscription(subscriptionNode));
        }
    }

    public void subscribe(String plan, String ccNumber, String ccExpiration) {
        subscribe(plan, ccNumber, ccExpiration, null, null);
    }

    public void subscribe(String plan, String ccNumber, String ccExpiration, String ccCardCode, String ccZip) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("planCode", plan);
        params.put("ccNumber", ccNumber);
        params.put("ccExpiration", ccExpiration);
        if (ccCardCode != null)
            params.put("ccCardCode", ccCardCode);
        params.put("ccFirstName", firstName);
        params.put("ccLastName", lastName);
        if (ccZip != null)
            params.put("ccZip", ccZip);
        String url = Service.ROOT + "/customers/edit-subscription/productCode/" + service.getProductCode() + "/code/" + code;
        HttpResponse resp = WS
                .url(url)
                .params(params).authenticate(service.getUser(), service.getPassword()).post();
        CheddarGetterException.validate(resp);
    }

    public void cancel() {
        String url = Service.ROOT + "/customers/cancel/productCode/" + service.getProductCode() + "/code/" + code;
        HttpResponse resp = WS
                .url(url)
                .authenticate(service.getUser(), service.getPassword()).get();
        CheddarGetterException.validate(resp);
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
