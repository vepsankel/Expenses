package com.springprojects.expenses;

import org.json.simple.JSONObject;

public class EntryParser {
    static Entry toEntry(JSONObject json){
        Entry ret = new Entry();

        ret.setDescriprion((String) json.get("description"));
        ret.setValute((String) json.get("valute"));
        ret.setPrice( Float.parseFloat( Double.toString((double) json.get("price")) ) );
        ret.setImportance((String) json.get("importance"));
        ret.setType((String) json.get("type"));
        ret.setDate((String) json.get("date"));

        try{
            ret.setID( ((Long) json.get("id")).intValue() );
        } catch (NullPointerException e) {
            return null;
        }


        return ret;
    }

    static JSONObject toJSON(Entry entry) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("description", entry.getDescriprion());
        jsonObject.put("valute", entry.getValute().name());
        jsonObject.put("price", entry.getPrice());
        jsonObject.put("importance",entry.getImportance().name());
        jsonObject.put("type",entry.getType().name());
        jsonObject.put("date",entry.getDate().toString());
        jsonObject.put("id",Integer.toString(entry.getID()));

        return jsonObject;
    }
}
