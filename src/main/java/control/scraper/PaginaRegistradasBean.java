package control.scraper;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.persistence.NoResultException;
import models.ScraperMagic;
import org.primefaces.context.RequestContext;

/**
 *
 * @author moicsmarkez
 */
@ManagedBean
@ViewScoped
public class PaginaRegistradasBean implements Serializable {

    private List<ScraperMagic> paginas;
    private List<ScraperMagic> paginasFiltradas;
    private ScraperMagic selecPagina;

    public PaginaRegistradasBean() {
    }

    public void eliminarPage(ActionEvent event) {
        try {
            if (selecPagina == null) {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Revisa", "Debes seleccionar sobre algún elemento de la tabla!"));
            } else {
                ScraperMagicBean.getINSTANCIA().getEm().getTransaction().begin();
                ScraperMagicBean.getINSTANCIA().getEm().remove(ScraperMagicBean.getINSTANCIA().getEm().merge(selecPagina));

                ScraperMagicBean.getINSTANCIA().getEm().getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void modificarPage() {
        try {
            if (selecPagina == null) {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Revisa", "Debes seleccionar sobre algún elemento de la tabla!"));
            } else {
                System.out.println("BUENO::::::");
                ScraperMagicBean.getINSTANCIA().getEm().getTransaction().begin();
                ScraperMagicBean.getINSTANCIA().getEm().persist(ScraperMagicBean.getINSTANCIA().getEm().merge(selecPagina));
                ScraperMagicBean.getINSTANCIA().getEm().getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ScraperMagic> getPaginas() {
        try {
            return paginas = (List<ScraperMagic>) ScraperMagicBean.getINSTANCIA().getEm().createNamedQuery("ScraperMagic.findAll").getResultList();
        } catch (NullPointerException | NoResultException e) {
            return paginas;
        } catch (Exception e) {
            return paginas;
        }
    }

    public void setPaginas(List<ScraperMagic> paginas) {
        this.paginas = paginas;
    }

    public List<ScraperMagic> getPaginasFiltradas() {
        return paginasFiltradas;
    }

    public void setPaginasFiltradas(List<ScraperMagic> paginasFiltradas) {
        this.paginasFiltradas = paginasFiltradas;
    }

    public ScraperMagic getSelecPagina() {
        return selecPagina;
    }

    public void setSelecPagina(ScraperMagic selecPagina) {
        this.selecPagina = selecPagina;
    }

}
