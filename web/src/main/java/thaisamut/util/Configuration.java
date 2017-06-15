
package thaisamut.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationRuntimeException;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.configuration.DefaultConfigurationBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.6 $
 */
public final class Configuration
{
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);
    private static org.apache.commons.configuration.Configuration config;

    /**
     * DOCUMENT ME!
     */
    public static final String CONFIG_DEF_FILE = System.getProperty("org.apache.commons.configuration.definitionFile",
            "config.xml");

    /**
     * Creates a new Configuration object.
     */
    private Configuration()
    {
        ;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getInstance()
    {
        return getInstance(CONFIG_DEF_FILE);
    }

    /**
     * DOCUMENT ME!
     *
     * @param clazz DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getInstance(
        final Class clazz)
    {
        return getInstance(clazz.getClassLoader(), CONFIG_DEF_FILE);
    }

    /**
     * DOCUMENT ME!
     *
     * @param clazz DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getSubset(Class clazz)
    {
        return getInstance().subset(clazz.getName());
    }

    /**
     * DOCUMENT ME!
     *
     * @param prefix DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getSubset(
        String prefix)
    {
        return getInstance().subset(prefix);
    }

    /**
     * DOCUMENT ME!
     *
     * @param clazz DOCUMENT ME!
     * @param resourceName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getInstance(
        final Class clazz,
        final String resourceName)
    {
        return getInstance(clazz.getClassLoader(), resourceName);
    }

    /**
     * DOCUMENT ME!
     *
     * @param resourceName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getInstance(
        final String resourceName)
    {
        try
        {
            return getInstance(Thread.currentThread().getContextClassLoader(),
                resourceName);
        }
        catch (ConfigurationRuntimeException e)
        {
            return getInstance(Configuration.class, resourceName);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param cl DOCUMENT ME!
     * @param resourceName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getInstance(
        final ClassLoader cl,
        final String resourceName)
    {
        try
        {
            if (null == config)
            {
                synchronized (Configuration.class)
                {
                    if (null == config)
                    {
                        URL configfile = cl.getResource(resourceName);
                        DefaultConfigurationBuilder builder = null;

                        if (null == configfile)
                        {
                            throw new ConfigurationException("Resource '"
                                + resourceName + "' not found.");
                        }
                        else if (log.isDebugEnabled())
                        {
                            log.debug(
                                "Configuration resource URL if using ClassLoader is "
                                + configfile);
                        }

                        builder = new DefaultConfigurationBuilder();
                        builder.setURL(configfile);
                        
                        builder.addConfigurationProvider("database",
                            new DefaultConfigurationBuilder.ConfigurationProvider(
                                DatabaseConfiguration.class));
                        
                        config = builder.getConfiguration();
                    }
                }
            }

            return config;
        }
        catch (ConfigurationException e)
        {
            throw new ConfigurationRuntimeException(e.getMessage(), e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public static void invalidate()
    {
        if (null != config)
        {
            synchronized (Configuration.class)
            {
                if (null != config)
                {
                    config = null;
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getConfiguration()
    {
        return getInstance();
    }

    /**
     * DOCUMENT ME!
     *
     * @param clazz DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getConfiguration(
        Class clazz)
    {
        return getInstance(clazz);
    }

    /**
     * DOCUMENT ME!
     *
     * @param clazz DOCUMENT ME!
     * @param resourceName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getConfiguration(
        final Class clazz,
        final String resourceName)
    {
        return getInstance(clazz, resourceName);
    }

    /**
     * DOCUMENT ME!
     *
     * @param resourceName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static org.apache.commons.configuration.Configuration getConfiguration(
        final String resourceName)
    {
        return getInstance(resourceName);
    }
}


// vim:nu:ts=4:sts=4:sw=4:ft=java:et:ai:sm:sta
