package clientfilm.entity;
// Generated Nov 13, 2021 7:14:54 PM by Hibernate Tools 4.3.1



/**
 * Employe generated by hbm2java
 */
public class Employe  implements java.io.Serializable {


     private String idutilisateur;
     private int codematricule;

    public Employe() {
    }

    public Employe(String idutilisateur, int codematricule) {
       this.idutilisateur = idutilisateur;
       this.codematricule = codematricule;
    }
   
    public String getIdutilisateur() {
        return this.idutilisateur;
    }
    
    public void setIdutilisateur(String idutilisateur) {
        this.idutilisateur = idutilisateur;
    }
    public int getCodematricule() {
        return this.codematricule;
    }
    
    public void setCodematricule(int codematricule) {
        this.codematricule = codematricule;
    }




}


