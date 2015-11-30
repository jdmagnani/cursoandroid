package br.com.cursoandroid.fragments;

import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

import br.com.cadastro.modelo.Prova;
import br.com.cursoandroid.R;
import br.com.cursoandroid.cadastro.ProvasActivity;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class ListaProvasFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.provas_lista,container, false);
		
		ListView ListViewProvas = (ListView) layout.findViewById(R.id.lista_provas);
		
		Prova prova1 = new Prova("25/11/205","Finanças");
		prova1.setTopicos(Arrays.asList("Juros Simples","Juros Compostos", "Fluxo de Caixa"));
		
		Prova prova2 = new Prova("20/12/205","Lógica de Programação");
		prova2.setTopicos(Arrays.asList("Estruturas de Desição","Laços", "Busca binária"));
		
		List<Prova> NovasProvas = Arrays.asList(prova1,prova2);
		
		ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getActivity(), android.R.layout.simple_list_item_1,NovasProvas);
		ListViewProvas.setAdapter(adapter);
		
		ListViewProvas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				
				Prova provaSelecionada = (Prova) adapter.getItemAtPosition(posicao);				
				ProvasActivity calendarioProvas = (ProvasActivity) getActivity();
			    calendarioProvas.selecionaProva(provaSelecionada);								
			}			
		});		
		return layout;		
	}	
}
