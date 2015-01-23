/*
 * clBuscaResultSet.java
 *
 * Created on 23 de Fevereiro de 2007, 18:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package conexoes;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author adm
 */
public class clBuscaResultSet {

    public clBuscaResultSet(String SQL) {
        try {
            FvaTabela = Conectar.getStatement().executeQuery(SQL);
            if (FvaTabela.next()) {
                isExiste = true;
            } else {
                isExiste = false;
            }
        } catch (Exception e) {
            isExiste = false;
            System.out.println("Erro ao executar pesquisa na base de dados. Erro: " + e);
        }
    }

    public clBuscaResultSet(String SQL, Statement FvaBanco) {
        try {
            FvaTabela = FvaBanco.executeQuery(SQL);
            if (FvaTabela.next()) {
                isExiste = true;
            } else {
                isExiste = false;
            }
        } catch (Exception e) {
            isExiste = false;
            System.out.println("Erro ao executar pesquisa na base de dados. Erro: " + e);
        }
    }

    public static ResultSet getPesquisa(String SQL) {
        try {
            ResultSet FvaRS = Conectar.getStatement().executeQuery(SQL);
            return FvaRS;
        } catch (Exception e) {
            System.out.println("Erro ao executar pesquisa na base de dados. Erro: " + e);
            return null;
        }
    }

    public static ResultSet getPesquisa(String SQL, Statement FvaBanco) {
        try {
            ResultSet FvaRS = FvaBanco.executeQuery(SQL);
            return FvaRS;
        } catch (Exception e) {
            System.out.println("Erro ao executar pesquisa na base de dados. Erro: " + e);
            return null;
        }
    }

    public String getString(String Campo) {
        try {
            return FvaTabela.getString(Campo);
        } catch (Exception e) {
            System.out.println("Erro ao tenta atualizar o campo " + Campo + ". Erro: " + e);
            return "";
        }
    }

    public java.sql.Timestamp getDateTime(String Campo) {
        try {
            return FvaTabela.getTimestamp(Campo);
        } catch (Exception e) {
            System.out.println("Erro ao tenta atualizar o campo " + Campo + ". Erro: " + e);
            return null;
        }
    }

    public java.sql.Timestamp getDateTime(int Campo) {
        try {
            return FvaTabela.getTimestamp(Campo);
        } catch (Exception e) {
            System.out.println("Erro ao tenta atualizar o campo " + Campo + ". Erro: " + e);
            return null;
        }
    }

    public double getDouble(String Campo) {
        try {
            return FvaTabela.getDouble(Campo);
        } catch (Exception e) {
            System.out.println("Erro ao tenta atualizar o campo " + Campo + ". Erro: " + e);
            return 0.0;
        }
    }

    public double getDouble(int Campo) {
        try {
            return FvaTabela.getDouble(Campo);
        } catch (Exception e) {
            System.out.println("Erro ao tenta atualizar o campo " + Campo + ". Erro: " + e);
            return 0.0;
        }
    }

    public int getInt(String Campo) {
        try {
            return FvaTabela.getInt(Campo);
        } catch (Exception e) {
            System.out.println("Erro ao tenta atualizar o campo " + Campo + ". Erro: " + e);
            return 0;
        }
    }

    public int getInt(int Campo) {
        try {
            return FvaTabela.getInt(Campo);
        } catch (Exception e) {
            System.out.println("Erro ao tenta atualizar o campo " + Campo + ". Erro: " + e);
            return 0;
        }
    }

    public java.sql.Date getDate(String Campo) {
        try {
            return FvaTabela.getDate(Campo);
        } catch (Exception e) {
            System.out.println("Erro ao tenta bucar o campo " + Campo + ". Erro: " + e);
            return null;
        }
    }

    public java.sql.Date getDate(int Campo) {
        try {
            return FvaTabela.getDate(Campo);
        } catch (Exception e) {
            System.out.println("Erro ao tenta buscar o campo " + Campo + ". Erro: " + e);
            return null;
        }
    }

    public boolean getExiste() {
        return isExiste;
    }

