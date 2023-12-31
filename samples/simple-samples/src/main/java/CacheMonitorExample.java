import com.alicp.jetcache.Cache;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.alicp.jetcache.support.DefaultCacheMonitor;
import com.alicp.jetcache.support.DefaultMetricsManager;
import com.alicp.jetcache.support.Fastjson2KeyConvertor;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2016/11/2.
 *
 * @author huangli
 */
public class CacheMonitorExample {
    public static void main(String[] args) throws Exception {
        DefaultCacheMonitor orderCacheMonitor = new DefaultCacheMonitor("OrderCache");
        Cache<String, Integer> cache = CaffeineCacheBuilder.createCaffeineCacheBuilder()
                .limit(100)
                .expireAfterWrite(200, TimeUnit.SECONDS)
                .keyConvertor(Fastjson2KeyConvertor.INSTANCE)
                .addMonitor(orderCacheMonitor)
                .buildCache();

        boolean verboseLog = false;
        DefaultMetricsManager statLogger = new DefaultMetricsManager(1, TimeUnit.SECONDS, verboseLog);

        statLogger.add(orderCacheMonitor);
        statLogger.start();

        Thread t = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                cache.put("20161111", 123456789);
                cache.get("20161111");
                cache.get("20161212");
                cache.remove("20161111");
                cache.remove("20161212");
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
        });
        t.start();
        t.join();

        statLogger.stop();
    }
}
