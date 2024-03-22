package com.example.user;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Service
public class UsuarioService {

    private final String jsonFilePath = "usuarios.json";
    private final Hashtable<String, Usuario> hashtable = new Hashtable<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public UsuarioService() {
        popularHashtableComUsuarios();
    }

    private void popularHashtableComUsuarios() {
        List<Usuario> usuarios = listarUsuarios();
        for (Usuario usuario : usuarios) {
            hashtable.put(usuario.getCpf(), usuario);
        }
    }

    public Usuario buscarUsuarioPorCPF(String cpf) {
        return hashtable.get(cpf);
    }

    public List<Usuario> listarUsuarios() {
        try {
            File file = new File(jsonFilePath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Usuario>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void cadastrarUsuario(Usuario usuario) {
        try {
            List<Usuario> usuarios = listarUsuarios();
            usuarios.add(usuario);
            objectMapper.writeValue(new File(jsonFilePath), usuarios);
            hashtable.put(usuario.getCpf(), usuario); // Atualiza a hashtable ao cadastrar novo usuário
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}