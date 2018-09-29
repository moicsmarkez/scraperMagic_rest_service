package control.scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.ResultadoEntiti;
import models.ScraperMagic;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author moicsmarkez
 */
@ManagedBean
@ApplicationScoped
public class ScraperMagicBean implements Serializable {

    private static final Pattern patternDomainName;
    private Matcher matcher;
    private static final String PRICE_PATTERN = "[0-9]+([,.][0-9]{1,2})?";

    static {
        patternDomainName = Pattern.compile(PRICE_PATTERN, Pattern.CASE_INSENSITIVE );
    }

    private static final ScraperMagicBean INSTANCIA = new ScraperMagicBean();

    private String Unidad_Persistencia = "com.prueba_Prueba3_war_1.0-SNAPSHOTPU";
    EntityManagerFactory factori = Persistence.createEntityManagerFactory(Unidad_Persistencia, System.getProperties());
    EntityManager em = factori.createEntityManager();

    private ScraperMagicBean() {
    }

    public static ScraperMagicBean getINSTANCIA() {
        return INSTANCIA;
    }

    private String getPriceBien(String url) {
        String PriceBien = "";
        matcher = patternDomainName.matcher(url);
        if (matcher.find()) {
            PriceBien = matcher.group(0);
        }
        return PriceBien;
    }

    public List<ResultadoEntiti> getDataFromWebShop(String query, String dominio, String mtd_q, String tag_thumb, String tag_title, String tag_price, String tag_div, String nomTienda, String adicional, boolean isPost, boolean https, boolean jsonID) {
        List<ResultadoEntiti> results = new ArrayList<ResultadoEntiti>();
        ResultadoEntiti rstl = new ResultadoEntiti();
        String Precio = "",
                imagen_link = "",
                http = "https";

        if (https) {
            http = "https";
        } else {
            http = "http";
        }
        Elements links;
        Element link = null;
        try {
            query = query.replaceAll("[-|+|#|.|\"|–|,|.]", "").replaceAll("trick", "");
            if (isPost) {
                String request = http + "://" + dominio + "/";
                //  System.out.println("Enviando requerimiento...." + request + query);
                Document doc = Jsoup.connect(request).
                        data("kw", (query.contains(" ") ? query.replaceAll(" ", "+") : query)).
                        userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11").timeout(5000).
                        post();
                links = doc.select("Noup!");
            } else if (jsonID) {
                try {
                    String request = http + "://" + dominio + "/" + mtd_q + query + adicional;
                    System.out.println("(JSON) Enviando requerimiento...." + request);

                    URLConnection conexion = (URLConnection) new URL(request).openConnection();
                    conexion.setDoOutput(true);
                    conexion.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                    BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream(), Charset.forName("UTF-8")));
                    JSONObject jsonO = new JSONObject(br.readLine());
                    JSONArray arrJson = jsonO.getJSONArray(tag_div.replace(".", ""));
                    String todoJsonPagina = "";
                    for (int i = 0; i < arrJson.length(); i++) {
                        todoJsonPagina = todoJsonPagina + " " + "<div class='" + tag_div.replace(".", "") + "'>"
                                + "<div class='" + tag_title.replace(".", "") + "'>" + arrJson.getJSONObject(i).getString(tag_title.replace(".", "")) + "</div>"
                                + "<img src='" + arrJson.getJSONObject(i).getString(tag_thumb) + "' ></img>"
                                + "<div class='" + tag_price.replace(".", "") + "'>" + arrJson.getJSONObject(i).getString(tag_price.replace(".", "")) + "</div>"
                                + "</div>";
                    }
                    Document doc = Jsoup.parseBodyFragment(todoJsonPagina);
                    links = doc.select(tag_div);
                    tag_thumb = "img";
                } catch (Exception e) {
                    links = null;
                    // e.printStackTrace();
                }

            } else {
                String request = http + "://" + dominio + "/" + mtd_q + (query.contains(" ") ? query.replaceAll(" ", "+") : query) + adicional;
                System.out.println("Enviando requerimiento...." + request);
                Document doc = Jsoup
                        .connect(request)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                        .timeout(5000).get();
                links = doc.select(tag_div);

                if (dominio.equals("www.maxello.com")) {
                    Elements filas = links.get(0).select("tr");
                    String maxelloResultDiv = "";
                    for (int i = 0; i < filas.size(); i = i + 6) {
                        maxelloResultDiv = maxelloResultDiv + " " + "<div class=\"result-tmp\" >"
                                + "<div class=\"titulo\">" + filas.get(i).select(tag_title).text() + "</div>"
                                + "<img src=\"" + filas.get(i + 2).select(tag_thumb).attr("src") + "\" ></img>"
                                + "<div class=\"precio\" >" + filas.get(i + 1).select(tag_price).get(0).text() + " </div>"
                                + "</div>";
                    }
                    tag_title = ".titulo";
                    tag_price = ".precio";
                    tag_thumb = "img";

                    doc = Jsoup.parseBodyFragment(maxelloResultDiv);
                    links = doc.select(".result-tmp");
                } else if (dominio.equals("shop.misdirections.com")) {
                    Elements filas = links.get(0).select("tr");
                    String misdiretionsResultTmp = "";

                    for (int i = 1; i < filas.size(); i = i + 5) {
                        for (int j = 0; j < 3; j++) {
                            misdiretionsResultTmp = misdiretionsResultTmp + " "
                                    + "<div class=\"result-tmp\">"
                                    + "<div class=\"titulo\" >" + filas.get(i).select("td").get(j).select(".titleRow").text() + " </div>"
                                    + "<img src=\"" + (filas.get(i + 1).select("td").get(j).select(".descRow > img").attr("src").isEmpty() ? filas.get(i + 1).select("td").get(j).select(".descRow > div.qsc-html-content > p > img").attr("src") : filas.get(i + 1).select("td").get(j).select(".descRow > img").attr("src")) + "\" > </img>"
                                    + "<div class=\"precio\" > " + filas.get(i + 2).select("td").get(j).select(".price").text() + " </div>"
                                    + "</div>";
                        }
                    }

                    tag_title = ".titulo";
                    tag_price = ".precio";
                    tag_thumb = "img";

                    doc = Jsoup.parseBodyFragment(misdiretionsResultTmp);
                    links = doc.select(".result-tmp");
                }

            }

