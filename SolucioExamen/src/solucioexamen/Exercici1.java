package solucioexamen;



/**************************************/
/* 		 Exercici 1 Examen PSP  	  */
/* 		 IES LLu�s Simarro            */
/*		 Curs 19/20                   */
/**************************************/


// imports

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/* 100 fils generen aleat�riament cara o creu 0 - 1 amb un percentatge  del 50% */
/*Tornen els valors de cara i creu obtinguts*/
/*El programa principal suma totes les cares i totes les creus, mostra el resultat i decideix cara o creu*/
/* Per cada intent, guarda el resultat en un fitxer tirades */

public class Exercici1 {

	// nombre de fils
	private static final int NFILS = 100;
	// nombre de tirades de moneda
	private static final int TIRADES = 1000;

	static class TiraMoneda implements Callable<String> {

		private int tirades;
		private int id;

		public TiraMoneda(int id, int tirades) {
			this.tirades = tirades;
			this.id = id;
		}

		@Override
		public String call() throws Exception {
			String resultat ="";
			int cares=0;
			int creus=0;
			// creem la variable random per simular cara o creu
			Random rn = new Random();
			// valor de la tirada
			int valorTirada = 0;
			for (int i = 0; i < tirades; i++) {
				valorTirada = rn.nextInt(2);
				// 0 �s cara i 1 �s creu
				if (valorTirada == 0) {
					cares++;
				} else {
					creus++;
				}
			}
			resultat=""+cares+":"+creus;
			System.out.println("Fil "+id+": Cares: "+cares+", Creus: "+creus);
			return resultat;
		}

	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// classe per a executar diversos fils, en concret 100.
		// Preparem el gestor de fils
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NFILS);
		// Llista d'elements Tiramoneda. Que seran les tasques que executarem
		List<TiraMoneda> llistaTasques = new ArrayList<TiraMoneda>();

		// Inseriem els elements a executar en la llista. Cridarem amb invokeAll
		// a totes les tasques.
		for (int i = 1; i <= NFILS; i++) {
			// creem l'objecte Tiramoneda
			TiraMoneda tm = new TiraMoneda(i,TIRADES);
			// afegim l'objecte a les llistes
			llistaTasques.add(tm);
		}
		// Creem una llista de valors Future (que requereix la funci� invokeAll
		// per tornar i guardar els valors de les execucions)
		List<Future<String>> llistaResultats;
		// Fem que s'executen totes les tasques cridant-les amb invokeAll (des
		// del gestor de fils)
		llistaResultats = executor.invokeAll(llistaTasques);
		// Evitem que s'execute cap altre proc�s fins que acaben els que hem
		// ficat en marxa.
		executor.shutdown();

		// ara hem de recorrer l'array i obtenir la suma de cares i creus
		int totalCares = 0;
		int totalCreus = 0;
		for (int i = 0; i < llistaResultats.size(); i++) {
			// obtenim el valor de RecompteMonedes del resultat
			Future<String> resultat = llistaResultats.get(i);
			// actualitzem valors de cares i creus
			totalCares += Integer.parseInt(resultat.get().split(":")[0]);
			totalCreus += Integer.parseInt(resultat.get().split(":")[1]);
		}
		// Mostrem el resultat final:
		System.out.println("Total de cares: " + totalCares + ", Total de creus: " + totalCreus + ", Total tirades: "
				+ (totalCares + totalCreus));

		// hem de veure la quantitat de l�nies que t� el fitxer
		FileReader fitxerIn = null;
		int comptadorLinies = 1;
		try {
			// Creem el fitxer associat al fitxer real
			File f = new File("moneda.txt");
			// Comprovem si realment existeix i
			if (f.exists() && f.isFile()) {
				fitxerIn = new FileReader(f);
				BufferedReader br = new BufferedReader(fitxerIn);
				while (br.readLine() != null) {
					comptadorLinies++;
				}
				br.close();
			} else {
				System.out.println("Primer llan�ament, fitxer moneda.txt no existeix.");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Tanquem els streams.
		if (fitxerIn != null) {
			try {
				fitxerIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// ara hem d'escriure els resultats en un fitxer
		FileWriter fitxerOut = null;
		try {
			fitxerOut = new FileWriter("moneda.txt",true);
			BufferedWriter bw = new BufferedWriter(fitxerOut);
			if (totalCares > totalCreus) {
				bw.write("Llan�ament " + comptadorLinies+": cara (" + totalCares + "/" + totalCreus + ")");
				bw.newLine();
			} else if (totalCares < totalCreus) {
				bw.write("Llan�ament " + comptadorLinies+": creu (" + totalCares + "/" + totalCreus + ")");
				bw.newLine();
			} else {
				bw.write("Llan�ament " + comptadorLinies+": empat (" + totalCares + "/" + totalCreus + ")");
				bw.newLine();
			}
			bw.close();
			// tanquem el fitxer
			fitxerOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Tanquem els streams.
		if (fitxerOut != null) {
			try {
				fitxerOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
