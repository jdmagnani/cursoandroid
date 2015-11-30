package br.com.cursoandroid.cadastro;

import br.com.cadastro.modelo.Prova;
import br.com.cursoandroid.R;
import br.com.cursoandroid.fragments.DetalhesProvaFragment;
import br.com.cursoandroid.fragments.ListaProvasFragment;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;


@SuppressLint("NewApi")
public class ProvasActivity extends FragmentActivity {
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.provas);
				
		FragmentTransaction tx = getFragmentManager().beginTransaction();
		
		if (EhTablet()) {
			tx.replace(R.id.provas_lista, new ListaProvasFragment());
			tx.replace(R.id.provas_detalhes, new DetalhesProvaFragment());
		} else {
		
			tx.replace(R.id.lista_provas, new ListaProvasFragment());
		}
				
		tx.commit();
	}
	
	/**
	 * Pega o conteudo da variavel isTablet to arquivo de resource
	 * @return boolean isTablet
	 */
	private boolean EhTablet(){		
		return getResources().getBoolean(R.bool.isTablet);
	}
	
	
	public void selecionaProva(Prova prova){
		Bundle provaParaMostrar = new Bundle();
		provaParaMostrar.putSerializable("prova", prova);
		
		DetalhesProvaFragment detalhes = new DetalhesProvaFragment();
		detalhes.setArguments(provaParaMostrar);
		
		FragmentTransaction tx = getFragmentManager().beginTransaction();		
		tx.replace(R.id.provas_detalhes, detalhes);
		tx.addToBackStack(null);
		tx.commit();
	}
	
	
	
}
