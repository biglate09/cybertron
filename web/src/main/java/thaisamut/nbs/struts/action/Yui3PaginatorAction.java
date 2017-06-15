package thaisamut.nbs.struts.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public class Yui3PaginatorAction extends JsonAction
{
    public static final String KEY_TOTAL_ITEMS = "totalItems";
    public static final String KEY_ITEMS_PER_PAGE = "itemsPerPage";
    public static final String KEY_REQUESTED_PAGE = "page";
    public static final int DEFAULT_ITEMS_PER_PAGE = 0;
    public static final int DEFAULT_PAGE = 1;

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    public class PaginationState implements Serializable
    {
        protected List results = Collections.EMPTY_LIST;
        protected long totalItems = 0L;
        protected int itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        protected int page = DEFAULT_PAGE;
        protected Map<String, Integer> sortBy = new HashMap<String, Integer>();

        public long getTotalItems() { return totalItems; }
        public void setTotalItems(long rhs) { totalItems = rhs; }

        public int getItemsPerPage() { return itemsPerPage; }
        public void setItemsPerPage(int rhs) { itemsPerPage = rhs; }

        public int getPage() { return page; }
        public void setPage(int rhs) { page = rhs; }

        public List getResults() { return results; }
        public void setResults(List rhs) { results = rhs; }

        public Map<String, Integer> getSortBy() { return sortBy; }
        public void setSortBy(Map<String, Integer> rhs) { sortBy = rhs; }
    }

    public class PaginationResult extends Result<PaginationState>
    {
        public PaginationResult()
        {
            data = new PaginationState();
        }
    }

    protected abstract class Processor
    {
        public String run()
        {
            try
            {
                PaginationState state = result.getData();
                List results = null;

                state.setTotalItems(getTotalItems());

                results = getResults(state.getPage(), state.getItemsPerPage(), state.getSortBy());

                if ((null != results) && (results.size() > state.getItemsPerPage()))
                {
                    throw new Exception(String.format(
                                "Unmatch number of results (%1$,d) and page size (%2$,d)",
                                results.size(), state.getItemsPerPage()));
                }

                state.setResults(results);
            }
            catch (Exception e)
            {
                result.setErrorMessage(e.getMessage());

                if (LOG.isTraceEnabled())
                {
                    LOG.error(e.getMessage(), e);
                }
                else
                {
                    LOG.error(e.getMessage());
                }
            }

            return SUCCESS;
        }

        protected abstract long getTotalItems() throws Exception;
        protected abstract List getResults(int requestedPage,
                int pageSize, Map<String, Integer> sortBy) throws Exception;
    }

    protected PaginationResult result = new PaginationResult();

    @Override
    public PaginationResult getResult() { return this.result; }
}
