package play.modules.cheese;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import antlr.StringUtils;

import play.libs.XPath;

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
        this.id = XPath.selectText("@id", node);
        this.ccFirstName = XPath.selectText("ccFirstName", node);
        this.ccLastName = XPath.selectText("ccLastName", node);
        this.ccType = XPath.selectText("ccType", node);
        this.ccLastFour = XPath.selectText("ccLastFour", node);
        this.ccExpirationDate = XPath.selectText("ccExpirationDate", node);
        List<Node> planNodes = XPath.selectNodes("plans/plan", node);
        for (Node planNode : planNodes) {
            String planName = XPath.selectText("name", planNode);
            this.plans.add(planName);
        }
        String canceledDateTime = XPath.selectText("canceledDatetime", node);
        canceled = canceledDateTime != null && canceledDateTime.length() > 0;
    }

    public String getId() {
        return id;
    }

    public String getCcFirstName() {
        return ccFirstName;
    }

    public String getCcLastName() {
        return ccLastName;
    }

    public String getCcType() {
        return ccType;
    }

    public String getCcLastFour() {
        return ccLastFour;
    }

    public String getCcExpirationDate() {
        return ccExpirationDate;
    }

    public List<String> getPlans() {
        return plans;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
