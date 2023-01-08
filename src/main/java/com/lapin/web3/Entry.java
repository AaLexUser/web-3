package com.lapin.web3;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.ManagedBean;
import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@ManagedBean
public class Entry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    @SequenceGenerator(name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private int id;

    //private long id; // The primary key of the entity
    private Double x;
    private Double y;
    private Double r;
    private String hitResult;

    private boolean checkTriangle() {
        return x >=0 && y >=0 && (y + x <=(double) r);
    }

    private boolean checkRectangle() {
        return x >= 0 && y <= 0 && Math.abs(x) <= r/2 && Math.abs(y) <= (double) r;
    }

    private boolean checkCircle() {
        return x <= 0 && y >= 0 && x * x + y * y <= (double) r * r /4;
    }

    public String checkHit() {
        hitResult = checkTriangle() || checkRectangle() || checkCircle() ? "Попадание" : "Промах";
        return hitResult;
    }

    @Override
    public String toString() {
        return "Entry{" +
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Entry) {
            Entry entryObj = (Entry) obj;
            return x.equals(entryObj.getX()) &&
                    y.equals(entryObj.getY()) &&
                    r.equals(entryObj.getR());
        }
        return false;
    }
}
