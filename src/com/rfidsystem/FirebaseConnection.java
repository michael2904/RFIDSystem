package com.rfidsystem;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import org.apache.log4j.Logger;


public class FirebaseConnection {

	protected final Logger LOGGER = Logger.getRootLogger();
	private static FirebaseResponse response = null;
	private Firebase firebase;

	public FirebaseConnection(){
		System.out.println("This is a firebase connection");
//        String firebaseBaseUrl = "https://rfidsystem-cc897.firebaseio.com/";
		String firebaseBaseUrl = "https://designproject-bdb72.firebaseio.com/";
		if( firebaseBaseUrl == null || firebaseBaseUrl.trim() == "" ) {
			throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );
		}


		// create the firebase
		try {
			firebase = new Firebase( firebaseBaseUrl );
		} catch (FirebaseException e) {
			e.printStackTrace();
		}
	}

//    public static void main(String[] args){
//        FirebaseConnection fc = new FirebaseConnection();
//        LinkedHashMap<String, Object> dataMap;
//        Calendar start = Calendar.getInstance();
//        Date startDate = new Date(1486998000000L);
//        start.setTime(startDate);
//        Calendar end = Calendar.getInstance();
//        Date endDate = new Date(1487541600000L);
//        end.setTime(endDate);
//        Date date;
//        for (date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
//            // Do your job here with `date`.
//            System.out.println(date);
//            for(int i = 0;i<10;i++){
//                dataMap = new LinkedHashMap<String, Object>();
//                long startTime = date.getTime();
//                long endTime = date.getTime() + (i+1)*10000;
//                dataMap.put("startTime", startTime);
//                dataMap.put("endTime", endTime);
//                dataMap.put("duration", endTime-startTime);
//                dataMap.put("day", start.get(Calendar.DAY_OF_MONTH) );
//                dataMap.put("month",start.get(Calendar.MONTH) + 1);
//                dataMap.put("year",start.get(Calendar.YEAR));
//                String path = "Pickups/890123456789012/pickup"+ UUID.randomUUID().toString();
//                System.out.println(path);
//                try {
//                    response = fc.firebase.put(path, dataMap);
//                } catch (JacksonUtilityException e) {
//                    e.printStackTrace();
//                } catch (FirebaseException e) {
//                    e.printStackTrace();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("\n\nResult of PUT (for the test-PUT):\n" + response);
//                System.out.println("\n");
//                start.add(Calendar.HOUR_OF_DAY,1);
//                date = start.getTime();
//            }
//        }
//    }

	public void pushPickUps(ArrayList<PickUps> pickUps){
		LinkedHashMap<String, Object> dataMap;
		for (PickUps pu: pickUps) {
			dataMap = new LinkedHashMap<String, Object>();
			dataMap.put("startTime", pu.getStartTime());
			dataMap.put("endTime", pu.getEndTime());
			dataMap.put("duration", pu.getDuration());
			dataMap.put("day", pu.getDay() );
			dataMap.put("month", pu.getMonth());
			dataMap.put("year", pu.getYear());
			try {
				response = firebase.put("/Pickups/"+pu.getItem().getUid()+"/pickup"+ UUID.randomUUID().toString(), dataMap);
			} catch (JacksonUtilityException e) {
				e.printStackTrace();
			} catch (FirebaseException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println("\n\nResult of PUT (for the test-PUT):\n" + response);
			System.out.println("\n");
		}
	}

	public void pushPickUp(PickUps pu){
		LinkedHashMap<String, Object> dataMap;
		dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("startTime", pu.getStartTime());
		dataMap.put("endTime", pu.getEndTime());
		dataMap.put("duration", pu.getDuration());
		dataMap.put("day", pu.getDay() );
		dataMap.put("month", pu.getMonth());
		dataMap.put("year", pu.getYear());
		try {
			response = firebase.put("/Pickups/"+pu.getItem().getUid()+"/pickup"+ UUID.randomUUID().toString(), dataMap);
		} catch (JacksonUtilityException e) {
			e.printStackTrace();
		} catch (FirebaseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("\n\nResult of PUT (for the test-PUT):\n" + response);
		System.out.println("\n");
	}
}
