/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutils;

import java.awt.Color;
import javax.swing.JTextField;
import conexoes.*;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Classe criada para armazenar as funções de conexão com o banco de dados
 *
 * @author Valdir Cordeiro
 */
public class Conexoes {

    public static Statement getConexaoPostgresEntrada(JLabel status5, JTextField host5, JTextField bd5, JTextField porta5,
            JTextField senha5, JTextField user5, Statement stmtPgEntrada) {
        conexoes.Conectar.setDriveJDBC("org.postgresql.Driver");
        conexoes.Conectar.setIpServidor(host5.getText());
        conexoes.Conectar.setNomeDB(bd5.getText());
        conexoes.Conectar.setPorta(porta5.getText());
        conexoes.Conectar.setSenhaBanco(senha5.getText());
        conexoes.Conectar.setUsurioBanco(user5.getText());
        conexoes.Conectar.setUrlConexao("jdbc:postgresql://" + conexoes.Conectar.getIpServidor() + ":" + conexoes.Conectar.getPorta() + "/" + conexoes.Conectar.getNomeDB());
        stmtPgEntrada = conexoes.Conectar.getStatement();

        if (conexoes.clBuscaResultSet.getCount("SELECT DATNAME FROM PG_DATABASE where datname like '" + bd5.getText() + "' ORDER BY LOWER(DATNAME)  ") > 0) {
            status5.setForeground(new Color(0, 153, 51));
            status5.setText("Conectado");
        } else {
            status5.setForeground(new Color(204, 0, 0));
            status5.setText("Desconectado");
        }
        return stmtPgEntrada;
    }

