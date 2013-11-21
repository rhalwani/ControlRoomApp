/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.controlroomapp;

/**
 *
 * @author Riad
 */
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.util.StringTokenizer;

public class AppLogger implements Runnable {

    private final Connection CR_DB_Connection, CSTMR_DB_Connection;
    private final long sID;
    private volatile short logEventID;
    private volatile String servedMSISDN, refNum1, refNum2, refNum3, chkRes, SIMRepResult, logActParam, logActInvoice, logActComments;
    final static short nullID = 0, DispAccount = 1, SIMReplace = 2, TOKSub = 3, TOKNumSet = 4, Roaming = 5,
            USSD = 6, Validity = 7, Language = 8, RefCheck = 9, DispNum = 10, DispAC = 11, CreateAC = 12,
            CancAC = 13, CreateSub = 14, CancSub = 15, IncCalls = 16, OutCalls = 17, RoamCalls = 18,
            CallFwd = 19, PostPaid = 20, ViewAccHist = 21, ViewCallHist = 22, RegSIM = 23,
            evoucher = 24, funRingSub = 25, funRingUnsub = 26, jazeeraUnsub = 27, boyTipsUnsub = 28,
            girlTipsUnsub = 29, currencyUnsub = 30, horoscopeUnsub = 31, prayerTimesUnsub = 32,
            sportsNewsUnsub = 33, fbUnsub = 34, freeMoneyAdjust = 35, chkSMSDelivery = 36, chkCrdtTransfer = 37,
            payBackDealer = 38, delCurrTOKNum = 39, creditAdd = 40, dataSubscrComplain = 41, SMSExpressUnsub = 42,
            CallFwdB = 43, CallFwdNRY = 44, CallFwdNRC = 45, kolarehUnsub = 46;

    AppLogger(Connection Ctrl_Conn, Connection CSTMR_Conn, long sessionID) throws IOException, SQLException {

        sID = sessionID;
        CR_DB_Connection = Ctrl_Conn;
        CSTMR_DB_Connection = CSTMR_Conn;
    }

    public void run() {

        while (true) {
            switch (logEventID) {
                case RefCheck:
                    try {
                        logRefNumCheck();
                    } catch (SQLException sqle) {
                        System.out.println("Exception during log ref num check");
                    }
                    setLogEventID(nullID);
                    break;
                case SIMReplace:
                    try {
                        SIMRepCSTMRUpd();
                        logAction(SIMReplace);
                    } catch (SQLException sqle) {
                        System.out.println("Exception during SIM Replace:\n" + sqle.getMessage());
                    }
                    setLogEventID(nullID);
                    break;
                case DispAccount:
                case TOKSub:
                case TOKNumSet:
                case Roaming:
                case USSD:
                case Validity:
                case PostPaid:
                case Language:
                case DispNum:
                case DispAC:
                case CreateAC:
                case CancAC:
                case CreateSub:
                case CancSub:
                case IncCalls:
                case OutCalls:
                case RoamCalls:
                case CallFwd:
                case ViewAccHist:
                case ViewCallHist:
                case RegSIM:
                case evoucher:
                case funRingSub:
                case funRingUnsub:
                case jazeeraUnsub:
                case boyTipsUnsub:
                case girlTipsUnsub:
                case currencyUnsub:
                case horoscopeUnsub:
                case prayerTimesUnsub:
                case sportsNewsUnsub:
                case fbUnsub:
                case freeMoneyAdjust:
                case chkSMSDelivery:
                case chkCrdtTransfer:
                case payBackDealer:
                case delCurrTOKNum:
                case creditAdd:
                case dataSubscrComplain:
                case SMSExpressUnsub:
                case CallFwdB:
                case CallFwdNRY:
                case CallFwdNRC:
                case kolarehUnsub:
                    try {
                        logAction(logEventID);
                    } catch (SQLException sqle) {
                        System.out.println("Exception during action: " + logEventID
                                + " for MSISDN " + servedMSISDN + "\n" + sqle.getMessage());
                    }
                    setLogEventID(nullID);
                    break;
                default:
                    try {
                        Thread.currentThread().sleep((long) 1000);
                    } catch (InterruptedException intexc) {
                        System.out.println("Thread " + Thread.currentThread().getName()
                                + " interrupted");
                    }
                    break;
            }
        }
    }

    private void logRefNumCheck() throws SQLException {
        //LOG Check ref nums action
        java.sql.CallableStatement refChkLogStmt = CR_DB_Connection.prepareCall(
                "{call logRefNumsCheck(?, ?, ?, ?, ?, ?, ?)}");
        refChkLogStmt.setLong(1, sID);
        refChkLogStmt.setString(2, this.servedMSISDN);
        refChkLogStmt.setString(3, this.refNum1);
        refChkLogStmt.setString(4, this.refNum2);
        refChkLogStmt.setString(5, this.refNum3);
        refChkLogStmt.setString(6, this.chkRes);
        refChkLogStmt.registerOutParameter(7, java.sql.Types.DATE);
        refChkLogStmt.execute();
    }

