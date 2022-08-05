package com.minimakerlabrobo.valueObject;

public class ActivityBoxValue {

    String ino;
    String hex;
    String prot;
    String label;

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    String directory;
    String comando;

    public String getIno() {
        return ino;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getProt() {

        return prot;
    }

    public void setProt(String prot) {
        this.prot = prot;
    }

    public void setIno(String ino) {
        this.ino = ino;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
