package thaisamut.commons.permission;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.SystemUtils.*;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public class Function implements Serializable, Cloneable
{
    private static final Logger LOG = LoggerFactory.getLogger(Function.class);

    private static final char[] GCHARS = "│└├─".toCharArray();

    protected Integer id;
    protected transient Function parent;
    protected String name;
    protected String description;
    protected String command;
    protected boolean visible;
    protected boolean flag;
    protected boolean allChildFlagsSet;
    protected transient Map<Integer, Function> all = Collections.emptyMap();
    protected List<Function> children = new ArrayList<Function>();

    public Function(Function func)
    {
        this(func.id, func.name, func.description, func.command, func.visible);
    }

    public Function(Integer id, String name, String description, String command,
            boolean visible)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.command = command;
        this.visible = visible;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("Created Function: " + this.toString());
        }
    }

    public Function(Integer id, String name, String description, String command,
            boolean visible, List<Function> children)
    {
        this(id, name, description, command, visible);

        if ((null != children) && !children.isEmpty())
        {
            for (Function function : children)
            {
                function.parent = this;
            }

            this.children.addAll(children);
        }
    }

    public Integer getId() { return id; }

    public Function getParent() { return parent; }

    public List<Function> getChildren() { return Collections.unmodifiableList(children); }

    public String getName() { return name; }

    public String getCommand() { return command; }

    public String getDescription() { return description; }

    public boolean isVisible() { return visible; }

    public String toString()
    {
        return new StrBuilder("{ id: ")
            .append(id).append(", name: ").append(name)
            .append(", description: ").append(description)
            .append(", command: ").append(command)
            .append(", visible: ").append(visible)
            .append(" }").toString();
    }

    public boolean equals(Object another)
    {
        return ((another instanceof Function) 
                && (((Function) another).id.equals(this.id)));
    }

    public int hashCode()
    {
        return (null == id) ? 0 : id.hashCode();
    }

    public boolean contains(Function f)
    {
        if (null == f) return false;
        else if (children.contains(f)) return true;

        for (Function fn : children) if (fn.contains(f)) return true;

        return false;
    }

    public void detach()
    {
        if (null != parent)
        {
            parent.children.remove(this);
            parent = null;
        }
    }

    public void detachCascade()
    {
        detach();

        for (Function child : children) child.detach();

        children.clear(); // ensure that it's cleaned
    }

    public boolean isRoot()
    {
        return (null == parent);
    }

    public List<Function> getCascadeChildren()
    {
        return cascadeChildren(new ArrayList<Function>());
    }

    protected List<Function> cascadeChildren(List<Function> allChilds)
    {
        for (Function child : children)
        {
            allChilds.add(child);
            child.cascadeChildren(allChilds);
        }

        return allChilds;
    }

    public Collection<Function> getAll()
    {
        return Collections.unmodifiableCollection(getRoot().all.values());
    }

    public Function getRoot()
    {
        if (isRoot())
        {
            if ((Collections.EMPTY_MAP == all)
                || (null == all))
            {
                synchronized (this)
                {
                    if ((Collections.EMPTY_MAP == all)
                        || (null == all))
                    {
                        boolean clone = (null == all);

                        Map<Integer, Function> map = new HashMap<Integer, Function>();

                        map.put(id, this);

                        for (Function child : cascadeChildren(new ArrayList<Function>()))
                        {
                            map.put(child.id, child);
                        }

                        all = map;

                        if (LOG.isDebugEnabled()) LOG.debug("Function.getRoot(), clone=" + clone + ", all.size()=" + all.size());

                        if (LOG.isDebugEnabled() && !clone)
                        {
                            printGraph();
                        }
                    }
                }
            }

            return this;
        }

        return parent.getRoot();
    }

    private boolean isLastSibling()
    {
        if (isRoot()) return true;

        return (parent.children.size() == (1 + parent.children.indexOf(this)));
    }

    private void printGraph()
    {
        StrBuilder buffer = new StrBuilder("Permission Tree:")
            .append(LINE_SEPARATOR);
        Function root = getRoot();

        buffer.append("[").append(root.id)
              .append(":").append(root.name)
              .append("]").append(LINE_SEPARATOR);

        for (Function func : root.children)
        {
            printGraph(func, buffer, "");
        }

        LOG.debug(buffer.toString());
    }

    private void printGraph(Function func, StrBuilder buffer, String prefix)
    {
        boolean last = func.isLastSibling();

        buffer.append(prefix)
              .append(GCHARS[last ? 1 : 2])
              .append(GCHARS[3]);

        buffer.append(func.id)
              .append(":").append(func.name)
              .append(LINE_SEPARATOR);

        for (Function child : func.children)
        {
            printGraph(child, buffer, prefix + (last ? " " : GCHARS[0]) + " ");
        }
    }

    public List<Function> getTopLevelFunctions()
    {
        return getRoot().getChildren();
    }

    public Function clone()
    {
        Function function = (Function) SerializationUtils.clone(this);

        function.parent = null;

        function.getRoot(); // triggering all nodes map initialization

        return function;
    }

    public static Function locate(Function tree, Integer id)
    {
        tree.getClass(); // test null
        id.getClass(); // test null

        Map<Integer, Function> all = tree.getRoot().all;

        if (all.containsKey(id))
        {
            return all.get(id);
        }

        return null;
    }

    public static List<Function> locate(Function tree, Collection<Integer> ids)
    {
        tree.getClass(); // test null
        ids.getClass(); // test null

        List<Function> set = new ArrayList<Function>(ids.size());
        Map<Integer, Function> all = tree.getRoot().all;

        for (Integer id : ids)
        {
            if (all.containsKey(id)) set.add(all.get(id));
        }

        return set;
    }

    public static Set<Integer> getIDs(Collection<Function> functions)
    {
        functions.getClass(); // test null

        Set<Integer> ids = new HashSet<Integer>(functions.size());

        for (Function function : functions)
        {
            ids.add(function.id);
        }

        return ids;
    }

    public static Function subset(final Function root, Set<Integer> ids)
    {
        boolean trace = LOG.isTraceEnabled();

        if (trace)
        {
            LOG.trace(new StrBuilder("Function.subset(")
                    .append(root.name).append(", ")
                    .append(StringUtils.join(ids)) .append(")")
                    .toString());
        }

        Function clone = root.getRoot().clone();
        List<Function> all = clone.getCascadeChildren();

        all.add(clone);

        // restore parent refs that are lost upon cloning
        for (Function func : all)
            for (Function child : func.children)
                child.parent = func;

        if (trace) LOG.trace(new StrBuilder("Cloned function(s): ")
                .append(all.size()).toString());

        /*
        for (Function func : all)
        {
            if (trace) LOG.trace(new StrBuilder("Function.name=")
                    .append(func.name).append(" [ visible: ")
                    .append(func.visible).append(", ")
                    .append(StringUtils.join(ids))
                    .append(".contains(").append(func.id)
                    .append(")=").append(ids.contains(func.id))
                    .append(" ]").toString());

            if (!func.visible || !ids.contains(func.id))
            {
                func.detach();

                if (trace) LOG.trace(new StrBuilder("Function.name=")
                        .append(func.name).append(" detached.")
                        .toString());
            }
            else if (trace)
            {
                LOG.trace(new StrBuilder("Function.name=")
                        .append(func.name).append(" remains attached.")
                        .toString());
            }
        }
        */

        for (Integer id : ids)
        {
            Function r = clone.getRoot();

            if (r.all.containsKey(id)) r.all.get(id).flagSet();
        }

        for (Function func : all)
        {
            if (func.flag && func.visible)
            {
                if (LOG.isDebugEnabled())
                    LOG.trace("Function.name=" + func.name 
                            + ", flag=" + func.flag 
                            + ", visible=" + func.visible 
                            + " remains attached");
            }
            else
            {
                func.detach();

                if (LOG.isTraceEnabled())
                    LOG.trace("Function.name=" + func.name 
                            + ", flag=" + func.flag 
                            + ", visible=" + func.visible 
                            + " detached");
            }
        }

        return clone;
    }

    protected void flagSet()
    {
        Function p = parent;

        while (null != p)
        {
            p.flag = true;

            if (LOG.isDebugEnabled()) 
                LOG.debug("parent.name=" + p.name + ", flag=" 
                        + p.flag + ", visible=" + p.visible);

            p = p.parent;
        }

        this.flag = true;

        if (!allChildFlagsSet) 
            for (Function child : children) child.flagSet();
        else allChildFlagsSet = true;
    }
}
