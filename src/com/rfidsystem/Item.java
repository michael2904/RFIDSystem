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
    private TagReadData[] inPlaceData = new TagReadData[20];
    private int[] filtered = new int[5];
    private int inPlaceC = 0;
    private int filteredC = 0;
    private int count = 0;
    private int threshold = 4;
    private Boolean inPlace = true;
    private long lastSeen;
    private ItemInfoScreen info;
    private Boolean infoLaunched = false;

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

    public TagReadData[] getInPlaceData() {
        return this.inPlaceData;
    }

    public void setInPlaceData(TagReadData[] data) {
        this.inPlaceData = data;
    }

    public void addData(TagReadData data){
        java.util.Date startTimeDate = new java.util.Date(data.getTime());
        int dRssi = data.getRssi();
        int filtRSSI = dRssi;
        this.filtered[this.filteredC] = dRssi;
        this.filteredC = (this.filteredC + 1) % 5;
        if (count >= 5) {
            filtRSSI = getAvIntData(this.filtered);
//            System.out.println(data.getTag().epcString() +"," + data.getTime() + ","+startTimeDate+"," + data.getReadCount() + "," + filtRSSI+","+dRssi);
        }else{
//            System.out.println(data.getTag().epcString() +"," + data.getTime() + ","+startTimeDate+"," + data.getReadCount() + ",None,"+dRssi);
        }
        if(count >= 20) {
            int average = getAvData(this.inPlaceData);
            if(count == 20)System.out.println("Position saved: with value"+average);
//            System.out.println("is in Place if: "+(average+ this.threshold)+">"+filtRSSI+">"+(average - this.threshold));
            if (((average + this.threshold) >= filtRSSI && filtRSSI >= (average - this.threshold)) && inPlace) {
//                System.out.println("in place: "+filtRSSI);
//                this.inPlaceData[this.inPlaceC] = data;
//                this.inPlaceC = (this.inPlaceC + 1) %10;
            }else if(!inPlace){
                if ((average + this.threshold) >= filtRSSI && filtRSSI >= (average - this.threshold)) {
//                    System.out.println("%%%%%%%%%%%%% put back in place: "+filtRSSI);
                    inPlace = true;
                    if(data.getTime()-this.lastSeen >= 1000) {
                        System.out.println("%%%%%%%%%%%%% put back in place: "+filtRSSI);
                        PickUps pu = new PickUps(this.lastSeen,data.getTime(),this);
//                      FirebaseConnection fc = new FirebaseConnection();
//                      fc.pushPickUp(pu);
                        this.pickUps.add(pu);
                        if(infoLaunched) {
                            System.out.println("%%%%%%%%%%%%% window closed: "+filtRSSI);
                            info.closeWindow();
                            infoLaunched = false;
                        }
                    }
//                    this.inPlaceData[this.inPlaceC] = data;
//                    this.inPlaceC = (this.inPlaceC + 1) %10;
                }else{
                    System.out.println("************ not in place: "+filtRSSI);
                    if(data.getTime()-this.lastSeen >= 0 && !infoLaunched) {
                        info = new ItemInfoScreen(this);
                        infoLaunched = true;
                    }
                }
            }else{
                System.out.println("------------- taken out of place: "+filtRSSI+" Tag ID: " + data.epcString());
                inPlace = false;
                this.lastSeen = data.getTime();
            }
        }else{
//            System.out.println("adding data else count: "+count);
            this.inPlaceData[this.inPlaceC] = data;
            this.inPlaceC = (this.inPlaceC + 1) %20;
        }
        this.count++;
    }

    public int getAvData(TagReadData[] data){
        int average = 0;
        for(int i = 0; i< data.length;i++){
            average += data[i].getRssi();
        }
        average /= data.length;
        return average;
    }

    public int getAvIntData(int[] data){
        int average = 0;
        for(int i = 0; i< data.length;i++){
            average += data[i];
        }
        average /= data.length;
        return average;
    }
}
