package br.com.cursoandroid.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.cadastro.modelo.Prova;
import br.com.cursoandroid.R;



@SuppressLint("NewApi")
public class DetalhesProvaFragment extends Fragment{

	private Prova prova;
	
	/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
                            Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.provas_detalhes, container, false);

        if (getArguments() != null) {
        	
        	Log.i("ARGUMENTOS", "Dentro do If");
        	
        	prova = (Prova) getArguments().getSerializable("prova");
        	
        	Toast.makeText(getActivity(), "Topicos : " + prova.getTopicos().toString(), Toast.LENGTH_SHORT).show();
        	
            TextView materia = (TextView) layout.findViewById(R.id.detalhe_prova_materia);
            TextView data = (TextView) layout.findViewById(R.id.detalhe_prova_data);
            ListView topicos = (ListView) layout.findViewById(R.id.detalhe_prova_topicos);
            
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
            		android.R.layout.simple_list_item_1,prova.getTopicos());
            
            materia.setText(prova.getMateria());
            topicos.setAdapter(adapter);
        }
        return layout;
    }
    */
        
    public View onCreateView(LayoutInflater inflater, 
            ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.provas_detalhes,
                        container, false);

        if  (getArguments() != null) {
            this.prova = (Prova) getArguments().getSerializable("prova");
        }

        if (this.prova != null) {
            TextView materia = (TextView) layout
                    .findViewById(R.id.detalhe_prova_materia);
            TextView data = (TextView) layout
                    .findViewById(R.id.detalhe_prova_data);
            ListView topicos = (ListView) layout
                    .findViewById(R.id.detalhe_prova_topicos);

            materia.setText(this.prova.getMateria());
            data.setText(this.prova.getData());

            ArrayAdapter<String> adapter = 
                new ArrayAdapter<String>(
                    getActivity(), 
                    android.R.layout.simple_list_item_1,
                    prova.getTopicos());

            topicos.setAdapter(adapter);
        }
        return layout;
    }
}

