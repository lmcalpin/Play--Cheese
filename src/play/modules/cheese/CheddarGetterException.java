package play.modules.cheese;

import org.w3c.dom.Node;

import play.Logger;
import play.libs.WS.HttpResponse;
import play.modules.cheese.util.XPathUtil;

public class CheddarGetterException extends RuntimeException {
    private String code;
    private String auxCode;

    public CheddarGetterException() {
        super();
    }

    public CheddarGetterException(String message) {
        super(message);
    }

    public CheddarGetterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheddarGetterException(Throwable cause) {
        super(cause);
    }

    public static void validate(HttpResponse resp) {
        if (resp.getStatus() == 200)
            return;
        Logger.debug("Response Status = %d", resp.getStatus());
        System.out.println(resp.getString());
        Node node = resp.getXml().getFirstChild();
        String errorMessage = XPathUtil.selectText("text()", node);
        String code = XPathUtil.selectText("@code", node);
        String auxCode = XPathUtil.selectText("@auxCode", node);
        CheddarGetterException e = new CheddarGetterException(errorMessage);
        e.code = code;
        e.auxCode = auxCode;
        throw e;
    }

    public String getAuxCode() {
        return auxCode;
    }

    public String getCode() {
        return code;
    }
}
