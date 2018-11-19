/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.business.items;

import java.io.Serializable;

/**
 *
 * @author Erik
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public Item() {
        // Required by JSONB
    }

    public Item(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "com.example.business.items.Item[ id=" + id + ", name=" + name + " ]";
    }

}
