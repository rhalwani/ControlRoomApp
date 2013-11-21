package my.controlroomapp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Riad
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class dbCommunicator {

    static String DB_CUSTCARE_URL;
    static String DB_CUSTCARE_user;
    static String DB_CUSTCARE_pwd;
    private Connection dbcon;
    //private SubscriberRegistration subRegObj;

    public dbCommunicator() throws java.lang.ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        loadXmlFile();
        this.dbcon = DriverManager.getConnection(DB_CUSTCARE_URL,DB_CUSTCARE_user,DB_CUSTCARE_pwd);
        //subRegObj = new SubscriberRegistration();
    }

    private void loadXmlFile() {

        try {
            java.util.Properties p = new java.util.Properties();
            java.io.FileInputStream fis = new java.io.FileInputStream("appconfig.xml");
            p.loadFromXML(fis);
            DB_CUSTCARE_URL = p.getProperty("DB_CUSTCARE_URL");
            DB_CUSTCARE_user = p.getProperty("DB_CUSTCARE_user");
            DB_CUSTCARE_pwd = p.getProperty("DB_CUSTCARE_pwd");
            }
        catch (java.io.FileNotFoundException fe) {
            System.out.println(fe.getMessage());
        }
        catch (java.io.IOException ie) {
            System.out.println(ie.getMessage());
        }
    }
    
    String connect(String userid, String password) throws SQLException {
        
        PreparedStatement loginStmt = this.dbcon.prepareStatement("SELECT role, username "+
                "from sub_mgmt_users where user_id = ? and password = ? and deleted = 0");
        loginStmt.setString(1, userid);
        loginStmt.setString(2, password);
        ResultSet lrs = loginStmt.executeQuery();
        if (!lrs.next())
            return null;
        else
            return Short.toString(lrs.getShort("role"))+","+lrs.getString("username");
    }

    void disconnect() throws SQLException {
        if (dbcon != null) dbcon.close();
    }
    
    void insertRegistrationInfo(Object[] regInfo, boolean activate) throws SQLException {

        PreparedStatement subInsStmt = dbcon.prepareStatement("insert into subscriber_registration ("+
            "phone,"+
            "first_name,"+
            "fathers_name,"+
            "family_name,"+
            "gender,"+
            "id_type,"+
            "id_number,"+
            "nationality,"+
            "birthday_date,"+
            "email,"+
            "residence_city,"+
            "residence_street,"+
            "residence_postal,"+
            "sales_person,"+
            "phone_type,"+
            "education,"+
            "occupation,"+
            "company_name,"+
            "company_city,"+
            "company_street,"+
            "purchase_date,"+
            "question_1,"+
            "question_2,"+
            "question_3,"+
            "question_4,"+
            "status"+
            ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'COMPLETE')");
        int i = 0; //phone
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //first_name
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //fathers_name
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //family_name
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //gender
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //id_type
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //id_number
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //nationality
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //birthday_date
        subInsStmt.setDate((i+1), java.sql.Date.valueOf((String)regInfo[i]));
        i++; //email
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //residence_city
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //residence_street
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //residence_postal
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //sales_person
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //phone_type
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //education
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //occupation
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //company_name
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //company_city
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //company_street
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //purchase_date
        subInsStmt.setDate((i+1), java.sql.Date.valueOf((String)regInfo[i]));
        i++; //question_1
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //question_2
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //question_3
        subInsStmt.setString((i+1), (String)regInfo[i]);
        i++; //question_4
        subInsStmt.setString((i+1), (String)regInfo[i]);
        subInsStmt.executeUpdate();

        if (activate) {
            subInsStmt = dbcon.prepareStatement(
                "insert into directs (date, customer_name, address,"+
                "tel, user, activated values (curdate(),?,?,?,?,0)");
            subInsStmt.setString(1, regInfo[1]+" "+regInfo[2]);
            subInsStmt.setString(2, regInfo[8]+", "+regInfo[7]);
            subInsStmt.setString(3, (String)regInfo[0]);
            subInsStmt.setString(4, (String)regInfo[10]);
            subInsStmt.executeUpdate();
        }
    }
    
    void insertActivationInfo(String[] regInfo)throws SQLException {
        
        PreparedStatement subInsStmt = dbcon.prepareStatement(
                "insert into subscriber_registration("+
                "phone,"+
                "first_name,"+
                "family_name,"+
                "gender,"+
                "id_type,"+
                "id_number,"+
                "nationality,"+
                "residence_city,"+
                "residence_street,"+
                "purchase_date,"+
                "sales_person,"+
                "receipt_no) "+
                "values (?,?,?,?,?,?,?,?,?,?,?,?)");
        subInsStmt.setString(1, regInfo[0]); //phone
        subInsStmt.setString(2, regInfo[1]); //first_name
        subInsStmt.setString(3, regInfo[2]); //family_name
        subInsStmt.setString(4, regInfo[3]); //gender
        subInsStmt.setString(5, regInfo[4]); //id_type
        subInsStmt.setString(6, regInfo[5]); //ID_No
        subInsStmt.setString(7, regInfo[6]); //nationality
        subInsStmt.setString(8, regInfo[7]); //residence_city
        subInsStmt.setString(9, regInfo[8]); //residence_street
        subInsStmt.setDate(10,java.sql.Date.valueOf(regInfo[9]));
        subInsStmt.setString(11, regInfo[10]); //sales_person
        subInsStmt.setString(12, regInfo[11]); //receipt_no
        subInsStmt.executeUpdate();

        subInsStmt = dbcon.prepareStatement(
            "insert into directs (date, customer_name, address,"+
            "tel, user, activated) values (curdate(),?,?,?,?,0)");
        subInsStmt.setString(1, regInfo[1]+" "+regInfo[2]);
        subInsStmt.setString(2, regInfo[8]+", "+regInfo[7]);
        subInsStmt.setString(3, regInfo[0]);
        subInsStmt.setString(4, regInfo[9]);
        subInsStmt.executeUpdate();
    }

    void updateRegistrationInfo(Object[] regInfo, int custId) throws SQLException {

        System.out.println("Update customer id: "+custId);
        PreparedStatement subUpdStmt = dbcon.prepareStatement("update subscriber_registration set "+
            "first_name = ?,"+
            "fathers_name = ?,"+
            "family_name = ?,"+
            "gender = ?,"+
            "id_type = ?,"+
            "id_number = ?,"+
            "nationality = ?,"+
            "birthday_date = ?,"+
            "email = ?,"+
            "residence_city = ?,"+
            "residence_street = ?,"+
            "residence_postal = ?,"+
            "sales_person = ?,"+
            "phone_type = ?,"+
            "education = ?,"+
            "occupation = ?,"+
            "company_name = ?,"+
            "company_city = ?,"+
            "company_street = ?,"+
            "purchase_date = ?,"+
            "question_1 = ?,"+
            "question_2 = ?,"+
            "question_3 = ?,"+
            "question_4 = ?,"+
            "status = 'COMPLETE' "+
            "WHERE id = ?");
        
        int i = 0; //first_name
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //fathers_name
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //family_name
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //gender
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //id_type
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //id_number
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //nationality
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //birthday_date
        System.out.println("info at "+i+": "+regInfo[i]);
        subUpdStmt.setDate((i+1), java.sql.Date.valueOf((String)regInfo[i]));
        //subUpdStmt.setDate(i,new java.sql.Date());
        i++; //email
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //residence_city
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //residence_street
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //residence_postal
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //sales_person
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //phone_type
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //education
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //occupation
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //company_name
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //company_city
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //company_street
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //purchase_date
        System.out.println("info at "+i+": "+regInfo[i]);
        subUpdStmt.setDate((i+1), java.sql.Date.valueOf((String)regInfo[i]));
        i++; //question_1
        System.out.println("info at "+i+": "+regInfo[i]);
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //question_2
        System.out.println("info at "+i+": "+regInfo[i]);
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //question_3
        System.out.println("info at "+i+": "+regInfo[i]);
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++; //question_4
        System.out.println("info at "+i+": "+regInfo[i]);
        subUpdStmt.setString((i+1), (String)regInfo[i]);
        i++;
        //System.out.println("info at "+i+": "+regInfo[i]);
        subUpdStmt.setInt((i+1), custId);
        
        subUpdStmt.executeUpdate();
    }
    
    Object[] getRegistrationInfo(String phoneNum) throws SQLException {

        PreparedStatement subGetStmt = dbcon.prepareStatement(
                "SELECT "+
                /*
                "first_name, fathers_name, family_name, gender, id_type, id_number, nationality, "+
                "residence_city, residence_street, receipt_no, sales_person, phone_type, birthday_date, "+
                "email, education, occupation, company_name, company_city, company_street, purchase_date, "+
                "sales_person, question_1, question_2, question_3, question_4 "+
                 */
                "first_name,"+
                "fathers_name,"+
                "family_name,"+
                "gender,"+
                "birthday_date,"+
                "email,"+
                "education,"+
                "id_type,"+
                "id_number,"+
                "nationality,"+
                "residence_city,"+
                "residence_street,"+
                "residence_postal,"+
                "sales_person,"+
                "phone_type,"+
                "occupation,"+
                "company_name,"+
                "company_city,"+
                "company_street,"+
                "purchase_date,"+
                "sales_person,"+
                "question_1,"+
                "question_2,"+
                "question_3,"+
                "question_4,"+
                "id "+
                "from subscriber_registration WHERE phone = ?");// and status = 'INCOMPLETE'");
        
        subGetStmt.setString(1, phoneNum); //phone
        ResultSet srs = subGetStmt.executeQuery();
        if (!srs.next())
            return null;
        else
            return new Object[] {
                srs.getString("first_name"),
                srs.getString("fathers_name"),
                srs.getString("family_name"),
                srs.getString("gender"),
                srs.getString("id_type"),
                srs.getString("id_number"),
                srs.getString("nationality"),
                srs.getDate("birthday_date"),
                srs.getString("email"),
                srs.getString("residence_city"),
                srs.getString("residence_street"),
                srs.getString("residence_postal"),
                srs.getString("sales_person"),
                srs.getString("phone_type"),
                srs.getString("education"),
                srs.getString("occupation"),
                srs.getString("company_name"),
                srs.getString("company_city"),
                srs.getString("company_street"),
                srs.getDate("purchase_date"),
                srs.getString("question_1"),
                srs.getString("question_2"),
                srs.getString("question_3"),
                srs.getString("question_4"),
                srs.getInt("id")
            };
    }
    
    java.util.ArrayList<String> getAllSubscibers() throws SQLException {
        
        PreparedStatement subGetStmt = dbcon.prepareStatement("SELECT phone from subscriber_registration order by phone");
        ResultSet phoneNumsRS = subGetStmt.executeQuery();
        java.util.ArrayList<String> allPhoneNums = new java.util.ArrayList<String>();
        
        while(phoneNumsRS.next()) {
            allPhoneNums.add(phoneNumsRS.getString("phone"));
        }
        return allPhoneNums;
    }
    
    void logAction(String user, String telNum, short actType) throws SQLException, java.net.UnknownHostException {

        java.net.InetAddress i = java.net.InetAddress.getLocalHost();
        PreparedStatement logStmt = dbcon.prepareStatement("INSERT INTO logs ("+
                "user, action_date, action_time, msisdn, actionType, ip, pc) VALUES ("+
                "?, curdate(), curtime(), ?, ?, ?, ?)");
        logStmt.setString(1, user);
        logStmt.setString(2, telNum);
        logStmt.setShort(3, actType);
        logStmt.setString(4, i.getHostAddress());
        logStmt.setString(5, i.getHostName());
        logStmt.executeUpdate();
    }

