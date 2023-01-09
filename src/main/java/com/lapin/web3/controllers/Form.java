package com.lapin.web3.controllers;

import com.lapin.web3.beans.Entries;
import com.lapin.web3.beans.Entry;
import com.lapin.web3.utility.Checker;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
@Setter
@Getter
public class Form implements Serializable {
    @NotNull(message = "X is empty")
    private Double x;
    @NotNull(message = "Y is empty")
    @Min(value=-3, message = "Y must be greater than -3")
    @Max(value = 3, message = "Y must be lower than 5")
    private Double y;
    @NotNull(message = "R is empty")
    private Double r;
    private String hitResult;
    @Inject
    private Entries entries;


    public void submit() throws IOException {
        Entry entry = new Entry();
        entry.setX(x);
        entry.setY(y);
        entry.setR(r);
        entry.setHitResult(new Checker(x, y, r).checkHit());
        entries.addEntry(entry);
        FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
    }

    /**
     * Clears all entries in the table
     * @throws IOException
     */
    public void clear() throws IOException {
        entries.clearEntries();
        FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
    }

    @Override
    public String toString() {
        return "Form {" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", hitResult=" + hitResult +
                '}';
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode() +
                r.hashCode();
    }

}
