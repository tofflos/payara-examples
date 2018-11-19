/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.business.accounts;

import java.io.Serializable;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Erik
 */
public class Account implements  Principal, Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final String password;
    private final List<String> roles;

    public Account(String name, String password, String... roles) {
        this.name = name;
        this.password = password;
        this.roles = Arrays.asList(roles);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "com.example.accounts.Account[ name=" + name + " ]";
    }

}
