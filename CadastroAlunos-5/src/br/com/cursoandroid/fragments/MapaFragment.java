package br.com.cursoandroid.fragments;

import java.util.List;

import br.com.cadastro.dao.AlunoDAO;
import br.com.cadastro.modelo.Aluno;
import br.com.cursoandroid.cadastro.utils.AtualizaLocalizacao;
import br.com.cursoandroid.cadastro.utils.Localizador;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends SupportMapFragment {
	
	@Override
	public void onResume() {
		
		
//		Localizador localizador = new Localizador(getActivity());
//		LatLng local = localizador.getCoordenadas("R. Dr. Ricardo Benetton Martins, 1.000");
//		centralizaNoLocal(local);	
		
		Localizador localizador = new Localizador(getActivity());
		new AtualizaLocalizacao(getActivity(), this);
		
		
		AlunoDAO dao = new AlunoDAO(getActivity());
		List<Aluno> alunos = dao.getLista();
		dao.close();
		
		for (Aluno aluno : alunos) {
			
			LatLng coordenadaAluno = localizador.getCoordenadas(aluno.getEndereco());
			if (coordenadaAluno != null) {
				MarkerOptions marcador = new MarkerOptions()
					.position(coordenadaAluno)
					.title(aluno.getNome()).snippet(aluno.getEndereco());
				
				getMap().addMarker(marcador);
				
			}
		}		
	}	
	public void centralizaNoLocal(LatLng local) {		
		GoogleMap mapa = getMap();
		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 17)); // segundo argumento e o ZOOM				
	}

}
