package br.com.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlunosActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);					
		
		ListView lista = (ListView) findViewById(R.id.lista);
		
		String[] alunos = {"Jezinho","Jãozinho", "Mariazinha"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,alunos);
		
		lista.setAdapter(adapter);
		
		// Criando listener para mapear o click nos elementos da lista
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				
				Toast.makeText(ListaAlunosActivity.this, "A posição : " + posicao, Toast.LENGTH_SHORT).show();
				
			}
		});	
		
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {

				Toast.makeText(ListaAlunosActivity.this, "Aluno clicado é : " + adapter.getItemAtPosition(posicao), Toast.LENGTH_LONG).show();
				// vai consumir o recurso sozinho
				return true;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
		
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.novo:
			
			// this = onde estou, FormularioActivity.class onde vou
			Intent IrParaFormulatio = new Intent(this, FormularioActivity.class);
			startActivity(IrParaFormulatio);
			
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}

