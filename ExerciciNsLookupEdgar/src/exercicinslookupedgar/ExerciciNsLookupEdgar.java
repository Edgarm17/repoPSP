/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercicinslookupedgar;

import com.sun.xml.internal.ws.util.StringUtils;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Edgar
 */
public class ExerciciNsLookupEdgar {

    /**
     * @param args the command line arguments
     */
    static Scanner s = new Scanner(System.in);
    
   private static String lookup(String ip){
        InetAddress node;
        String resposta;
        try{
            node = InetAddress.getByName(ip);
            
        }catch(UnknownHostException e){
             return "No s'ha pogut trobar la correspondència " + ip;
        }
        if(!esIp(ip)){
            resposta = "Resposta: \n";
            resposta+="\tNom host:"+ip+"\n";
            resposta+="\tAdreça:"+node.getHostAddress();
            
                    
            return resposta;
        }else {

            resposta = "Resposta: \n";
            resposta+="\tNom host:"+node.getHostName()+"\n";
            resposta+="\tAdreça:"+ip;
            
                    
            return resposta;
        }
    }

    private static boolean esIp(String ip) {
        
        Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        
        return PATTERN.matcher(ip).matches();
    }
    
    public static void main(String[] args) {
        
        int seleccion = 0;
        do {
            System.out.println("SIMULACIÓ NSLOOKUP");
            System.out.println("1.Introduïr hostname o IP");
            System.out.println("2.Sortir");
            
            seleccion = s.nextInt();
            
            switch (seleccion) {
                case 1:
                    System.out.println("Introdueix nom del host o IP:");
                    String valor = s.next();
                    System.out.println(lookup(valor));
                    System.out.println("");
                    System.out.println("");
                    break;
                case 2:
                    
                    break;
                default:
                    System.out.println("Opció no vàlida, introdueix una opció correcta.");
                    
            }
        } while (seleccion != 2);
        
    }
    
}
