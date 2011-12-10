package play.modules.cheese;

import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;

import play.modules.cheese.util.XPathUtil;

public class Subscription {
    private String id;
    private String ccFirstName;
    private String ccLastName;
    private String ccType;
    private String ccLastFour;
    private String ccExpirationDate;
    private List<Plan> plans;
    private List<Item> items;
    private List<Invoice> invoices;
    private boolean canceled;
    private Date canceledDateTime;

    public Subscription(Service service, Node node) {
        this.id = XPathUtil.selectText("@id", node);
        this.ccFirstName = XPathUtil.selectText("ccFirstName", node);
        this.ccLastName = XPathUtil.selectText("ccLastName", node);
        this.ccType = XPathUtil.selectText("ccType", node);
        this.ccLastFour = XPathUtil.selectText("ccLastFour", node);
        this.ccExpirationDate = XPathUtil.selectText("ccExpirationDate", node);
        this.plans = XPathUtil.selectList("plans/plan", node, service, Plan.class);
        this.items = XPathUtil.selectList("items/item", node, service, Item.class);
        this.invoices = XPathUtil.selectList("invoices/invoice", node, service, Invoice.class);
        this.canceledDateTime = XPathUtil.selectDate("canceledDatetime", node);
        this.canceled = canceledDateTime != null;
    }

    public Date getCanceledDateTime() {
        return canceledDateTime;
    }

    public String getCcExpirationDate() {
        return ccExpirationDate;
    }

    public String getCcFirstName() {
        return ccFirstName;
    }

    public String getCcLastFour() {
        return ccLastFour;
    }

    public String getCcLastName() {
        return ccLastName;
    }

    public String getCcType() {
        return ccType;
    }

    public String getId() {
        return id;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
