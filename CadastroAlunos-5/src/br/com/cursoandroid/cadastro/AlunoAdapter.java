package br.com.cursoandroid.cadastro;

import java.util.List;

import br.com.cadastro.modelo.Aluno;
import br.com.cursoandroid.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlunoAdapter extends BaseAdapter {
	
	private List<Aluno> alunos;
	private Activity listaAlunosActivity;

	public AlunoAdapter(List<Aluno> alunos, ListaAlunosActivity listaAlunosActivity) {
		this.alunos = alunos;
		this.listaAlunosActivity = listaAlunosActivity;
	}

	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int position) {
		return alunos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return alunos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
						
		/**
		 * Este é o nosso metodo principal, é aqui que vamos dizer ao Android que ele deverá
		 * carregar o nosso layout customizado
		 */		
		Aluno aluno = alunos.get(position);
				
		LayoutInflater inflater = listaAlunosActivity.getLayoutInflater();
		View linhaAluno = inflater.inflate(R.layout.item_activity, null);
		
		// Listra zebrada!
		if (position % 2 == 0) {
			linhaAluno.setBackgroundColor(listaAlunosActivity.getResources().
					getColor(R.color.linha_par));
		} 

		
		TextView nome = (TextView) linhaAluno.findViewById(R.id.nome);
		nome.setText(aluno.getNome());
		
		ImageView foto = (ImageView) linhaAluno.findViewById(R.id.foto);
		
		if (aluno.getCaminhoFoto() != null) {
			Bitmap imagem = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
			Bitmap imagemMenor = Bitmap.createScaledBitmap(imagem, 100, 100, true);					
			foto.setImageBitmap(imagemMenor);
		} else {
			foto.setImageResource(R.drawable.ic_no_image);
		}
		
		TextView fone = (TextView) linhaAluno.findViewById(R.id.telefone);
		TextView site = (TextView) linhaAluno.findViewById(R.id.site);
		
		if (fone != null) {			
			fone.setText(aluno.getTelefone());
			site.setText(aluno.getSite());
		}
				
		return linhaAluno;
	}

}
