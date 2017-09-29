package com.myapp.rishabhrawat.womensafety;

/**
 * Created by Rishabh Rawat on 7/3/2017.
 */

public class UserContact {

    private String uname;
    private String uphone;
    private String uemail;
    private String uaddress;
    //private byte[] uimage;

    public UserContact(String uname, String uphone, String uemail, String uaddress){//, byte[] uimage) {
        this.uname = uname;
        this.uphone = uphone;
        this.uemail = uemail;
        this.uaddress = uaddress;
      //  this.uimage = uimage;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

   // public byte[] getUimage() {
     //   return uimage;
    //}

    //public void setUimage(byte[] uimage) {
      //  this.uimage = uimage;
    //}
}
