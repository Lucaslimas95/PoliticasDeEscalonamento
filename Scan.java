package politicas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

	//Construtor da classe.
	//que recebe os dados e chama o metodo para ordenar os processos de acordo com a politica.
public class Scan extends Politica {
	private int accessTime;
	private int accessTimeLocal = 0;
	private Disco disco;
	private List<Processo> processosEmOrdem = new ArrayList<Processo>();
	private List<Processo> retardatarios = new ArrayList<Processo>();

	public Scan(List<Processo> processos, String nome, Disco disco) {
		super(nome, disco);
		ordenarSCAN(processos, disco.getPartida(), (disco.getSetores() - 1));
	}

	//Metodo utilizado para ordenar os processos que estao na fila de requisicao,
	//e seleciona qual o primeiro passo e quais processos serao atendidos primeiro.
	public void ordenarSCAN(List<Processo> processos, int partida, int tamanhoDisco) {
		List<Processo> ordenados = new ArrayList<Processo>();
		List<Processo> temp = new ArrayList<Processo>();
		disco = this.getDisco();
		
		temp = processos.stream().sorted(Comparator.comparing(Processo::getChegada)).collect(Collectors.toList());

		Processo primeiro = temp.get(0);
		
		//Ordena os processos caso o atendimento for comecar para a direita.
		ordenados = temp.stream().sorted(Comparator.comparing(Processo::getLatency)).collect(Collectors.toList());
		temp.clear();
		//Ordena os processos caso o antendimento for comecar para a esquerda.
		temp = ordenados.stream().sorted(Comparator.comparing(Processo::getLatency).reversed()).collect(Collectors.toList());

		//Define qual sera o primeiro passo em funcao do primeiro processo a ser atendido 
		if(primeiro.getLatency() < partida) {
			calcularEsquerda(temp, partida, primeiro);
		}else if(primeiro.getLatency() > partida) {
			calcularDireita(ordenados, partida, primeiro);

		}
		
		//Apos a execucao dos calculos de todos os processos chama os metodos para calculo dos tempos medios.
		calcularAccessTimeMedio(accessTimeLocal, processosEmOrdem);
		calcularWaitingTime(processosEmOrdem);
	}

	//Executa caso o sentido de atendimentoseja para a esquerda.
	private void calcularEsquerda(List<Processo> ordenados, int partida, Processo primeiro) {
		List<Processo> direita = new ArrayList<Processo>();
		Processo anterior = primeiro;
		
		for(Processo e: ordenados) {
			if(e == primeiro) //Executa caso seja o primeiro de todos os processos, caso contrario analisa se o processo ja chegou a lista e se esta a esquerda do processo atual.
				partida = adicionarEsquerda(e, partida);
			else if((e.getChegada() <= accessTimeLocal) && (e.getLatency() < anterior.getLatency())) { 
				partida = adicionarEsquerda(e, partida);
				anterior = e;
			}else //caso contrario adiciona a uma nova lista
				retardatarios.add(e);
		}

		direita = retardatarios.stream().sorted(Comparator.comparing(Processo::getLatency)).collect(Collectors.toList());

		retardatarios.clear();
		
		//Realiza a chamada para a mudanca no sentido de leitura.
		if(direita.size() > 0)
			calcularDireita(direita, (- partida), anterior);
	}

	//Executa caso o sentido de atendimento seja para a direita.
	private void calcularDireita(List<Processo> ordenados, int partida, Processo primeiro) {
		List<Processo> esquerda = new ArrayList<Processo>();
		Processo anterior = primeiro;
		
		for(Processo e: ordenados) {
			if(e == primeiro) //Executa caso seja o primeiro de todos os processos, caso contrario analisa se o processo ja chegou a lista e se esta a direita do processo atual.
				partida = adicionarDireita(e, partida);
			else if((e.getChegada() <= accessTimeLocal) && (e.getLatency() > anterior.getLatency())) { 
				partida = adicionarDireita(e, partida);
				anterior = e;
			}else 
				retardatarios.add(e);//caso contrario adiciona a uma nova lista  
		}
		esquerda = retardatarios.stream().sorted(Comparator.comparing(Processo::getLatency).reversed()).collect(Collectors.toList());

		retardatarios.clear();

		//Realiza a chamada para a mudanca no sentido de leitura.
		if(esquerda.size() > 0) {
			int proximo = esquerda.get(0).getLatency();
			int tamanho = ((disco.getSetores()) - 1);
			int newPartida = ((tamanho - partida) + (tamanho - proximo));
			
			calcularEsquerda(esquerda, newPartida, anterior);
		}
	}

	//Realiza o calculo dos tempos do processo caso esteja a esquerda, e adiciona a lista final.
	public int adicionarEsquerda(Processo e, int partida) {
		accessTime = (e.getTrilha() + (partida - e.getLatency()) + e.getTransfer());
		e.setAcesso(accessTime);
		accessTimeLocal += accessTime;		
		partida = e.getLatency();
		this.processosEmOrdem.add(e);

		return partida;
	}

	//Realiza o calculo dos tempos do processo caso esteja a esquerda, e adiciona a lista final.
	private int adicionarDireita(Processo e, int partida) {
		accessTime = (e.getTrilha() + (e.getLatency() - partida) + e.getTransfer());
		e.setAcesso(accessTime);
		accessTimeLocal += accessTime;
		partida = e.getLatency();
		this.processosEmOrdem.add(e);

		return partida;
	}

}


		 

