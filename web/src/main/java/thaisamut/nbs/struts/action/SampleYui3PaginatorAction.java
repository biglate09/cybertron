package thaisamut.nbs.struts.action;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class SampleYui3PaginatorAction extends Yui3PaginatorAction
{
    protected Map<String, String> params = new HashMap<String, String>();

    public Map<String, String> getParams() { return params; }

    @Override public String index()
    {
        return (new Processor() {
            @Override
            protected long getTotalItems() throws Exception {
                /* SELECT COUNT(1) FROM some_table; */
                return 1234L;
            }

            @Override
            protected List getResults(int requestedPage, int pageSize, Map<String, Integer> sortBy) throws Exception {
                List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
                Map<String, Object> item = new HashMap<String, Object>();

                item.put("filename", "/tmp/pub/Test.java");
                item.put("size", 1234);
                item.put("lastupdated", new Date());

                results.add(item);
                results.add(item);
                results.add(item);
                results.add(item);
                results.add(item);
                results.add(item);
                results.add(item);
                results.add(item);

                return results;
            }
        }).run();
    }
}
