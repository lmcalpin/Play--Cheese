package play.modules.cheese;

import java.util.Date;

import org.w3c.dom.Node;

import play.modules.cheese.util.XPathUtil;

public class Transaction {
    private String code;
    private String id;
    private String parentId;
    private String memo;
    private Date createdDatetime;

    public Transaction(Service service, Node node) {
        this.code = XPathUtil.selectText("@code", node);
        this.id = XPathUtil.selectText("@id", node);
        this.parentId = XPathUtil.selectText("parentId", node);
        this.memo = XPathUtil.selectText("memo", node);
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

    public String getMemo() {
        return memo;
    }

    public String getParentId() {
        return parentId;
    }

}
