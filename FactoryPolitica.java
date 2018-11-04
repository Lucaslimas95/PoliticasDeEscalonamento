package politicas;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FactoryPolitica {

	//Metodo que recebe os parametros obtidos apos a leitura do arquivo de entrada, e a partir 
	//desses parametros constroe as politicas, para que os calculos sejam efetuados.
	public void getPolitica(Disco disco, List<Processo> processos) throws Exception {

		List<Politica> politicas = new ArrayList<Politica>();

		politicas.add(new Fcfs(processos, "FCFS", disco));

		politicas.add(new Scan(processos,  "SCAN", disco));

		//Chama o metodo que ira escrever o arquivo de saida contendo os tempos ja calculados na criacao das politicas.
		saidaFile(politicas);		
	}

	//Metodo que escreve os dados no arquivo.
	public void saidaFile(List<Politica> politicas) throws Exception  {
		FileWriter saida = new FileWriter("src/Saida.txt");

		PrintWriter gravarSaida = new PrintWriter(saida);
		for(Politica e: politicas) {
			gravarSaida.println(e.getNome());
			gravarSaida.println("-AccessTime = " + e.getAccessTimeMedio());
			gravarSaida.println("-WaitingTime = " + e.getWaitingTimeMedio());
		}
		gravarSaida.close();
	}

}
