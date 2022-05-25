/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.Controller;
import config.Conexao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author nicolau
 */
public class UserController extends Controller {

    public Conexao conn;

    public String strore(User user) {

        this.response = null;
        this.sql = "insert into utilizador (utilizador,palavra_passe,acesso,estado) "
                + "values (?,?,?,?)";
        try {

            this.cmd = conn.getConexaoMySQL().prepareStatement(sql);
            this.cmd.setString(1, user.getUtilizador());
            this.cmd.setString(2, user.getPalavra_passe());
            this.cmd.setString(3, user.getAcesso());
            this.cmd.setString(4, user.getEstado());
            int rowsInserted = this.cmd.executeUpdate();
            if (rowsInserted > 0) {
                this.response = "yes";
            } else {
                this.response = "no";
            }

        } catch (SQLException ex) {
            this.response = ex.getMessage();
        }finally{
            conn.FecharConexao();
        }
        return this.response;
    }

    public String login(User user) {
        this.sql = "select *from utilizador where utilizador=? and palavra_passe=?";
        try {
            this.cmd = conn.getConexaoMySQL().prepareStatement(this.sql);
            this.cmd.setString(1, user.getUtilizador());
            this.cmd.setString(2, user.getPalavra_passe());
            this.rs = this.cmd.executeQuery();

            if (this.rs.next()) {
                user.setUtilizador(this.rs.getString("utilizador"));
                user.setPalavra_passe(this.rs.getString("palavra_passe"));
                user.setEstado(this.rs.getString("estado"));
                user.setAcesso(this.rs.getString("acesso"));
                this.response = "yes";
            } else {
                this.response = "no";
            }
        } catch (SQLException ex) {
            this.response = ex.getMessage();
        }finally{
            conn.FecharConexao();
        }
        return this.response;
    }

    public List<User> list() {
        List<User> utilizadores = new ArrayList<>();
        this.sql = "select *from utilizador where acesso!=?";
        try {
            this.cmd = conn.getConexaoMySQL().prepareStatement(this.sql);
            this.cmd.setString(1, "Administrador");
            this.rs = this.cmd.executeQuery();

            while (this.rs.next()) {
                User user = new User();
                user.setId(this.rs.getInt("id"));
                user.setUtilizador(this.rs.getString("utilizador"));
                user.setPalavra_passe(this.rs.getString("palavra_passe"));
                user.setAcesso(this.rs.getString("acesso"));
                user.setEstado(this.rs.getString("estado"));
                utilizadores.add(user);
            }
        } catch (SQLException ex) {
            this.response = ex.getMessage();
        }finally{
            conn.FecharConexao();
        }
        return utilizadores;
    }
    
    public String update(User user){
        this.sql = "update utilizador set utilizador=?, acesso=?, "
        try{
            
        }catch(SQLException ex){
            this.response = ex.getMessage();
        }finally{
            conn.FecharConexao();
        }
        
        return this.response;
    }

}
