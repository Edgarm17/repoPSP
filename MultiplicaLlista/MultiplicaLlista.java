package multiplicallista;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MultiplicaLlista{
	
	//Objecte que ha de realitzar la tasca
	//Com va a retornar un valor, ha de ser Callable
	
	static class Multiplicacio implements Callable<Integer>{

		// atributs de la classe: valors dels operands
		
		private int operador1;
		private int operador2;
		
		
		
		//constructor
		
		public Multiplicacio(int operador, int operador2) {
			this.operador1 = operador;
			this.operador2 = operador2;
		}

		
		
		//funcio obligatoria call() de la interficie callable
		
		@Override
		public Integer call() throws Exception {
			// TODO Auto-generated method stub
			return operador1 * operador2;
		}
		
	}

	public static void main(String[] args) {
		int randomNum1,randomNum2;
		
		//Creem una instància del gestor de fils ThreadPoolExecutor
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		
		//Creem una llista d'objectes multiplicaciṕ (Callable)
		List<Multiplicacio> llistaTasques = new ArrayList<Multiplicacio>();
		
		//Creem els objectes i els afegim a la llista
		for(int i=0;i<10;i++) {
			//Creem objecte amb dos valors aleatoris
			Multiplicacio m = new Multiplicacio((int)(Math.random()*10),(int)(Math.random()*10));
			
			//Afegim l'objecte a la llista
			llistaTasques.add(m);
		}
		

	}

}
