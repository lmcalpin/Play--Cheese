package play.modules.cheese;

import org.w3c.dom.Node;

import play.Logger;
import play.libs.WS.HttpResponse;
import play.libs.XPath;

public class CheddarGetterException extends RuntimeException {
    private String code;
    private String auxCode;

    public CheddarGetterException() {
        super();
    }

    public CheddarGetterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheddarGetterException(String message) {
        super(message);
    }

    public CheddarGetterException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public String getAuxCode() {
        return auxCode;
    }

    public static void validate(HttpResponse resp) {
        if (resp.getStatus() == 200)
            return;
        Logger.debug("Response Status = %d", resp.getStatus());
        System.out.println(resp.getString());
        Node node = resp.getXml().getFirstChild();
        String errorMessage = XPath.selectText("text()", node);
        String code = XPath.selectText("@code", node);
        String auxCode = XPath.selectText("@auxCode", node);
        CheddarGetterException e = new CheddarGetterException(errorMessage);
        e.code = code;
        e.auxCode = auxCode;
        throw e;
    }
}
