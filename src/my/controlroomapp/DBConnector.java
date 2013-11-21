/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.controlroomapp;

/**
 *
 * @author Riad
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnector {

    private Connection VASConnection, CRConnection, VSDConnection, TDSConnection,
            CSTMRConnection, EVOUCHConnection, VAS1Connection, VAS2Connection,
            CRBTConnection, FBConnection, CustCareConnection, CreditTransferConnection,
            SMSSCConnection, TokensDBConnection, MSCDBConnection;
    final static short ControlRoomDBId = 1, VASDBId = 2, VSDDBId = 3, TDSDBId = 4,
            CSTMRDBId = 5, EVOUCHDBId = 6, VAS1DBId = 7, VAS2DBId = 8, CRBTDBId = 9,
            FBDBId = 10, CUSTCAREDBId = 11, CREDTRANSDBId = 12, SMSSCDBId = 13,
            DataTokensDBId = 14, MSCDBId = 15;
    private String medianDealer;

    public DBConnector() throws java.lang.ClassNotFoundException, InstantiationException, IllegalAccessException {
        //Load JDBC Drivers
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        Class.forName("com.sybase.jdbc2.jdbc.SybDriver");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }

    void connectToControlRoomDB() throws SQLException {

        CRConnection = DriverManager.getConnection(LoadXmlProperties.DB_CtrlRoom_URL,
                LoadXmlProperties.DB_CR_user, LoadXmlProperties.DB_CR_pwd);
    }

    void connectToVASDB() throws SQLException {

        VASConnection = DriverManager.getConnection(LoadXmlProperties.DB_VAS_URL,
                LoadXmlProperties.DB_VAS_user, LoadXmlProperties.DB_VAS_pwd);
    }

    void connectToVAS1DB() throws SQLException {

        VAS1Connection = DriverManager.getConnection(LoadXmlProperties.DB_VAS1_URL,
                LoadXmlProperties.DB_VAS1_user, LoadXmlProperties.DB_VAS1_pwd);
    }

    void connectToVAS2DB() throws SQLException {

        VAS2Connection = DriverManager.getConnection(LoadXmlProperties.DB_VAS2_URL,
                LoadXmlProperties.DB_VAS2_user, LoadXmlProperties.DB_VAS2_pwd);
    }

    void connectToVSDDB() throws SQLException {

        VSDConnection = DriverManager.getConnection(LoadXmlProperties.DB_VSD_URL,
                LoadXmlProperties.DB_VSD_user, LoadXmlProperties.DB_VSD_pwd);
    }

    void connectToSMSSCDB() throws SQLException {
        SMSSCConnection = DriverManager.getConnection(LoadXmlProperties.DB_SMSSC_URL,
                LoadXmlProperties.DB_SMSSC_user, LoadXmlProperties.DB_SMSSC_pwd);
    }

    void connectToTDSDB() throws SQLException {

        TDSConnection = DriverManager.getConnection(LoadXmlProperties.DB_OMVIA_URL,
                LoadXmlProperties.DB_TDS_user, LoadXmlProperties.DB_TDS_pwd);
    }

    void connectToCSTMRDB() throws SQLException {
        CSTMRConnection = DriverManager.getConnection(LoadXmlProperties.DB_CSTMR_URL,
                LoadXmlProperties.DB_CSTMR_user, LoadXmlProperties.DB_CSTMR_pwd);
    }

    void connectToEvoucherDB() throws SQLException {

        EVOUCHConnection = DriverManager.getConnection(LoadXmlProperties.DB_EVOUCHER_URL,
                LoadXmlProperties.DB_EVOUCHER_user, LoadXmlProperties.DB_EVOUCHER_pwd);
    }

    void connectToCRBTDB() throws SQLException {
        CRBTConnection = DriverManager.getConnection(LoadXmlProperties.DB_CRBT_URL,
                LoadXmlProperties.DB_CRBT_user, LoadXmlProperties.DB_CRBT_pwd);
    }

    void connectToFBDB() throws SQLException {

        FBConnection = DriverManager.getConnection(LoadXmlProperties.DB_FB_URL,
                LoadXmlProperties.DB_FB_user, LoadXmlProperties.DB_FB_pwd);
    }

    void connectToCustCareDB() throws SQLException {

        CustCareConnection = DriverManager.getConnection(LoadXmlProperties.DB_CUSTCARE_URL,
                LoadXmlProperties.DB_CUSTCARE_user, LoadXmlProperties.DB_CUSTCARE_pwd);
    }

    void connectToCreditTransferDB() throws SQLException {
        CreditTransferConnection = DriverManager.getConnection(LoadXmlProperties.DB_CREDTRANS_URL,
                LoadXmlProperties.DB_CREDTRANS_user, LoadXmlProperties.DB_CREDTRANS_pwd);
    }

    void connectToDataTokensDB() throws SQLException {
        TokensDBConnection = DriverManager.getConnection(LoadXmlProperties.DB_TOKENS_URL,
                LoadXmlProperties.DB_TOKENS_user, LoadXmlProperties.DB_TOKENS_pwd);
    }

    void connectToMSCDB() throws SQLException {
        MSCDBConnection = DriverManager.getConnection(LoadXmlProperties.DB_MSC_URL,
                LoadXmlProperties.DB_MSC_user, LoadXmlProperties.DB_MSC_pwd);
    }

    void disconnect() throws SQLException {
        if (VASConnection != null) {
            VASConnection.close();
        }
        if (CRConnection != null) {
            CRConnection.close();
        }
        if (VSDConnection != null) {
            VSDConnection.close();
        }
        if (TDSConnection != null) {
            TDSConnection.close();
        }
        if (CSTMRConnection != null) {
            CSTMRConnection.close();
        }
        if (EVOUCHConnection != null) {
            EVOUCHConnection.close();
        }
        if (VAS1Connection != null) {
            VAS1Connection.close();
        }
        if (VAS2Connection != null) {
            VAS2Connection.close();
        }
        if (CRBTConnection != null) {
            CRBTConnection.close();
        }
        if (FBConnection != null) {
            FBConnection.close();
        }
        if (CustCareConnection != null) {
            CustCareConnection.close();
        }
    }

    public String getMedianDealer() {
        return medianDealer;
    }

    void initMedianDealer() {
        this.medianDealer = LoadXmlProperties.MED_DEALER;
    }

    Connection getDBConnection(short DBId) {

        switch (DBId) {
            case ControlRoomDBId:
                return CRConnection;
            case VASDBId:
                return VASConnection;
            case TDSDBId:
                return TDSConnection;
            case VSDDBId:
                return VSDConnection;
            case CSTMRDBId:
                return CSTMRConnection;
            case EVOUCHDBId:
                return EVOUCHConnection;
            case VAS1DBId:
                return VAS1Connection;
            case VAS2DBId:
                return VAS2Connection;
            case CRBTDBId:
                return CRBTConnection;
            case FBDBId:
                return FBConnection;
            case CUSTCAREDBId:
                return CustCareConnection;
            case CREDTRANSDBId:
                return CreditTransferConnection;
            case SMSSCDBId:
                return SMSSCConnection;
            case DataTokensDBId:
                return TokensDBConnection;
            case MSCDBId:
                return MSCDBConnection;
            default:
                return null;
        }
    }
}
