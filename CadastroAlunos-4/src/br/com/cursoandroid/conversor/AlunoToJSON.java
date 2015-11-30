package br.com.cursoandroid.conversor;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.cadastro.modelo.Aluno;

public class AlunoToJSON {
	
	public String toJSON(List<Aluno> alunos) {
        try {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.object().key("list").array()
                .object().key("aluno").array();

            for (Aluno aluno : alunos) {
                jsonStringer.object()
                        .key("id").value(aluno.getId())
                        .key("nome").value(aluno.getNome())
                        .key("telefone").value(aluno.getTelefone())
                        .key("endereco").value(aluno.getEndereco())
                        .key("site").value(aluno.getSite())
                        .key("nota").value(aluno.getNota())
                .endObject();
            }

            jsonStringer.endArray().endObject();
            jsonStringer.endArray().endObject();
            
            return jsonStringer.toString();
            
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
