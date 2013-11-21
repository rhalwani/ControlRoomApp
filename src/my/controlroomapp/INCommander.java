package my.controlroomapp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class INCommander {

    static final short COS_AFRICELL_DEF = 12;
    static final short COS_ROAM_EN = 29;
    static final short COS_USSD_DIS = 16;
    static final short COS_CFBlock = 123;
    static final short COS_FandFCFBlock = 127;
    static final short COS_Valid = 40;
    static final short COS_Post = 31;
    static final short COS_FRENCH = 13;
    static final short COS_WOLOF = 14;
    static final short COS_MANDINKA = 15;
    static final short COS_ENGLISH_TOK = 110;
    static final short COS_WOLOF_TOK = 111;
    static final short COS_MANDINKA_TOK = 112;
    static final short COS_FRENCH_TOK = 113;
    static final int evcSearchPrd = 5;
    static final String defaultRingID = "11933";
    private long sessionID;
    DBConnector dbc;
    private Connection VASconnection;
    private Connection CRConnection;
    private Connection VSDConnection;
    private Connection TDSConnection;
    private Connection CSTMRConnection;
    private Connection EVoucherConnection;
    private Connection VAS1Connection;
    private Connection VAS2Connection;
    private Connection CRBTConnection;
    private Connection ccConnection;
    private Connection creditTransConnection;
    private Connection SMSSCConnection;
    private Connection TokensDBConnection;
    private CallableStatement subCreditAdjustStmt;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    INCommander()
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.dbc = new DBConnector();
        this.dbc.connectToControlRoomDB();
        this.CRConnection = this.dbc.getDBConnection((short) 1);

        this.dbc.initMedianDealer();
    }

    private void setSessionID(long sid) {
        this.sessionID = sid;
    }

    long getSessionID() {
        return this.sessionID;
    }

    DBConnector getDBConnector() {
        return this.dbc;
    }

    String logIn(String userName, String passwd) throws SQLException, UnknownHostException {
        CallableStatement loginStmt = this.CRConnection.prepareCall("{call login(?, ?, ?, ?, ?, ?, ?)}");

        loginStmt.setString(1, userName);
        loginStmt.setString(2, passwd);
        InetAddress i = InetAddress.getLocalHost();
        loginStmt.setString(3, i.getHostAddress());
        loginStmt.setString(4, i.getHostName());
        loginStmt.registerOutParameter(5, -5);
        loginStmt.registerOutParameter(6, 12);
        loginStmt.registerOutParameter(7, 5);

        loginStmt.execute();
        String userLogCredential = loginStmt.getString(6);
        if (!loginStmt.wasNull()) {
            setSessionID(loginStmt.getLong(5));
            short role = loginStmt.getShort(7);
            userLogCredential = String.valueOf(role) + "," + loginStmt.getString(6);

            if (role == 1) {
                System.out.println("Connecting to VAS DB...");
                this.dbc.connectToVASDB();
                System.out.println("Connecting to VSD DB...");
                this.dbc.connectToVSDDB();
                System.out.println("Connecting to TDS DB...");
                this.dbc.connectToTDSDB();
                System.out.println("Connecting to CSTMR DB...");
                this.dbc.connectToCSTMRDB();
                System.out.println("Connecting to E-Voucher DB...");
                this.dbc.connectToEvoucherDB();
                System.out.println("Connecting to VAS1 DB...");
                this.dbc.connectToVAS1DB();
                System.out.println("Connecting to VAS2 DB...");
                this.dbc.connectToVAS2DB();
                System.out.println("Connecting to CRBT DB...");
                this.dbc.connectToCRBTDB();

                System.out.println("Connecting to Credit Transfer DB...");
                this.dbc.connectToCreditTransferDB();
                System.out.println("Connecting to SMSC DB...");
                this.dbc.connectToSMSSCDB();
                System.out.println("Connecting to Data Tokens DB...");
                this.dbc.connectToDataTokensDB();

                this.VASconnection = this.dbc.getDBConnection((short) 2);
                this.VSDConnection = this.dbc.getDBConnection((short) 3);
                this.TDSConnection = this.dbc.getDBConnection((short) 4);
                this.CSTMRConnection = this.dbc.getDBConnection((short) 5);
                this.EVoucherConnection = this.dbc.getDBConnection((short) 6);
                this.VAS1Connection = this.dbc.getDBConnection((short) 7);
                this.VAS2Connection = this.dbc.getDBConnection((short) 8);
                this.CRBTConnection = this.dbc.getDBConnection((short) 9);

                this.ccConnection = null;
                this.creditTransConnection = this.dbc.getDBConnection((short) 12);
                this.SMSSCConnection = this.dbc.getDBConnection((short) 13);
                this.TokensDBConnection = this.dbc.getDBConnection((short) 14);
            } else if (role == 2) {
                this.dbc.connectToVASDB();
                this.dbc.connectToVSDDB();
                this.dbc.connectToTDSDB();
                this.dbc.connectToCSTMRDB();
                this.dbc.connectToEvoucherDB();
                this.dbc.connectToVAS1DB();
                this.dbc.connectToVAS2DB();
                this.dbc.connectToCRBTDB();

                this.dbc.connectToCreditTransferDB();
                this.dbc.connectToDataTokensDB();

                this.VASconnection = this.dbc.getDBConnection((short) 2);
                this.VSDConnection = this.dbc.getDBConnection((short) 3);
                this.TDSConnection = this.dbc.getDBConnection((short) 4);
                this.CSTMRConnection = this.dbc.getDBConnection((short) 5);
                this.EVoucherConnection = this.dbc.getDBConnection((short) 6);
                this.VAS1Connection = this.dbc.getDBConnection((short) 7);
                this.VAS2Connection = this.dbc.getDBConnection((short) 8);
                this.CRBTConnection = this.dbc.getDBConnection((short) 9);

                this.creditTransConnection = this.dbc.getDBConnection((short) 12);

                this.TokensDBConnection = this.dbc.getDBConnection((short) 14);
                this.ccConnection = null;
            } else if (role == 3) {
                this.dbc.connectToVSDDB();
                this.dbc.connectToTDSDB();

                this.VASconnection = this.dbc.getDBConnection((short) 2);
                this.VSDConnection = this.dbc.getDBConnection((short) 3);
                this.TDSConnection = this.dbc.getDBConnection((short) 4);
                this.CSTMRConnection = this.dbc.getDBConnection((short) 5);

                this.EVoucherConnection = null;
                this.VAS1Connection = null;
                this.VAS2Connection = null;
                this.CRBTConnection = null;
            } else if (role == 4) {
                this.dbc.connectToVSDDB();
                this.dbc.connectToCSTMRDB();
                this.dbc.connectToVASDB();
                this.dbc.connectToSMSSCDB();
                this.dbc.connectToTDSDB();
                this.dbc.connectToCreditTransferDB();
                this.VASconnection = this.dbc.getDBConnection((short) 2);
                this.VSDConnection = this.dbc.getDBConnection((short) 3);
                this.SMSSCConnection = this.dbc.getDBConnection((short) 13);
                this.CSTMRConnection = this.dbc.getDBConnection((short) 5);
                this.TDSConnection = this.dbc.getDBConnection((short) 4);
                this.creditTransConnection = this.dbc.getDBConnection((short) 12);
                this.EVoucherConnection = null;
                this.VAS1Connection = null;
                this.VAS2Connection = null;
                this.CRBTConnection = null;

                this.ccConnection = null;
            } else if (role == 5) {
                this.dbc.connectToTDSDB();
                this.dbc.connectToVSDDB();
                this.VSDConnection = this.dbc.getDBConnection((short) 3);

                this.TDSConnection = this.dbc.getDBConnection((short) 4);
            }

        }

        return userLogCredential;
    }

    void logOut() throws SQLException {
        Statement logOutStmt = CRConnection.createStatement();
        logOutStmt.executeUpdate("UPDATE Sessions SET logoutTime = getDate() WHERE SessionID = " + getSessionID());
    }

    void removeRoamerFromCug(String subNum)
            throws SQLException {
        PreparedStatement cugChkStmt = this.EVoucherConnection.prepareStatement("SELECT 1 from cug_num WHERE msisdn = ?");

        cugChkStmt.setString(1, subNum);
        ResultSet cugChkRes = cugChkStmt.executeQuery();

        if (cugChkRes.isBeforeFirst()) {
            PreparedStatement insCugStmt = this.EVoucherConnection.prepareStatement("INSERT INTO cug_numtemp (msisdn, cos, rowguid) SELECT msisdn, cos, rowguid from cug_num WHERE msisdn = ?");

            insCugStmt.setString(1, subNum);
            insCugStmt.executeUpdate();

            PreparedStatement delCugStmt = this.EVoucherConnection.prepareStatement("DELETE from cug_num WHERE msisdn = ?");

            delCugStmt.setString(1, subNum);
            delCugStmt.executeUpdate();
        }
    }

    String[] getSubInfo(String subNum) throws SQLException {
        PreparedStatement subInfoStmt = this.VSDConnection.prepareStatement("SELECT balance, free_money, free_sms_remaining free_sms, cos_name, first_used, time_of_last_call, pin_code FROM dbo.db_sub_view WHERE subscriber_id = ?");

        subInfoStmt.setString(1, subNum);
        ResultSet subStatSet = subInfoStmt.executeQuery();
        String[] subInfoStr;
        if (subStatSet.next()) {
            subInfoStr = new String[10];
            int resIndex = 0;
            subInfoStr[(resIndex++)] = Float.toString(subStatSet.getFloat("balance"));
            subInfoStr[(resIndex++)] = Float.toString(subStatSet.getFloat("free_money"));
            subInfoStr[(resIndex++)] = Float.toString(subStatSet.getFloat("free_sms"));
            subInfoStr[(resIndex++)] = subStatSet.getString("cos_name");
            subInfoStr[(resIndex++)] = (subStatSet.getDate("first_used") == null ? "N/A" : subStatSet.getDate("first_used").toString());
            subInfoStr[(resIndex++)] = (subStatSet.getDate("time_of_last_call") == null ? "N/A" : subStatSet.getDate("time_of_last_call").toString());
            subInfoStr[resIndex] = subStatSet.getString("pin_code");
        } else {
            subInfoStr = new String[]{"Not Found"};
        }

        return subInfoStr;
    }

    String getMSIN(String ICCD) {
        try {
            PreparedStatement selMsinStmt = this.CRConnection.prepareStatement("SELECT msin FROM dbo.msin_inventory WHERE iccd like '%'+?");

            selMsinStmt.setString(1, ICCD);
            ResultSet msinRS = selMsinStmt.executeQuery();

            if (!msinRS.next()) {
                return "0";
            }
            return msinRS.getString(1);
        } catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
        }
        return "-1";
    }

    int removeTOKNum(String subNum, String curTOKNum)
            throws SQLException {
        CallableStatement tokRemNumStmt = this.VSDConnection.prepareCall("{call sp_db_del_friend(?, ?)}");

        tokRemNumStmt.setString(1, subNum);
        tokRemNumStmt.setString(2, curTOKNum);

        return tokRemNumStmt.executeUpdate();
    }

    int setTOKNum(String subNum, String tokNum) throws SQLException {
        PreparedStatement selCOSStmt = this.VSDConnection.prepareStatement("SELECT cos_num FROM dbo.db_subscriber_tbl WHERE subscriber_id = ?");

        selCOSStmt.setString(1, subNum);
        ResultSet cosIDSet = selCOSStmt.executeQuery();

        if (!cosIDSet.next()) {
            return -1;
        }
        short cosNum = cosIDSet.getShort(1);
        short newCos;
        switch (cosNum) {
            case COS_ENGLISH_TOK:
            case COS_WOLOF_TOK:
            case COS_MANDINKA_TOK:
            case COS_FRENCH_TOK:
            case COS_FandFCFBlock:
                newCos = cosNum;
                break;
            case COS_CFBlock:
                newCos = COS_FandFCFBlock;
                break;
            case COS_WOLOF:
                newCos = COS_WOLOF_TOK;
                break;
                case COS_FRENCH:
                    newCos = COS_FRENCH_TOK;
                break;
                case COS_MANDINKA:
                    newCos = COS_MANDINKA_TOK;
                break;
                case COS_AFRICELL_DEF:
                default:
                    newCos = COS_ENGLISH_TOK;
                break;
        }

CallableStatement tokCallStmt = this.VSDConnection.prepareCall("{call sp_db_add_friend(?, ?, ?)}");

tokCallStmt.setString(1, subNum);
        tokCallStmt.setShort(2, (short) 1);
        tokCallStmt.setString(3, tokNum);
        int tokSetReturn = tokCallStmt.executeUpdate();
        if (tokSetReturn == 1) {
            setNewCOS(subNum, cosNum, newCos, 0);
        }

        return tokSetReturn;
    }

    Object[] checkRefForSimReplace(String subNum, String refNum1, String refNum2, String refNum3)
            throws SQLException {
        String[] subInfoArr = getSubInfo(subNum);
        if (subInfoArr.length > 1) {
            short[] refRes = new short[3];
            CallableStatement refChkStmt = this.TDSConnection.prepareCall("{call CHKREFNUMBERS(?, ?, ?, ?, ?, ?, ?)}");

            refChkStmt.setString(1, subNum);
            refChkStmt.setString(2, refNum1);
            refChkStmt.setString(3, refNum2);
            refChkStmt.setString(4, refNum3);
            refChkStmt.registerOutParameter(5, 5);
            refChkStmt.registerOutParameter(6, 5);
            refChkStmt.registerOutParameter(7, 5);
            refChkStmt.execute();
            refRes[0] = refChkStmt.getShort(5);
            refRes[1] = refChkStmt.getShort(6);
            refRes[2] = refChkStmt.getShort(7);

            return new Object[]{refRes};
        }
        return null;
    }

    String simReplace(String subNum, String oldMSIN, String newMSIN)
            throws SQLException {
        CallableStatement SIMRepCrdStmt = this.VSDConnection.prepareCall("{call dbo.SIMReplaceCredAdjust(?, ?, ?, ?, ?, ?)}");

        SIMRepCrdStmt.setString(1, subNum);
        SIMRepCrdStmt.registerOutParameter(2, 5);
        SIMRepCrdStmt.registerOutParameter(3, 3);
        SIMRepCrdStmt.registerOutParameter(4, 3);
        SIMRepCrdStmt.registerOutParameter(5, 3);
        SIMRepCrdStmt.registerOutParameter(6, 3);
        SIMRepCrdStmt.execute();

        String queryRet = SIMRepCrdStmt.getShort(2) + "," + SIMRepCrdStmt.getFloat(3) + "," + SIMRepCrdStmt.getFloat(4) + "," + SIMRepCrdStmt.getFloat(5) + "," + SIMRepCrdStmt.getFloat(6);

        return queryRet;
    }

    String getA4K(String MSIN) throws SQLException {
        CallableStatement A4KStmt = this.CRConnection.prepareCall("{call getki(?, ?)}");

        A4KStmt.setString(1, MSIN);
        A4KStmt.registerOutParameter(2, 12);
        A4KStmt.execute();
        String strA4ki = A4KStmt.getString(2);
        return strA4ki;
    }

    boolean tokSubCheck(String subNum) throws SQLException {
        PreparedStatement FnFStmt = this.VASconnection.prepareStatement("select 1 from friendsandfamily where msisdn = ?");

        FnFStmt.setString(1, subNum);
        if (FnFStmt.executeQuery().next()) {
            return true;
        }
        return false;
    }

    void adjustFreeMoney(String subNum, float adjustAmnt, String operatorId)
            throws SQLException {
        CallableStatement adjustFreeMonStmt = this.VSDConnection.prepareCall("{call AddFreeMoney(?,?,?)}");

        adjustFreeMonStmt.setString(1, subNum);
        adjustFreeMonStmt.setString(2, operatorId);
        adjustFreeMonStmt.setFloat(3, adjustAmnt);
        adjustFreeMonStmt.execute();
    }

    void adjustFreeSMSCount(String subNum, float freeSMSCount) throws SQLException {
        PreparedStatement updFreeSMSStmt = this.VSDConnection.prepareStatement("UPDATE db_subscriber_tbl set free_sms_remaining = ? WHERE subscriber_id = ?");

        updFreeSMSStmt.setFloat(1, freeSMSCount);
        updFreeSMSStmt.setString(2, subNum);
        updFreeSMSStmt.executeUpdate();
    }

    void insertRegistrationInfo(Object[] regInfo, boolean activate)
            throws SQLException {
        PreparedStatement subInsStmt = this.ccConnection.prepareStatement("insert into subscriber_registration (phone,first_name,fathers_name,family_name,gender,id_type,id_number,nationality,birthday_date,email,residence_city,residence_street,residence_postal,sales_person,phone_type,education,occupation,company_name,company_city,company_street,purchase_date,question_1,question_2,question_3,question_4,status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'COMPLETE')");

        int i = 0;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setDate(i + 1, java.sql.Date.valueOf((String) regInfo[i]));
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setDate(i + 1, java.sql.Date.valueOf((String) regInfo[i]));
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subInsStmt.setString(i + 1, (String) regInfo[i]);
        subInsStmt.executeUpdate();

        if (activate) {
            subInsStmt = this.ccConnection.prepareStatement("insert into directs (date, customer_name, address,tel, user, activated values (curdate(),?,?,?,?,0)");

            subInsStmt.setString(1, regInfo[1] + " " + regInfo[2]);
            subInsStmt.setString(2, regInfo[8] + ", " + regInfo[7]);
            subInsStmt.setString(3, (String) regInfo[0]);
            subInsStmt.setString(4, (String) regInfo[10]);
            subInsStmt.executeUpdate();
        }
    }

    int insertSubscriberDetails(String[] subInfo) throws SQLException {
        PreparedStatement subInsStmt = this.ccConnection.prepareStatement("insert into change_of_sim(Date,Time,Branch,Name,Sex,DOB,Address,ID_Card, ID_No,GSM_No,Type,user,Ref1,Ref2,Ref3) values (curdate(), curtime(),'Application',?,?,?,?,?,?,?,'Prepaid',?,?,?,?)", 1);

        subInsStmt.setString(1, subInfo[0]);
        subInsStmt.setString(2, subInfo[1]);
        subInsStmt.setString(3, subInfo[2]);
        subInsStmt.setString(4, subInfo[3]);
        subInsStmt.setString(5, subInfo[4]);
        subInsStmt.setString(6, subInfo[5]);
        subInsStmt.setString(7, subInfo[6]);
        subInsStmt.setString(8, subInfo[7]);
        subInsStmt.setString(9, subInfo[8]);
        subInsStmt.setString(10, subInfo[9]);
        subInsStmt.setString(11, subInfo[10]);
        subInsStmt.executeUpdate();

        ResultSet rs = subInsStmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    String getTOKNumber(String tokSub)
            throws SQLException {
        PreparedStatement tokNumStmt = this.VSDConnection.prepareStatement("select ff_phone_number from dbo.db_sub_friends_family_tbl where subscriber_id = ?");

        tokNumStmt.setString(1, tokSub);
        ResultSet tokrs = tokNumStmt.executeQuery();
        if (tokrs.next()) {
            return tokrs.getString(1);
        }
        return null;
    }

    void updateRegistrationInfo(Object[] regInfo, int custId)
            throws SQLException {
        System.out.println("Update customer id: " + custId);
        PreparedStatement subUpdStmt = this.ccConnection.prepareStatement("update subscriber_registration set first_name = ?,fathers_name = ?,family_name = ?,gender = ?,id_type = ?,id_number = ?,nationality = ?,birthday_date = ?,email = ?,residence_city = ?,residence_street = ?,residence_postal = ?,sales_person = ?,phone_type = ?,education = ?,occupation = ?,company_name = ?,company_city = ?,company_street = ?,purchase_date = ?,question_1 = ?,question_2 = ?,question_3 = ?,question_4 = ?,status = 'COMPLETE' WHERE id = ?");

        int i = 0;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        System.out.println("info at " + i + ": " + regInfo[i]);
        subUpdStmt.setDate(i + 1, java.sql.Date.valueOf((String) regInfo[i]));

        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        System.out.println("info at " + i + ": " + regInfo[i]);
        subUpdStmt.setDate(i + 1, java.sql.Date.valueOf((String) regInfo[i]));
        i++;
        System.out.println("info at " + i + ": " + regInfo[i]);
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        System.out.println("info at " + i + ": " + regInfo[i]);
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        System.out.println("info at " + i + ": " + regInfo[i]);
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;
        System.out.println("info at " + i + ": " + regInfo[i]);
        subUpdStmt.setString(i + 1, (String) regInfo[i]);
        i++;

        subUpdStmt.setInt(i + 1, custId);

        subUpdStmt.executeUpdate();
    }

    Object[] getRegistrationInfo(String phoneNum) throws SQLException {
        PreparedStatement subGetStmt = this.ccConnection.prepareStatement("SELECT first_name,family_name,id_type,id_numberfrom sim_registration WHERE phone = ?");

        subGetStmt.setString(1, phoneNum);
        ResultSet srs = subGetStmt.executeQuery();
        if (!srs.next()) {
            return null;
        }
        return new Object[]{srs.getString("first_name"), srs.getString("family_name"), srs.getString("id_type"), srs.getString("id_number")};
    }

    boolean isRegistered(String phoneNum)
            throws SQLException {
        PreparedStatement subGetStmt = this.ccConnection.prepareStatement("SELECT 1 from sim_registration WHERE phone = ?");

        subGetStmt.setString(1, phoneNum);
        ResultSet srs = subGetStmt.executeQuery();
        if (srs.next()) {
            return true;
        }
        return false;
    }

    Vector<String> getRequesters()
            throws SQLException {
        Statement credReqStmt = this.CRConnection.createStatement(1003, 1007);
        ResultSet rs = credReqStmt.executeQuery("SELECT Requester FROM dbo.CreditRequester ORDER BY Requester");
        Vector vname = new Vector(10);

        while (rs.next()) {
            vname.add(rs.getString(1));
        }

        return vname;
    }

    ArrayList<String> getAllSubscibers() throws SQLException {
        PreparedStatement subGetStmt = this.ccConnection.prepareStatement("SELECT phone from subscriber_registration order by phone");
        ResultSet phoneNumsRS = subGetStmt.executeQuery();
        ArrayList allPhoneNums = new ArrayList();

        while (phoneNumsRS.next()) {
            allPhoneNums.add(phoneNumsRS.getString("phone"));
        }
        return allPhoneNums;
    }

    Vector<VASServices> getVAS(String subNum) throws SQLException {
        Vector vasList = new Vector();

        PreparedStatement VASSubStmt = this.VAS1Connection.prepareStatement("select 'Al-Jazeera' as service from News_Sub where msisdn = ?");

        VASSubStmt.setString(1, subNum);
        ResultSet vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.JAZEERA);
        }

        VASSubStmt = this.VAS1Connection.prepareStatement("select 'Boy Tips' as service from boy_sub where msisdn = ?");

        VASSubStmt.setString(1, subNum);
        vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.BOY_TIPS);
        }

        VASSubStmt = this.VAS1Connection.prepareStatement("select 'Girl Tips' as service from girl_sub where msisdn = ?");

        VASSubStmt.setString(1, subNum);
        vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.GIRL_TIPS);
        }

        VASSubStmt = this.VAS1Connection.prepareStatement("select '99 Names of Allah' as service from allaname_sub where msisdn = ?");

        VASSubStmt.setString(1, subNum);
        vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.ALLAH_NAMES);
        }

        VASSubStmt = this.VASconnection.prepareStatement("select 'Currency Exchange' as service from currency_Sub where msisdn = ?");

        VASSubStmt.setString(1, subNum);
        vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.CURRENCY);
        }

        VASSubStmt = this.VASconnection.prepareStatement("select 'Horoscope' as service from horoscope_sub where msisdn = ?");

        VASSubStmt.setString(1, subNum);
        vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.HOROSCOPE);
        }

        VASSubStmt = this.VAS1Connection.prepareStatement("select 'Prayer Reminder' as service from ramadan_Sub where msisdn = ?");

        VASSubStmt.setString(1, subNum);
        vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.PRAYER);
        }

        VASSubStmt = this.VAS2Connection.prepareStatement("select 'Sports News' as service from infocell_sub where msisdn = ?");

        VASSubStmt.setString(1, subNum);
        vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.SPORTS_NEWS);
        }

        VASSubStmt = this.CRBTConnection.prepareStatement("select 'Fun Ring' as service from subscribers where msisdn = concat('220',?)");

        VASSubStmt.setString(1, subNum);
        vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.CRBT);
        }

        VASSubStmt = this.VAS1Connection.prepareStatement("select 'SMS Express' as service from free_sms_sub where msisdn = ?");

        VASSubStmt.setString(1, subNum);
        vasRS = VASSubStmt.executeQuery();
        if (vasRS.next()) {
            vasList.add(VASServices.SMS);
        }

        VASSubStmt = this.VSDConnection.prepareStatement("select loyalty_status from db_subscriber_tbl where subscriber_id = ?");

        VASSubStmt.setString(1, subNum);
        ResultSet kRS = VASSubStmt.executeQuery();

        if ((kRS.next())
                && (kRS.getString("loyalty_status").equalsIgnoreCase("Y"))) {
            vasList.add(VASServices.KOLAREH);
        }

        return vasList;
    }

    void removeVAS(String subNum, VASServices[] VASListToRemove)
            throws SQLException {
        if (VASListToRemove.length > 0) {
            int creditToDeduct = 0;

            for (int i = 0; i < VASListToRemove.length; i++) {
                PreparedStatement VASUnsubStmt;
                switch (VASListToRemove[i]) {
                    case JAZEERA:
                        VASUnsubStmt = this.VAS1Connection.prepareStatement("delete from News_Sub where msisdn = ?");

                        VASUnsubStmt.setString(1, subNum);
                        VASUnsubStmt.executeUpdate();
                        creditToDeduct++;
                        break;
                    case BOY_TIPS:
                        VASUnsubStmt = this.VAS1Connection.prepareStatement("delete from boy_sub where msisdn = ?");

                        VASUnsubStmt.setString(1, subNum);
                        VASUnsubStmt.executeUpdate();
                        creditToDeduct++;
                        break;
                    case GIRL_TIPS:
                        VASUnsubStmt = this.VAS1Connection.prepareStatement("delete from girl_sub where msisdn = ?");

                        VASUnsubStmt.setString(1, subNum);
                        VASUnsubStmt.executeUpdate();
                        creditToDeduct++;
                        break;
                    case ALLAH_NAMES:
                        VASUnsubStmt = this.VAS1Connection.prepareStatement("delete from allahame_sub where msisdn = ?");

                        VASUnsubStmt.setString(1, subNum);
                        VASUnsubStmt.executeUpdate();
                        creditToDeduct++;
                        break;
                    case CURRENCY:
                        VASUnsubStmt = this.VASconnection.prepareStatement("delete from currency_Sub where msisdn = ?");

                        VASUnsubStmt.setString(1, subNum);
                        VASUnsubStmt.executeUpdate();
                        creditToDeduct++;
                        break;
                    case HOROSCOPE:
                        VASUnsubStmt = this.VASconnection.prepareStatement("delete from horoscope_sub where msisdn = ?");

                        VASUnsubStmt.setString(1, subNum);
                        VASUnsubStmt.executeUpdate();
                        creditToDeduct++;
                        break;
                    case PRAYER:
                        VASUnsubStmt = this.VAS1Connection.prepareStatement("delete from ramadan_Sub where msisdn = ?");

                        VASUnsubStmt.setString(1, subNum);
                        VASUnsubStmt.executeUpdate();
                        creditToDeduct++;
                        break;
                    case SPORTS_NEWS:
                        VASUnsubStmt = this.VAS2Connection.prepareStatement("delete from infocell_sub where msisdn = ?");

                        VASUnsubStmt.setString(1, subNum);
                        VASUnsubStmt.executeUpdate();
                        creditToDeduct++;
                        break;
                    case CRBT:
                        VASUnsubStmt = this.CRBTConnection.prepareStatement("insert into unsubscription(msisdn, status, timestamp) values (concat('220',?), 0, now())");

                        VASUnsubStmt.setString(1, subNum);
                        VASUnsubStmt.executeUpdate();
                        break;
                    case SMS:
                        PreparedStatement smsRemStmt = this.VAS1Connection.prepareStatement("SELECT free_sms, count_rech from free_sms_sub where msisdn = ?");

                        smsRemStmt.setString(1, subNum);
                        ResultSet sRes = smsRemStmt.executeQuery();
                        if (sRes.next()) {
                            int freeSMS = sRes.getInt("free_sms");
                            int count_rech = sRes.getInt("count_rech") + 1;
                            String[] subInfo = getSubInfo(subNum);
                            if (subInfo.length > 1) {
                                float freeSMSRemaining = Float.parseFloat(subInfo[2]);
                                PreparedStatement SMSUnsubStmt = this.VAS1Connection.prepareStatement("insert into free_sms_unsub_stats (msisdn, sms_sent,timestamp) VALUES (?, ?, GETDATE())");

                                SMSUnsubStmt.setString(1, subNum);
                                SMSUnsubStmt.setFloat(2, freeSMS * count_rech - freeSMSRemaining);
                                SMSUnsubStmt.executeUpdate();

                                adjustFreeSMSCount(subNum, 0.0F);

                                VASUnsubStmt = this.VAS1Connection.prepareStatement("delete from free_sms_sub where msisdn = ?");

                                VASUnsubStmt.setString(1, subNum);
                                VASUnsubStmt.executeUpdate();
                                creditToDeduct++;
                            }
                        }
                        break;
                    case KOLAREH:
                        CallableStatement unSubStmt = this.VSDConnection.prepareCall("{call sp_esme_loyalty_optout (?)}");

                        unSubStmt.setString(1, subNum);
                        unSubStmt.execute();
                }

            }

            if (creditToDeduct > 0) {
                adjustSubscriberBalance(subNum, -1 * creditToDeduct);
            }
        }
    }

    void setFunRingSong(String subNum, String songCode)
            throws SQLException {
        PreparedStatement funRingSubStmt = this.CRBTConnection.prepareStatement("INSERT INTO subqueue (msisdn, tone_id, status, timestamp) VALUES (concat('220',?), ?, 0, now())");

        funRingSubStmt.setString(1, subNum);
        funRingSubStmt.setString(2, songCode);
        funRingSubStmt.executeUpdate();

        adjustSubscriberBalance(subNum, -1.0F);
    }

    void adjustSubscriberBalance(String subNum, float credit) throws SQLException {
        adjustSubscriberBalance(subNum, credit, "croom");
    }

    void adjustSubscriberBalance(String subNum, float credit, String userAccount) throws SQLException {
        this.subCreditAdjustStmt = this.VSDConnection.prepareCall("{call AddCredit(?, ?, ?, ?)}");
        this.subCreditAdjustStmt.setString(1, subNum);
        this.subCreditAdjustStmt.setString(2, userAccount);
        this.subCreditAdjustStmt.setFloat(3, credit);
        this.subCreditAdjustStmt.setInt(4, 0);
        this.subCreditAdjustStmt.execute();
    }

    void adjustDealerBalance(String subNum, float credit) throws SQLException {
        this.subCreditAdjustStmt = this.VSDConnection.prepareCall("{call AddDealerCredit(?, ?, ?)}");
        this.subCreditAdjustStmt.setString(1, subNum);
        this.subCreditAdjustStmt.setFloat(2, credit);
        this.subCreditAdjustStmt.setString(3, "croom");
        this.subCreditAdjustStmt.execute();
    }

    void changeService(String subNum, short serviceID, int validPrd) throws SQLException {

        final PreparedStatement selCOSStmt = VSDConnection.prepareStatement(
                "SELECT cos_num FROM dbo.db_subscriber_tbl WHERE subscriber_id = ?"
        );
        selCOSStmt.setString(1, subNum);
        ResultSet cosIDSet = selCOSStmt.executeQuery();

        if (cosIDSet.next()) {
            short curCOS = cosIDSet.getShort(1);
            short updCOS;

            switch (serviceID) {
                case COS_ENGLISH_TOK:
                    switch (curCOS) {
                        case COS_ENGLISH_TOK:
                        case COS_WOLOF_TOK:
                        case COS_MANDINKA_TOK:
                        case COS_FRENCH_TOK:
                            updCOS = COS_ENGLISH_TOK;
                            break;
                        default:
                            updCOS = COS_AFRICELL_DEF;
                            break;
                    }
                case COS_FRENCH:
                    switch (curCOS) {
                        case COS_ENGLISH_TOK:
                        case COS_WOLOF_TOK:
                        case COS_MANDINKA_TOK:
                        case COS_FRENCH_TOK:
                            updCOS = COS_FRENCH_TOK;
                            break;
                        default:
                            updCOS = COS_FRENCH;
                            break;
                    }
                case COS_WOLOF:
                    switch (curCOS) {
                        case COS_ENGLISH_TOK:
                        case COS_WOLOF_TOK:
                        case COS_MANDINKA_TOK:
                        case COS_FRENCH_TOK:
                            updCOS = COS_WOLOF_TOK;
                            break;
                        default:
                            updCOS = COS_WOLOF;
                            break;
                    }
                case COS_MANDINKA:
                    switch (curCOS) {
                        case COS_ENGLISH_TOK:
                        case COS_WOLOF_TOK:
                        case COS_MANDINKA_TOK:
                        case COS_FRENCH_TOK:
                            updCOS = COS_MANDINKA_TOK;
                            break;
                        default:
                            updCOS = COS_MANDINKA;
                            break;
                    }
                default:
                    updCOS = serviceID;
                    break;
            }
            setNewCOS(subNum, curCOS, updCOS, validPrd);
        }
    }

    private void setNewCOS(String subNum, short oldCos, short newCOS, int validPrd) throws SQLException {
        PreparedStatement updCOSStmt = this.VSDConnection.prepareStatement("UPDATE db_subscriber_tbl set cos_num = ? WHERE subscriber_id = ?");

        updCOSStmt.setShort(1, newCOS);
        updCOSStmt.setString(2, subNum);
        updCOSStmt.executeUpdate();

        if (newCOS == 40) {
            CallableStatement validStmt = this.EVoucherConnection.prepareCall("{call dbo.ValiditySubscriber(?, ?)}");
            validStmt.setString(1, subNum);
            validStmt.setInt(2, validPrd);
            validStmt.execute();
        }
    }

    ResultSetTableModel getResultSetTableModel(short queryID, String subNum) throws SQLException {
        final short accHist = 0;
        final short callHist = 1;
        final short callHistShowAll = 2;
        final short evoucherHist = 3;
        final short creditTransfer = 4;
        PreparedStatement histStmt;
        switch (queryID) {
            case accHist:
                histStmt = this.CSTMRConnection.prepareStatement("select * from (select to_char(H.CHANGEDATE,'mm/dd/yyyy hh24:mi:ss') CHANGEDATE, M.DESCRIPTION UPDATEMETHOD, H.OPERATORID Operator, H.VOUCHERID CreditSource, round(H.OPENINGBALANCE,2) OPENINGBALANCE, round(H.BALANCECHANGE,2) BALANCECHANGE, round(H.OPENINGFREEMONEY,2) OPENINGFREEMONEY, round(H.FREEMONEYCHANGE,2) FREEMONEYCHANGE from CSTMR.ACCOUNTHISTORY H INNER JOIN CSTMR.ACCOUNT A ON H.SUBSCRIBERID = A.SUBSCRIBERID INNER JOIN UPDATEMETHODS M ON H.UPDATEMETHODID = M.ID WHERE A.MSISDN = ? order by H.CHANGEDATE desc) t where rownum < 51", 1004, 1007);

                break;
            case callHist:
                histStmt = this.TDSConnection.prepareStatement("select * from (select to_char(CHARGEDATE,'mm/dd/yyyy hh24:mi:ss') CALLDATE, CALLTYPE, substr(CPARTY,1,5) CALLED, CHARGEDURATION DURATION, DEBITRATE1 CREDITUSED, CREDITUSED FREEMONEYUSED from TDS.PAY_SDR P INNER JOIN TDS.PAY_SDR_CALLTYPE L ON P.\"TYPE\" = L.ID where ORIGINNODE <> 'CP1' and ACCNUM = ? order by CHARGEDATE desc) calltab WHERE rownum < 51", 1004, 1007);

                break;
            case callHistShowAll:
                histStmt = this.TDSConnection.prepareStatement("select * from (select to_char(CHARGEDATE,'mm/dd/yyyy hh24:mi:ss') CALLDATE, CALLTYPE, CPARTY CALLED, CHARGEDURATION DURATION, DEBITRATE1 CREDITUSED, CREDITUSED FREEMONEYUSED from TDS.PAY_SDR P INNER JOIN TDS.PAY_SDR_CALLTYPE L ON P.\"TYPE\" = L.ID where ORIGINNODE <> 'CP1' and ACCNUM = ? order by CHARGEDATE desc) calltab WHERE rownum < 101", 1004, 1007);

                break;
            case evoucherHist:
                histStmt = this.CSTMRConnection.prepareStatement("select * from (select to_char(H.CHANGEDATE,'mm/dd/yyyy hh24:mi:ss') CHANGEDATE, M.DESCRIPTION UPDATEMETHOD, round(H.OPENINGBALANCE,2) OPENINGBALANCE, round(H.BALANCECHANGE,2) BALANCECHANGE, H.TARGET TARGET from CSTMR.DEALERHISTORY H JOIN CSTMR.UPDATEMETHODS M ON H.UPDATEMETHODID = M.ID WHERE H.MSISDN = ? order by H.CHANGEDATE desc) t where rownum < 51", 1004, 1007);

                break;
            case creditTransfer:
                histStmt = this.creditTransConnection.prepareStatement("select TOP 20 convert(varchar(10),date,101)+' '+convert(varchar(8),date,114) TRANSDATE, bnum TARGET, AMOUNT from dbo.UTS_transfer_log WHERE Anum = ? ORDER BY date desc", 1004, 1007);

                break;
            default:
                return null;
        }

        histStmt.setString(1, subNum);
        return new ResultSetTableModel(histStmt.executeQuery());
    }

    ResultSetTableModel getSMSDeliveryTableModel(String origNum, String destNum, java.util.Date startDate, java.util.Date endDate) throws SQLException {

        PreparedStatement SMSHistStmt;
        String queryWEndDate = "SELECT origin, destination, sc_timestamp \"Time Sent\", delivery_time \"Time Delivered\", case sms_status "
                + " WHEN 201 THEN 'Delivered' WHEN 34 THEN 'System Fail' WHEN 22 THEN 'Memory Full' WHEN 29 THEN 'Absent Mobile Sub' WHEN 202 THEN 'Expired'"
                + " WHEN 1 THEN 'Unknown Mobile Sub' END Status FROM dbo.delivery_stats_tbl WHERE sms_type = 254 AND origin = '220'+? and destination like '%'+?"
                + " and sc_timestamp between ? and ? ORDER BY sc_timestamp desc";
        String queryWOEndDate = "SELECT origin, destination, sc_timestamp \"Time Sent\", delivery_time \"Time Delivered\", case sms_status "
                + " WHEN 201 THEN 'Delivered' WHEN 34 THEN 'System Fail' WHEN 22 THEN 'Memory Full' WHEN 29 THEN 'Absent Mobile Sub' WHEN 202 THEN 'Expired'"
                + " WHEN 1 THEN 'Unknown Mobile Sub' END Status FROM dbo.delivery_stats_tbl WHERE sms_type = 254 AND origin = '220'+? and destination like '%'+?"
                + " and sc_timestamp >= ? ORDER BY sc_timestamp desc";

        if (endDate != null) { //end date is specified
            SMSHistStmt = SMSSCConnection.prepareStatement(queryWEndDate, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            SMSHistStmt.setString(1, origNum);
            SMSHistStmt.setString(2, destNum);
            SMSHistStmt.setDate(3, java.sql.Date.valueOf(dateFormat.format(startDate)));
            SMSHistStmt.setDate(4, java.sql.Date.valueOf(dateFormat.format(endDate)));

        } else { //end date is not specified
            SMSHistStmt = SMSSCConnection.prepareStatement(queryWOEndDate, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            SMSHistStmt.setString(1, origNum);
            SMSHistStmt.setString(2, destNum);
            SMSHistStmt.setDate(3, java.sql.Date.valueOf(dateFormat.format(startDate)));
        }

        return new ResultSetTableModel(SMSHistStmt.executeQuery());
    }

    TableModel searchEVCTrans(String senderNum, String recipientNum, float[] transAmts, java.util.Date eventDate)
            throws SQLException {
        PreparedStatement EVCSearchStmt = this.CSTMRConnection.prepareStatement("SELECT VOUCHERID Sender, MSISDN Recipient, to_char(CHANGEDATE,'mm/dd/yyyy hh24:mi:ss') EVENTDATE, BALANCECHANGE, FREEMONEYCHANGE FROM ACCOUNTHISTORY JOIN ACCOUNT using (SUBSCRIBERID) WHERE updatemethodid = 97  AND VOUCHERID = ? AND MSISDN = ? AND ACCOUNTHISTORY.changedate between ? and ? + 3 AND (BALANCECHANGE between ?-0.1 and ?+0.1 or BALANCECHANGE between ?-0.1 and ?+0.1 or BALANCECHANGE between ?-0.1 and ?+0.1) ORDER BY CHANGEDATE desc", 1004, 1007);

        EVCSearchStmt.setString(1, senderNum);
        EVCSearchStmt.setString(2, recipientNum);
        EVCSearchStmt.setDate(3, java.sql.Date.valueOf(this.dateFormat.format(eventDate)));
        EVCSearchStmt.setDate(4, java.sql.Date.valueOf(this.dateFormat.format(eventDate)));
        EVCSearchStmt.setFloat(5, transAmts[0]);
        EVCSearchStmt.setFloat(6, transAmts[0]);
        EVCSearchStmt.setFloat(7, transAmts[1]);
        EVCSearchStmt.setFloat(8, transAmts[1]);
        EVCSearchStmt.setFloat(9, transAmts[2]);
        EVCSearchStmt.setFloat(10, transAmts[2]);

        ResultSet EVCSearchResultSet = EVCSearchStmt.executeQuery();
        if (EVCSearchResultSet.isBeforeFirst()) {
            ArrayList headerArrList = new ArrayList(EVCSearchResultSet.getMetaData().getColumnCount());
            for (int c = 1; c <= EVCSearchResultSet.getMetaData().getColumnCount(); c++) {
                String cl = EVCSearchResultSet.getMetaData().getColumnLabel(c);

                headerArrList.add(cl);
            }
            EVCSearchResultSet.last();
            ArrayList searchResList = new ArrayList(EVCSearchResultSet.getRow());
            EVCSearchResultSet.beforeFirst();

            while (EVCSearchResultSet.next()) {
                if (!isCredTransReversed(senderNum, recipientNum, EVCSearchResultSet.getFloat("BALANCECHANGE"), EVCSearchResultSet.getString("EVENTDATE"))) {
                    searchResList.add(new CreditTransModel(senderNum, recipientNum, EVCSearchResultSet.getString("EVENTDATE"), EVCSearchResultSet.getFloat("BALANCECHANGE"), EVCSearchResultSet.getFloat("FREEMONEYCHANGE")));
                }
            }

            if (searchResList.isEmpty()) {
                return new DefaultTableModel();
            }
            return new ArrayListTableModel(headerArrList, searchResList);
        }

        return null;
    }

    boolean isCredTransReversed(String senderNum, String receipientNum, float transAmt, String evtDate)
            throws SQLException {
        PreparedStatement prevEVCAdjust = this.CRConnection.prepareStatement("SELECT 1 FROM ActionLog WHERE ActionID = ? and MSISDN = ? and [Parameter] = ? and Invoice = ? and Comments = ? ");

        prevEVCAdjust.setShort(1, (short) 38);
        prevEVCAdjust.setString(2, senderNum);
        prevEVCAdjust.setString(3, receipientNum);
        prevEVCAdjust.setString(4, Float.toString(transAmt));
        prevEVCAdjust.setString(5, evtDate);
        return prevEVCAdjust.executeQuery().isBeforeFirst();
    }

    TableModel searchSMSCreditTrans(String senderNum, String recipientNum, float[] transAmt, java.util.Date eventDate)
            throws SQLException {
        PreparedStatement searchCrdtTransStmt = this.CSTMRConnection.prepareStatement("select send.Msisdn SENDER, recv.Msisdn RECIPIENT, send.ChangeDate EVENTDATE, recv.BalanceChange AMOUNT FROM ( select t.Msisdn, y.Changedate, y.Balancechange from accounthistory y JOIN account t USING (subscriberid) where Msisdn = ? and changedate between ? and ?+3 AND updatemethodid = 33) send JOIN ( select t.Msisdn, y.Changedate, y.Balancechange from accounthistory y JOIN account t USING (subscriberid) where Msisdn = ? and changedate between ? and ?+3 AND updatemethodid = 34 and (balancechange between ?-0.1 and ?+0.1 or balancechange between ?-0.1 and ?+0.1 or balancechange between ?-0.1 and ?+0.1)) recv ON send.changedate = recv.changedate And abs(send.Balancechange) = recv.Balancechange ORDER by EVENTDATE desc", 1004, 1007);

        searchCrdtTransStmt.setString(1, senderNum);
        searchCrdtTransStmt.setDate(2, java.sql.Date.valueOf(this.dateFormat.format(eventDate)));
        searchCrdtTransStmt.setDate(3, java.sql.Date.valueOf(this.dateFormat.format(eventDate)));
        searchCrdtTransStmt.setString(4, recipientNum);
        searchCrdtTransStmt.setDate(5, java.sql.Date.valueOf(this.dateFormat.format(eventDate)));
        searchCrdtTransStmt.setDate(6, java.sql.Date.valueOf(this.dateFormat.format(eventDate)));
        searchCrdtTransStmt.setFloat(7, transAmt[0]);
        searchCrdtTransStmt.setFloat(8, transAmt[0]);
        searchCrdtTransStmt.setFloat(9, transAmt[1]);
        searchCrdtTransStmt.setFloat(10, transAmt[1]);
        searchCrdtTransStmt.setFloat(11, transAmt[2]);
        searchCrdtTransStmt.setFloat(12, transAmt[2]);

        ResultSet smsCreditTransResultSet = searchCrdtTransStmt.executeQuery();

        if (smsCreditTransResultSet.isBeforeFirst()) {
            ArrayList headerArrList = new ArrayList(smsCreditTransResultSet.getMetaData().getColumnCount());
            for (int c = 1; c <= smsCreditTransResultSet.getMetaData().getColumnCount(); c++) {
                String cl = smsCreditTransResultSet.getMetaData().getColumnLabel(c);

                headerArrList.add(cl);
            }
            smsCreditTransResultSet.last();
            ArrayList searchResList = new ArrayList(smsCreditTransResultSet.getRow());
            smsCreditTransResultSet.beforeFirst();

            while (smsCreditTransResultSet.next()) {
                if (!isCredTransReversed(senderNum, recipientNum, smsCreditTransResultSet.getFloat("AMOUNT"), smsCreditTransResultSet.getString("EVENTDATE"))) {
                    searchResList.add(new CreditTransModel(senderNum, recipientNum, smsCreditTransResultSet.getString("EVENTDATE"), smsCreditTransResultSet.getFloat("AMOUNT"), 0.0F));
                }
            }

            if (searchResList.isEmpty()) {
                return new DefaultTableModel();
            }
            return new ArrayListTableModel(headerArrList, searchResList);
        }

        return null;
    }

    short adjustCrdtTransfer(String Anumber, String Bnumber, Float crdtAmount)
            throws SQLException {
        CallableStatement checkSubAccountStmt = this.VSDConnection.prepareCall("{call GetAccount(?)}");
        checkSubAccountStmt.setString(1, Bnumber);
        ResultSet accountResultSet = checkSubAccountStmt.executeQuery();

        if (accountResultSet.next()) {
            float receipientCurrBalance = accountResultSet.getFloat("Balance");
            if (receipientCurrBalance >= crdtAmount.floatValue()) {
                adjustSubscriberBalance(Bnumber, -1.0F * crdtAmount.floatValue(), "croom");
                adjustSubscriberBalance(this.dbc.getMedianDealer(), crdtAmount.floatValue(), "croom");
                adjustSubscriberBalance(Anumber, crdtAmount.floatValue(), "croom");
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException ite) {
                    System.err.println(ite.getMessage());
                }
                adjustSubscriberBalance(this.dbc.getMedianDealer(), -1.0F * crdtAmount.floatValue(), "croom");

                return 2;
            }
            return 1;
        }

        return 0;
    }

    short adjustEVCTransfer(String dealerNum, String receipientNum, Float balanceChng, Float freeMoneyChng)
            throws SQLException {
        CallableStatement checkSubAccountStmt = this.VSDConnection.prepareCall("{call GetAccount(?)}");
        checkSubAccountStmt.setString(1, receipientNum);
        ResultSet accountResultSet = checkSubAccountStmt.executeQuery();

        if (accountResultSet.next()) {
            float receipientCurrBalance = accountResultSet.getFloat("Balance");
            float receipientCurrFreeMoney = accountResultSet.getFloat("FreeMoney");

            if ((receipientCurrBalance >= balanceChng.floatValue()) && (receipientCurrFreeMoney >= freeMoneyChng.floatValue())) {
                adjustDealerBalance(this.dbc.getMedianDealer(), balanceChng.floatValue());
                adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * balanceChng.floatValue());

                adjustDealerBalance(dealerNum, balanceChng.floatValue());
                adjustSubscriberBalance(receipientNum, -1.0F * balanceChng.floatValue());
                adjustFreeMoney(receipientNum, -1.0F * freeMoneyChng.floatValue(), "croom");
            } else if ((receipientCurrBalance >= balanceChng.floatValue()) && (receipientCurrFreeMoney < freeMoneyChng.floatValue())) {
                float freeMonDiff = freeMoneyChng.floatValue() - receipientCurrFreeMoney;

                adjustDealerBalance(this.dbc.getMedianDealer(), balanceChng.floatValue() - freeMonDiff);
                adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * (balanceChng.floatValue() - freeMonDiff));

                adjustDealerBalance(dealerNum, balanceChng.floatValue() - freeMonDiff);
                adjustSubscriberBalance(receipientNum, -1.0F * balanceChng.floatValue());
                adjustFreeMoney(receipientNum, -1.0F * receipientCurrFreeMoney, "croom");
            } else if ((receipientCurrBalance < balanceChng.floatValue()) && (receipientCurrFreeMoney >= freeMoneyChng.floatValue())) {
                adjustDealerBalance(this.dbc.getMedianDealer(), receipientCurrBalance);
                adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * receipientCurrBalance);

                adjustDealerBalance(dealerNum, receipientCurrBalance);
                adjustSubscriberBalance(receipientNum, -1.0F * receipientCurrBalance);
                adjustFreeMoney(receipientNum, -1.0F * freeMoneyChng.floatValue(), "croom");
            } else {
                float transferedMonDiff = balanceChng.floatValue() - receipientCurrBalance + (freeMoneyChng.floatValue() - receipientCurrFreeMoney);

                if (transferedMonDiff < balanceChng.floatValue()) {
                    adjustDealerBalance(this.dbc.getMedianDealer(), balanceChng.floatValue() - transferedMonDiff);
                    adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * (balanceChng.floatValue() - transferedMonDiff));

                    adjustDealerBalance(dealerNum, balanceChng.floatValue() - transferedMonDiff);
                    adjustSubscriberBalance(receipientNum, -1.0F * receipientCurrBalance);
                    adjustFreeMoney(receipientNum, -1.0F * receipientCurrFreeMoney, "croom");
                } else {
                    return 1;
                }
            }
            return 2;
        }
        return 0;
    }

    short adjustNormaltoEVC(String dealerNum, String recipientNum, Float balanceChng, Float freeMoneyChng)
            throws SQLException {
        CallableStatement checkSubAccountStmt = this.VSDConnection.prepareCall("{call GetAccount(?)}");
        checkSubAccountStmt.setString(1, recipientNum);
        ResultSet accountResultSet = checkSubAccountStmt.executeQuery();

        if (accountResultSet.next()) {
            float recipientCurrBalance = accountResultSet.getFloat("Balance");
            float recipientCurrFreeMoney = accountResultSet.getFloat("FreeMoney");

            if ((recipientCurrBalance >= balanceChng.floatValue()) && (recipientCurrFreeMoney >= freeMoneyChng.floatValue())) {
                adjustSubscriberBalance(recipientNum, -1.0F * balanceChng.floatValue());
                adjustFreeMoney(recipientNum, -1.0F * freeMoneyChng.floatValue(), "croom");

                adjustDealerBalance(this.dbc.getMedianDealer(), balanceChng.floatValue());
                adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * balanceChng.floatValue());

                adjustDealerBalance(recipientNum, balanceChng.floatValue());
            } else if ((recipientCurrBalance >= balanceChng.floatValue()) && (recipientCurrFreeMoney < freeMoneyChng.floatValue())) {
                float freeMonDiff = freeMoneyChng.floatValue() - recipientCurrFreeMoney;

                adjustSubscriberBalance(recipientNum, -1.0F * balanceChng.floatValue());
                adjustFreeMoney(recipientNum, -1.0F * recipientCurrFreeMoney, "croom");

                adjustDealerBalance(this.dbc.getMedianDealer(), balanceChng.floatValue() - freeMonDiff);
                adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * (balanceChng.floatValue() - freeMonDiff));

                adjustDealerBalance(recipientNum, balanceChng.floatValue() - freeMonDiff);
            } else if ((recipientCurrBalance < balanceChng.floatValue()) && (recipientCurrFreeMoney >= freeMoneyChng.floatValue())) {
                adjustSubscriberBalance(recipientNum, -1.0F * recipientCurrBalance);
                adjustFreeMoney(recipientNum, -1.0F * freeMoneyChng.floatValue(), "croom");

                adjustDealerBalance(this.dbc.getMedianDealer(), recipientCurrBalance);
                adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * recipientCurrBalance);

                adjustDealerBalance(recipientNum, recipientCurrBalance);
            } else {
                float transferedMonDiff = balanceChng.floatValue() - recipientCurrBalance + (freeMoneyChng.floatValue() - recipientCurrFreeMoney);

                if (transferedMonDiff < balanceChng.floatValue()) {
                    adjustSubscriberBalance(recipientNum, -1.0F * recipientCurrBalance);
                    adjustFreeMoney(recipientNum, -1.0F * recipientCurrFreeMoney, "croom");

                    adjustDealerBalance(this.dbc.getMedianDealer(), balanceChng.floatValue() - transferedMonDiff);
                    adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * (balanceChng.floatValue() - transferedMonDiff));

                    adjustDealerBalance(recipientNum, balanceChng.floatValue() - transferedMonDiff);
                } else {
                    return 1;
                }
            }
            return 2;
        }
        return 0;
    }

    short adjustDealerTransfer(String dealerNum, String receipientNum, Float crdtAmount)
            throws SQLException {
        CallableStatement checkSubAccountStmt = this.VSDConnection.prepareCall("{call GetAccount(?)}");
        checkSubAccountStmt.setString(1, "161" + receipientNum);
        ResultSet accountResultSet = checkSubAccountStmt.executeQuery();

        if (accountResultSet.next()) {
            float receipientCurrBalance = accountResultSet.getFloat("Balance");

            if (receipientCurrBalance >= crdtAmount.floatValue()) {
                adjustDealerBalance(receipientNum, -1.0F * crdtAmount.floatValue());
                adjustDealerBalance(this.dbc.getMedianDealer(), crdtAmount.floatValue());
                adjustDealerBalance(dealerNum, crdtAmount.floatValue());
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException ite) {
                    System.err.println("Interrupted Exception.");
                }
                adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * crdtAmount.floatValue());
            } else {
                adjustDealerBalance(receipientNum, -1.0F * receipientCurrBalance);
                adjustDealerBalance(this.dbc.getMedianDealer(), receipientCurrBalance);
                adjustDealerBalance(dealerNum, receipientCurrBalance);
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException ite) {
                    System.err.println("Interrupted Exception.");
                }
                adjustDealerBalance(this.dbc.getMedianDealer(), -1.0F * receipientCurrBalance);
            }

            return 2;
        }
        return 0;
    }

    TableModel searchDealerTrans(String senderNum, String recipientNum, float[] transAmts, java.util.Date eventDate)
            throws SQLException {
        PreparedStatement EVCSearchStmt = this.CSTMRConnection.prepareStatement("select msisdn AS SENDER, substr(target,4,7) As DEALER, changedate AS TRANSFERDATE, abs(balancechange) As AMOUNT from dealerhistory where msisdn = ? and target = ? and changedate between ? and ?+3 and (abs(balancechange) between ? - 0.1 and ? + 0.1 or abs(balancechange) between ? - 0.1 and ? + 0.1 or abs(balancechange) between ? - 0.1 and ? + 0.1) order by TRANSFERDATE desc", 1004, 1007);

        EVCSearchStmt.setString(1, senderNum);
        EVCSearchStmt.setString(2, "161" + recipientNum);
        EVCSearchStmt.setDate(3, java.sql.Date.valueOf(this.dateFormat.format(eventDate)));
        EVCSearchStmt.setDate(4, java.sql.Date.valueOf(this.dateFormat.format(eventDate)));
        EVCSearchStmt.setFloat(5, transAmts[0]);
        EVCSearchStmt.setFloat(6, transAmts[0]);
        EVCSearchStmt.setFloat(7, transAmts[1]);
        EVCSearchStmt.setFloat(8, transAmts[1]);
        EVCSearchStmt.setFloat(9, transAmts[2]);
        EVCSearchStmt.setFloat(10, transAmts[2]);

        ResultSet EVCSearchResultSet = EVCSearchStmt.executeQuery();

        if (EVCSearchResultSet.isBeforeFirst()) {
            ArrayList headerArrList = new ArrayList(EVCSearchResultSet.getMetaData().getColumnCount());
            for (int c = 1; c <= EVCSearchResultSet.getMetaData().getColumnCount(); c++) {
                String cl = EVCSearchResultSet.getMetaData().getColumnLabel(c);

                headerArrList.add(cl);
            }
            EVCSearchResultSet.last();
            ArrayList searchResList = new ArrayList(EVCSearchResultSet.getRow());
            EVCSearchResultSet.beforeFirst();

            while (EVCSearchResultSet.next()) {
                if (!isCredTransReversed(senderNum, recipientNum, EVCSearchResultSet.getFloat("AMOUNT"), EVCSearchResultSet.getString("TRANSFERDATE"))) {
                    searchResList.add(new CreditTransModel(senderNum, recipientNum, EVCSearchResultSet.getString("TRANSFERDATE"), EVCSearchResultSet.getFloat("AMOUNT"), 0.0F));
                }
            }

            if (searchResList.isEmpty()) {
                return new DefaultTableModel();
            }
            return new ArrayListTableModel(headerArrList, searchResList);
        }

        return null;
    }

    short fixDataSubscription(String subNum)
            throws SQLException {
        CallableStatement fixDataStmt = this.TokensDBConnection.prepareCall("{call dbo.fix_3G_service(?, ?, ?)}");
        fixDataStmt.setString(1, subNum);
        fixDataStmt.setString(2, "Control Room");
        fixDataStmt.registerOutParameter(3, 5);

        fixDataStmt.execute();

        return fixDataStmt.getShort(3);
    }
}
