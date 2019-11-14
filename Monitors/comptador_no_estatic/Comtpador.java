package comptadorsynchronized;

public class Comptador {
	//variable comptador que es podrà incrementar des de diversos processos/fils
	int comptador = 0;
    //mètode per incrementar el valor
	
	//sense  synchronized, no garantim que es farà en exclusió mútua
	public void incrementa(){
      this.comptador ++;
    }
	//amb  synchronized, garantim que es farà en exclusió mútua
	/*public synchronized void incrementa(){
      this.comptador ++;
    }*/
	//amb  synchronized, garantim que es farà en exclusió mútua
	// ara sincronitzant un bloc, no el mètode sencer
	/*public void incrementa(){
		synchronized(this) {
	      this.comptador ++;
		}
	}*/
    //mètode per mostrar el valor del comptador
    public int mostra() {
    	return this.comptador;
    }
}

