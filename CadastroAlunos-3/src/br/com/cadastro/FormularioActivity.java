package br.com.cadastro;

import java.io.File;
import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import br.com.cadastro.dao.AlunoDAO;
import br.com.cadastro.modelo.Aluno;

public class FormularioActivity extends Activity {

		
	private FormularioHelper helper;
	private String nomeArquivoFoto;

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

		// Retornando a foto
		ImageView foto = helper.getFoto();
		foto.setOnClickListener(new OnClickListener() {
			
			

			@Override
			public void onClick(View v) {
				
				/**
				 * Toda vez que o usuario clicar na imagem, queremos abrir a 
				 * camera do Android e tirar um "retrato do caboclo" e armazenar no
				 * seu cadastro! 
				 * 
				 * E agora quem podera nos ajudar?? as Intents!! 
				 * 
				 * Vamos trabalhar com o objeto media store!
				 */
				Intent abrirCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				// Definindo o nome do arquivo de foto
				nomeArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".png";
				File arquivo = new File(nomeArquivoFoto);
				
				// passando este nome para a intent (camera)
				Uri caminhoDaFoto = Uri.fromFile(arquivo);
				abrirCamera.putExtra(MediaStore.EXTRA_OUTPUT, caminhoDaFoto);
				
				/**
				 * Vamos analisar um pouco a situacao, aqui neste caso, nos
				 * queremos salvar a imagem em um diretorio definido mas tambem
				 * precisamos salvar esta informacao no nosso banco de dados!
				 * 
				 * 
				 */
				startActivityForResult(abrirCamera, 999);
				
				
				
			}
		});
		
		
		
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == 999) {
			
			if (resultCode == Activity.RESULT_OK) {
				
				// Carregando a imagem no ImageView
				helper.carregaImagem(nomeArquivoFoto);
			} else {
				nomeArquivoFoto = null;
			}
		}
		
	}
}
