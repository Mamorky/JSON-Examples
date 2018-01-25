package com.example.json.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mamorky on 25/01/18.
 */

public class Analisis {

    public static String analizarPrimitiva(JSONObject texto) throws JSONException {
        JSONArray jsonContenido;
        String tipo;
        JSONObject item;
        StringBuilder cadena = new StringBuilder();
        tipo = texto.getString("info");
        jsonContenido = new JSONArray(texto.getString("sorteo"));
        cadena.append("Sorteos de la Primitiva:" + "\n");
        for (int i = 0; i < jsonContenido.length(); i++) {
            item = jsonContenido.getJSONObject(i);
            cadena.append(tipo+":"+item.getString("fecha")+"\n");
            cadena.append(item.getInt("numero1")+","+item.getInt("numero2")+","+
                    item.getInt("numero3")+","+item.getInt("numero4")+","+
                    item.getInt("numero5")+","+item.getInt("numero6")+"\n");
            cadena.append("Complementario: "+item.getInt("complementario")+"\n\n");
        }

        return cadena.toString();
    }
}
