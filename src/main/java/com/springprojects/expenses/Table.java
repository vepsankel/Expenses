package com.springprojects.expenses;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Table{

    private HashMap<Integer, Entry> entries;
    private HashSet<Integer> reserved;
    String readAddress;

    Table(String address) {
        this.readAddress = address;
        entries = new HashMap<>();
        reserved = new HashSet<>();

        try{
            JSONParser parser = new JSONParser();
            JSONObject jsonTable = (JSONObject) parser.parse(new FileReader(address));
            JSONArray jsonEntries = (JSONArray) jsonTable.get("entries");
            for (Object jsonEntry : jsonEntries) {
                if (jsonEntry instanceof JSONObject) {
                    Entry entry = EntryParser.toEntry((JSONObject) jsonEntry);

                    if (entry != null)
                        this.entries.put( entry.getID(), entry);
                } else {
                    System.out.println("Not JSONObject met");
                }
            }

        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Entry> getEntries() {
        return entries;
    }

    public void setEntries(HashMap<Integer, Entry> entries) {
        this.entries = entries;
    }

    public void put(Integer ID, Entry entry){
        entry.setID(ID);

        if (reserved.contains(ID)) {
            entries.put(ID, entry);
        } else if (entries.containsKey(ID)) {
            entries.put(ID, entry);
        } else {
            Integer newID = getFreeID();
            entry.setID(newID);
            entries.put(newID, entry);
        }

        reserved.remove(ID);
    }

    public void add(Integer id, Entry entry){
        entry.setID(id);
        entries.put(id,entry);
    }

    public Integer add(Entry entry){

        Integer id = getFreeID();
        add(id, entry);
        return id;
    }

    public Integer getFreeID(){
        Integer id = null;
        while (id == null || entries.containsKey(id) || reserved.contains(id)){
            id = new Random().nextInt() & Integer.MAX_VALUE;
        }
        return id;
    }

    public Integer addReservedID(){
        Integer id = getFreeID();
        reserved.add(id);
        return id;
    }

    boolean isReserved(Integer ID){
        return reserved.contains(ID);
    }

    public void save() throws IOException {
        System.out.println("Started writing : "+getJSON().toString());
        FileWriter fw = new FileWriter(readAddress);
        fw.write(getJSON().toString());
        fw.close();
    }

    public JSONObject getJSON() {
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonEntries = new JSONArray();

        for (Entry entry : entries.values()) {
            jsonEntries.add(EntryParser.toJSON(entry));
        }

        jsonObject.put("entries", jsonEntries);

        return jsonObject;
    }
}
