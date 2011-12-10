package play.modules.cheese;

import java.math.BigDecimal;
import java.util.Date;

import org.w3c.dom.Node;

import play.modules.cheese.util.XPathUtil;

public class Charge {
    private String code;
    private String id;
    private String type;
    private int quantity;
    private BigDecimal eachAmount;
    private String description;
    private Date createdDatetime;

    public Charge(Service service, Node node) {
        this.code = XPathUtil.selectText("@code", node);
        this.id = XPathUtil.selectText("@id", node);
        this.type = XPathUtil.selectText("type", node);
        this.quantity = XPathUtil.selectInt("quantity", node);
        this.eachAmount = XPathUtil.selectDecimal("eachAmount", node);
        this.description = XPathUtil.selectText("description", node);
        this.createdDatetime = XPathUtil.selectDate("createdDatetime", node);
    }

    public Charge(String code, int quantity, BigDecimal eachAmount, String description) {
        this.code = code;
        this.quantity = quantity;
        this.eachAmount = eachAmount;
        this.description = description;
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

    public BigDecimal getEachAmount() {
        return eachAmount;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

}
