package br.com.cursoandroid.task;

import java.util.List;

import br.com.cadastro.dao.AlunoDAO;
import br.com.cadastro.modelo.Aluno;
import br.com.cursoandroid.conversor.AlunoToJSON;
import br.com.cursoandroid.suporte.WebClient;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class EnviaDadosDosAlunos extends AsyncTask<Object, Object, String> {
	
	private Context ctx;
	private Dialog progress;

	public EnviaDadosDosAlunos(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(ctx, "Aguarde...", "Enviando dados para o servidor.");
	}
	
	@Override
	protected String doInBackground(Object... params) {
		
		AlunoDAO dao = new AlunoDAO(ctx);
		List<Aluno> alunos = dao.getLista();
		dao.close();
		String alunosJSON = new AlunoToJSON().toJSON(alunos);
		String postAluno = new WebClient("http://www.mocky.io/v2/56558d250f0000a218282be5").post(alunosJSON);		

		return postAluno;
	}
	
	@Override
	protected void onPostExecute(String postAluno) {
		progress.dismiss();
		Toast.makeText(ctx, "Retorno : " + postAluno, Toast.LENGTH_LONG).show();
	}

}
