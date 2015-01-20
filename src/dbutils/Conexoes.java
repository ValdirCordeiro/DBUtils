/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutils;

import java.awt.Color;
import javax.swing.JTextField;
import conexoes.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JLabel;

/**
 * Classe criada para armazenar as funções de conexão com o banco de dados
 *
 * @author Valdir Cordeiro
 */
public class Conexoes {

    public static void getConexaoPostgresEntrada(JLabel status5, JTextField host5, JTextField bd5, JTextField porta5,
            JTextField senha5, JTextField user5, Statement stmtPgEntrada, Statement stmtPgEntrada2) {
        conexoes.Conectar.setDriveJDBC("org.postgresql.Driver");
        conexoes.Conectar.setIpServidor(host5.getText());
        conexoes.Conectar.setNomeDB(bd5.getText());
        conexoes.Conectar.setPorta(porta5.getText());
        conexoes.Conectar.setSenhaBanco(senha5.getText());
        conexoes.Conectar.setUsurioBanco(user5.getText());
        conexoes.Conectar.setUrlConexao("jdbc:postgresql://" + conexoes.Conectar.getIpServidor() + ":" + conexoes.Conectar.getPorta() + "/" + conexoes.Conectar.getNomeDB());
        stmtPgEntrada = conexoes.Conectar.getStatement();
        stmtPgEntrada2 = conexoes.Conectar.getStatement();
        if (conexoes.clBuscaResultSet.getCount("SELECT DATNAME FROM PG_DATABASE where datname like '" + bd5.getText() + "' ORDER BY LOWER(DATNAME)  ") > 0) {
            status5.setForeground(new Color(0, 153, 51));
            status5.setText("Conectado");
        } else {
            status5.setForeground(new Color(204, 0, 0));
            status5.setText("Desconectado");
        }
    }

