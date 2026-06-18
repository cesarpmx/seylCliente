/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author gespinosa
 */
import java.io.InputStream;
import java.util.Properties;

public class ReadProps {

    /** Creates a new instance of ReadProps */
    public ReadProps() {
    }

    public Properties config(String nameProps) {
        Properties prop = new Properties();
        try {
            InputStream in = getClass().getResourceAsStream(nameProps);
            prop.load(in);
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return prop;
    }
}
