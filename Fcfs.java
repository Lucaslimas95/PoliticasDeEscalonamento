package politicas;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Fcfs extends Politica{
	private int accessTime;
	private int accessTimeLocal;
	
	//Construtor da classe.
	//que recebe os dados e chama o metodo para ordenar os processos de acordo com a politica.
	public Fcfs(List<Processo> processos, String nome, Disco disco) {
		super(nome, disco);
		ordenarFcfs(processos, disco.getPartida());
	}
	
	//Metodo que realiza a ordenacao dos processos de acordo com as diretrizes da politica, 
	//e passa a lista obtida para o calculo dos tempos de acesso.
	public void ordenarFcfs(List<Processo> processos, int partida) {
		List<Processo> ordem = processos.stream().sorted(Comparator.comparing(Processo::getChegada))
				.collect(Collectors.toList());
		
		calcularAccessTime(partida, ordem);
	}
	
	
	//Metodo utilizado para calcular o tempo de acesso de cada processo fornecido,
	//e posteriormente realizar a chamada dos metodos que iram calcular o tempo de
	//acesso medio e de waitingTime.
	public void calcularAccessTime(int partida, List<Processo> processos) {
		accessTime = 0;
		accessTimeLocal = 0;
		for(Processo e: processos) {
			if(e.getLatency() >= partida) {
				accessTime = (e.getTrilha() + (e.getLatency() - partida) + e.getTransfer());
				e.setAcesso(accessTime);
				accessTimeLocal += accessTime;
			}
			else {
				accessTime = (e.getTrilha() + (partida - e.getLatency()) + e.getTransfer());
				e.setAcesso(accessTime);
				accessTimeLocal += accessTime;
			}
			partida = e.getLatency();
		}
		calcularAccessTimeMedio(accessTimeLocal, processos);
		calcularWaitingTime(processos);
	}


}
