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
import javax.persistence.Lob;
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
@Table(name = "scraperMagic")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScraperMagic.findAll", query = "SELECT s FROM ScraperMagic s")
    , @NamedQuery(name = "ScraperMagic.findByIdscraperMagic", query = "SELECT s FROM ScraperMagic s WHERE s.idscraperMagic = :idscraperMagic")
    , @NamedQuery(name = "ScraperMagic.findByDominio", query = "SELECT s FROM ScraperMagic s WHERE s.dominio = :dominio")
    , @NamedQuery(name = "ScraperMagic.findByMtdQ", query = "SELECT s FROM ScraperMagic s WHERE s.mtdQ = :mtdQ")
    , @NamedQuery(name = "ScraperMagic.findByBoolId", query = "SELECT s FROM ScraperMagic s WHERE s.boolId = :boolId")
    , @NamedQuery(name = "ScraperMagic.findByTagDiv", query = "SELECT s FROM ScraperMagic s WHERE s.tagDiv = :tagDiv")
    , @NamedQuery(name = "ScraperMagic.findByTagThumb", query = "SELECT s FROM ScraperMagic s WHERE s.tagThumb = :tagThumb")
    , @NamedQuery(name = "ScraperMagic.findByTagTitle", query = "SELECT s FROM ScraperMagic s WHERE s.tagTitle = :tagTitle")
    , @NamedQuery(name = "ScraperMagic.findByTagPrice", query = "SELECT s FROM ScraperMagic s WHERE s.tagPrice = :tagPrice")
    , @NamedQuery(name = "ScraperMagic.findByBoolHttps", query = "SELECT s FROM ScraperMagic s WHERE s.boolHttps = :boolHttps")
    , @NamedQuery(name = "ScraperMagic.findByBoolPost", query = "SELECT s FROM ScraperMagic s WHERE s.boolPost = :boolPost")
    , @NamedQuery(name = "ScraperMagic.findByArgAdi", query = "SELECT s FROM ScraperMagic s WHERE s.argAdi = :argAdi")
    , @NamedQuery(name = "ScraperMagic.findByNomTienda", query = "SELECT s FROM ScraperMagic s WHERE s.nomTienda = :nomTienda")})
public class ScraperMagic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idscraperMagic")
    private Integer idscraperMagic;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "dominio")
    private String dominio;
    @Size(max = 255)
    @Column(name = "mtd_q")
    private String mtdQ;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bool_id")
    private boolean boolId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tag_div")
    private String tagDiv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tag_thumb")
    private String tagThumb;
    @Size(max = 255)
    @Column(name = "tag_title")
    private String tagTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tag_price")
    private String tagPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bool_https")
    private boolean boolHttps;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bool_post")
    private boolean boolPost;
    @Size(max = 255)
    @Column(name = "arg_adi")
    private String argAdi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nom_tienda")
    private String nomTienda;
    @Lob
    @Column(name = "logo")
    private byte[] logo;

    public ScraperMagic() {
    }

    public ScraperMagic(Integer idscraperMagic) {
        this.idscraperMagic = idscraperMagic;
    }

    public ScraperMagic(String dominio, String mtdQ, boolean boolId, String tagDiv, String tagThumb, String tagTitle, String tagPrice, boolean boolHttps, boolean boolPost, String argAdi, String nomTienda) {
        this.dominio = dominio;
        this.mtdQ = mtdQ;
        this.boolId = boolId;
        this.tagDiv = tagDiv;
        this.tagThumb = tagThumb;
        this.tagTitle = tagTitle;
        this.tagPrice = tagPrice;
        this.boolHttps = boolHttps;
        this.boolPost = boolPost;
        this.argAdi = argAdi;
        this.nomTienda = nomTienda;
    }

    public ScraperMagic(Integer idscraperMagic, String dominio, boolean boolId, String tagDiv, String tagThumb, String tagPrice, boolean boolHttps, boolean boolPost, String nomTienda) {
        this.idscraperMagic = idscraperMagic;
        this.dominio = dominio;
        this.boolId = boolId;
        this.tagDiv = tagDiv;
        this.tagThumb = tagThumb;
        this.tagPrice = tagPrice;
        this.boolHttps = boolHttps;
        this.boolPost = boolPost;
        this.nomTienda = nomTienda;
    }

    public Integer getIdscraperMagic() {
        return idscraperMagic;
    }

    public void setIdscraperMagic(Integer idscraperMagic) {
        this.idscraperMagic = idscraperMagic;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getMtdQ() {
        return mtdQ;
    }

    public void setMtdQ(String mtdQ) {
        this.mtdQ = mtdQ;
    }

    public boolean getBoolId() {
        return boolId;
    }

    public void setBoolId(boolean boolId) {
        this.boolId = boolId;
    }

    public String getTagDiv() {
        return tagDiv;
    }

    public void setTagDiv(String tagDiv) {
        this.tagDiv = tagDiv;
    }

    public String getTagThumb() {
        return tagThumb;
    }

    public void setTagThumb(String tagThumb) {
        this.tagThumb = tagThumb;
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

    public boolean getBoolHttps() {
        return boolHttps;
    }

    public void setBoolHttps(boolean boolHttps) {
        this.boolHttps = boolHttps;
    }

    public boolean getBoolPost() {
        return boolPost;
    }

    public void setBoolPost(boolean boolPost) {
        this.boolPost = boolPost;
    }

    public String getArgAdi() {
        return argAdi;
    }

    public void setArgAdi(String argAdi) {
        this.argAdi = argAdi;
    }

    public String getNomTienda() {
        return nomTienda;
    }

    public void setNomTienda(String nomTienda) {
        this.nomTienda = nomTienda;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idscraperMagic != null ? idscraperMagic.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScraperMagic)) {
            return false;
        }
        ScraperMagic other = (ScraperMagic) object;
        if ((this.idscraperMagic == null && other.idscraperMagic != null) || (this.idscraperMagic != null && !this.idscraperMagic.equals(other.idscraperMagic))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.ScraperMagic[ idscraperMagic=" + idscraperMagic + " ]";
    }

}
