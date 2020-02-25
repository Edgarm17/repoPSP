package solucioexamen;



/**************************************/
/* 		 Exercici 2 Examen PSP  	  */
/* 		 IES LLu�s Simarro            */
/*		 Curs 19/20                   */
/**************************************/


// imports


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Exercici2 {
	// nombre de terminals
	static private final ArrayList<String> paraules = new ArrayList<String>();
	// Longitud m�xima de l'array
	static private final int LONGITUD = 20;

	// Quantitat de participants
	static private final int NPARTICIPANTS = 5;

	// Acc�s en exclusi� m�tua a l'array de cadenes
	private static final Semaphore MUTEX = new Semaphore(1);

	static class Participant extends Thread {

		// identificador del participant
		private int id;

		public Participant(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			String lletres = "ABC�DEFGHIJKLMNOPQRSTUVWXYZ";
			boolean array_ple = false;
			while (!array_ple) {
				try {
					// intentem accedir al recurs
					MUTEX.acquire();
					// quan accedim, anem a llegir l'�ltima paraula
					String paraula = "";
					Random rn = new Random();
					if (paraules.size() == 0) {
						// �s el primer, crea una paraula de 10 caracters
						// aleat�ria
						for (int i = 0; i < 10; i++) {
							paraula += "" + lletres.charAt(rn.nextInt(lletres.length()));
						}
						System.out.println("Participant: " +id + " Paraula("+paraules.size()+"): " + paraula);
						// afegim la paraula a l'array
						paraules.add(paraula);
					} else {
						// comprovem mida de l'array, per si est� ja ple
						if (paraules.size() == LONGITUD) {
							array_ple = true;
							System.out.println("Participant: " + id + " Array ja est� ple:  Paraula Final: " + paraules.get(paraules.size() - 1));
						} else {
							// hem de llegir l'�ltima paraula i obtenir l'�ltima lletra
							paraula = "" + paraules.get(paraules.size() - 1).charAt(9);
							for (int i = 0; i < 9; i++) {
								paraula += "" + lletres.charAt(rn.nextInt(lletres.length()));
							}
							System.out.println("Participant: " + id + " Paraula("+paraules.size()+"): " + paraula);
							// afegim la paraula a l'array
							paraules.add(paraula);
						}
					}
					// alliberem el sem�for
					MUTEX.release();
					try{
						Thread.sleep(rn.nextInt(500));
					} catch (InterruptedException e){
						e.printStackTrace();
					}

				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Quantitat de participants: "+NPARTICIPANTS);
		System.out.println("Quantitat de paraules en l'array: "+LONGITUD);
		System.out.println("");
		
		// creem els participants i els fiquem en marxa
		//ficant-los en un array per poder fer un join despr�s
        
		//Hem de crear l'array de participants indicant la quantitat inicial
        Participant[] arrayparticipants = new Participant[NPARTICIPANTS];
        
        // Creem el participant i l'assignem a la posici� de l'array
		for (int i = 0; i < NPARTICIPANTS; i++) {
			arrayparticipants[i] = new Participant(i);
		}
		// Iniciem tots els participants
		for (int i = 0; i < NPARTICIPANTS; i++) {
			arrayparticipants[i].start();
		}
		//Es fa un join a cada participant per esperar a que tots acaben
		for (int i = 0; i < NPARTICIPANTS; i++) {
			try {
				arrayparticipants[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


		String cadenaEixida = "";
		for (int i = 0; i<paraules.size(); i++) {			
			if (i==(paraules.size()-1)){
				cadenaEixida+=paraules.get(i)+".";
			}else{
				cadenaEixida+=paraules.get(i)+"-";
			}
		}
		System.out.println("Paraules encadenades: "+cadenaEixida);
		
		
	}
}
