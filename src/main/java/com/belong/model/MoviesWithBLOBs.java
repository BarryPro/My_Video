package com.belong.model;

public class MoviesWithBLOBs extends Movies {
    private String vname;

    private String vinfo;

    private byte[] vpic;

    private String vsrc;

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname == null ? null : vname.trim();
    }

    public String getVinfo() {
        return vinfo;
    }

    public void setVinfo(String vinfo) {
        this.vinfo = vinfo == null ? null : vinfo.trim();
    }

    public byte[] getVpic() {
        return vpic;
    }

    public void setVpic(byte[] vpic) {
        this.vpic = vpic;
    }

    public String getVsrc() {
        return vsrc;
    }

    public void setVsrc(String vsrc) {
        this.vsrc = vsrc == null ? null : vsrc.trim();
    }
}