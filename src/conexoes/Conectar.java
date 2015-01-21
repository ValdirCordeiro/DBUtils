/*
 * Conexao.java
 *
 * Created on 22 de Fevereiro de 2007, 17:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package conexoes;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adm
 */
public class Conectar{
    private static int Conectado;
    private static Connection GAConexao;
    private static String DriveJDBC;
    private static String UsurioBanco;
    private static String SenhaBanco;
    private static String IpServidor;
    private static String urlConexao;
    private static String NomeDB;
    private static String Porta;
    public static int isInicializado(){
        return Conectado;
    }
    public synchronized static Connection getInstance() {
         if( GAConexao == null ) {
             try{
                Class.forName(DriveJDBC);
                GAConexao = java.sql.DriverManager.getConnection(getUrlConexao(), getUsurioBanco(), getSenhaBanco());
                Conectado=1;
             }catch(Exception e){
                 System.out.println("Erro ao tentar se conectar ao banco de dados. "+e);
                 Conectado=0;
             }
         }
         return GAConexao;
    }
    public synchronized static Connection RefleshConnection() {
        try{
            GAConexao.close();
        }catch(Exception e){
             System.out.println("Erro ao tentar se conectar ao banco de dados. RefreshConnection: "+e);
             Conectado=0;
         }
         try{            
            GAConexao=null;
            Class.forName(DriveJDBC);
            Conectado=0;
            GAConexao = java.sql.DriverManager.getConnection(getUrlConexao(), getUsurioBanco(), getSenhaBanco());
            Conectado=1;
         }catch(Exception e){
             System.out.println("Erro ao tentar se conectar ao banco de dados. RefreshConnection: "+e);
             Conectado=0;
         }
        return GAConexao;
    }
    public static Statement getStatement(){
        try{
            return getInstance().createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
        }catch(Exception e){
            System.out.print("Erro em utilitarios.Conexoes.Conectar.getStatemant(), não foi possivel retornar o Statement: "+e);
            return null;
        }
        
    }
    
    public static String getDriveJDBC() {
        return DriveJDBC;
    }

    public static void setDriveJDBC(String aDriveJDBC) {
        DriveJDBC = aDriveJDBC;
    }

    public static String getUsurioBanco() {
        return UsurioBanco;
    }

    public static void setUsurioBanco(String aUsurioBanco) {
        UsurioBanco = aUsurioBanco;
    }

    public static String getSenhaBanco() {
        return SenhaBanco;
    }

    public static void setSenhaBanco(String aSenhaBanco) {
        SenhaBanco = aSenhaBanco;
    }

    public static String getIpServidor() {
        return IpServidor;
    }

    public static void setIpServidor(String aIpServidor) {
        IpServidor = aIpServidor;
    }

    public static String getUrlConexao() {
        return urlConexao;
    }

    public static void setUrlConexao(String aUrlConexao) {
        urlConexao = aUrlConexao.trim() + 
                "?" +
                
                //"zeroDateTimeBehavior=convertToNull&" +
                "emptyStringsConvertToZero=true&" +
                "autoReconnect=true&" +
                //"jdbcCompliantTruncation=false&" +
                //"useUsageAdvisor=true&"+
                "noDatetimeStringSync=true"
                //"nullCatalogMeansCurrent=true"+                
                ;
    }
    public static String getNomeDB() {
        return NomeDB;
    }

    public static void setNomeDB(String aNomeDB) {
        NomeDB = aNomeDB;
    }  
    public static String getHostAddress(){
        String FvaRetorno="";
        try{
            InetAddress adda=InetAddress.getLocalHost();
            FvaRetorno=adda.getHostAddress();
            adda=null;
        }catch(Exception e){
            System.out.println("Ocorreu um erro ao tentar recuperar o ip do host. Erro: "+e);
        }
        return FvaRetorno;
    }
    public static String getHostName(){
        String FvaRetorno="";
        try{
            InetAddress adda=InetAddress.getLocalHost();
            FvaRetorno=adda.getHostName();
            adda=null;
        }catch(Exception e){
            System.out.println("Ocorreu um erro ao tentar recuperar o ip do host. Erro: "+e);
        }
        return FvaRetorno;
    }
    public static String getPorta() {
        return Porta;
    }

    public static void setPorta(String aPorta) {
        if(true/*utilitarios.ValidaValor.isInteger(aPorta.trim())*/){
            Porta = aPorta;
        }else{
            Porta="3306";
        }
    }
    private static boolean ehBancoValido(String sBanco){
        try{
            return clBuscaResultSet.getPesquisa("SELECT CODBANCO FROM "+
                    sBanco+".IDBANCO WHERE CODBANCO = 2").next();
        }catch(Exception e){
            System.out.println("Erro na checagem do banco: "+ sBanco + " _ _ " + e.toString());
        }    
        return false;
    }    
    public static void getModeloBanco(JTable grid){
        try {
            System.out.println("chegou getModeloBanco");
            DefaultTableModel dtm = (DefaultTableModel) grid.getModel();
            dtm.setNumRows(0);
            ResultSet rs = clBuscaResultSet.getPesquisa("SHOW DATABASES");
            while(rs.next()){
               if(!rs.getString(1).equalsIgnoreCase("mysql") && !rs.getString(1).equalsIgnoreCase("mysql"))
                   if(ehBancoValido(rs.getString(1))){
                       dtm.addRow(new Object[]{rs.getString(1)});
                   }
            }                
        } catch (Exception e) {
            System.out.println("Erro ao obter um modelo de banco "+e);
        }
        
    }
}
