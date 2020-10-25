package com.philou.afpa_cda.beans;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author philou
 */
public class Tag implements Serializable{
    
    private long idTag;
    private String libelle;
    
    public Tag(){
        //
    }

    /**
     * @return the idTag
     */
    public long getIdTag() {
        return idTag;
    }

    /**
     * @param idTag the idTag to set
     */
    public void setIdTag(long idTag) {
        this.idTag = idTag;
    }

    /**
     * @return the libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * @param libelle the libelle to set
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tag other = (Tag) obj;
        if (this.idTag != other.idTag) {
            return false;
        }
        if (!Objects.equals(this.libelle, other.libelle)) {
            return false;
        }
        return true;
    }

    
    
    
}
