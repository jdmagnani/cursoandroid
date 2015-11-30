package br.com.cursoandroid.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import br.com.cadastro.modelo.Aluno;
import br.com.cursoandroid.R;

public class FormularioHelper {

	private EditText campoNome;
	private EditText campoSite;
	private EditText campoEndereco;
	private EditText campoTelefone;
	private RatingBar campoNota;
	private Aluno aluno;
	private ImageView foto;

	public FormularioHelper(FormularioActivity activity){
		aluno = new Aluno();
		campoNome = (EditText) activity.findViewById(R.id.nome);
		campoSite = (EditText) activity.findViewById(R.id.site);
		campoEndereco = (EditText) activity.findViewById(R.id.endereco);
		campoTelefone = (EditText) activity.findViewById(R.id.telefone);
		campoNota = (RatingBar) activity.findViewById(R.id.nota);
		foto = (ImageView) activity.findViewById(R.id.foto);
	}
	
	public Aluno pegaAlunoDoFormulario(){
		
		String nome = campoNome.getText().toString();
		String site = campoSite.getText().toString();
		String endereco = campoEndereco.getText().toString();
		String telefone = campoTelefone.getText().toString();		
		double nota = campoNota.getRating();
				
		aluno.setNome(nome);
		aluno.setEndereco(endereco);
		aluno.setSite(site);
		aluno.setTelefone(telefone);
		aluno.setNota(nota);
		
		return aluno;
        
	}

	public void populaFormulario(Aluno alunoParaAlterar) {
		
		// Atualizando o objeto Aluno
		aluno = alunoParaAlterar;

		campoNome.setText(alunoParaAlterar.getNome());
		campoSite.setText(alunoParaAlterar.getSite());
		campoEndereco.setText(alunoParaAlterar.getEndereco());
		campoTelefone.setText(alunoParaAlterar.getTelefone());
		campoNota.setRating(alunoParaAlterar.getNota().floatValue());
		
		if (alunoParaAlterar.getCaminhoFoto() != null) {
			carregaImagem(alunoParaAlterar.getCaminhoFoto());
		}
	}
	
	public ImageView getFoto() {
		return foto;
	}

	public void carregaImagem(String nomeArquivoFoto) {
		aluno.setCaminhoFoto(nomeArquivoFoto);
		
		Bitmap imagem = BitmapFactory.decodeFile(nomeArquivoFoto);
		// Queremos uma imagem pequena, entao vamos redimensionar
		Bitmap imagemPequena = Bitmap.createScaledBitmap(imagem, 100,100, true);
		
		foto.setImageBitmap(imagemPequena);
		
	}
	
}
