package thaisamut.nbs.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
@Entity(name="USERS")
@SequenceGenerator(name="userID", sequenceName="USER_ID", allocationSize = 1)
@NamedQueries({
    @NamedQuery(name="UserEntity.findByUsername", query="SELECT o FROM thaisamut.nbs.core.model.UserEntity o WHERE lower(o.username) = lower(:username)")
})
public class UserEntity implements Serializable
{
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=SEQUENCE, generator="userID")
    protected long id;

    @Column(name="USERNAME", length=25, unique=true)
    protected String username;

    @Column(name="ID_NUMBER", length=25, unique=false)
    protected String idNumber;

    @Column(name="FULLNAME", nullable=true, length=100)
    protected String fullname;

    @Column(name="JOB_TITLE", nullable=true, length=100)
    protected String jobTitle;

    @Column(name="SECTION", nullable=true, length=100)
    protected String section;

    @Column(name="DEPARTMENT", nullable=true, length=100)
    protected String department;

    @Column(name="EMAIL", nullable=true, length=100)
    protected String email;

    @Column(name="ACCESS_PERMIT_TIME", nullable=true, length=100)
    protected String accessPermitTime;

    @Column(name="PERMISSIONS", nullable=true, length=255)
    protected String permissions;

    @Column(name="CREATED_DATE", nullable=false)
    protected Date createdDate;

    @Column(name="LAST_LOGIN_TIME", nullable=true)
    protected Date lastLoginTime;

    @Column(name="BRANCH_CODE", nullable=true, length=25)
    protected String branchCode;

    public long getId() { return id; }

    public void setId(long rhs) { id = rhs; }

    public String getUsername() { return username; }

    public void setUsername(String rhs) { username = rhs; }

    public String getIdNumber() { return idNumber; }

    public void setIdNumber(String rhs) { idNumber = rhs; }

    public String getFullname() { return fullname; }

    public void setFullname(String rhs) { fullname = rhs; }

    public String getJobTitle() { return jobTitle; }

    public void setJobTitle(String rhs) { jobTitle = rhs; }

    public String getSection() { return section; }

    public void setSection(String rhs) { section = rhs; }

    public String getDepartment() { return department; }

    public void setDepartment(String rhs) { department = rhs; }

    public String getEmail() { return email; }

    public void setEmail(String rhs) { email = rhs; }

    public String getAccessPermitTime() { return accessPermitTime; }

    public void setAccessPermitTime(String rhs) { accessPermitTime = rhs; }

    public String getPermissions() { return permissions; }

    public void setPermissions(String rhs) { permissions = rhs; }

    public Date getCreatedDate() { return createdDate; }

    public void setCreatedDate(Date rhs) { createdDate = rhs; }

    public Date getLastLoginTime() { return lastLoginTime; }

    public void setLastLoginTime(Date rhs) { lastLoginTime = rhs; }

    public String getBranchCode() { return branchCode; }

    public void setBranchCode(String rhs) { branchCode = rhs; }
}
