package com.scrbl.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author vishvesh
 */
@Entity
@Table(name = "users", catalog = "fragbait_scribblein", schema = "")
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
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "last_updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedAt;
    
    @Column(name = "template_base64_image_url")
    private String templateBase64ImageUrl;
    
    @Column(name = "match_base64_image_url")
    private String matchBase64ImageUrl;
    
    @Column(name = "deviceName")
    private String deviceName;
    
    @Column(name = "browserName")
    private String browserName;
    
    @Column(name = "operatingSystem")
    private String operatingSystem;
    
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
    
    public Date getCreatedAt() {
    	return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
    	this.createdAt = createdAt;
    }
    
    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
    
    public void setTemplateBase64ImageUrl(String templateBase64ImageUrl) {
		this.templateBase64ImageUrl = templateBase64ImageUrl;
	}
    
    public String getTemplateBase64ImageUrl() {
		return templateBase64ImageUrl;
	}
    
    public void setMatchBase64ImageUrl(String matchBase64ImageUrl) {
		this.matchBase64ImageUrl = matchBase64ImageUrl;
	}
    
    public String getMatchBase64ImageUrl() {
		return matchBase64ImageUrl;
	}
    
    public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
    
    public String getDeviceName() {
		return deviceName;
	}
    
    public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
    
    public String getBrowserName() {
		return browserName;
	}
    
    public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
    
    public String getOperatingSystem() {
		return operatingSystem;
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
    	return "\n****User Details**** \nid = " + id + ", \nemail = " + email + ", \nfirstName = "
				+ firstName + ", \nlastName = " + lastName + ", \ntemplateData = "
				+ templateData + ", \nmatchData = " + matchData + ", \ncosineValue = "
				+ cosValue + ", \nshapeValue = " + shpValue + ", \nageGroup = "
				+ ageGroup + ", \nipAddress = " + ipAddress + ", \n lastUpdatedAt = " + lastUpdatedAt+", \nlatitude = "
				+ latitude + ", \nlongitude = " + longitude + "\n****User Details****";
	}
    
}