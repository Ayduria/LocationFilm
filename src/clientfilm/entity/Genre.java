package clientfilm.entity;
// Generated Nov 13, 2021 7:14:54 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Genre generated by hbm2java
 */
public class Genre  implements java.io.Serializable {


     private Long idgenre;
     private String descripteur;
     private Set genrefilms = new HashSet(0);

    public Genre() {
    }

	
    public Genre(Long idgenre, String descripteur) {
        this.idgenre = idgenre;
        this.descripteur = descripteur;
    }
    public Genre(Long idgenre, String descripteur, Set genrefilms) {
       this.idgenre = idgenre;
       this.descripteur = descripteur;
       this.genrefilms = genrefilms;
    }
   
    public Long getIdgenre() {
        return this.idgenre;
    }
    
    public void setIdgenre(Long idgenre) {
        this.idgenre = idgenre;
    }
    public String getDescripteur() {
        return this.descripteur;
    }
    
    public void setDescripteur(String descripteur) {
        this.descripteur = descripteur;
    }
    public Set getGenrefilms() {
        return this.genrefilms;
    }
    
    public void setGenrefilms(Set genrefilms) {
        this.genrefilms = genrefilms;
    }




}


