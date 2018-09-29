package com.scraper.magic.rest.service;

import control.scraper.ScraperMagicBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import models.MurphysProductList;
import models.ResultadoEntiti;
import models.ScraperMagic;

/**
 *
 * @author moicsmarkez
 */
@Path("json")
public class Resource {

    private List<ResultadoEntiti> resultados;
    private final List<ScraperMagic> paginas = (List<ScraperMagic>) ScraperMagicBean.getINSTANCIA().getEm().createNamedQuery("ScraperMagic.findAll").getResultList();

    @GET
    @Path("hola")
    @Produces(MediaType.TEXT_HTML)
    public String helloWorld() {
        String header = "<h2>Probando Mensaje!</h2>\n";
        header += "<ul>";
        header += "\n<li>.-" + "BUENO AQUI ESTOY" + "</li>";
        header += "\n</ul>";

        return header;
    }

    @GET
    @Path("busqueda/{consulta}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public List<ResultadoEntiti> listaResultados(@PathParam("consulta") String consulta) throws InterruptedException {

        if (consulta.isEmpty()) {
            return new ArrayList<ResultadoEntiti>();
        } else {
            resultados = null;
            resultados = new ArrayList<ResultadoEntiti>();
            CopyOnWriteArrayList<ResultadoEntiti> sincrnialist = new CopyOnWriteArrayList<ResultadoEntiti>();
            ExecutorService executor = Executors.newFixedThreadPool(15);
            for (int ht = 0; ht <= (paginas.size() / 4); ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    continue;
                } else {
                    executor.submit(new TestHilos(sincrnialist, ht, consulta));
                }
            }
            for (int ht = (paginas.size() / 4) + 1; ht <= (paginas.size() / 4) * 2; ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    continue;
                } else {
                    executor.submit(new TestHilos(sincrnialist, ht, consulta));
                }
            }
            for (int ht = ((paginas.size() / 4) * 2) + 1; ht <= (paginas.size() / 4) * 3; ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    continue;
                } else {
                    executor.submit(new TestHilos(sincrnialist, ht, consulta));
                }
            }
            for (int ht = ((paginas.size() / 4) * 3) + 1; ht < paginas.size(); ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    continue;
                } else {
                    executor.submit(new TestHilos(sincrnialist, ht, consulta));
                }
            }
            for (int ht = 0; ht < paginas.size(); ht++) {
                if (paginas.get(ht).getDominio().equals("www.amazon.com") || paginas.get(ht).getDominio().equals("www.ebay.com")) {
                    executor.submit(new TestHilos(sincrnialist, ht, consulta));
                }
            }
            executor.shutdown();
            while (executor.awaitTermination(1, TimeUnit.HOURS)) {

                System.out.println("Tamaño: " + sincrnialist.size());
                resultados = sincrnialist;
                break;
            }
        }
        System.out.println("Listo, ah Probar!");
        Runtime.getRuntime().gc();
        return resultados;
    }

    private class TestHilos implements Runnable {

        List<ResultadoEntiti> list;
        String consulta;
        int ht;

        public TestHilos(List<ResultadoEntiti> list, int ht, String consulta) {
            this.list = list;
            this.ht = ht;
            this.consulta = consulta;
        }

        @Override
        public void run() {
            list.addAll(ScraperMagicBean.getINSTANCIA().getDataFromWebShop(consulta.toLowerCase(), paginas.get(ht).getDominio(), paginas.get(ht).getMtdQ(), paginas.get(ht).getTagThumb(), paginas.get(ht).getTagTitle(), paginas.get(ht).getTagPrice(), paginas.get(ht).getTagDiv(), paginas.get(ht).getNomTienda(), paginas.get(ht).getArgAdi(), paginas.get(ht).getBoolPost(), paginas.get(ht).getBoolHttps(), paginas.get(ht).getBoolId()));
        }
    }

    @GET
    @Path("requiero/dos/{producto}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public String verificacionPrueba(@PathParam("producto") String producto) {
        String cadena = "{",
                titulo = null,
                imagen_url = null,
                codigo = null;
        Float precio_mph = null;
        boolean valor = false;

        producto = producto.replaceAll("'", " ").replaceAll("\"", "");
        System.out.println("Aja si Entro!");
        if (!(ScraperMagicBean.getINSTANCIA().getEm().createNamedQuery("MurphysProductList.findByTitle").setParameter("title", producto).getResultList()).isEmpty()) {
            System.out.println("Aja si Entro! 2");

            System.out.println("Antes del titulo: " + producto);
            titulo = ((MurphysProductList) ScraperMagicBean.getINSTANCIA().getEm().createNamedQuery("MurphysProductList.findByTitle").setParameter("title", producto).setMaxResults(5).getResultList().get(0)).getTitle();
            System.out.println("Despues del titulo: " + titulo);
            if (comprobarNombres(producto.toLowerCase(), titulo.toLowerCase()) > 84) {
                System.out.println(comprobarNombres(producto.toLowerCase(), titulo.toLowerCase()));
                imagen_url = ((MurphysProductList) ScraperMagicBean.getINSTANCIA().getEm().createNamedQuery("MurphysProductList.findByTitle").setParameter("title", titulo).setMaxResults(5).getResultList().get(0)).getImageUrl();
                System.out.println("Despues del imagen: " + imagen_url);
                precio_mph = ((MurphysProductList) ScraperMagicBean.getINSTANCIA().getEm().createNamedQuery("MurphysProductList.findByTitle").setParameter("title", titulo).setMaxResults(5).getResultList().get(0)).getWhosale();
                System.out.println("Despues del precio: " + precio_mph);
                codigo = ((MurphysProductList) ScraperMagicBean.getINSTANCIA().getEm().createNamedQuery("MurphysProductList.findByTitle").setParameter("title", titulo).setMaxResults(5).getResultList().get(0)).getProductCode();
                valor = true;
            } else if (comprobarNombres(producto.toLowerCase(), titulo.toLowerCase()) < 85) {
                System.out.println(comprobarNombres(producto.toLowerCase(), titulo.toLowerCase()));
                valor = false;
                imagen_url = null;
                precio_mph = null;
                titulo = null;
                codigo = null;
            }
        } else {
            valor = false;
            imagen_url = null;
            precio_mph = null;
            titulo = null;
            codigo = null;
        }

        cadena = cadena + "\"titulo\": \"" + (titulo==null ? titulo : titulo.replaceAll("\"", "\\\\\"").replaceAll("&", "and")) + "\", \"imagen_url\":\"" + imagen_url + "\", \"p_t\":\"" + valor + "\"," + "\"precio_mph\":\"" + precio_mph + "\","+ "\"code_id\":\"" + codigo + "\" }";
        Runtime.getRuntime().gc();
        System.out.println("Listo, ah Probar!");
        return cadena;
    }

    private float comprobarNombres(String Titulo, String Respuesta) {
        int cuenta = 0, longitud = Titulo.split(" ").length;
        Titulo = Titulo.replaceAll("[-|()|\'|\"|–]", "").replaceAll("\\bby\\b", "").replaceAll("\\bBY\\b", "").replaceAll("\\bBy\\b", "").replaceAll("\\btrick\\b", "");
        Respuesta = Respuesta.replaceAll("[-|()|\'|\"]", "").replaceAll("\\bby\\b", "").replaceAll("\\bBY\\b", "").replaceAll("\\bBy\\b", "").replaceAll("\\btrick\\b", "");
        System.out.println("Titulo: " + Titulo + " Y Respuesta: " + Respuesta);
        for (String token : Titulo.split(" ")) {
            if (Respuesta.contains(token)) {
                cuenta++;
            }
        }
        return (cuenta * 100) / longitud;
    }

}
