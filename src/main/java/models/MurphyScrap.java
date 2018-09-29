/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author moicsmarkez
 */
@Entity
@Table(name = "murphyScrap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MurphyScrap.findAll", query = "SELECT m FROM MurphyScrap m"),
    @NamedQuery(name = "MurphyScrap.findByIdmurphyScrap", query = "SELECT m FROM MurphyScrap m WHERE m.idmurphyScrap = :idmurphyScrap"),
    @NamedQuery(name = "MurphyScrap.findByDominio", query = "SELECT m FROM MurphyScrap m WHERE m.dominio = :dominio"),
    @NamedQuery(name = "MurphyScrap.findAllDominio", query = "SELECT m.dominio FROM MurphyScrap m"),
    @NamedQuery(name = "MurphyScrap.findByPathname", query = "SELECT m FROM MurphyScrap m WHERE m.pathname = :pathname"),
    @NamedQuery(name = "MurphyScrap.findByTagTitle", query = "SELECT m FROM MurphyScrap m WHERE m.tagTitle = :tagTitle"),
    @NamedQuery(name = "MurphyScrap.findByTagPrice", query = "SELECT m FROM MurphyScrap m WHERE m.tagPrice = :tagPrice"),
    @NamedQuery(name = "MurphyScrap.findByHttps", query = "SELECT m FROM MurphyScrap m WHERE m.https = :https")})
public class MurphyScrap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmurphyScrap")
    private Integer idmurphyScrap;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "dominio")
    private String dominio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "pathname")
    private String pathname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "tag_title")
    private String tagTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "tag_price")
    private String tagPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "https")
    private boolean https;

    public MurphyScrap() {
    }

    public MurphyScrap(Integer idmurphyScrap) {
        this.idmurphyScrap = idmurphyScrap;
    }

    public MurphyScrap(Integer idmurphyScrap, String dominio, String pathname, String tagTitle, String tagPrice, boolean https) {
        this.idmurphyScrap = idmurphyScrap;
        this.dominio = dominio;
        this.pathname = pathname;
        this.tagTitle = tagTitle;
        this.tagPrice = tagPrice;
        this.https = https;
    }

    public Integer getIdmurphyScrap() {
        return idmurphyScrap;
    }

    public void setIdmurphyScrap(Integer idmurphyScrap) {
        this.idmurphyScrap = idmurphyScrap;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public String getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(String tagPrice) {
        this.tagPrice = tagPrice;
    }

    public boolean getHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmurphyScrap != null ? idmurphyScrap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MurphyScrap)) {
            return false;
        }
        MurphyScrap other = (MurphyScrap) object;
        if ((this.idmurphyScrap == null && other.idmurphyScrap != null) || (this.idmurphyScrap != null && !this.idmurphyScrap.equals(other.idmurphyScrap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.MurphyScrap[ idmurphyScrap=" + idmurphyScrap + " ]";
    }
    
}
