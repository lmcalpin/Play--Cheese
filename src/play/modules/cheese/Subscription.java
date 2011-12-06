package play.modules.cheese;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import play.libs.XPath;
import play.modules.cheese.util.XPathUtil;

public class Subscription {
    private String id;
    private String ccFirstName;
    private String ccLastName;
    private String ccType;
    private String ccLastFour;
    private String ccExpirationDate;
    private List<String> plans = new ArrayList<String>();
    private boolean canceled;

    public Subscription(Node node) {
        this.id = XPathUtil.selectText("@id", node);
        this.ccFirstName = XPathUtil.selectText("ccFirstName", node);
        this.ccLastName = XPathUtil.selectText("ccLastName", node);
        this.ccType = XPathUtil.selectText("ccType", node);
        this.ccLastFour = XPathUtil.selectText("ccLastFour", node);
        this.ccExpirationDate = XPathUtil.selectText("ccExpirationDate", node);
        List<Node> planNodes = XPath.selectNodes("plans/plan", node);
        for (Node planNode : planNodes) {
            String planName = XPathUtil.selectText("name", planNode);
            this.plans.add(planName);
        }
        String canceledDateTime = XPathUtil.selectText("canceledDatetime", node);
        canceled = canceledDateTime != null && canceledDateTime.length() > 0;
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

    public List<String> getPlans() {
        return plans;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
