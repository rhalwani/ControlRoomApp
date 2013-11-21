/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.controlroomapp;

/**
 *
 * @author Riad
 */
import java.net.Socket;
import java.net.InetAddress;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.io.InputStreamReader;
import java.io.InputStream;

public class HuaweiHLR {

    private final int PGWport = 7776;
    private final String PGWIP = "192.167.0.12";
    private final String cmdEnd = "---    END";
    static final String[] callFwdOpts = {"Busy", "No Reply", "Not Reachable"};
    private String user;
    private String password;
    private BufferedWriter bufout;
    private BufferedReader bufin;
    private Socket sock;
    private InputStream is;
    private String servedIMSI;
    private String servedMSISDN;
    private String A4KI;
    private String newIMSI;
    private String BAICval;
    private String BAOCval;
    private String BARRAOM;
    private String FTN;
    private String cmdOutput;
    static final short nullcmd = 0;
    static final short dispImsiDat = 1;
    static final short dispMsisdnDat = 2;
    static final short dispAC = 3;
    static final short crtAC = 4;
    static final short crtMSUB = 5;
    static final short simRepCmd = 6;
    static final short modifyBARROAM = 7;
    static final short modifyBARIC = 8;
    static final short modifyBAROC = 9;
    static final short canAC = 10;
    static final short canMSUB = 11;
    static final short actCallFwdU = 12;
    static final short canISDN = 13;
    static final short deactDataISDN = 14;
    static final short SIMReg = 15;
    static final short actData = 16;
    static final short simRepISDN = 17;
    static final short modifyBARICISDN = 18;
    static final short modifyBAROCISDN = 19;
    static final short modifyBARROAMISDN = 20;
    static final short actCallFwdUISDN = 21;
    static final short actDataISDN = 22;
    static final short deactCallFwdU = 23;
    static final short deactCallFwdUISDN = 24;
    static final short deactCallFwdB = 25;
    static final short deactCallFwdBISDN = 26;
    static final short actCallFwdB = 27;
    static final short actCallFwdBISDN = 28;
    static final short deactCallFwdNRY = 29;
    static final short deactCallFwdNRYISDN = 30;
    static final short actCallFwdNRY = 31;
    static final short actCallFwdNRYISDN = 32;
    static final short deactCallFwdNRC = 33;
    static final short deactCallFwdNRCISDN = 34;
    static final short actCallFwdNRC = 35;
    static final short actCallFwdNRCISDN = 36;

    public String getCmdOutput() {
        return this.cmdOutput;
    }

    public void setCmdOutput(String cmdOutput) {
        this.cmdOutput = cmdOutput;
    }

    public void setA4KI(String A4KI) {
        this.A4KI = A4KI;
    }

    public void setBAICval(String BAICval) {
        this.BAICval = BAICval;
    }

    public void setBAOCval(String BAOCval) {
        this.BAOCval = BAOCval;
    }

    public void setBARRAOM(String BARRAOM) {
        this.BARRAOM = BARRAOM;
    }

    public void setFTN(String FTN) {
        this.FTN = FTN;
    }

    public void setNewIMSI(String newIMSI) {
        this.newIMSI = newIMSI;
    }

    public void setServedIMSI(String servedIMSI) {
        this.servedIMSI = servedIMSI;
    }

    public void setServedMSISDN(String servedMSISDN) {
        this.servedMSISDN = servedMSISDN;
    }

    public String getPassword() {
        return this.password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return this.user;
    }

    public final void setUser(String user) {
        this.user = user;
    }

    public HuaweiHLR(String user, String pass) {
        setUser(user);
        setPassword(pass);
        login();
    }

    private void login() {
        try {
            this.sock = new Socket(InetAddress.getByName("192.167.0.12"), 7776);
            this.bufout = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
            this.is = this.sock.getInputStream();
            this.bufin = new BufferedReader(new InputStreamReader(this.is));

            this.bufout.write(new StringBuilder().append("LGI: HLRSN =1, OPNAME=\"").append(getUser()).append("\", PWD=\"").append(getPassword()).append("\";").toString());
            this.bufout.flush();

            while (this.is.available() == 0) {
                Thread.sleep(1000L);
            }
            String readLine;
            do {
                readLine = new StringBuilder().append(this.bufin.readLine()).append("\n").toString();
            } while (!readLine.contains("---    END".subSequence(0, "---    END".length() - 1)));
        } catch (InterruptedException ie) {
            System.err.println("Interrupted Exception during login.");
        } catch (IOException ie) {
            System.err.println("IO Exception during login.");
        }
    }

    public void logout() {
        try {
            this.bufout.write("LGO:;");
            this.bufout.flush();

            while (this.is.available() == 0) {
                Thread.sleep(1000L);
            }
            String readLine;
            do {
                readLine = new StringBuilder().append(this.bufin.readLine()).append("\n").toString();
            } while (!readLine.contains("---    END".subSequence(0, "---    END".length() - 1)));
        } catch (InterruptedException ie) {
            System.err.println("Interrupted Exception during logout.");
        } catch (IOException ie) {
            System.err.println("IO Exception during logout.");
        }
    }

