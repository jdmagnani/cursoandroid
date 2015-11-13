package br.com.cadastro;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import br.com.cadastro.dao.AlunoDAO;
import br.com.cadastro.modelo.Aluno;

public class FormularioActivity extends Activity {

	private FormularioHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		final Button botao = (Button) findViewById(R.id.botao);
		helper = new FormularioHelper(this);
		
		
		/**
		 * Verificando se estamos editando um Aluno 
		 * e populando o formaulario com os dados a
		 * serem alterados
		 */
		final Aluno alunoParaAlterar = (Aluno) getIntent().getSerializableExtra("alunoClicado");
		
		// Criando metodo helper que popula os dados do aluno a ser alerado no formulario
		if (alunoParaAlterar != null){
			helper.populaFormulario(alunoParaAlterar);
			botao.setText("Alterar");
		}
				
		//Toast.makeText(this, "Aluno Clicado: " + alunoParaAlterar, Toast.LENGTH_SHORT).show();
				
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Toast.makeText(FormularioActivity.this, 
						"Voce clicou no botão", Toast.LENGTH_LONG).show();

				Aluno aluno = helper.pegaAlunoDoFormulario();				
				AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
				
				// Verificando se vai ser alteracao ou inclusao				
				if (alunoParaAlterar != null){
					// Vamos setar o id do aluno que vamos dar Update
					aluno.setId(alunoParaAlterar.getId());					
					dao.atualizar(aluno);
				} else {
					dao.insere(aluno);
				}				
				dao.close();
				finish();
			}
		});

		
		
	}
}
