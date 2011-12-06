package play.modules.cheese.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.w3c.dom.Node;

public class XPathUtil {
    public static String selectText(String xpath, Node node) {
        return selectText(xpath, node, "");
    }

    public static String selectText(String xpath, Node node, String def) {
        String val = XPathUtil.selectText(xpath, node);
        if (val == null)
            return def;
        return val;
    }

    public static boolean selectBoolean(String xpath, Node node) {
        return selectBoolean(xpath, node, false);
    }

    public static boolean selectBoolean(String xpath, Node node, boolean def) {
        String val = XPathUtil.selectText(xpath, node);
        if (val == null)
            return def;
        return Boolean.parseBoolean(val);
    }

    public static BigDecimal selectDecimal(String xpath, Node node) {
        return selectDecimal(xpath, node, BigDecimal.ZERO);
    }

    public static BigDecimal selectDecimal(String xpath, Node node, BigDecimal def) {
        String val = XPathUtil.selectText(xpath, node);
        if (val == null)
            return def;
        return new BigDecimal(val);
    }

    public static int selectInt(String xpath, Node node) {
        return selectInt(xpath, node, 0);
    }

    public static int selectInt(String xpath, Node node, int def) {
        String val = XPathUtil.selectText(xpath, node);
        if (val == null)
            return def;
        return Integer.parseInt(val);
    }

    public static float selectFloat(String xpath, Node node) {
        return selectFloat(xpath, node, 0.0f);
    }

    public static float selectFloat(String xpath, Node node, float def) {
        String val = XPathUtil.selectText(xpath, node);
        if (val == null)
            return def;
        return Float.parseFloat(val);
    }

    private static final SimpleDateFormat cgf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static Date selectDate(String xpath, Node node) {
        cgf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String val = XPathUtil.selectText(xpath, node);
        try {
            // the actual format is yyyy-MM-dd'T'HH:mm:ssZ except the timezone format is weird and
            // has a ':' in it.  but the response is always UTC and will be +00:00, so we just
            // ignore the timezone.
            String strippedVal = val.substring(0, 19);
            return cgf.parse(strippedVal);
        } catch (ParseException e) {
            play.Logger.fatal(e, "Error parsing date from CheddarGetter: %s", val);
            return null;
        }
    }
}
