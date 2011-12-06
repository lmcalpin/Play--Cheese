package play.modules.cheese;

import java.math.BigDecimal;
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
    private boolean isFree;
    private int trialDays;
    private int initialBillCount;
    private String initialBillCountUnit;
    private String billingFrequency;
    private String billingFrequencyPer;
    private String billingFrequencyUnit;
    private int billingFrequencyQuantity;
    private Date nextInvoiceBillingDatetime;
    private String setupChargeCode;
    private BigDecimal setupChargeAmount;
    private String recurringChargeCode;
    private BigDecimal recurringChargeAmount;
    private Date createdDatetime;
    private List<Item> items;

    public Plan(Service service, Node node) {
        this.code = XPathUtil.selectText("@code", node);
        this.id = XPathUtil.selectText("@id", node);
        this.name  = XPathUtil.selectText("name", node);
        this.description  = XPathUtil.selectText("description", node);
        this.isActive   = XPathUtil.selectBoolean("isActive", node);
        this.isFree   = XPathUtil.selectBoolean("isFree", node);
        this.trialDays   = XPathUtil.selectInt("trialDays", node);
        this.initialBillCount   = XPathUtil.selectInt("initialBillCount", node);
        this.initialBillCountUnit   = XPathUtil.selectText("initialBillCountUnit", node);
        this.billingFrequency  = XPathUtil.selectText("billingFrequency", node);
        this.billingFrequencyPer  = XPathUtil.selectText("billingFrequencyPer", node);
        this.billingFrequencyUnit  = XPathUtil.selectText("billingFrequencyUnit", node);
        this.billingFrequencyQuantity  = XPathUtil.selectInt("billingFrequencyQuantity", node);
        this.nextInvoiceBillingDatetime  = XPathUtil.selectDate("nextInvoiceBillingDatetime", node);
        this.setupChargeCode  = XPathUtil.selectText("setupChargeCode", node);
        this.setupChargeAmount  = XPathUtil.selectDecimal("setupChargeAmount", node);
        this.createdDatetime  = XPathUtil.selectDate("createdDatetime", node);
        this.recurringChargeCode  = XPathUtil.selectText("recurringChargeCode", node);
        this.recurringChargeAmount  = XPathUtil.selectDecimal("recurringChargeAmount", node);
        this.items = XPathUtil.selectList("items/item", node, service, Item.class);
    }

    public String getBillingFrequency() {
        return billingFrequency;
    }

    public String getBillingFrequencyPer() {
        return billingFrequencyPer;
    }

    public int getBillingFrequencyQuantity() {
        return billingFrequencyQuantity;
    }

    public String getBillingFrequencyUnit() {
        return billingFrequencyUnit;
    }

    public String getCode() {
        return code;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public int getInitialBillCount() {
        return initialBillCount;
    }

    public String getInitialBillCountUnit() {
        return initialBillCountUnit;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public Date getNextInvoiceBillingDatetime() {
        return nextInvoiceBillingDatetime;
    }

    public BigDecimal getRecurringChargeAmount() {
        return recurringChargeAmount;
    }

    public String getRecurringChargeCode() {
        return recurringChargeCode;
    }

    public BigDecimal getSetupChargeAmount() {
        return setupChargeAmount;
    }

    public String getSetupChargeCode() {
        return setupChargeCode;
    }

    public int getTrialDays() {
        return trialDays;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isFree() {
        return isFree;
    }
}
