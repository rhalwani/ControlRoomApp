/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.controlroomapp;

/**
 *
 * @author Riad
 */

import com.jscape.inet.bsd.Rsh;
import com.jscape.inet.bsd.BsdException;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MSCCommander implements Runnable {

    private String hostname, username, localUsername;
    static final String[] callFwdOpts = {"ALLCF","CFU","CFBUSY","CFNREPLY","CFNREACH"};
    private volatile String servedIMSI, servedMSISDN, dispVal, A4KI, BAROAMval, BAOCval, BAICval, NewIMSI, cmdOutput, callFwdOpt, callFwdDest, dataProfile;
    private Rsh rexec;
    private short execMode, command;
    private final short localMode = 0, remoteMode = 1;
    final static short nullcmd = 0, dispImsiDat = 1, dispMsisdnDat = 2, dispAC = 3,
            crtAC = 4 , crtMSUB = 5, simRepCmd = 6, modifyBARROAM = 7, modifyBARIC = 8,
            modifyBAROC = 9, canAC = 10, canMSUB = 11, actCallFwd = 12, actCallFwdAllCF = 13, canCallFwd = 14, SIMReg = 15, actData = 16;

    //cli task -n MSC1 -i DISPMSUB:SN=,DATAKD=ALLDATA;
    public MSCCommander(String CLIServer, String CLIUser, String LocalUser) {

        hostname = CLIServer;
        username = CLIUser;
        localUsername = LocalUser;
        execMode = remoteMode;
        rexec = new Rsh(hostname,username,localUsername,null,false);
    }

    public MSCCommander() {
        execMode = localMode;
        rexec = null;
    }

    synchronized void setMSISDN(String MSISDN) {
        servedMSISDN = MSISDN;
    }

    synchronized void setIMSI(String IMSI) {
        servedIMSI = IMSI;
    }

    synchronized void setDisplayValue(String val) {
        dispVal = val;
    }

    synchronized void setAuthKey(String key) {
        A4KI = key;
    }

    synchronized void setBARROAM(String val) {
        BAROAMval = val;
    }

    synchronized void setBAOC(String val) {
        BAOCval = val;
    }

    synchronized void setBAIC(String val) {
        BAICval = val;
    }

    synchronized void setNewIMSI(String val) {
        NewIMSI = val;
    }

    synchronized void setCallFwdCfServ(String opt) {
        callFwdOpt = opt;
    }

    synchronized void setCallFwdDestNum(String destNum) {
        callFwdDest = destNum;
    }
    
    synchronized void setDataProfile(String profileSpeed) {
        dataProfile = profileSpeed;
    }

    synchronized void setCLICommand(short cmd) {
        command = cmd;
    }

    synchronized String readOutput() {
        return cmdOutput;
    }

    synchronized void resetOutput() {
        cmdOutput = null;
    }

    String[] getcallFwdOpts() {
        return callFwdOpts;
    }

    private synchronized void setOutput(String bufOut) {
        cmdOutput = bufOut;
    }

    private synchronized void executeCommand(short cmd) throws BsdException, IOException {

        String cliCmd;
        switch(cmd) {
            case dispImsiDat:
                cliCmd = "cli task -n MSC1 -i DISPMSUB:MSIN="+servedIMSI+",DATAKD="+dispVal;
                break;
            case dispMsisdnDat:
                cliCmd = "cli task -n MSC1 -i DISPMSUB:SN="+servedMSISDN+",DATAKD="+dispVal;
                break;
            case dispAC:
                cliCmd = "cli task -n MSC1 -i DISPACMSUB:MSIN="+servedIMSI;
                break;
            case crtAC:
                cliCmd = "cli task -n MSC1 -i CRACMSUB:MSIN="+servedIMSI+",A4KI="+A4KI;
                break;
            case canAC:
                cliCmd = "cli task -n MSC1 -i CANACMSUB:MSIN="+servedIMSI;
                break;
            case canMSUB:
                cliCmd = "cli task -n MSC1 -i CANMSUB:MSIN="+servedIMSI;
                break;
            case crtMSUB:
                cliCmd = "cli task -n MSC1 -i CRMSUB:MSIN="+servedIMSI+",BSNBC=TELEPHON-"+servedMSISDN+"-TELEPHON&TS21&TS22,REFMSIN=9900000020";
                break;
            case simRepCmd:
                cliCmd = "cli task -n MSC1 -i ENTRSIMCCH:MSIN="+servedIMSI+",NMSIN="+NewIMSI+",TIME=OPR";
                break;
            case modifyBARIC:
                cliCmd = "cli task -n MSC1 -i MODMSUB:MSIN="+servedIMSI+",BAIC="+BAICval;
                break;
            case modifyBAROC:
                cliCmd = "cli task -n MSC1 -i MODMSUB:MSIN="+servedIMSI+",BAOC="+BAOCval;
                break;
            case modifyBARROAM:
                cliCmd = "cli task -n MSC1 -i MODMSUB:MSIN="+servedIMSI+",BAROAM="+BAROAMval;
                break;
            case actCallFwd:
                cliCmd = "cli task -n MSC1 -i ENTRCFSERV:MSIN="+servedIMSI+",CFSERV="+callFwdOpt+",BSFTNO=TELEPHON-"+callFwdDest;
                break;
            case actCallFwdAllCF:
                cliCmd = "cli task -n MSC1 -i ENTRCFSERV:MSIN="+servedIMSI+",CFSERV=ALLCF";
                break;
            case canCallFwd:
                cliCmd = "cli task -n MSC1 -i CANCFSERV:MSIN="+servedIMSI+",CFSERV="+callFwdOpt;
                break;
            case SIMReg:
                cliCmd = "cli task -n MSC1 -i MODMSUB:MSIN="+servedIMSI+",MSCAT=ORDINSUB";
                break;
            case actData:
                cliCmd = "cli task -n MSC1 -i MODMSUB:MSIN="+servedIMSI+",BSNBC=GPRS,CACCRES=UTRAN;\r\n"+
                         "cli task -n MSC1 -i ENTRGPRSERV:MSIN="+servedIMSI+",PDPREC=PDPREC8,PDPADR=IPV4,QOSPROF="+dataProfile+",APNAREA=ALLPLMN";
                break;
            default:
                cliCmd = null;
                break;
        }
        if (cliCmd != null)
            switch(execMode) {
                case remoteMode:
                    rexec.setCommand(cliCmd);
                    // connect to server and execute command
                    rexec.connect();
                    rexec.execute();
                    // get results of command executed and print to console
                    InputStream input = rexec.getInputStream();
                    int in = input.read();
                    StringBuffer buffer = new StringBuffer();
                    if(in != 0)
                        buffer.append("Failure! Remote host returned: \n");
                    while((in = input.read()) != -1) {
                        buffer.append((char)in);
                        if (in == 10) {
                            in = input.read();
                            if (in == 13 || in == 32)
                                input.skip((long)1);
                            else
                                if (in != -1)
                                    buffer.append((char)in);
                        }
                    }
                    cmdOutput = buffer.toString();
                    // disconnect from server
                    rexec.disconnect();
                    break;
                case localMode:
                    Runtime systemShell = Runtime.getRuntime();
                    Process output = systemShell.exec(cliCmd);
                    BufferedReader br = new BufferedReader (new InputStreamReader(output.getInputStream()));
                    String line, temp = new String();
                    while((line = br.readLine()) != null )
                        if(!(line.startsWith("") || line.startsWith(" ")))
                            temp += line+'\n';
                    setOutput(temp);
                    break;
                default:
                    break;
            }
}

    public void run() {

        while(true) {
            switch(command) {
                case dispImsiDat:
                case dispMsisdnDat:
                case dispAC:
                case crtAC:
                case crtMSUB:
                case simRepCmd:
                case modifyBARIC:
                case modifyBAROC:
                case modifyBARROAM:
                case canAC:
                case canMSUB:
                case canCallFwd:
                case actCallFwd:
                case actCallFwdAllCF:
                case SIMReg:
                case actData:
                    try {
                        executeCommand(command);
                        }
                    catch(BsdException bsde) {
                        System.out.println("BSD Exception occured: "+bsde.getMessage());
                    }
                    catch(IOException ioe) {
                        System.out.println("I/O Exception occured: "+ioe.getMessage());
                    }
                    setCLICommand(nullcmd);
                    break;
                default:
                    try {
                        Thread.currentThread().sleep((long)1000);
                        }
                    catch (InterruptedException intexc) {
                        System.out.println("Thread "+Thread.currentThread().getName()+
                                " interrupted");
                    }
                    break;
            }
        }
    }
}
