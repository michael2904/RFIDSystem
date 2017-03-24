package com.rfidsystem;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.thingmagic.TagReadData;

import java.util.ArrayList;


/**
 * Created by michaelAM on 3/22/17.
 */
public class Item {

    private String uid;
    private String name;
    private String description;
    private String type;
    private String price;
    private ArrayList<PickUps> pickUps = new ArrayList<PickUps>();
    private TagReadData[] data = new TagReadData[10];
    private int dataC = 0;
    private int count;
    private int threshold = 3;
    private Boolean inPlace = true;
    private long lastSeen;

    public Item(String uid){
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<PickUps> getPickUps() {
        return pickUps;
    }

    public void setPickUps(ArrayList<PickUps> pickUps) {
        this.pickUps = pickUps;
    }

    public TagReadData[] getData() {
        return data;
    }

    public void setData(TagReadData[] data) {
        this.data = data;
    }

    public void addData(TagReadData data){
        this.count++;
        if(count >= 10) {
            int average = getAvData();
            int dRssi = data.getRssi();
            if (((average + this.threshold) > dRssi && dRssi > (average - this.threshold)) && inPlace) {
                this.data[this.dataC] = data;
                this.dataC = (this.dataC + 1) %10;
            }else if(!inPlace){
                if ((average + this.threshold) > dRssi && (average - this.threshold < dRssi)) {
                    inPlace = true;
                    PickUps pu = new PickUps(this.lastSeen,data.getTime());
                    this.pickUps.add(pu);
                    this.data[this.dataC] = data;
                    this.dataC = (this.dataC + 1) %10;
                }
            }else{
                inPlace = false;
                this.lastSeen = data.getTime();
            }
        }
    }

    public int getAvData(){
        int average = 0;
        for(int i = 0; i< this.data.length;i++){
            average += this.data[i].getRssi();
        }
        average /= this.data.length;
        return average;
    }
}
