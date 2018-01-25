package com.example.json.utils;

import com.example.json.pojo.Contacto;
import com.example.json.pojo.Telefono;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public static ArrayList<Contacto> analizarContactos(JSONObject respuesta) throws JSONException {
        JSONArray jAcontactos;
        JSONObject jOcontacto, jOtelefono;
        Contacto contacto;
        Telefono telefono;
        ArrayList<Contacto> personas;
        // añadir contactos (en JSON) a personas

        jAcontactos = respuesta.getJSONArray("contactos");

        for (int i = 0; i < jAcontactos.length(); i++) {
            jOcontacto = jAcontactos.getJSONObject(i);
            jOtelefono = jOcontacto.getJSONObject("telefono");

            contacto = new Contacto();
            contacto.setNombre(jOcontacto.getString("nombre"));
            contacto.setDireccion(jOcontacto.getString("direccion"));
            contacto.setEmail(jOcontacto.getString("email"));

            telefono = new Telefono();
            telefono.setCasa(jOcontacto.getJSONObject("telefono").getString("casa"));
            telefono.setMovil(jOcontacto.getJSONObject("telefono").getString("movik"));
        }


        return null;
    }
}
