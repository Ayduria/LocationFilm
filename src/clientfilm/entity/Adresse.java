package clientfilm.entity;
// Generated Nov 13, 2021 7:14:54 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Adresse generated by hbm2java
 */
public class Adresse  implements java.io.Serializable {


     private Long idadresse;
     private long nocivique;
     private String rue;
     private String ville;
     private String province;
     private String codepostal;
     private Set utilisateurs = new HashSet(0);

    public Adresse() {
    }

	
    public Adresse(Long idadresse, long nocivique, String rue, String ville, String province, String codepostal) {
        this.idadresse = idadresse;
        this.nocivique = nocivique;
        this.rue = rue;
        this.ville = ville;
        this.province = province;
        this.codepostal = codepostal;
    }
    public Adresse(Long idadresse, long nocivique, String rue, String ville, String province, String codepostal, Set utilisateurs) {
       this.idadresse = idadresse;
       this.nocivique = nocivique;
       this.rue = rue;
       this.ville = ville;
       this.province = province;
       this.codepostal = codepostal;
       this.utilisateurs = utilisateurs;
    }
   
    public Long getIdadresse() {
        return this.idadresse;
    }
    
    public void setIdadresse(Long idadresse) {
        this.idadresse = idadresse;
    }
    public long getNocivique() {
        return this.nocivique;
    }
    
    public void setNocivique(long nocivique) {
        this.nocivique = nocivique;
    }
    public String getRue() {
        return this.rue;
    }
    
    public void setRue(String rue) {
        this.rue = rue;
    }
    public String getVille() {
        return this.ville;
    }
    
    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getProvince() {
        return this.province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCodepostal() {
        return this.codepostal;
    }
    
    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }
    public Set getUtilisateurs() {
        return this.utilisateurs;
    }
    
    public void setUtilisateurs(Set utilisateurs) {
        this.utilisateurs = utilisateurs;
    }




}


