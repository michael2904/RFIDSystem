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
    private int[] lastData = new int[5];
    private int dataC = 0;
    private int lastDataC = 0;
    private int count = 0;
    private int threshold = 5;
    private Boolean inPlace = true;
    private long lastSeen;

    public Item(String uid){
        System.out.println("New Item with uid: "+uid);
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
        System.out.println("adding data count: "+count);
        int dRssi = data.getRssi();
        int avDRddi = dRssi;
        if (count >= 5) {
            this.lastData[this.lastDataC] = dRssi;
            this.lastDataC = (this.lastDataC + 1) % 5;
            avDRddi = getAvIntData(this.lastData);
        }
        if(count >= 10) {
            int average = getAvData(this.data);
            System.out.println("average: "+average+" and rssi: "+avDRddi);
            if (((average + this.threshold) > avDRddi && avDRddi > (average - this.threshold)) && inPlace) {
                System.out.println("in place: "+avDRddi);
                this.data[this.dataC] = data;
                this.dataC = (this.dataC + 1) %10;
            }else if(!inPlace){
                System.out.println("not in place: "+avDRddi);
                if ((average + this.threshold) > avDRddi && avDRddi > (average - this.threshold)) {
                    System.out.println("put back in place: "+avDRddi);
                    inPlace = true;
                    PickUps pu = new PickUps(this.lastSeen,data.getTime(),this);
                    FirebaseConnection fc = new FirebaseConnection();
                    fc.pushPickUp(pu);
                    this.pickUps.add(pu);
                    this.data[this.dataC] = data;
                    this.dataC = (this.dataC + 1) %10;
                }
            }else{
                System.out.println("taken out of place: "+avDRddi);
                inPlace = false;
                this.lastSeen = data.getTime();
            }
        }else{
            System.out.println("adding data else count: "+count);
            this.data[this.dataC] = data;
            this.dataC = (this.dataC + 1) %10;
        }
        this.count++;
    }

    public int getAvData(TagReadData[] data){
        System.out.println("getAvData adding data count: ");
        int average = 0;
        for(int i = 0; i< data.length;i++){
            average += data[i].getRssi();
        }
        average /= data.length;
        System.out.println("average: "+average);
        return average;
    }

    public int getAvIntData(int[] data){
        System.out.println("getAvDataInt adding data count: ");
        int average = 0;
        for(int i = 0; i< data.length;i++){
            average += data[i];
            System.out.println("average: "+average);
        }
        average /= data.length;
        System.out.println("average: "+average);
        return average;
    }
}
