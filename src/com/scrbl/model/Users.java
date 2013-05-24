package com.scrbl.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vishvesh
 */
@Entity
@Table(name = "users", catalog = "scribblein", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "Users.findByTemplateData", query = "SELECT u FROM Users u WHERE u.templateData = :templateData"),
    @NamedQuery(name = "Users.findByMatchData", query = "SELECT u FROM Users u WHERE u.matchData = :matchData"),
    @NamedQuery(name = "Users.findByCosValue", query = "SELECT u FROM Users u WHERE u.cosValue = :cosValue"),
    @NamedQuery(name = "Users.findByShpValue", query = "SELECT u FROM Users u WHERE u.shpValue = :shpValue"),
    @NamedQuery(name = "Users.findByAgeGroup", query = "SELECT u FROM Users u WHERE u.ageGroup = :ageGroup"),
    @NamedQuery(name = "Users.findByIpAddress", query = "SELECT u FROM Users u WHERE u.ipAddress = :ipAddress"),
    @NamedQuery(name = "Users.findByLatitude", query = "SELECT u FROM Users u WHERE u.latitude = :latitude"),
    @NamedQuery(name = "Users.findByLongitude", query = "SELECT u FROM Users u WHERE u.longitude = :longitude")})

public class Users implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "template_data")
    private String templateData;
    
    @Column(name = "match_data")
    private String matchData;
    
    @Column(name = "cos_value")
    private String cosValue;
    
    @Column(name = "shp_value")
    private String shpValue;
    
    @Column(name = "age_group")
    private String ageGroup;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "latitude")
    private String latitude;
    
    @Column(name = "longitude")
    private String longitude;

    public Users() {
    }

    public Users(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTemplateData() {
        return templateData;
    }

    public void setTemplateData(String templateData) {
        this.templateData = templateData;
    }

    public String getMatchData() {
        return matchData;
    }

    public void setMatchData(String matchData) {
        this.matchData = matchData;
    }

    public String getCosValue() {
        return cosValue;
    }

    public void setCosValue(String cosValue) {
        this.cosValue = cosValue;
    }

    public String getShpValue() {
        return shpValue;
    }

    public void setShpValue(String shpValue) {
        this.shpValue = shpValue;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
	public String toString() {
    	return "User : \nid = " + id + ", \nemail = " + email + ", \nfirstName = "
				+ firstName + ", \nlastName = " + lastName + ", \ntemplateData = "
				+ templateData + ", \nmatchData = " + matchData + ", \ncosineValue = "
				+ cosValue + ", \nshapeValue = " + shpValue + ", \nageGroup = "
				+ ageGroup + ", \nipAddress = " + ipAddress + ", \nlatitude = "
				+ latitude + ", \nlongitude = " + longitude;
	}
    
}

/*import java.util.List;

import org.apache.log4j.Logger;

public class Users {
	
	Logger logger = Logger.getLogger(getClass());

	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private List<Point> templateData;
	private List<Point> matchData;
	private double cosineValue;
	private double shapeValue;
	private String ageGroup;
	private String ipAddress;
	private String latitude;
	private String longitude;
	
	@Override
	public String toString() {
		String returnString = "User : \nid = " + id + ", \nemail = " + email + ", \nfirstName = "
				+ firstName + ", \nlastName = " + lastName + ", \ntemplateData = "
				+ templateData + ", \nmatchData = " + matchData + ", \ncosineValue = "
				+ cosineValue + ", \nshapeValue = " + shapeValue + ", \nageGroup = "
				+ ageGroup + ", \nipAddress = " + ipAddress + ", \nlatitude = "
				+ latitude + ", \nlongitude = " + longitude;
		logger.info(returnString);
		return returnString;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Point> getTemplateData() {
		return templateData;
	}
	public void setTemplateData(List<Point> templateData) {
		this.templateData = templateData;
	}
	public List<Point> getMatchData() {
		return matchData;
	}
	public void setMatchData(List<Point> matchData) {
		this.matchData = matchData;
	}
	public double getCosineValue() {
		return cosineValue;
	}
	public void setCosineValue(double cosineValue) {
		this.cosineValue = cosineValue;
	}
	public double getShapeValue() {
		return shapeValue;
	}
	public void setShapeValue(double shapeValue) {
		this.shapeValue = shapeValue;
	}
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}*/

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "users", catalog = "lazypizza", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "First_Name")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "Last_Name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "User_Email")
    private String userEmail;
    @Basic(optional = false)
    @Column(name = "Phone_Number")
    private String phoneNumber;
    @Basic(optional = false)
    @Column(name = "User_Password")
    private String userPassword;
    @Basic(optional = false)
    @Column(name = "Date_Of_Birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Basic(optional = false)
    @Column(name = "Gender")
    private String gender;
    @Basic(optional = false)
    @Column(name = "Activation_Code")
    private String activationCode;
    @Basic(optional = false)
    @Column(name = "Enabled")
    private boolean enabled;
    @Basic(optional = false)
    @Column(name = "TermsAndConditionsAccepted")
    private boolean termsAndConditionsAccepted;
    @Column(name = "Password_Needs_Change")
    private String passwordNeedsChange;
 
    public Users() {
    }

    public Users(Long id) {
        this.id = id;
    }

    public Users(Long id, String firstName, String lastName, String userEmail, String userPassword, Date dateOfBirth, String gender, String activationCode, boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.activationCode = activationCode;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
       return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getTermsAndConditionsAccepted() {
		return termsAndConditionsAccepted;
	}
    
    public void setTermsAndConditionsAccepted(boolean termsAndConditionsAccepted) {
		this.termsAndConditionsAccepted = termsAndConditionsAccepted;
	}
    
    public String getPasswordNeedsChange() {
        return passwordNeedsChange;
    }

    public void setPasswordNeedsChange(String passwordNeedsChange) {
        this.passwordNeedsChange = passwordNeedsChange;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.lazypizza.data.model.Users[ id=" + id + " ]";
    }
    
}*/
