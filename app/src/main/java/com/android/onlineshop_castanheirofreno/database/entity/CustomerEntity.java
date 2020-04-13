package com.android.onlineshop_castanheirofreno.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

//@Entity(tableName = "customer", primaryKeys = {"email"})
public class CustomerEntity implements Comparable {




    private String idCustomer;


    // @NonNull
    private String email;

    //@ColumnInfo(name = "first_name")
    private String firstname;

    //@ColumnInfo(name = "last_name")
    private String lastname;

    //@ColumnInfo(name = "city")
    private String city;

    // @ColumnInfo(name = "city_code")
    private int city_code;

    //@ColumnInfo(name = "telephone")
    private String telephone;

    private String password;

    @Ignore
    public CustomerEntity() {
    }

    public CustomerEntity(@NonNull String email, String firstname, String lastname, String city, int city_code, String telephone, String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.city_code = city_code;
        this.telephone = telephone;
        this.password = password;
    }

    @Exclude
    public String getIdCustomer() { return idCustomer; }

    public void setIdCustomer(String idCustomer) { this.idCustomer = idCustomer; }

    //@NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCity_code() {
        return city_code;
    }

    public void setCity_code(int city_code) {
        this.city_code = city_code;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CustomerEntity)) return false;
        CustomerEntity o = (CustomerEntity) obj;
        return o.getEmail().equals(this.getEmail());
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("email" , email);
        result.put("firstname" , firstname);
        result.put("lastname" , lastname);
        result.put("city" , city);
        result.put("city_code" , city_code);
        result.put("telephone" , telephone);

        return result;
    }
}
