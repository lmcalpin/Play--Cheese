package play.modules.cheese;

import java.math.BigDecimal;
import java.util.Date;

import org.w3c.dom.Node;

import play.modules.cheese.util.XPathUtil;

public class Item {
    private String code;
    private String id;
    private String name;
    private BigDecimal quantity;
    private int quantityIncluded;
    private boolean isPeriodic;
    private BigDecimal overageAmount;
    private Date createdDatetime;

    public Item(Service service, Node node) {
        this.code = XPathUtil.selectText("@code", node);
        this.id = XPathUtil.selectText("@id", node);
        this.name = XPathUtil.selectText("name", node);
        this.quantity = XPathUtil.selectDecimal("quantity", node);
        this.quantityIncluded = XPathUtil.selectInt("quantityIncluded", node);
        this.isPeriodic = XPathUtil.selectBoolean("isPeriodic", node);
        this.overageAmount = XPathUtil.selectDecimal("overageAmount", node);
        this.createdDatetime = XPathUtil.selectDate("createdDatetime", node);
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

    public String getName() {
        return name;
    }

    public BigDecimal getOverageAmount() {
        return overageAmount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public int getQuantityIncluded() {
        return quantityIncluded;
    }

    public boolean isPeriodic() {
        return isPeriodic;
    }

}
