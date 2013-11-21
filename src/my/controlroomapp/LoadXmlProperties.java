/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.controlroomapp;

/**
 *
 * @author Riad
 */
public class LoadXmlProperties {

    static String DB_CtrlRoom_URL;
    static String DB_VAS_URL;
    static String DB_VAS1_URL;
    static String DB_VAS2_URL;
    static String DB_VSD_URL;
    static String DB_OMVIA_URL;
    static String DB_CSTMR_URL;
    static String DB_EVOUCHER_URL;
    static String DB_CRBT_URL;
    static String DB_FB_URL;
    static String DB_CUSTCARE_URL;
    static String DB_CREDTRANS_URL;
    static String DB_SMSSC_URL;
    static String DB_TOKENS_URL;
    static String DB_MSC_URL;
    static String DB_CR_user;
    static String DB_VAS_user;
    static String DB_VAS1_user;
    static String DB_VAS2_user;
    static String DB_VSD_user;
    static String DB_TDS_user;
    static String DB_CSTMR_user;
    static String DB_EVOUCHER_user;
    static String DB_CRBT_user;
    static String DB_FB_user;
    static String DB_CUSTCARE_user;
    static String DB_CREDTRANS_user;
    static String DB_SMSSC_user;
    static String DB_TOKENS_user;
    static String DB_MSC_user;
    static String DB_VAS_pwd;
    static String DB_VAS1_pwd;
    static String DB_VAS2_pwd;
    static String DB_CR_pwd;
    static String DB_VSD_pwd;
    static String DB_TDS_pwd;
    static String DB_CSTMR_pwd;
    static String DB_EVOUCHER_pwd;
    static String DB_CRBT_pwd;
    static String DB_FB_pwd;
    static String DB_CUSTCARE_pwd;
    static String DB_CREDTRANS_pwd;
    static String DB_SMSSC_pwd;
    static String DB_TOKENS_pwd;
    static String DB_MSC_pwd;
    static String CLI_USER;
    static String CLI_LOCAL_USER;
    static String MED_DEALER;

    public static void loadXmlFile() {

        try {
            java.util.Properties p = new java.util.Properties();
            java.io.FileInputStream fis = new java.io.FileInputStream("config.xml");
            p.loadFromXML(fis);
            DB_CtrlRoom_URL = p.getProperty("DB_CtrlRoom_URL");
            DB_VAS_URL = p.getProperty("DB_VAS_URL");
            DB_VSD_URL = p.getProperty("DB_VSD_URL");
            DB_OMVIA_URL = p.getProperty("DB_OMVIA_URL");
            DB_CSTMR_URL = p.getProperty("DB_CSTMR_URL");
            DB_EVOUCHER_URL = p.getProperty("DB_EVOUCHER_URL");
            DB_VAS1_URL = p.getProperty("DB_VAS1_URL");
            DB_VAS2_URL = p.getProperty("DB_VAS2_URL");
            DB_CRBT_URL = p.getProperty("DB_CRBT_URL");
            DB_FB_URL = p.getProperty("DB_FB_URL");
            DB_CUSTCARE_URL = p.getProperty("DB_CUSTCARE_URL");
            DB_CREDTRANS_URL = p.getProperty("DB_CREDTRANS_URL");
            DB_SMSSC_URL = p.getProperty("DB_SMSSC_URL");
            DB_TOKENS_URL = p.getProperty("DB_TOKENSDB_URL");
            DB_MSC_URL = p.getProperty("DB_MSC_URL");
            DB_CR_user = p.getProperty("DB_CR_user");
            DB_VAS_user = p.getProperty("DB_VAS_user");
            DB_VAS1_user = p.getProperty("DB_VAS1_user");
            DB_VAS2_user = p.getProperty("DB_VAS2_user");
            DB_VSD_user = p.getProperty("DB_VSD_user");
            DB_TDS_user = p.getProperty("DB_TDS_user");
            DB_CSTMR_user = p.getProperty("DB_CSTMR_user");
            DB_EVOUCHER_user = p.getProperty("DB_EVOUCHER_user");
            DB_CRBT_user = p.getProperty("DB_CRBT_user");
            DB_FB_user = p.getProperty("DB_FB_user");
            DB_CUSTCARE_user = p.getProperty("DB_CUSTCARE_user");
            DB_CREDTRANS_user = p.getProperty("DB_CREDTRANS_user");
            DB_SMSSC_user = p.getProperty("DB_SMSSC_user");
            DB_TOKENS_user = p.getProperty("DB_TOKENSDB_user");
            DB_MSC_user = p.getProperty("DB_MSC_user");
            DB_VAS_pwd = p.getProperty("DB_VAS_pwd");
            DB_VAS1_pwd = p.getProperty("DB_VAS1_pwd");
            DB_VAS2_pwd = p.getProperty("DB_VAS2_pwd");
            DB_CR_pwd = p.getProperty("DB_CR_pwd");
            DB_VSD_pwd = p.getProperty("DB_VSD_pwd");
            DB_TDS_pwd = p.getProperty("DB_TDS_pwd");
            DB_CSTMR_pwd = p.getProperty("DB_CSTMR_pwd");
            DB_EVOUCHER_pwd = p.getProperty("DB_EVOUCHER_pwd");
            DB_CRBT_pwd = p.getProperty("DB_CRBT_pwd");
            DB_FB_pwd = p.getProperty("DB_FB_pwd");
            DB_CREDTRANS_pwd = p.getProperty("DB_CREDTRANS_pwd");
            DB_CUSTCARE_pwd = p.getProperty("DB_CUSTCARE_pwd");
            DB_SMSSC_pwd = p.getProperty("DB_SMSSC_pwd");
            DB_TOKENS_pwd = p.getProperty("DB_TOKENSDB_pwd");
            DB_MSC_pwd = p.getProperty("DB_MSC_pwd");
            CLI_USER = p.getProperty("CLI_User");
            CLI_LOCAL_USER = p.getProperty("CLI_Local_User");
            MED_DEALER = p.getProperty("MED_DEALER", "");
        } catch (java.io.FileNotFoundException fe) {
            System.out.println(fe.getMessage());
        } catch (java.io.IOException ie) {
            System.out.println(ie.getMessage());
        }
    }
}