    public static int getCount(String FvaSQ) {
        int FvaRetorno = 0;
        try {
            ResultSet rs = Conectar.getStatement().executeQuery(FvaSQ);
            if (rs.next()) {
                rs.last();
                FvaRetorno = rs.getRow();
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao realiza pesquisa no banco de dados. SQL: " + FvaSQ + ". Erro: " + e);
        }
        return FvaRetorno;
    }

    public static int getCount(String FvaSQ, Statement FvaBanco) {
        int FvaRetorno = 0;
        try {
            ResultSet rs = FvaBanco.executeQuery(FvaSQ);
            if (rs.next()) {
                rs.last();
                FvaRetorno = rs.getRow();
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao realiza pesquisa no banco de dados. SQL: " + FvaSQ + ". Erro: " + e);
        }
        return FvaRetorno;
    }

    public static int getCodigo(String FvaSQ) {
        int FvaRetorno = 0;
        try {
            ResultSet rs = Conectar.getStatement().executeQuery(FvaSQ);
            if (rs.next()) {
                FvaRetorno = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao realiza pesquisa no banco de dados. SQL: " + FvaSQ + ". Erro: " + e);
        }
        return FvaRetorno;
    }

    public static String getCampo(String FvaSQ) {
        String FvaRetorno = "";
        try {
            ResultSet rs = Conectar.getStatement().executeQuery(FvaSQ);
            if (rs.next()) {
                FvaRetorno = rs.getString(1);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao realiza pesquisa no banco de dados. SQL: " + FvaSQ + ". Erro: " + e);
        }
        return FvaRetorno;
    }

    public static String getCampo(String FvaSQ, Statement banco) {
        String FvaRetorno = "";
        try {
            ResultSet rs = banco.executeQuery(FvaSQ);
            if (rs.next()) {
                FvaRetorno = rs.getString(1);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao realiza pesquisa no banco de dados. SQL: " + FvaSQ + ". Erro: " + e);
        }
        return FvaRetorno;
    }

    public static java.sql.Date getCampoDate(String FvaSQ) {
        try {
            ResultSet rs = Conectar.getStatement().executeQuery(FvaSQ);
            if (rs.next()) {
                return rs.getDate(1);
            }
            rs.close();
            return null;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao realiza pesquisa no banco de dados. SQL: " + FvaSQ + ". Erro: " + e);
        }
        return null;
    }

    public static double getCampoDouble(String FvaSQ) {
        double FvaRetorno = 0.0;
        try {
            ResultSet rs = Conectar.getStatement().executeQuery(FvaSQ);
            if (rs.next()) {
                FvaRetorno = rs.getDouble(1);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao realiza pesquisa no banco de dados. SQL: " + FvaSQ + ". Erro: " + e);
        }
        return FvaRetorno;
    }

    public static boolean setUpdate(String FvaSQ) {
        boolean FvaRetorno = false;
        try {
            Conectar.getStatement().executeUpdate(FvaSQ);
            FvaRetorno = true;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao atualizar o banco de dados em setUpdate. SQL: " + FvaSQ + ". Erro: " + e);
        }
        return FvaRetorno;
    }

    public static boolean setExecute(String FvaSQ) {
        boolean FvaRetorno = false;
        try {
            Conectar.getStatement().execute(FvaSQ);
            FvaRetorno = true;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao executar o banco de dados em setUpdate. SQL: " + FvaSQ + ". Erro: " + e);
        }
        return FvaRetorno;
    }

    public static double getCampoDouble(String SQL, Statement FvaBanco) {
        try {
            ResultSet FvaRS = FvaBanco.executeQuery(SQL);
            if (FvaRS.next()) {
                return FvaRS.getDouble(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Erro ao executar pesquisa na base de dados. Erro: " + e);
            return 0;
        }
    }

    public static int getCampoInt(String SQL, Statement FvaBanco) {
        try {
            ResultSet FvaRS = FvaBanco.executeQuery(SQL);
            if (FvaRS.next()) {
                return FvaRS.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Erro ao executar pesquisa na base de dados. Erro: " + e);
            return 0;
        }
    }

    public static int getCampoInt(String SQL) {
        try {
            ResultSet FvaRS = Conectar.getStatement().executeQuery(SQL);
            if (FvaRS.next()) {
                return FvaRS.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Erro ao executar pesquisa na base de dados. Erro: " + e);
            return 0;
        }
    }
    private ResultSet FvaTabela;
    private boolean isExiste;
}
