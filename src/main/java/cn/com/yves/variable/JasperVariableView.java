/**
 * Filename:    JasperVariableView.java
 * Copyright:   Copyright (c)2016
 * Company:     Yves
 * @version:    1.0
 * Create at:   2017-9-18
 * Description:
 *
 * Author       Yves He
 */
package cn.com.yves.variable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

/**
 * javax.xml.parsers.DocumentBuilderFactory", "
 * com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
 *
 * @author Yves He
 *
 */
public class JasperVariableView {

    public static void main(String[] args) {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

        InputStream in = null;
        Connection con = null;
        try {
            in = new FileInputStream(new File("Jasper-Detail.jrxml"));

            // 1.获取JasperDesign: 获取JasperDesign对象
            JasperDesign jasperDesign = JRXmlLoader.load(in);

            JRVariable[] variables = jasperDesign.getVariables(); // JRVariable-->JRBaseVariable/JRFillVariable-->JRDesignVariable(extends
                                                                  // JRBaseVariable)
            for (JRVariable v : variables) {
                JRDesignVariable jrV = (JRDesignVariable) v;

                System.out.println(v.getClass());

                // ResetTypeEnum.REPORT //返回初始化时固定的值.

            }

            // 2.编译: 产生 .Jasper文件
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            // 生成最后的模板文件
            JRXmlWriter.writeReport(jasperReport, "Jasper-Detail-Dest.jrxml", "UTF-8");

            System.out.println("success!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/jasperreport_test?characterEncoding=utf-8", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
