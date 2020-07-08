package com.validity.monolithstarter.service;

/**
 * Reads records from advanced.csv and returns a JSON object that contains
 * pairs of duplicates and the remaining nonduplicates 
 */

import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;
import org.apache.commons.text.similarity.LevenshteinDistance;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import com.opencsv.CSVReader;

@Service
public class DuplicateFinderService {
    /** Main method */
    public JSONObject findDuplicates() {
        JSONObject data = new JSONObject();
        try {
            // process & sort data
            List<JSONObject> list = stringsToObject(readCSV());
            sortList(list);

            List<JSONObject[]> dups = collectDuplicates(list);

            // remove the duplicates from the original list 
            for (int i = 0; i < dups.size(); i++) {
                JSONObject o1 = dups.get(i)[0];
                JSONObject o2 = dups.get(i)[1];

                list.remove(o1);
                list.remove(o2);
            }

            // convert lists to arrays to be stored in final json object
            JSONObject[] nonduplicates = list.toArray(new JSONObject[0]);
            JSONObject[][] duplicates = dups.toArray(new JSONObject[0][]);

            data.put("duplicates", duplicates);
            data.put("nonduplicates", nonduplicates);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return data;
        }
    }


    /**
     * Uses Levenshtein distance of full names to find potential duplicates 
     * and put them in a list
     */
    public List<JSONObject[]> collectDuplicates(List<JSONObject> list) {
        LevenshteinDistance levDist = new LevenshteinDistance();

        // duplicates stored in arrays of length 2
        List<JSONObject[]> dups = new ArrayList<>();

        Iterator<JSONObject> iter = list.iterator();
        JSONObject lastObj = null;
        while (iter.hasNext()) {
            if (lastObj != null) {
                JSONObject obj = iter.next();

                String lastFullName = getFullName(lastObj);
                String currentFullName = getFullName(obj);
                
                int distance = levDist.apply(lastFullName, currentFullName);
                // lev dist small enough => move objects to duplicates list 
                if (distance < 3) {
                    JSONObject[] pair = {lastObj, obj};
                    dups.add(pair);
                }
                lastObj = obj;
            }
            else {
                lastObj = iter.next();
            }
        }

        return dups;
    }

    /**
     * Returns the first and last name from the given record
     */
    public String getFullName(JSONObject obj) {
        String firstName = (String) obj.get("first_name");
        String lastName = (String) obj.get("last_name");
        return firstName + " " + lastName; 
    }

    /**
    *  Reads advanced.csv into an ArrayList
    */
    public List<String[]> readCSV() throws Exception {
        List<String[]> list = new ArrayList<>();
        try { 
            CSVReader csvReader = new CSVReader(new FileReader("../test-files/advanced.csv"),',');
            list = csvReader.readAll();

            csvReader.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            return list;
        }
    }

    /**
     * Converts the string array list from readCSV to a list of JSON objects
     * (Probably less efficient than doing it while reading the file, but readAll() is easier)
     */
    public List<JSONObject> stringsToObject(List<String[]> list) {
        List<JSONObject> newList = new ArrayList<>();
        Iterator<String[]> iter = list.iterator();
        String[] fields = iter.next(); // get field names from first line
        
        while (iter.hasNext()) {
            String[] vals = iter.next();
            JSONObject obj = new JSONObject();

            for (int i = 0; i < fields.length; i++) {
                obj.put(fields[i], vals[i]);
            }
            newList.add(obj);
        }

        return newList;
    }

    /**
     * Sorts list based on last name
     * (hoping to ensure duplicates are close together in the list)
     */
    public void sortList(List<JSONObject> list) throws Exception {
        list.sort(
            new Comparator<JSONObject>() {
                public int compare(JSONObject o1, JSONObject o2) {
                    String ln1 = (String) o1.get("last_name");
                    String ln2 = (String) o2.get("last_name");
                    return ln1.compareTo(ln2);
                }
            }
        );
    }
}