/*
    int insertSubscriberDetails(String[] subInfo) throws SQLException {

        PreparedStatement subInsStmt = dbcon.prepareStatement(
                "insert into change_of_sim(Date,Time,Branch,Name,Sex,DOB,Address,ID_Card, ID_No,GSM_No,Type,user,Ref1,Ref2,Ref3) "+
                "values (curdate(), curtime(),'Application',?,?,?,?,?,?,?,'Prepaid',?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        subInsStmt.setString(1, subInfo[0]); //Name
        subInsStmt.setString(2, subInfo[1]); //Sex
        subInsStmt.setString(3, subInfo[2]); //DOB
        subInsStmt.setString(4, subInfo[3]); //Address
        subInsStmt.setString(5, subInfo[4]); //ID_Card
        subInsStmt.setString(6, subInfo[5]); //ID_No
        subInsStmt.setString(7, subInfo[6]); //GSM_No
        subInsStmt.setString(8, subInfo[7]); //user
        subInsStmt.setString(9, subInfo[8]); //ref1
        subInsStmt.setString(10, subInfo[9]); //ref2
        subInsStmt.setString(11, subInfo[10]); //ref3
        subInsStmt.executeUpdate();

        ResultSet rs = subInsStmt.getGeneratedKeys();
        if (rs.next())
            return rs.getInt(1);
        else
            return -1;
    }
 *
 */
}