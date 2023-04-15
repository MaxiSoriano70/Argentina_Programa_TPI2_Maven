package Modelos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Resultado {
	private ArrayList<Ronda> rondas = new ArrayList<Ronda>();
	private String ruta;

	public Resultado(String ruta) {
		this.ruta=ruta;
	}
	
	public Ronda buscarRonda(ArrayList<Ronda> rondas, int numero) {
	    for (Ronda ronda : rondas) {
	        if (ronda.getNumero()==numero) {
	            return ronda;
	        }
	    }
	    return null;
	}
	
	public boolean isRonda(ArrayList<Ronda> rondas, int numero) {
		int is=0;
	    for (Ronda ronda : rondas) {
	        if (ronda.getNumero()==numero) {
	            is=1;
	        }
	    }
	    if(is==1) {
	    	return true;
	    }
	    else {
	    	return false;
	    }
	}

	public void setResultados(){
		try {
			for(String linea:Files.readAllLines(Paths.get(this.ruta))) {
				int auxRonda=Integer.parseInt(linea.split(";")[0]);
				//VERIFICAMOS SI LA RONDA ESTA EN LA LISTA DE RONDAS
				if(isRonda(getRondas(), auxRonda)) {
					//BUSCAMOS LA RONDA Y LE AGREGAMOS EL PARTIDO
					Ronda rondaselecionada=buscarRonda(getRondas(), auxRonda);
					String local=linea.split(";")[1];
					String visitante=linea.split(";")[4];
					int golesLocal=Integer.valueOf(linea.split(";")[2]);
					int golesVisitante=Integer.valueOf(linea.split(";")[3]);
					Partido partido=setPartido(local,visitante,golesLocal,golesVisitante);
					rondaselecionada.getPartidos().add(partido);
				}
				else {
					//SI NO ESTA CREAMOS LA RONDA
					Ronda ronda=new Ronda(auxRonda);
					rondas.add(ronda);
					//BUSCAMOS LA RONDA Y LE AGREGAMOS EL PARTIDO
					Ronda rondaselecionada=buscarRonda(getRondas(), auxRonda);
					String local=linea.split(";")[1];
					String visitante=linea.split(";")[4];
					int golesLocal=Integer.valueOf(linea.split(";")[2]);
					int golesVisitante=Integer.valueOf(linea.split(";")[3]);
					Partido partido=setPartido(local,visitante,golesLocal,golesVisitante);
					rondaselecionada.getPartidos().add(partido);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Partido setPartido(String local,String visitante,int golesLocal,int golesVisitante) {
		Equipo l=new Equipo(local);
		Equipo v=new Equipo(visitante);
		Partido partido=new Partido(l,v);
		if(golesLocal>golesVisitante) {
			partido.setResultado(EGanador.LOCAL);
		}else if(golesLocal<golesVisitante) {
			partido.setResultado(EGanador.VISITANTE);
		}
		else {
			partido.setResultado(EGanador.EMPATE);
		}
		return partido;
	}
	
	public void mostrar_Rondas() {
		ArrayList<Ronda> rondas = this.getRondas();
		for(int i=0;i<rondas.size();i++) {
			System.out.print("Ronda "+rondas.get(i).getNumero()+" ");
			rondas.get(i).mostrar_Resultados();
		}
	}
}
