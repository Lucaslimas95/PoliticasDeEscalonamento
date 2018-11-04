package politicas;

import java.util.List;

//Classe abstrata contendo os metodos compartilhados por todas as politicas.
public abstract class Politica {
	private String nome;
	private Disco disco;
	private float accessTimeMedio;
	private float waitingTimeMedio;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public float getAccessTimeMedio() {
		return accessTimeMedio;
	}

	public void setAccessTimeMedio(float accessTimeMedio) {
		this.accessTimeMedio = accessTimeMedio;
	}

	public float getWaitingTimeMedio() {
		return waitingTimeMedio;
	}

	public void setWaitingTimeMedio(float waitingTimeMedio) {
		this.waitingTimeMedio = waitingTimeMedio;
	}
	
	public Disco getDisco() {
		return disco;
	}

	public void setDisco(Disco disco) {
		this.disco = disco;
	}
	
	public Politica(String nome, Disco disco) {
		this.nome = nome;
		this.setDisco(disco);
	}
	
	
	//Metodo utilizado para somar os waitingTime de todos os processos da politica.
	public void calcularWaitingTime(List<Processo> processos) {
		int espera = 0;
		int waitingTime = 0;
		int total = 0;
		
		for(Processo e: processos) {
			waitingTime = (espera - e.getChegada());
			e.setEspera(waitingTime);
			total += waitingTime;
			espera += e.getAcesso();
		}
		calcularWaitingTimeMedio(total, processos);//chama o metodo para calcular o tempo medio.
		
	}
	
	//Metodo utilizado para calcular o accessTime das politicas criadas, a partir dos dados fornecidos,
	//para cada politica criada.
	public void calcularAccessTimeMedio(int tempo, List<Processo> processos) {
		float temp = (tempo / processos.size());
		setAccessTimeMedio(temp);
	}
	
	//Metodo utilizado para calcular o waitingTime medio das politicas criadas, a partir dos dados especificos 
	//de cada politica instanciada.
	public void calcularWaitingTimeMedio(int tempo, List<Processo> processos) {
		float temp = (tempo / processos.size());
		setWaitingTimeMedio(temp);	
	}

	
	
}