    public static void getConexaoMSSQLSERVER(JTextField host3, JTextField porta3, JTextField bd3, JTextField user3,
            JTextField senha3, JLabel status3, Statement stmtMsSqlServerSaida, Statement stmtMsSqlServerSaida2) {//Saida
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlserver://" + host3.getText() + ":" + porta3.getText() + ";databasename=" + bd3.getText(), user3.getText(), senha3.getText());
            stmtMsSqlServerSaida = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_UPDATABLE);
            stmtMsSqlServerSaida2 = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_UPDATABLE);

            int quantBancos = 0;
            ResultSet rs = stmtMsSqlServerSaida.executeQuery("SELECT     '' AS Expr1");
            if (rs.next()) {
                quantBancos = 1;
            } else {
                quantBancos = 0;
            }
            if (quantBancos > 0) {
                status3.setForeground(new Color(0, 153, 51));
                status3.setText("Conectado");
            } else {
                status3.setForeground(new Color(204, 0, 0));
                status3.setText("Desconectado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            status3.setForeground(new Color(204, 0, 0));
            status3.setText("Desconectado");
        }
    }

    public static void getConexaoMySqlEntrada(JTextField host, JTextField bd, JTextField porta, JTextField senha, JTextField user,
            Statement stmtSqlEntrada, Statement stmtSqlEntrada2, Statement stmtSqlEntrada3, Statement stmtSqlEntrada4, JLabel status) {
        conexoes.Conectar.setDriveJDBC("com.mysql.jdbc.Driver");
        conexoes.Conectar.setIpServidor(host.getText());
        conexoes.Conectar.setNomeDB(bd.getText());
        conexoes.Conectar.setPorta(porta.getText());
        conexoes.Conectar.setSenhaBanco(senha.getText());
        conexoes.Conectar.setUsurioBanco(user.getText());
        conexoes.Conectar.setUrlConexao("jdbc:mysql://" + conexoes.Conectar.getIpServidor() + ":" + conexoes.Conectar.getPorta() + "/" + conexoes.Conectar.getNomeDB());
        stmtSqlEntrada = conexoes.Conectar.getStatement();
        stmtSqlEntrada2 = conexoes.Conectar.getStatement();
        stmtSqlEntrada3 = conexoes.Conectar.getStatement();
        stmtSqlEntrada4 = conexoes.Conectar.getStatement();
        if (conexoes.clBuscaResultSet.getCount("SHOW TABLES FROM " + bd.getText()) > 0) {
            status.setForeground(new Color(0, 153, 51));
            status.setText("Conectado");
        } else {
            status.setForeground(new Color(204, 0, 0));
            status.setText("Desconectado");
        }
    }

    public static void getConexaoSQL(JTextField porta1, JTextField host1, JTextField bd1, Statement stmtMySqlSaida,
            JLabel status1) {//Saida
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://" + host1.getText() + ":" + porta1.getText() + "/" + bd1.getText(), "root", "427623");
            stmtMySqlSaida = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_UPDATABLE);

            int quantBancos = 0;
            ResultSet rs = stmtMySqlSaida.executeQuery("SHOW TABLES FROM " + bd1.getText());
            if (rs.next()) {
                quantBancos = 1;
            } else {
                quantBancos = 0;
            }
            if (quantBancos > 0) {
                status1.setForeground(new Color(0, 153, 51));
                status1.setText("Conectado");
            } else {
                status1.setForeground(new Color(204, 0, 0));
                status1.setText("Desconectado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getConexaoDBFPadrao(JTextField campoDirAdpm, Statement stmtDBFSaida) {
        try {
            String dirDBF = campoDirAdpm.getText();
            Class.forName("com.hxtt.sql.dbf.DBFDriver");
            Connection conn = DriverManager.getConnection("jdbc:dbf:/" + dirDBF);
            stmtDBFSaida = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getConexaoPG(JTextField host2, JTextField porta2, JTextField bd2, JTextField user2, JTextField senha2,
            Statement stmtPgSqlSaida, JLabel status2) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + host2.getText().trim() + ":" + porta2.getText() + "/" + bd2.getText(), user2.getText(), senha2.getText());
            stmtPgSqlSaida = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs0 = stmtPgSqlSaida.executeQuery("SELECT DATNAME FROM PG_DATABASE");
            if (rs0.next()) {
                System.out.println(rs0.getString(1));
            }
            int quantBancos = 0;
            ResultSet rs = stmtPgSqlSaida.executeQuery("select relname from pg_stat_user_tables order by relname ");
            if (rs.next()) {
                System.out.println(rs.getString(1));
                quantBancos = 1;
            } else {
                quantBancos = 0;
            }
            if (quantBancos > 0) {
                status2.setForeground(new Color(0, 153, 51));
                status2.setText("Conectado");
            } else {
                status2.setForeground(new Color(204, 0, 0));
                status2.setText("Desconectado");
            }
            while (rs.next()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getConexaoInterBase(JTextField host4, JTextField porta4, JTextField bd4, JTextField user4,
                JTextField senha4, Statement stmtInterBaseSaida, JLabel status4) {//Saida
        try {
            Class.forName("interbase.interclient.Driver");
            Connection conn = java.sql.DriverManager.getConnection("jdbc:interbase://" + host4.getText() + ":" + porta4.getText() + "/" + bd4.getText(), user4.getText(), senha4.getText());
            stmtInterBaseSaida = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_UPDATABLE);

            int quantBancos = 0;
            ResultSet rs = stmtInterBaseSaida.executeQuery("show databases");
            if (rs.next()) {
                quantBancos = 1;
            } else {
                quantBancos = 0;
            }
            if (quantBancos > 0) {
                status4.setForeground(new Color(0, 153, 51));
                status4.setText("Conectado");
            } else {
                status4.setForeground(new Color(204, 0, 0));
                status4.setText("Desconectado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            status4.setForeground(new Color(204, 0, 0));
            status4.setText("Desconectado");
        }
    }
    
    public static void getConexaoParadoxPadrao( JTextField campoDirParadox, Statement stmtParadoxSaida, Statement stmtParadoxSaida2 ) {
        try {
            String dirDBF = campoDirParadox.getText();
            Class.forName("com.hxtt.sql.paradox.ParadoxDriver");
            String sss = "jdbc:paradox:/" + dirDBF;
            System.out.println("url: " + sss);
            Connection conn = DriverManager.getConnection(sss);
            Connection conn2 = DriverManager.getConnection(sss);
            stmtParadoxSaida = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmtParadoxSaida2 = conn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
