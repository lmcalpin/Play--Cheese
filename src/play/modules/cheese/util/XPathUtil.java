package play.modules.cheese.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;

import play.libs.XPath;
import play.modules.cheese.CheddarGetterException;
import play.modules.cheese.Service;

public class XPathUtil {
    private static final SimpleDateFormat cgf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static boolean selectBoolean(String xpath, Node node) {
        return selectBoolean(xpath, node, false);
    }

    public static boolean selectBoolean(String xpath, Node node, boolean def) {
        String val = XPathUtil.selectText(xpath, node);
        if (StringUtils.isBlank(val))
            return def;
        return Boolean.parseBoolean(val);
    }

    public static Date selectDate(String xpath, Node node) {
        cgf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String val = XPathUtil.selectText(xpath, node);
        if (StringUtils.isBlank(val))
            return null;
        try {
            // the actual format is yyyy-MM-dd'T'HH:mm:ssZ except the timezone format is weird and
            // has a ':' in it. but the response is always UTC and will be +00:00, so we just
            // ignore the timezone.
            String strippedVal = val.substring(0, 19);
            return cgf.parse(strippedVal);
        } catch (ParseException e) {
            play.Logger.fatal(e, "Error parsing date from CheddarGetter: %s", val);
            return null;
        }
    }

    public static BigDecimal selectDecimal(String xpath, Node node) {
        return selectDecimal(xpath, node, BigDecimal.ZERO);
    }

    public static BigDecimal selectDecimal(String xpath, Node node, BigDecimal def) {
        String val = XPathUtil.selectText(xpath, node);
        if (StringUtils.isBlank(val))
            return def;
        return new BigDecimal(val);
    }

    public static float selectFloat(String xpath, Node node) {
        return selectFloat(xpath, node, 0.0f);
    }

    public static float selectFloat(String xpath, Node node, float def) {
        String val = XPathUtil.selectText(xpath, node);
        if (StringUtils.isBlank(val))
            return def;
        return Float.parseFloat(val);
    }

    public static int selectInt(String xpath, Node node) {
        return selectInt(xpath, node, 0);
    }

    public static int selectInt(String xpath, Node node, int def) {
        String val = XPathUtil.selectText(xpath, node);
        if (StringUtils.isBlank(val))
            return def;
        return Integer.parseInt(val);
    }

    /**
     * @param xpath
     * @param node
     * @param typeToken
     *            to work around the lack of reified generics in Java
     * @return
     */
    public static <T> List<T> selectList(String xpath, Node node, Service service, Class<T> typeToken) {
        List<Node> foundNodes = XPath.selectNodes(xpath, node);
        List<T> list = new ArrayList<T>();
        for (Node foundNode : foundNodes) {
            try {
                list.add(typeToken.getConstructor(Service.class, Node.class).newInstance(service, foundNode));
            } catch (IllegalArgumentException e) {
                // checked exceptions are stupid
                throw new CheddarGetterException("Error reading response xml", e);
            } catch (SecurityException e) {
                // checked exceptions are stupid, for reals
                throw new CheddarGetterException("Error reading response xml", e);
            } catch (InstantiationException e) {
                // checked exceptions are stupid
                throw new CheddarGetterException("Error reading response xml", e);
            } catch (IllegalAccessException e) {
                // no, seriously, checked exceptions are stupid
                throw new CheddarGetterException("Error reading response xml", e);
            } catch (InvocationTargetException e) {
                // checked exceptions are REALLY stupid
                throw new CheddarGetterException("Error reading response xml", e);
            } catch (NoSuchMethodException e) {
                // screw this, next time I'm programming in Scala
                throw new CheddarGetterException("Error reading response xml", e);
            }
        }
        return list;
    }

    public static String selectText(String xpath, Node node) {
        return selectText(xpath, node, "");
    }

    public static String selectText(String xpath, Node node, String def) {
        String val = XPath.selectText(xpath, node);
        if (val == null)
            return def;
        return val;
    }
}
