package com.springprojects.expenses;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.Proxy;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Entry {
    LocalDate date;

    @NotNull
    Importance importance;

    @NotNull
    Types type;

    @Length(max = 50, message = "This field should not contain long messages; If you need to write one," +
            "use \"long description\" field instead")
    @Length(min = 3, message = "should have at least 3 symbols")
    String descriprion;
    String longDescription;

    Float ron;
    Float eur;
    Float mdl;

    Float price;

    @NotNull
    Valute valute;

    private int id;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setDate() {
        if (date == null)
        this.date = LocalDate.now();
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public void setImportance(String importance) {
        this.importance = Importance.valueOf(importance);
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = Types.valueOf(type);
    }

    public Valute getValute() {
        return valute;
    }

    public void setValute(Valute valute) {
        System.out.println("Setted value Valute");
        this.valute = valute;
    }

    public void setValute(String valute) {
        System.out.println("Setted value String");
        this.valute = Valute.valueOf(valute);
    }

    public String getDescriprion() {
        return descriprion;
    }

    public void setDescriprion(String descriprion) {
        this.descriprion = descriprion;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}

enum Importance{
    CRITICAL("Critical"),
    EXTR_IMPORTANT("Extremely important"),
    VER_IMPORTANT("Very important"),
    IMPORTANT("Important"),
    NOT_SO_IMPORTANT("Not so important"),
    UNIMPORTANT("Unimportant");

    private final String displayValue;

    Importance(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

enum Types {
    FOOD("Food"),
    MEDICAL("Health"),
    PROFESSION("Work & Study"),
    LOAN("Loan"),
    ONE_OFF("One-off expense"),
    TRAVEL("Traveling"),
    DISTRACTION("Distraction");

    private final String displayValue;

    Types(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

enum Valute{
    RON, EUR, MDL
}