    private void SIMRepCSTMRUpd() throws SQLException {
        //LOG SIM Replacement
        CallableStatement logAccStmt = CSTMR_DB_Connection.prepareCall(
                "{call LogSIMRepCredit(?, ?, ?, ?, ?, ?)}");
        logAccStmt.setString(1, servedMSISDN);
        StringTokenizer tokenizer = new StringTokenizer(this.SIMRepResult, ",");
        while (tokenizer.hasMoreTokens()) {
            try {
                logAccStmt.setDouble(2, Double.parseDouble(tokenizer.nextToken()));
                logAccStmt.setDouble(3, Double.parseDouble(tokenizer.nextToken()));
                logAccStmt.setDouble(4, Double.parseDouble(tokenizer.nextToken()));
                logAccStmt.setDouble(5, Double.parseDouble(tokenizer.nextToken()));
                logAccStmt.setDouble(6, Double.parseDouble(tokenizer.nextToken()));
            } catch (NumberFormatException ne) {
                System.out.println("NumberFormat Exception during SIM Replace:\n" + ne.getMessage());
            }
        }
        logAccStmt.execute();
    }

    private void logAction(short actID) throws SQLException {

        CallableStatement logActStmt;
        switch (actID) {
            case DispAccount:
            case TOKSub:
            case DispNum:
            case DispAC:
            case CancAC:
            case CancSub:
            case ViewAccHist:
            case ViewCallHist:
            case RegSIM:
            case evoucher:
            case funRingSub:
            case funRingUnsub:
            case jazeeraUnsub:
            case boyTipsUnsub:
            case girlTipsUnsub:
            case currencyUnsub:
            case horoscopeUnsub:
            case prayerTimesUnsub:
            case sportsNewsUnsub:
            case fbUnsub:
            case SMSExpressUnsub:
            case CallFwdB:
            case CallFwdNRY:
            case CallFwdNRC:
            case kolarehUnsub:
                logActStmt = CR_DB_Connection.prepareCall("{call logAction(?, ?, ?)}");
                logActStmt.setLong(1, this.sID);
                logActStmt.setString(2, servedMSISDN);
                logActStmt.setShort(3, actID);
                logActStmt.execute();
                break;
            case SIMReplace:
            case TOKNumSet:
            case Roaming:
            case USSD:
            case Language:
            case CreateAC:
            case CreateSub:
            case IncCalls:
            case OutCalls:
            case RoamCalls:
            case CallFwd:
            case Validity:
            case PostPaid:
            case chkSMSDelivery:
            case chkCrdtTransfer:
            case delCurrTOKNum:
            case dataSubscrComplain:
                logActStmt = CR_DB_Connection.prepareCall("{call logAction(?, ?, ?, ?, ?)}");
                logActStmt.setLong(1, this.sID);
                logActStmt.setString(2, servedMSISDN);
                logActStmt.setShort(3, actID);
                logActStmt.setString(4, null);
                logActStmt.setString(5, logActParam);
                logActStmt.execute();
                break;
            case freeMoneyAdjust:
            case creditAdd:
            case payBackDealer:
                logActStmt = CR_DB_Connection.prepareCall("{call logActionWithComments(?, ?, ?, ?, ?, ?)}");
                logActStmt.setLong(1, this.sID);
                logActStmt.setString(2, servedMSISDN);
                logActStmt.setShort(3, actID);
                logActStmt.setString(4, logActInvoice);
                logActStmt.setString(5, logActParam);
                logActStmt.setString(6, logActComments.isEmpty() ? null : logActComments);
                logActStmt.execute();
                break;
            default:
                break;
        }
    }

    synchronized void setMSISDN(String MSISDN) {
        servedMSISDN = MSISDN;
    }

    synchronized void setLogActParam(String param) {
        logActParam = param;
    }

    synchronized void setLogActInvoice(String invoiceNum) {
        logActInvoice = invoiceNum;
    }

    synchronized void setRefCheckParams(String refNum1, String refNum2, String refNum3, short[] chkRes) {

        this.refNum1 = refNum1;
        this.refNum2 = refNum2;
        this.refNum3 = refNum3;
        this.chkRes = chkRes[0] + "" + chkRes[1] + "" + chkRes[2];
    }

    synchronized void setLogActionComments(String comments) {
        this.logActComments = comments;
    }

    synchronized void setSIMRepResult(String result) {
        this.SIMRepResult = result;
    }

    synchronized void setLogEventID(short id) {
        this.logEventID = id;
    }
}
