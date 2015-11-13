package br.com.cadastro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.cadastro.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

	private static final String DB_NAME = "droiddb";
	private static final int VERSAO = 3;
	private static final String TABELA = "Alunos";
	public AlunoDAO(Context context) {
		super(context, DB_NAME, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String ddl = "CREATE TABLE " + TABELA + "( "
				+ "id INTEGER PRIMARY KEY, "
				+ "nome TEXT UNIQUE NOT NULL, "
				+ "endereco TEXT, "
				+ "telefone TEXT, "
				+ "site TEXT,"
				+ "nota REAL,"
				+ "caminhoFoto TEXT"
				+ ");";
				
		database.execSQL(ddl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		String ddl = "DROP TABLE IF EXISTS " + TABELA;
		database.execSQL(ddl);
		onCreate(database);
	}

	public void insere(Aluno aluno) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", aluno.getNome());
		contentValues.put("endereco", aluno.getEndereco());
		contentValues.put("telefone", aluno.getTelefone());
		contentValues.put("site", aluno.getSite());
		contentValues.put("nota", aluno.getNota());
		contentValues.put("caminhoFoto", aluno.getCaminhoFoto());
		
		getWritableDatabase().insert(TABELA, null, contentValues);
		
	}

	public List<Aluno> getLista() {
		String sql = "SELECT * FROM " + TABELA + ";";
		Cursor c = getReadableDatabase().rawQuery(sql, null);
		List<Aluno> alunos = new ArrayList<Aluno>();
		while(c.moveToNext()) {
			Aluno aluno = new Aluno();
			aluno.setId(c.getLong(c.getColumnIndex("id")));
			aluno.setNome(c.getString(c.getColumnIndex("nome")));
			aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
			aluno.setSite(c.getString(c.getColumnIndex("site")));
			aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
			aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
			aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
			
			// inserindo na lista tipada
			alunos.add(aluno);
		}	
		return alunos;
	}

	public void deletar(Aluno aluno) {
				
		String[] args = {aluno.getId().toString()};
		getWritableDatabase().delete("Alunos", "id=?", args);
		
	}

	public void atualizar(Aluno aluno) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", aluno.getNome());
		contentValues.put("telefone", aluno.getTelefone());
		contentValues.put("site", aluno.getSite());
		contentValues.put("nota", aluno.getNota());
		contentValues.put("caminhoFoto", aluno.getCaminhoFoto());
		
		String[] args = {aluno.getId().toString()};
		getWritableDatabase().update("Alunos", contentValues, "id=?", args);
	}	
	
}
