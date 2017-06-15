package thaisamut.nbs.struts.action;

import static java.lang.String.format;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class AbstractReportAction extends JsonAction {
	
	//@Resource 
	//protected DataSource dataSource;
	
	protected Connection connection;
	
	protected InputStream inputStream;

    public String input() throws Exception
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug(format("Processing %1$s::input()",
                        getClass().getSimpleName()));
        }

        return INPUT;
    }
    
    public String create() throws Exception 
    {
		if(LOG.isDebugEnabled())
		{
	        LOG.debug(format("Processing %1$s::create()",
	        			getClass().getSimpleName()));
		}
		
		return "pdf";
    }
    
    public String export() throws Exception 
    {
		if(LOG.isDebugEnabled())
		{
	        LOG.debug(format("Processing %1$s::export()",
	        			getClass().getSimpleName()));
		}
		
		return "excel";
    }
    
    public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/*
	public Connection getConnection() throws SQLException 
    {
		if(null != dataSource){
			return dataSource.getConnection();
		}
		return null;
	}
    */
    protected void closeConnection()
    {
    	
    }
}
