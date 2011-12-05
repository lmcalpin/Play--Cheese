package play.modules.cheese;

import org.w3c.dom.Node;

import play.libs.XPath;

public class Item {
    private String code;
    private String id;

    public Item(Service service, Node node) {
        this.code = XPath.selectText("@code", node);
        this.id = XPath.selectText("@id", node);
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return id;
    }
    
}