    public void disconnectFromPGW() {
        try {
            logout();
            this.bufout.close();
            this.bufin.close();
            this.sock.close();
        } catch (IOException io) {
            System.err.println("Could not disconenect from PGW.");
        }
    }

    public void executeCommand(short CmdId) {
        setCmdOutput(null);
        String cliCmd;
        switch (CmdId) {
            case dispImsiDat:
                cliCmd = new StringBuilder().append("LST SUB: IMSI=\"60702").append(this.servedIMSI).append("\",DETAIL=TRUE;").toString();
                break;
            case dispMsisdnDat:
                cliCmd = new StringBuilder().append("LST SUB: ISDN=\"220").append(this.servedMSISDN).append("\",DETAIL=TRUE;").toString();
                break;
            case dispAC:
                cliCmd = new StringBuilder().append("LST KI: IMSI=\"60702").append(this.servedIMSI).append("\";").toString();
                break;
            case crtAC:
                cliCmd = new StringBuilder().append("ADD KI: HLRSN=1,CARDTYPE=SIM,ALG = COMP128_2,IMSI=\"60702").append(this.servedIMSI).append("\",KIVALUE=\"").append(this.A4KI).append("\",K4SNO=1;").toString();
                break;
            case canAC:
                cliCmd = new StringBuilder().append("RMV KI: IMSI=\"60702").append(this.servedIMSI).append("\";").toString();
                break;
            case canMSUB:
                cliCmd = new StringBuilder().append("RMV SUB: IMSI=\"60702").append(this.servedIMSI).append("\",RMVKI=TRUE;").toString();
                break;
            case canISDN:
                cliCmd = new StringBuilder().append("RMV SUB: ISDN=\"220").append(this.servedMSISDN).append("\",RMVKI=TRUE;").toString();
                break;
            case crtMSUB:
                cliCmd = new StringBuilder().append("ADD TPLSUB: HLRSN=1,IMSI=\"60702").append(this.servedIMSI).append("\",ISDN=\"220").append(this.servedMSISDN).append("\",TPLID=1;").toString();
                break;
            case simRepCmd:
                cliCmd = new StringBuilder().append("MOD IMSI: IMSI=\"60702").append(this.servedIMSI).append("\",NEWIMSI=\"60702").append(this.newIMSI).append("\";").toString();
                break;
            case simRepISDN:
                cliCmd = new StringBuilder().append("MOD IMSI: ISDN=\"220").append(this.servedMSISDN).append("\",NEWIMSI=\"60702").append(this.newIMSI).append("\";").toString();
                break;
            case modifyBARIC:
                cliCmd = new StringBuilder().append("MOD ODBIC:IMSI=\"60702").append(this.servedIMSI).append("\",ODBIC=").append(this.BAICval).append(";").toString();
                break;
            case modifyBAROC:
                cliCmd = new StringBuilder().append("MOD ODBOC:IMSI=\"60702").append(this.servedIMSI).append("\",ODBOC=").append(this.BAOCval).append(";").toString();
                break;
            case modifyBARICISDN:
                cliCmd = new StringBuilder().append("MOD ODBIC:ISDN=\"220").append(this.servedMSISDN).append("\",ODBIC=").append(this.BAICval).append(";").toString();
                break;
            case modifyBAROCISDN:
                cliCmd = new StringBuilder().append("MOD ODBOC:ISDN=\"220").append(this.servedMSISDN).append("\",ODBOC=").append(this.BAOCval).append(";").toString();
                break;
            case 7:
                cliCmd = new StringBuilder().append("MOD ODBROAM:IMSI=\"60702").append(this.servedIMSI).append("\",ODBROAM=").append(this.BARRAOM).append(";").toString();
                break;
            case modifyBARROAMISDN:
                cliCmd = new StringBuilder().append("MOD ODBROAM:ISDN=\"220").append(this.servedMSISDN).append("\",ODBROAM=").append(this.BARRAOM).append(";").toString();
                break;
            case actCallFwdU:
                cliCmd = new StringBuilder().append("REG CFU:IMSI=\"60702").append(this.servedIMSI).append("\",FTN=\"").append(this.FTN).append("\", PROPERTY=INTERNATIONAL;").toString();
                break;
            case actCallFwdUISDN:
                cliCmd = new StringBuilder().append("REG CFU:ISDN=\"220").append(this.servedMSISDN).append("\",FTN=\"").append(this.FTN).append("\", PROPERTY=INTERNATIONAL;").toString();
                break;
            case actCallFwdB:
                cliCmd = new StringBuilder().append("REG CFB:IMSI=\"60702").append(this.servedIMSI).append("\",FTN=\"").append(this.FTN).append("\", PROPERTY=INTERNATIONAL;").toString();
                break;
            case actCallFwdBISDN:
                cliCmd = new StringBuilder().append("REG CFB:ISDN=\"220").append(this.servedMSISDN).append("\",FTN=\"").append(this.FTN).append("\", PROPERTY=INTERNATIONAL;").toString();
                break;
            case actCallFwdNRY:
                cliCmd = new StringBuilder().append("REG CFNRY:IMSI=\"60702").append(this.servedIMSI).append("\",FTN=\"").append(this.FTN).append("\", PROPERTY=INTERNATIONAL;").toString();
                break;
            case actCallFwdNRYISDN:
                cliCmd = new StringBuilder().append("REG CFNRY:ISDN=\"220").append(this.servedMSISDN).append("\",FTN=\"").append(this.FTN).append("\", PROPERTY=INTERNATIONAL;").toString();
                break;
            case actCallFwdNRC:
                cliCmd = new StringBuilder().append("REG CFNRC:IMSI=\"60702").append(this.servedIMSI).append("\",FTN=\"").append(this.FTN).append("\", PROPERTY=INTERNATIONAL;").toString();
                break;
            case actCallFwdNRCISDN:
                cliCmd = new StringBuilder().append("REG CFNRC:ISDN=\"220").append(this.servedMSISDN).append("\",FTN=\"").append(this.FTN).append("\", PROPERTY=INTERNATIONAL;").toString();
                break;
            case deactCallFwdU:
                cliCmd = new StringBuilder().append("ERA CFU:IMSI=\"60702").append(this.servedIMSI).append(";").toString();
                break;
            case deactCallFwdUISDN:
                cliCmd = new StringBuilder().append("ERA CFU:ISDN=\"220").append(this.servedMSISDN).append(";").toString();
                break;
            case deactCallFwdB:
                cliCmd = new StringBuilder().append("ERA CFB:IMSI=\"60702").append(this.servedIMSI).append(";").toString();
                break;
            case deactCallFwdBISDN:
                cliCmd = new StringBuilder().append("ERA CFB:ISDN=\"220").append(this.servedMSISDN).append(";").toString();
                break;
            case deactCallFwdNRY:
                cliCmd = new StringBuilder().append("ERA CFU:IMSI=\"60702").append(this.servedIMSI).append(";").toString();
                break;
            case deactCallFwdNRYISDN:
                cliCmd = new StringBuilder().append("ERA CFU:ISDN=\"220").append(this.servedMSISDN).append(";").toString();
                break;
            case deactCallFwdNRC:
                cliCmd = new StringBuilder().append("ERA CFU:IMSI=\"60702").append(this.servedIMSI).append(";").toString();
                break;
            case deactCallFwdNRCISDN:
                cliCmd = new StringBuilder().append("ERA CFU:ISDN=\"220").append(this.servedMSISDN).append(";").toString();
                break;
            case actData:
                cliCmd = new StringBuilder().append("MOD NAM:IMSI=\"").append(this.servedIMSI).append("\",NAM=BOTH;\n").append("MOD TPLGPRS:IMSI=\"").append(this.servedIMSI).append("\",PROV=TRUE,TPLID=1;\n").append("MOD ARD:IMSI=\"").append(this.servedIMSI).append("\",PROV=TRUE, UTRANNOTALLOWED=FALSE, GERANNOTALLOWED=FALSE;").toString();

                break;
            case actDataISDN:
                cliCmd = new StringBuilder().append("MOD NAM:ISDN=\"220").append(this.servedMSISDN).append("\",NAM=BOTH;\n").append("MOD TPLGPRS:ISDN=\"220").append(this.servedMSISDN).append("\",PROV=TRUE,TPLID=1;\n").append("MOD ARD:ISDN=\"220").append(this.servedMSISDN).append("\",PROV=TRUE, UTRANNOTALLOWED=FALSE, GERANNOTALLOWED=FALSE;").toString();

                break;
            case deactDataISDN:
                cliCmd = new StringBuilder().append("MOD TPLGPRS:ISDN=\"220").append(this.servedMSISDN).append("\",PROV=FALSE;\n").append("MOD ARD:ISDN=\"220").append(this.servedMSISDN).append("\",PROV=TRUE, UTRANNOTALLOWED=TRUE, GERANNOTALLOWED=FALSE;").toString();

                break;
            case SIMReg:
            default:
                cliCmd = null;
                return;
        }

        boolean done = false;

        login();

        StringBuilder pgwReply = new StringBuilder();

        while (!done) {
            try {
                this.bufout.write(cliCmd);
                this.bufout.flush();

                while (this.is.available() == 0) {
                    Thread.sleep(1000L);
                }
                String readLine;
                do {
                    readLine = new StringBuilder().append(this.bufin.readLine()).append("\n").toString();
                    pgwReply.append(readLine);
                } while (!readLine.contains("---    END".subSequence(0, "---    END".length() - 1)));

                done = true;
                setCmdOutput(pgwReply.toString());
                disconnectFromPGW();
            } catch (UnknownHostException unknownHoste) {
                System.err.println("Unknown Host "+unknownHoste);
            } catch (IOException io) {
                System.err.println("IO Exception");
            } catch (InterruptedException i) {
                System.err.println("Interrupted Exception");
            }
        }
    }
}