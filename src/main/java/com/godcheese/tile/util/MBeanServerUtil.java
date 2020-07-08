package com.godcheese.tile.util;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Set;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-09-12
 */
public class MBeanServerUtil {

    private static Set<ObjectName> getQueryNames(MBeanServer mBeanServer) throws MalformedObjectNameException {
        QueryExp subQuery1 = Query.match(Query.attr("protocol"), Query.value("HTTP/1.1"));
        QueryExp subQuery2 = Query.anySubString(Query.attr("protocol"), Query.value("Http11"));
        QueryExp query = Query.or(subQuery1, subQuery2);

        System.out.println(mBeanServer.queryNames(new ObjectName("*:*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1"))));
        return mBeanServer.queryNames(new ObjectName("*:type=Connector,*"), query);
    }

    public static String getScheme() throws MalformedObjectNameException,
            NullPointerException, AttributeNotFoundException,
            InstanceNotFoundException, MBeanException, ReflectionException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objs = getQueryNames(mBeanServer);
        if (objs.iterator().hasNext()) {
            return mBeanServer.getAttribute(objs.iterator().next(), "scheme").toString();
        }
        return null;
    }

    public static String getPort() throws MalformedObjectNameException,
            NullPointerException {
        Set<ObjectName> queryNames = getQueryNames(ManagementFactory.getPlatformMBeanServer());
        if (queryNames.iterator().hasNext()) {
            return queryNames.iterator().next().getKeyProperty("port");
        }
        return null;
    }
}
