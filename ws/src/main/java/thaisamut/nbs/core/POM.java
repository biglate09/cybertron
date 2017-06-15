package thaisamut.nbs.core;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public final class POM
{
    private static final Logger LOG = LoggerFactory.getLogger(POM.class);

    public static final String Version;
    public static final String ArtifactID;
    public static final String GroupID;
    public static final String Name;
    public static final String JenkinsBuildNumber;

    static
    {
        Properties props = new Properties();

        try
        {
            props.load(POM.class.getResourceAsStream("POM.properties"));
        }
        catch (Exception e)
        {
            if (LOG.isDebugEnabled())
            {
                LOG.error(e.getMessage(), e);
            }
            else
            {
                LOG.error(e.getMessage());
            }
        }

        ArtifactID = props.getProperty("artifactId", "UNDEFINED");
        GroupID = props.getProperty("groupId", "UNDEFINED");
        Version = props.getProperty("version", "UNDEFINED");
        Name = props.getProperty("name", "UNDEFINED");

        Properties jenkinsBuildProps = new Properties();

        try {
            jenkinsBuildProps.load(POM.class.getResourceAsStream("/jenkins.build.number.properties"));
        } catch (Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.error(e.getMessage(), e);
            }
            else {
                LOG.error(e.getMessage());
            }
        }

        JenkinsBuildNumber = jenkinsBuildProps.getProperty("jenkins.build.number", "");
    }

    private POM() { }
}

