package play.modules.cheese;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;

import play.libs.XPath;
import play.modules.cheese.util.XPathUtil;

public class Plan {
    private String code;
    private String id;
    private String name;
    private String description;
    private boolean isActive;
    private int trialDays;
    private String billingFrequency;
    private String billingFrequencyPer;
    private String billingFrequencyUnit;
    private int billingFrequencyQuantity;
    private String setupChargeCode;
    private float setupChargeAmount;
    private String recurringChargeCode;
    private float recurringChargeAmount;
    private Date createdDatetime;
    private List<Item> items;

    public Plan(Service service, Node node) {
        this.code = XPath.selectText("@code", node);
        this.id = XPath.selectText("@id", node);
        this.name  = XPath.selectText("name", node);
        this.description  = XPath.selectText("description", node);
        this.isActive   = XPathUtil.selectBoolean("name", node);
        this.trialDays   = XPathUtil.selectInt("trialDays", node);
        this.billingFrequency  = XPath.selectText("billingFrequency", node);
        this.billingFrequencyPer  = XPath.selectText("billingFrequencyPer", node);
        this.billingFrequencyUnit  = XPath.selectText("billingFrequencyUnit", node);
        this.billingFrequencyQuantity  = XPathUtil.selectInt("billingFrequencyQuantity", node);
        this.setupChargeCode  = XPath.selectText("setupChargeCode", node);
        this.setupChargeAmount  = XPathUtil.selectFloat("setupChargeAmount", node);
        this.createdDatetime  = XPathUtil.selectDate("createdDatetime", node);
        this.recurringChargeCode  = XPath.selectText("recurringChargeCode", node);
        this.recurringChargeAmount  = XPathUtil.selectFloat("recurringChargeAmount", node);
        this.items = new ArrayList<Item>();
        List<Node> itemNodes = XPath.selectNodes("items/item", node);
        for (Node itemNode : itemNodes) {
            this.items.add(new Item(service, itemNode));
        }
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getTrialDays() {
        return trialDays;
    }

    public String getBillingFrequency() {
        return billingFrequency;
    }

    public String getBillingFrequencyPer() {
        return billingFrequencyPer;
    }

    public String getBillingFrequencyUnit() {
        return billingFrequencyUnit;
    }

    public int getBillingFrequencyQuantity() {
        return billingFrequencyQuantity;
    }

    public String getSetupChargeCode() {
        return setupChargeCode;
    }

    public float getSetupChargeAmount() {
        return setupChargeAmount;
    }

    public String getRecurringChargeCode() {
        return recurringChargeCode;
    }

    public float getRecurringChargeAmount() {
        return recurringChargeAmount;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public List<Item> getItems() {
        return items;
    }
}
