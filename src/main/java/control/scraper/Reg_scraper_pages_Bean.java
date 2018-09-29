package control.scraper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import models.ResultadoEntiti;
import models.ScraperMagic;
import org.primefaces.context.RequestContext;

/**
 *
 * @author moicsmarkez
 */
@ManagedBean
@ViewScoped
public class Reg_scraper_pages_Bean implements Serializable {

    private boolean https = false, is_id = false, is_post = false;
    private String dominio, mtdQ, tagDiv, tagThumb, tagTitle, tagPrice, tagLink, argAdi, nomTienda;
    private List<ResultadoEntiti> pruebaResultados;
    private String item_search = "Magic Trick";

    public Reg_scraper_pages_Bean() {
    }

    public void pruebaScraper() {
        try {
            pruebaResultados = null;
            pruebaResultados = new ArrayList<ResultadoEntiti>();
            if (item_search.isEmpty()) {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR!", "El campo de busqueda no puede estar Vacido!"));
            } else {
                pruebaResultados.addAll(ScraperMagicBean.getINSTANCIA().getDataFromWebShop(item_search.toLowerCase(), dominio, mtdQ, tagThumb, tagTitle, tagPrice, tagDiv, nomTienda, argAdi, is_post, https,is_id));
                RequestContext.getCurrentInstance().update("reg_scr_pag:data-grid-search");
                RequestContext.getCurrentInstance().execute("PF('confir').show()");
            }
        } catch (NullPointerException e) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia!", "No encontro ningun dato!! Revisa: Puede que halla un error en los datos ó intenta con una nueva busqueda!"));
        }

    }

    public void processScraper(ActionEvent event) {
        try {

            System.out.println("Datos: " + dominio + mtdQ + is_id + tagDiv + tagThumb + tagTitle + tagPrice + tagLink + https + is_post + argAdi);
            ScraperMagicBean.getINSTANCIA().getEm().getTransaction().begin();
            ScraperMagicBean.getINSTANCIA().getEm().persist(new ScraperMagic(dominio, mtdQ, is_id, tagDiv, tagThumb, tagTitle, tagPrice, https, is_post, argAdi, nomTienda));
            ScraperMagicBean.getINSTANCIA().getEm().getTransaction().commit();
            resetMetodo();
            //          sf.create(new ScraperMagic(11,dominio, mtdQ, is_id, tagDiv, tagThumb, tagTitle, tagPrice, tagLink, https, is_post, argAdi));
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Exitoso", "Pagina registrada exitosamente, Gracias! "));
        } catch (Exception e) {
            e.printStackTrace();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Error grave", "!! " + e.toString()));
        }
    }

    private void resetMetodo() {
        https = false;
        is_id = false;
        is_post = false;
        dominio = null;
        mtdQ = null;
        tagDiv = null;
        tagThumb = null;
        tagTitle = null;
        tagPrice = null;
        tagLink = null;
        argAdi = null;
        nomTienda = null;
        RequestContext.getCurrentInstance().reset("reg_scr_pag");
        RequestContext.getCurrentInstance().update("reg_scr_pag:reg_pan_scrap");
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    public boolean isIs_id() {
        return is_id;
    }

    public void setIs_id(boolean is_id) {
        this.is_id = is_id;
    }
 
    public boolean isIs_post() {
        return is_post;
    }

    public void setIs_post(boolean is_post) {
        this.is_post = is_post;
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

    public String getTagLink() {
        return tagLink;
    }

    public void setTagLink(String tagLink) {
        this.tagLink = tagLink;
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

    public List<ResultadoEntiti> getPruebaResultados() {
        return pruebaResultados;
    }

    public void setPruebaResultados(List<ResultadoEntiti> pruebaResultados) {
        this.pruebaResultados = pruebaResultados;
    }

    public String getItem_search() {
        return item_search;
    }

    public void setItem_search(String item_search) {
        this.item_search = item_search;
    }

}
