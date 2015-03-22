package com.example.jorge.myapplication;

import android.graphics.Point;

/**
 * Created by Jorge on 22/03/2015.
 */
public class PuntoWifi {
    private String id;
    private String estado;
    private Geometry geometry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometrys(Geometry geometry) {
        this.geometry = geometry;
    }
}
