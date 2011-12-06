package play.modules.cheese;

import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;

import play.modules.cheese.util.XPathUtil;

public class Invoice {
    private String code;
    private String id;
    private int number;
    private String type;
    private Date billingDatetime;
    private Date createdDatetime;
    private List<Transaction> transactions;
    private List<Charge> charges;
    
    public Invoice(Service service, Node node) {
        this.code = XPathUtil.selectText("@code", node);
        this.id = XPathUtil.selectText("@id", node);
        this.type = XPathUtil.selectText("type", node);
        this.number = XPathUtil.selectInt("number", node);
        this.billingDatetime  = XPathUtil.selectDate("billingDatetime", node);
        this.createdDatetime  = XPathUtil.selectDate("createdDatetime", node);
        this.transactions = XPathUtil.selectList("transactions/transaction", node, service, Transaction.class);
        this.charges = XPathUtil.selectList("charges/charge", node, service, Charge.class);
    }

    public Date getBillingDatetime() {
        return billingDatetime;
    }

    public List<Charge> getCharges() {
        return charges;
    }

    public String getCode() {
        return code;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getType() {
        return type;
    }

}
