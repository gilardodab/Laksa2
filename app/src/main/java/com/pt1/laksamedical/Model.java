package com.pt1.laksamedical;

import java.io.Serializable;

public class Model implements Serializable {
    private String  title , Cost, Stok;
    private String img;
    private int quant;
    private int id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getStok() {
        return Stok;
    }

    public void setStok(String stok) {Stok = stok;}

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
