package br.com.cursoandroid.cadastro;

import br.com.cursoandroid.R;
import br.com.cursoandroid.R.id;
import br.com.cursoandroid.R.layout;
import br.com.cursoandroid.fragments.MapaFragment;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MostraAlunosProximosActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		
		android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.mapa, new MapaFragment());
		tx.commit();
	}
}
