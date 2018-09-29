package control.resultados;

import control.scraper.ScraperMagicBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import models.ResultadoEntiti;
import models.ScraperMagic;
import org.primefaces.context.RequestContext;

/**
 *
 * @author moicsmarkez
 */
@ManagedBean
@SessionScoped
public class ResultadoGridDataBean implements Serializable {

    private List<ResultadoEntiti> resultados;
    private List<String> lista;
    private boolean sinResultado;
    private String item_search;
    private Double progreso = 0d;
    private List<ScraperMagic> paginas;

    public ResultadoGridDataBean() {
        paginas = (List<ScraperMagic>) ScraperMagicBean.getINSTANCIA().getEm().createNamedQuery("ScraperMagic.findAll").getResultList();
    }

    public void removveMe() {
        FacesContext.getCurrentInstance().getApplication().createValueBinding("#{resultadoGridDataBean}").setValue(FacesContext.getCurrentInstance(), null);
        item_search = null;
        System.out.println("Aiui Estoy Saliendo!");
    }

    private class TestHilos implements Runnable {

        List<ResultadoEntiti> list;
        int ht;

        public TestHilos(List<ResultadoEntiti> list, int ht) {
            this.list = list;
            this.ht = ht;
        }

        @Override
        public void run() {
            list.addAll(ScraperMagicBean.getINSTANCIA().getDataFromWebShop(item_search.toLowerCase(), paginas.get(ht).getDominio(), paginas.get(ht).getMtdQ(), paginas.get(ht).getTagThumb(), paginas.get(ht).getTagTitle(), paginas.get(ht).getTagPrice(), paginas.get(ht).getTagDiv(), paginas.get(ht).getNomTienda(), paginas.get(ht).getArgAdi(), paginas.get(ht).getBoolPost(), paginas.get(ht).getBoolHttps(), paginas.get(ht).getBoolId()));
            progreso = (double) progreso + (100 / paginas.size());
        }
    }

    public void corriendoHilos() throws InterruptedException {
        if (item_search.isEmpty()) {
            progreso = null;
            RequestContext.getCurrentInstance().execute("PF('panelLoading').hide();PrimeFaces.widgets.widget_reg_scr_pag_form_action_search.enable();PrimeFaces.widgets.widget_reg_scr_pag_form_order_list.selectValue('');");
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR!", "El campo de busqueda no puede estar Vacido!"));
        } else {
            progreso = 1d;
            resultados = null;
            resultados = new ArrayList<ResultadoEntiti>();
            CopyOnWriteArrayList<ResultadoEntiti> sincrnialist = new CopyOnWriteArrayList<ResultadoEntiti>();
            ExecutorService executor = Executors.newFixedThreadPool(1);
            for (int ht = 0; ht <= (paginas.size() / 4); ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    continue;
                } else {
                    executor.submit(new TestHilos(sincrnialist, ht));
                }
            }
            for (int ht = (paginas.size() / 4) + 1; ht <= (paginas.size() / 4) * 2; ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    continue;
                } else {
                    executor.submit(new TestHilos(sincrnialist, ht));
                }
            }
            for (int ht = ((paginas.size() / 4) * 2) + 1; ht <= (paginas.size() / 4) * 3; ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    continue;
                } else {
                    executor.submit(new TestHilos(sincrnialist, ht));
                }
            }
            for (int ht = ((paginas.size() / 4) * 3) + 1; ht < paginas.size(); ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    continue;
                } else {
                    executor.submit(new TestHilos(sincrnialist, ht));
                }
            }
            for (int ht = 0; ht < paginas.size(); ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    executor.submit(new TestHilos(sincrnialist, ht));
                }
            }
            executor.shutdown();
            while (executor.awaitTermination(1, TimeUnit.HOURS)) {
                System.out.println("Termino Chao, con progrso: " + progreso);
                if (progreso < 100 || progreso > 100) {
                    progreso = (double) 100;
                }
                if (progreso == 100) {
                    RequestContext.getCurrentInstance().execute("PF('panelLoading').hide();PrimeFaces.widgets.widget_reg_scr_pag_form_action_search.enable();PrimeFaces.widgets.widget_reg_scr_pag_form_order_list.selectValue('');");
                }
                System.out.println("Tamaño: " + sincrnialist.size());
                resultados = sincrnialist;
                break;
            }
        }
        System.out.println("Listo, ah Probar!");
        Runtime.getRuntime().gc();
    }

    public void ordenacionMayorMenor(String order) throws InterruptedException {
        switch (order) {
            case "asc":
                Collections.sort(resultados, Collections.reverseOrder());
                break;
            case "desc":
                Collections.sort(resultados);
                break;
            default:
                break;
        }
    }

    public List<ResultadoEntiti> getResultados() {

        return resultados;

    }

    public String getItem_search() {
        return item_search;
    }

    public void setItem_search(String item_search) {
        this.item_search = item_search;
    }

    public boolean isSinResultado() {
        return sinResultado;
    }

    public void setSinResultado(boolean sinResultado) {
        this.sinResultado = sinResultado;
    }

    public Double getProgreso() {
        return progreso;
    }

    public void setProgreso(Double progreso) {
        this.progreso = progreso;
    }

}