    public static Statement getConexaoMSSQLSERVER(JTextField host3, JTextField porta3, JTextField bd3, JTextField user3,
            JTextField senha3, JLabel status3, Statement stmtMsSqlServerSaida) {//Saida
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlserver://" + host3.getText() + ":" + porta3.getText() + ";databasename=" + bd3.getText(), user3.getText(), senha3.getText());
            stmtMsSqlServerSaida = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_UPDATABLE);

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
        return stmtMsSqlServerSaida;
    }

    public static Statement getConexaoMySqlEntrada(JTextField host, JTextField bd, JTextField porta, JTextField senha, JTextField user,
            Statement stmtSqlEntrada, JLabel status) {
        conexoes.Conectar.setDriveJDBC("com.mysql.jdbc.Driver");
        conexoes.Conectar.setIpServidor(host.getText());
        conexoes.Conectar.setNomeDB(bd.getText());
        conexoes.Conectar.setPorta(porta.getText());
        conexoes.Conectar.setSenhaBanco(senha.getText());
        conexoes.Conectar.setUsurioBanco(user.getText());
        conexoes.Conectar.setUrlConexao("jdbc:mysql://" + conexoes.Conectar.getIpServidor() + ":" + conexoes.Conectar.getPorta() + "/" + conexoes.Conectar.getNomeDB());
        stmtSqlEntrada = conexoes.Conectar.getStatement();

        if (conexoes.clBuscaResultSet.getCount("SHOW TABLES FROM " + bd.getText()) > 0) {
            status.setForeground(new Color(0, 153, 51));
            status.setText("Conectado");
        } else {
            status.setForeground(new Color(204, 0, 0));
            status.setText("Desconectado");
        }
        return stmtSqlEntrada;
    }

    public static Statement getConexaoSQL(JTextField porta1, JTextField host1, JTextField bd1, Statement stmtMySqlSaida,
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
        return stmtMySqlSaida;
    }

    public static Statement getConexaoDBFPadrao(JTextField campoDirAdpm, Statement stmtDBFSaida) {
        try {
            String dirDBF = campoDirAdpm.getText();
            Class.forName("com.hxtt.sql.dbf.DBFDriver");
            Connection conn = DriverManager.getConnection("jdbc:dbf:/" + dirDBF);
            stmtDBFSaida = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stmtDBFSaida;
    }

    public static Statement getConexaoPG(JTextField host2, JTextField porta2, JTextField bd2, JTextField user2, JTextField senha2,
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
        return stmtPgSqlSaida;
    }

    public static Statement getConexaoInterBase(JTextField host4, JTextField porta4, JTextField bd4, JTextField user4,
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
        return stmtInterBaseSaida;
    }

    public static Statement getConexaoParadoxPadrao(JTextField campoDirParadox, Statement stmtParadoxSaida) {
        try {
            String dirDBF = campoDirParadox.getText();
            Class.forName("com.hxtt.sql.paradox.ParadoxDriver");
            String sss = "jdbc:paradox:/" + dirDBF;
            System.out.println("url: " + sss);
            Connection conn = DriverManager.getConnection(sss);
            Connection conn2 = DriverManager.getConnection(sss);
            stmtParadoxSaida = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stmtParadoxSaida;
    }

    public static class Importacoes {

        public static void ImportacaoDeSDBF(JTextField campoDirAdpm, JTextField tabelaEntrada, JTextArea ColunaEntrada,
                JTextArea SQLGenerico, Statement stmtDBFSaida) {

            if (campoDirAdpm.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Escolha o Diretório do banco de dados DBF antes de Processar.");
            } else {
                try {
                    String dirDBF = campoDirAdpm.getText();
                    Class.forName("com.hxtt.sql.dbf.DBFDriver");
                    Connection conn = DriverManager.getConnection("jdbc:dbf:/" + dirDBF);
                    stmtDBFSaida = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    try {
                        ResultSet rs = stmtDBFSaida.executeQuery(SQLGenerico.getText().trim());
                        if (rs.next()) {
                            clBuscaResultSet.setExecute("DELETE FROM " + tabelaEntrada.getText());
                            rs.beforeFirst();
                            ResultSet sr = clBuscaResultSet.getPesquisa("SELECT * FROM " + tabelaEntrada.getText());

                            String colunasEntrada[];

                            colunasEntrada = ColunaEntrada.getText().split(",");

                            while (rs.next()) {
                                sr.moveToInsertRow();

                                for (String colunasEntrada1 : colunasEntrada) {
                                    sr.updateString(colunasEntrada1.trim(), "" + rs.getString(colunasEntrada1).trim());
                                }

                                sr.insertRow();
                            }
                            rs.close();
                            JOptionPane.showMessageDialog(null, "Importação de dados concluída com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Cadastro de Servidores\nNão foi encontrado registro para importação.");
                        }
                    } catch (SQLException | HeadlessException e) {
                        JOptionPane.showMessageDialog(null, "Erro 1:\n" + e);
                    }

                } catch (ClassNotFoundException | SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "Erro:\n" + e);
                }
            }

        }

        public static void ImportacaoDeMSSQLServer(JTextField tabelaEntrada, JTextArea SQLGenerico, JTextArea ColunaEntrada,
                Statement stmtMsSqlServerSaida) {

            try {
                ResultSet rs = stmtMsSqlServerSaida.executeQuery(SQLGenerico.getText().trim());
                if (rs.next()) {
                    clBuscaResultSet.setExecute("DELETE FROM " + tabelaEntrada.getText());
                    rs.beforeFirst();
                    ResultSet sr = clBuscaResultSet.getPesquisa("SELECT * FROM " + tabelaEntrada.getText());

                    String colunasEntrada[];

                    colunasEntrada = ColunaEntrada.getText().split(",");

                    while (rs.next()) {
                        sr.moveToInsertRow();

                        for (String colunasEntrada1 : colunasEntrada) {
                            sr.updateString(colunasEntrada1.trim(), "" + rs.getString(colunasEntrada1.trim()));
                        }

                        sr.insertRow();
                    }
                    rs.close();
                    JOptionPane.showMessageDialog(null, "Tudo beleza!");
                } else {
                    JOptionPane.showMessageDialog(null, "Cadastro de Servidores\nNão foi encontrado registro para importação.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cadastro de Servidores\n" + e);
                e.printStackTrace();
            }
        }

        public static void ImportacaoDePostgreSQL(JTextField tabelaEntrada, JTextArea SQLGenerico, JTextArea ColunaEntrada,
                Statement stmtPgSqlSaida) {

            try {
                ResultSet rs = stmtPgSqlSaida.executeQuery(SQLGenerico.getText().trim());
                if (rs.next()) {
                    clBuscaResultSet.setExecute("DELETE FROM " + tabelaEntrada.getText());
                    rs.beforeFirst();
                    ResultSet sr = clBuscaResultSet.getPesquisa("SELECT * FROM " + tabelaEntrada.getText());

                    String colunasEntrada[];

                    colunasEntrada = ColunaEntrada.getText().split(",");

                    while (rs.next()) {
                        sr.moveToInsertRow();

                        for (String colunasEntrada1 : colunasEntrada) {
                            sr.updateString(colunasEntrada1.trim(), "" + rs.getString(colunasEntrada1.trim()));
                        }

                        sr.insertRow();
                    }
                    rs.close();
                    JOptionPane.showMessageDialog(null, "Tudo beleza!");
                } else {
                    JOptionPane.showMessageDialog(null, "Cadastro de Servidores\nNão foi encontrado registro para importação.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cadastro de Servidores\n" + e);
                e.printStackTrace();
            }
        }

        public static void ImportacaoDeMySQL(JTextField tabelaEntrada, JTextArea SQLGenerico, JTextArea ColunaEntrada,
                Statement stmtMySqlSaida) {

            try {
                ResultSet rs = stmtMySqlSaida.executeQuery(SQLGenerico.getText().trim());
                if (rs.next()) {
                    clBuscaResultSet.setExecute("DELETE FROM " + tabelaEntrada.getText());
                    rs.beforeFirst();
                    ResultSet sr = clBuscaResultSet.getPesquisa("SELECT * FROM " + tabelaEntrada.getText());

                    String colunasEntrada[];

                    colunasEntrada = ColunaEntrada.getText().split(",");

                    while (rs.next()) {
                        sr.moveToInsertRow();

                        for (String colunasEntrada1 : colunasEntrada) {
                            sr.updateString(colunasEntrada1.trim(), "" + rs.getString(colunasEntrada1.trim()));
                        }

                        sr.insertRow();
                    }
                    rs.close();
                    JOptionPane.showMessageDialog(null, "Tudo beleza!");
                } else {
                    JOptionPane.showMessageDialog(null, "Cadastro de Servidores\nNão foi encontrado registro para importação.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cadastro de Servidores\n" + e);
                e.printStackTrace();
            }
        }
    }// Fim da Classe static

} // Fim da classe
