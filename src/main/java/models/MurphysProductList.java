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
@Table(name = "murphysProductList")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MurphysProductList.findAll", query = "SELECT m FROM MurphysProductList m"),
    @NamedQuery(name = "MurphysProductList.findByIdmurphysProductList", query = "SELECT m FROM MurphysProductList m WHERE m.idmurphysProductList = :idmurphysProductList"),
    @NamedQuery(name = "MurphysProductList.findByProductCode", query = "SELECT m FROM MurphysProductList m WHERE m.productCode = :productCode"),
    @NamedQuery(name = "MurphysProductList.findByTitle", query = "SELECT m FROM MurphysProductList m WHERE SQL('MATCH(Title) AGAINST(?) ',:title)"),
    @NamedQuery(name = "MurphysProductList.findByWhosale", query = "SELECT m FROM MurphysProductList m WHERE m.whosale = :whosale"),
    @NamedQuery(name = "MurphysProductList.findByImageUrl", query = "SELECT m FROM MurphysProductList m WHERE m.imageUrl = :imageUrl")})
public class MurphysProductList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmurphysProductList")
    private Integer idmurphysProductList;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "ProductCode")
    private String productCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 445)
    @Column(name = "Title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "whosale")
    private float whosale;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 405)
    @Column(name = "image_url")
    private String imageUrl;

    public MurphysProductList() {
    }

    public MurphysProductList(Integer idmurphysProductList) {
        this.idmurphysProductList = idmurphysProductList;
    }

    public MurphysProductList(Integer idmurphysProductList, String productCode, String title, float whosale, String imageUrl) {
        this.idmurphysProductList = idmurphysProductList;
        this.productCode = productCode;
        this.title = title;
        this.whosale = whosale;
        this.imageUrl = imageUrl;
    }

    public Integer getIdmurphysProductList() {
        return idmurphysProductList;
    }

    public void setIdmurphysProductList(Integer idmurphysProductList) {
        this.idmurphysProductList = idmurphysProductList;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getWhosale() {
        return whosale;
    }

    public void setWhosale(float whosale) {
        this.whosale = whosale;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmurphysProductList != null ? idmurphysProductList.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MurphysProductList)) {
            return false;
        }
        MurphysProductList other = (MurphysProductList) object;
        if ((this.idmurphysProductList == null && other.idmurphysProductList != null) || (this.idmurphysProductList != null && !this.idmurphysProductList.equals(other.idmurphysProductList))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.MurphysProductList[ idmurphysProductList=" + idmurphysProductList + " ]";
    }
    
}