            int concidencias;
            for (Element fa : links) {
                concidencias = 0;
                if(fa.select(tag_title).text().toLowerCase().isEmpty()){
                    continue;
                }
                if (fa.select(tag_title).text().toLowerCase().startsWith(query.substring(0, 6))) {
                    for (String token : query.split(" ")) {
                        if (fa.select(tag_title).text().toLowerCase().contains(token)) {
                            concidencias++;
                        }
                    }
                }
                if (((concidencias * 100) / query.split(" ").length) > 74) {
                    link = fa;
                }
            }

            if (link == null) {
                return results;
            }

            if (!link.select(tag_thumb).attr("src").startsWith("http")) {
                if (dominio.endsWith("www.dudethatscoolmagic.co.uk")) {
                    imagen_link = http + "://" + dominio + "/" + (dominio.equals("www.magiccenterharri.com") ? "catalog/" : "acatalog/") + link.select(tag_thumb).attr("src");
                } else if (dominio.equals("www.mjmmagic.com")) {
                    imagen_link = http + "://" + dominio + "/" + (dominio.equals("www.magiccenterharri.com") ? "catalog/" : "store/") + link.select(tag_thumb).attr("src");
                } else {
                    imagen_link = http + "://" + dominio + "/" + (dominio.equals("www.magiccenterharri.com") ? "catalog/" : "") + link.select(tag_thumb).attr("src");
                }
            } else if (dominio.endsWith("www.dudethatscoolmagic.co.uk")) {
                imagen_link = (dominio.equals("www.magiccenterharri.com") ? "catalog/" : "acatalog/") + link.select(tag_thumb).attr("src");//tenercuidado desgasta 
            } else {
                imagen_link = (dominio.equals("www.magiccenterharri.com") ? "catalog/" : "") + link.select(tag_thumb).attr("src");
            }

            if (link.select(tag_price).text().contains("$") || link.select(tag_price).text().contains("USD")) {
                if(dominio.equals("www.bigguysmagic.com")){
                    Precio = "$" + getPriceBien(link.select(tag_price).text().split(" ")[0]);
                }else if(dominio.equals("www.amazon.com")){
                       Precio = "$" + getPriceBien(link.select(tag_price).text().replaceAll(" ", "."));
                }else {
                    Precio = "$" + getPriceBien(link.select(tag_price).text());
                }
            } else if (link.select(tag_price).text().contains("€") || link.select(tag_price).text().contains("Eur")) {
                Precio = "€" + getPriceBien(link.select(tag_price).text());
            } else if (link.select(tag_price).text().contains("£")) {
                Precio = "£" + getPriceBien(link.select(tag_price).text());
            }

            rstl.setTitulo(link.select(tag_title).text());
            rstl.setImagen_link(imagen_link);
            rstl.setPrecio_txt(Precio);
            rstl.setPrecio((Precio.contains(",") ? Double.parseDouble(Precio.replaceAll(",", ".").replaceAll("[$|€|£]", "")) : Double.parseDouble(Precio.replaceAll("[$|€|£]", ""))));
            rstl.setNomTienda(nomTienda);
            rstl.setIlogo(((ScraperMagic)ScraperMagicBean.getINSTANCIA().getEm().createNamedQuery("ScraperMagic.findByDominio").setParameter("dominio", dominio).setMaxResults(5).getResultList().get(0)).getLogo());

          
            results.add(rstl);

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<String> getResultadoMurphy(String request, String tag_title, String tag_price){
        String Precio=null;
        List<String> resutado = new ArrayList<String>();
        try {
            System.out.println("Peticion Murphy: "+request);
            Document doc = Jsoup
                        .connect(request)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                        .timeout(5000).get();
            
            Element linkP= doc.select(tag_price).first();
            
            Element linkT= doc.select(tag_title).first();
            
            
             if (linkP.text().contains("$") || linkP.text().contains("USD")) {
                Precio = "$" + getPriceBien(linkP.text());
            } else if (linkP.text().contains("€") || linkP.text().contains("Eur")) {
                Precio = "€" + getPriceBien(linkP.text());
            } else if (linkP.text().contains("£")) {
                Precio = "£" + getPriceBien(linkP.text());
            }
            resutado.add(linkT.text());
            resutado.add((Precio.contains(",") ? Precio.replaceAll(",", ".").replaceAll("[$|€|£]", "") : Precio.replaceAll("[$|€|£]", "")));
        
        } catch (Exception e) {
            
        }
        return resutado;
    }
    
    public String getUnidad_Persistencia() {
        return Unidad_Persistencia;
    }

    public EntityManager getEm() {
        return em;
    }

    public EntityManagerFactory getFactori() {
        return factori;
    }

}
