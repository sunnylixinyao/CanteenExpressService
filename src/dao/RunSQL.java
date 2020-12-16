package dao;

import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/14-16:01
 **/
public class RunSQL {
    private ArrayList<String> findResult = new ArrayList<String>();
    private int updateResult = -1;
    ConnectionOfDB conn = new ConnectionOfDB();

    /**
     *
     */
    public RunSQL() {
        super();
        conn.connectToDatabase();
    }

    public void updateSingle(String singleSql){
        updateResult = conn.getResultUpdate(singleSql);
        closeConnection();
    }

    public void updateBatch(ArrayList<String> sqlBatch){
        updateResult = conn.updateTransaction(sqlBatch);
        closeConnection();
    }

    public void findSingle(String singleSql){
        findResult = conn.getResultSetString(singleSql);
        closeConnection();
    }
    private void closeConnection() {
        conn.pstClose();
        conn.retClose();
        conn.connClose();
    }
    /**
     * @return the findResult
     */
    public ArrayList<String> getFindResult() {
        return findResult;
    };


    /**
     * @return the updateResult
     */
    public int getUpdateResult() {
        return updateResult;
    };

}
