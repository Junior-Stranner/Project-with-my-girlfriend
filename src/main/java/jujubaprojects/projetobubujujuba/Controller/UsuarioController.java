package jujubaprojects.projetobubujujuba.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jujubaprojects.projetobubujujuba.Model.Usuario;
import jujubaprojects.projetobubujujuba.Repository.UsuarioRepository;

@Controller
public class UsuarioController {

    ArrayList<Usuario> usuarios = new ArrayList<>();

    @Autowired
    UsuarioRepository repository;

    @GetMapping("/cadastroUsuario")
    public String cadastroUsuario(){
        return "cadastroUsuario";

    }

    @PostMapping("/cadastroUsuario")
    public String salvarCadastro(Usuario usuario){
        repository.save(usuario);
        return "redirect:/loginUsuario";

    }

    //Acessa o Login do Usuario
    @GetMapping("/loginUsuario")
    public String loginUsuario() {
        return "loginUsuario";
    }
    //Verifica se o login é o mesmo que o Cadastro 
    @PostMapping("/verificaLogin")
    public String verificaLogin(Usuario usuario){
        usuarios = (ArrayList<Usuario>) repository.findAll();
        for (Usuario usuarioLog : usuarios) {
            if(usuarioLog.getEmail().equalsIgnoreCase(usuario.getEmail()) &&
             usuarioLog.getSenha().equalsIgnoreCase(usuario.getSenha())){
                return "redirect:/listaUsuario";
            }
        }
        return "redirect:/loginUsuario";
    }
    //acessa a Lista pega do Back-end e envia para o front-end atualizado
    @GetMapping("/listaUsuario")
    public ModelAndView listaUsuario(){
    ModelAndView mv = new ModelAndView("listaUsuario");
    usuarios = (ArrayList<Usuario>) repository.findAll();
    mv.addObject("usuarios", usuarios);
    return mv;

    }
    //edita o Usuario no Login e envia para a Lista de volta atualizado
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id")int id){
        ModelAndView mv = new ModelAndView("cadastroUsuario");
        Usuario usuario = repository.findById(id).get();
        mv.addObject("usuario", usuario);
        return mv;

    }
    //exclui o Usuario da Lista 
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id")int id){
        repository.deleteById(id);
        return "redirect:/listaUsuario";
    }

}
