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
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Edgar
 */
public class Tasca implements Callable<String> {

    private String url;
    private String msg;

    public Tasca(String web) {
        this.url = web;
        this.msg = "Id: ";
    }
    
    public void crearFichero(BufferedReader bf) throws IOException{
        String linea;
        File carpeta = new File("fitxers");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
        String[] partesLink = url.split("/");
        File fichero = new File("fitxers/"+partesLink[4]+".html");
        PrintWriter pw = new PrintWriter(new FileWriter(fichero));
        msg+=partesLink[4]+", Nombre y fecha: ";
        while ((linea=bf.readLine()) != null) {
            pw.println(linea);
        }
        pw.close();
    }

    @Override
    public String call() throws Exception {
        String linia;
        String random;
        random = Integer.toString((int) (Math.random() * 100000));
        while (random.length() < 5) {
            random = "0" + random;
        }
        url += random;
        URL web = new URL(url);
        BufferedReader entrada = new BufferedReader(new InputStreamReader(web.openStream()));
        crearFichero(entrada);
        String lineaTitulo = "";
        String pattern = "<title>";
        Pattern pat = Pattern.compile(pattern);

        while ((linia = entrada.readLine()) != null) {
            Matcher m = pat.matcher(linia);
            if (m.find()) {
                lineaTitulo = linia;
                break;
            }

        }
        lineaTitulo = lineaTitulo.replace("<title>", "");
        lineaTitulo = lineaTitulo.replace("</title>", "");

        String[] partesTitulo = lineaTitulo.split("-");
        msg+=lineaTitulo;
        System.out.println(partesTitulo[0].trim());
        entrada.close();
        return partesTitulo[0].trim();
    }

}
