package br.com.cursoandroid.cadastro;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import br.com.cadastro.dao.AlunoDAO;
import br.com.cadastro.modelo.Aluno;
import br.com.cursoandroid.R;
import br.com.cursoandroid.conversor.AlunoToJSON;
import br.com.cursoandroid.suporte.WebClient;
import br.com.cursoandroid.task.EnviaDadosDosAlunos;

@SuppressLint("NewApi")
public class ListaAlunosActivity extends Activity{
		
	private Aluno aluno;
	private ListView lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);					
		
		
		
		ListView lista = (ListView) findViewById(R.id.lista);
	
		
		
		// Regisrando que a lista devera carregar o menu de contexto
		registerForContextMenu(lista);
				
		// Criando listener para mapear o click nos elementos da lista
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				
				//Toast.makeText(ListaAlunosActivity.this, "A posição : " + posicao, Toast.LENGTH_SHORT).show();
				/**
				 * Aqui, no click curto dos objetos da lista, 
				 * vamos implementar a edicao dos dados do aluno,
				 * para que isso seja possivel, vamos trabalhar com
				 * Intents! 
				 * Para isso, vamos seguir os passos descritos abaixo: 
				 */
				
				// 1) Precisamos pegar os dados do aluno que foi selecionado (clicado)
				Aluno alunoClicado = (Aluno) adapter.getItemAtPosition(posicao);
				
				// 2) Precisamos passar os dados deste aluno para a activity de formulario
				// quem permite isso e a Intent, pois vamos informar os elementos que ela contera								
				Intent editarAlunoIntent = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
				editarAlunoIntent.putExtra("alunoClicado", alunoClicado);
				startActivity(editarAlunoIntent);
				// 3) Agora estamos passando os dados do aluno clicado para a Intent, 
				// entao vamos la na activity de formulario e vamos pegar estes dados
				
				
			}
		});	
		
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				
				/**
				 * Antes estavamos exibindo uma mensagem, mas agora precisamos
				 * pegar qual foi o aluno selecionado para tomarmos as acoes
				 * selectionadas no menu de contexto, e aqui no metodo 
				 * onItemLongClick, fica facil pegar esta informacao visto que
				 * ele recebe um ada
				 */
				//Toast.makeText(ListaAlunosActivity.this, "Aluno clicado é : " + adapter.getItemAtPosition(posicao), Toast.LENGTH_LONG).show();
				
				aluno = (Aluno) adapter.getItemAtPosition(posicao);												
				
				/**
				 * vai consumir o recurso sozinho
				 * temos que deixar false para que 
				 * Android consiga chamar o metodo 
				 * de criacao do menu de contexto
				 */	
				return false;
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
			return false;
		case R.id.enviar_alunos:
			new EnviaDadosDosAlunos(this).execute();
			return false;
		case R.id.baixar_provas:
			Intent intentProvas = new Intent(this,ProvasActivity.class);
			startActivity(intentProvas);
			return false;
		case R.id.abrir_mapa:
			Intent abrirMapa = new Intent(this,MostraAlunosProximosActivity.class);
			startActivity(abrirMapa);
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.i("CICLO DE VIDA","onResume");
		lista = (ListView) findViewById(R.id.lista);		
		carregaLista();
	}

	private void carregaLista() {
		AlunoDAO dao = new AlunoDAO(this);
		List<Aluno> alunos = dao.getLista();
		
 
		//ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,alunos);
		
		/**
		 * Vamos criar nosso adapter customizado
		 */
		AlunoAdapter adapter = new AlunoAdapter(alunos,this);
		
		lista.setAdapter(adapter);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		/**
		 * Implementando a funcioalidade de ligar 
		 */
		MenuItem ligar = menu.add("Ligar"); 
		ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// Definindo intent padrao do Android
				Intent ligarAluno = new Intent(Intent.ACTION_CALL);
				
				// Passando telefone do aluno para ser utilizado pelo discador
				Uri foneAluno = Uri.parse("tel:" + aluno.getTelefone());
				ligarAluno.setData(foneAluno);
				startActivity(ligarAluno);
				return false;
			}
		});
		
		/**
		 * Implementando envio de SMS
		 */
		MenuItem enviarSMS = menu.add("Enviar SMS"); 
	    enviarSMS.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent novoSMS = new Intent(Intent.ACTION_VIEW);
				novoSMS.setData(Uri.parse("sms:"+ aluno.getTelefone()));
				novoSMS.putExtra("sms_body", "Mensagem");
				startActivity(novoSMS);
				return false;
			}
		});
				
	    
	    /**
	     * Implementando funcionalidade de achar no mapa 
	     */
	    
	    MenuItem acharNoMapa = menu.add("Achar no Mapa");
	    acharNoMapa.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent procuraNoMapa = new Intent(Intent.ACTION_VIEW);
				procuraNoMapa.setData(Uri.parse("geo:0,0?z=14&q=" + 
								aluno.getEndereco()));
				startActivity(procuraNoMapa);
				return false;
			}
		});
	    
	    
	    /**
	     * Implementando funcionalidade de navegar no site
	     */
	    MenuItem navegarNoSite = menu.add("Navegar no site");
	    navegarNoSite.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// Definindo intent padrao para abrir o navegador
				Intent abrirSite = new Intent(Intent.ACTION_VIEW);
				Uri siteDoAluno = Uri.parse("http://" + 
							aluno.getSite());
				abrirSite.setData(siteDoAluno);				
				startActivity(abrirSite);
				return false;
			}
		});
	    
	    
	    /**
	     * Aqui, estamos tratando o click diretamente, sem sobrescrever o
	     * metodo onContextItemSelected
	     */
	    MenuItem deletarClick = menu.add("Deletar");	    
	    deletarClick.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
				dao.deletar(aluno);
				dao.close();
				carregaLista();
				return false;
			}
		});
	    
	    /**
	     * Implementando funcionalidade de mandar email
	     */
	    MenuItem enviarEmail = menu.add("Enviar E-mail");
	    enviarEmail.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent novoEmail = new Intent(Intent.ACTION_SEND);
				novoEmail.setType("*/*");
				novoEmail.putExtra(Intent.EXTRA_EMAIL, 
				new String[] {"jorgedmagnani@gmail.com","jorgedmagnani@gmail.com"});
				novoEmail.putExtra(Intent.EXTRA_SUBJECT, 
						"Novo email para aluno " + aluno.getNome());
				novoEmail.putExtra(Intent.EXTRA_TEXT,"Caro " + 
					aluno.getNome() + " sua mensalidade será rejustada em 7% \n A partir de 02 de janeiro 2016.");
				startActivity(novoEmail);
				
				return false;
			}
		});
		
	    /**
	     * Implementando funciolidade de compartilhar
	     */
	    MenuItem compartilhar = menu.add("Compartihar Nota");
	    compartilhar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent compartilharNota = new Intent(Intent.ACTION_SEND);
				compartilharNota.setType("text/plain");
				
				compartilharNota.putExtra(Intent.EXTRA_SUBJECT, "Nota de Desempenho: " + aluno.getNome());
				compartilharNota.putExtra(Intent.EXTRA_TEXT, "Minha nota : " + aluno.getNota());
				startActivity(Intent.createChooser(compartilharNota, "Compartilhar..."));
				
				return false;
			}
		});	    	    	    
		super.onCreateContextMenu(menu, v, menuInfo);		
	}
}

