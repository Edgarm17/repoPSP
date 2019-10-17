/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simarro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;




/**
 *
 * @author Edgar
 */
public class AppImdb {

    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
    	File html = new File("pelicules.html");
    	FileWriter fw = new FileWriter(html);
    	PrintWriter pw = new PrintWriter(fw);
    	
    	if (!html.exists()) {
			html.createNewFile();
		}
    	
    	pw.println("<html><head><title>Índex de películes</title></head><body><h1>Películes:</h1>");
    	
    	
        String url = "https://www.imdb.com/title/tt00";
        
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        
        List<Tasca> llistaTasques = new ArrayList<Tasca>();
        
        for (int i = 0; i < 50; i++) {
            Tasca novaTasca = new Tasca(url);
            llistaTasques.add(novaTasca);
        }    
        
        List<Future<String[]>> llistaResultats;
        llistaResultats = executor.invokeAll(llistaTasques);
        executor.shutdown();
        
        for (int i = 0; i < llistaResultats.size(); i++) {
            Future<String[]> resultat = llistaResultats.get(i);
            try {
                pw.println("Pelicula "+(i+1)+"<div><a href="+url+resultat.get()[1]+">"+resultat.get()[0]+"</a></div>");
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
        pw.println("</body></html>");
        pw.close();
        
    }

}
