/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simarro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Edgar
 */
public class Tasca implements Callable<String[]> {

    private String url;
    private String id;
   

    public Tasca(String web) {
        this.url = web;
        
    }
    
    public void crearFichero(BufferedReader bf) throws IOException{
        String linea;
        File carpeta = new File("fitxers");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
        String[] partesLink = url.split("/");
        File fichero = new File("fitxers/"+partesLink[4]+".html");
        id = partesLink[4];
        PrintWriter pw = new PrintWriter(new FileWriter(fichero));
        
        while ((linea=bf.readLine()) != null) {
            pw.println(linea);
        }
        pw.close();
        bf.close();
        
    }

    @Override
    public String[] call() throws Exception {
    	
    	
    	
        String linia;
        String random;
        String[] vacio;
        
        random = Integer.toString((int) (Math.random() * 100000));
        while (random.length() < 5) {
            random = "0" + random;
        }
        url += random;
        URL web = new URL(url);
        HttpURLConnection huc = (HttpURLConnection) web.openConnection();
        int responseCode = huc.getResponseCode();
        // Si no s'ha pogut connectar, la resposta és 404
        if (responseCode==404){
        	System.out.println("La pàgina no existeix");
        	vacio = new String[] {"vacio","vacio"};
        } else {
        	BufferedReader entrada = new BufferedReader(new InputStreamReader(web.openStream()));
            crearFichero(entrada);
            BufferedReader entrada1 = new BufferedReader(new InputStreamReader(web.openStream()));
            String lineaTitulo = "";
            String pattern = "<title>";
            Pattern pat = Pattern.compile(pattern);

            while ((linia = entrada1.readLine()) != null) {
                Matcher m = pat.matcher(linia);
                if (m.find()) {
                    lineaTitulo = linia;
                    break;
                }

            }
            lineaTitulo = lineaTitulo.replace("<title>", "");
            lineaTitulo = lineaTitulo.replace("</title>", "");

            String[] partesTitulo = lineaTitulo.split("-");
            
            System.out.println("Título: "+partesTitulo[0].trim()+" Id: "+id);
            entrada1.close();
            String[] resultado = new String[] {partesTitulo[0],random};
            
        	
            
            return resultado;
        }
        
        return vacio;
        
    }

}
