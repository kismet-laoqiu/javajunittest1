package com.example.demo.util.attributeEncryption;

import com.example.demo.util.attributeEncryption.cpabe.Cpabe;

import java.io.File;

/**
 * @ClassName: AttributeEncryption
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/6/26 8:09
 **/
public class AttributeEncryption {
    private static Cpabe cpabe = new Cpabe();
    private static String dir = "output\\";
    private static String pubfile = dir + "pub_key";
    private static String mskfile = dir + "master_key";
    private static String prvfile = dir + "prv_key";
    private static String inputfile = dir + "input.txt";
    private static String encfile = dir + "e_input.txt";
    private static String decfile = dir + "d_input.txt";
    private static String attrs = "";
    private static String policy = "";


    public static void setPath(Long recordId) {
        dir = "output\\" + recordId.toString() + "\\";
        File path = new File(dir);
        if(!path.exists()){
            path.mkdirs();
        }
        pubfile = dir + "pub_key";
        mskfile = dir + "master_key";
        prvfile = dir + "prv_key";
        inputfile = dir + "input.txt";
        encfile = dir + "e_input.txt";
        decfile = dir + "d_input.txt";
    }

    public static void setPath(String recordId) {
        dir = "output\\" + recordId + "\\";
        File path = new File(dir);
        if(!path.exists()){
            path.mkdirs();
        }
        pubfile = dir + "pub_key";
        mskfile = dir + "master_key";
        prvfile = dir + "prv_key";
        inputfile = dir + "input.txt";
        encfile = dir + "e_input.txt";
        decfile = dir + "d_input.txt";
    }

    public static void setAttrs(String attrs) {
        AttributeEncryption.attrs = attrs;
    }

    public static void setPolicy(String policy) {
        AttributeEncryption.policy = policy;
    }

    public static void setup()throws Exception {
        cpabe.setup(pubfile, mskfile);
    }

    public static void keygen() throws Exception{
        cpabe.keygen(pubfile, prvfile, mskfile, attrs);
    }

    public static void ecode() throws Exception{
        cpabe.enc(pubfile, policy, inputfile, encfile);
    }

    public static void dcode() throws Exception{
        cpabe.dec(pubfile, prvfile, encfile, decfile);
    }

    public static String getInputfile() {
        return inputfile;
    }

    public static String getEncfile() {
        return encfile;
    }

    public static String getDecfile() {
        return decfile;
    }

}
