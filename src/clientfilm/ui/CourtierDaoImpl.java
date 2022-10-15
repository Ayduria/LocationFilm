/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientfilm.ui;

import clientfilm.entity.Acteurfilm;
import clientfilm.entity.Client;
import clientfilm.entity.Copie;
import clientfilm.entity.Film;
import clientfilm.entity.Genrefilm;
import clientfilm.entity.Locationactive;
import clientfilm.entity.Paysfilm;
import clientfilm.entity.Personnefilm;
import clientfilm.entity.Scenaristefilm;
import clientfilm.entity.Utilisateur;
import clientfilm.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.swing.JTextField;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Ayduria
 */

interface CourtierDao {
    int verifyLogin(String email, String password);
    Utilisateur getUserFromEmail(String email);
    boolean locationPossible(Utilisateur utilisateur);
    Copie copieDisponible(Film selectedMovie);
    void addLocation(Utilisateur utilisateur, Copie copie, Date date);
    Personnefilm getDirectorByName (String[] nameArray);
    Personnefilm getPersonInfoByActor (Acteurfilm selectedActor);
    String displayMovieInfo (Film selectedMovie);
    boolean searchMovie(String title, String minRelease, String maxRelease, String language, String director, ArrayList<JTextField> countries, ArrayList<JTextField> genres, ArrayList<JTextField> actors);
}

public class CourtierDaoImpl implements CourtierDao {
    @Override
    public int verifyLogin(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
            List lesUtilisateurs = session.createQuery("from Utilisateur u where u.courriel='" + email + "' and u.motdepasse='" + password + "'").list();
            int userSize = lesUtilisateurs.size();
            
        session.close();
        
        return userSize;
    }
    
    @Override
    public Utilisateur getUserFromEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
            Query q = session.createQuery("from Utilisateur u where u.courriel = '" + email + "'");
            Utilisateur utilisateur = (Utilisateur)q.uniqueResult();
            
        session.close();
        
        return utilisateur;
    }
    
    @Override
    public boolean locationPossible(Utilisateur utilisateur) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
            Query q = session.createQuery("from Utilisateur u where u.idutilisateur = '" + utilisateur.getIdutilisateur() + "'");
            Utilisateur qUtilisateur = (Utilisateur)q.uniqueResult();
            Query q2 = session.createQuery("from Client c where c.idutilisateur =" + qUtilisateur.getIdutilisateur());
            Client client = (Client)q2.uniqueResult();

            int locationsMax = client.getForfait().getLocationmax();
            int locationsActives = qUtilisateur.getLocationactives().size();

        session.close();
        
        return (locationsActives < locationsMax);
    }
    
    @Override
    public Copie copieDisponible(Film selectedMovie) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
            Query q = session.createQuery("from Film f where f.idfilm=" + selectedMovie.getIdfilm());
            Film film = (Film)q.uniqueResult();

            Set<Copie> copySet = film.getCopies();
            for (Copie copie : copySet) {
                if (copie.getStatut().equals("Disponible")) {
                    session.beginTransaction();
                    copie.setStatut("Louee");
                    session.save(copie);
                    session.getTransaction().commit();
                    session.close();
                    return copie;
                }
            }
   
        session.close();
        return null;
    }
    
    @Override
    public void addLocation(Utilisateur utilisateur, Copie copie, Date date) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
            Query q = session.createQuery("from Utilisateur u where u.idutilisateur = '" + utilisateur.getIdutilisateur() + "'");
            Utilisateur qUtilisateur = (Utilisateur)q.uniqueResult();
            
            Locationactive location = new Locationactive();
            location.setUtilisateur(qUtilisateur);
            location.setCopie(copie);
            location.setDatelocation(date);
            session.save(location);
            qUtilisateur.getLocationactives().add(location);
            session.save(qUtilisateur);
            
        session.getTransaction().commit();
        session.close();
    }
    
    @Override
    public Personnefilm getDirectorByName (String[] nameArray) {
        Session session = HibernateUtil.getSessionFactory().openSession();

            Query q = session.createQuery("from Personnefilm p where p.prenom = '" + nameArray[0] + "' and p.nom = '" + nameArray[1] + "'");
            Personnefilm personne = (Personnefilm)q.uniqueResult();

        session.close();
        
        return personne;
    }
    
    @Override
    public Personnefilm getPersonInfoByActor (Acteurfilm selectedActor) {
         Session session = HibernateUtil.getSessionFactory().openSession();

            Query q = session.createQuery("from Acteurfilm a where a.idacteur=" + selectedActor.getIdacteur());
            Acteurfilm acteur = (Acteurfilm)q.uniqueResult();
            Query q2 = session.createQuery("from Personnefilm p where p.idpersonne=" + acteur.getPersonnefilm().getIdpersonne());
            Personnefilm personne = (Personnefilm)q2.uniqueResult();

        session.close();
        
        return personne;
    }
    
    @Override
    public String displayMovieInfo (Film selectedMovie) {
            Session session = HibernateUtil.getSessionFactory().openSession();

                Query q = session.createQuery("from Film f where f.idfilm =" + selectedMovie.getIdfilm());
                Film film = (Film)q.uniqueResult();
                Set<Acteurfilm> actorSet = film.getActeurfilms();
                Set<Scenaristefilm> screenwriterSet = film.getScenaristefilms();
                Set<Paysfilm> countrySet = film.getPaysfilms();
                Set<Genrefilm> genreSet = film.getGenrefilms();

                for (Acteurfilm acteur : actorSet)
                    LocationFilm.actorListModel.addElement(acteur);

                for (Scenaristefilm scenariste : screenwriterSet)
                    LocationFilm.screenwriterListModel.addElement(scenariste.getPersonnefilm().getPrenom() + " " + scenariste.getPersonnefilm().getNom());

                for (Paysfilm pays : countrySet)
                    LocationFilm.countryListModel.addElement(pays.getPaysproduction().getNom());

                for (Genrefilm genre : genreSet)
                    LocationFilm.genreListModel.addElement(genre.getGenre().getDescripteur());

                String nomRealisateur = film.getPersonnefilm().getPrenom() + " " + film.getPersonnefilm().getNom();

            session.close();
            
            return nomRealisateur;
    }
    
    @Override
    public boolean searchMovie(String title, String minRelease, String maxRelease, String language, String director, ArrayList<JTextField> countries, ArrayList<JTextField> genres, ArrayList<JTextField> actors) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
            String query = "select f from Film f where";

            if (!title.equals(""))
                query += " and upper(f.titre) like upper('%" + title + "%')";

            if (!minRelease.equals("") && !maxRelease.equals(""))
                query += " and f.anneeparution between " + Integer.parseInt(minRelease) + " and " + Integer.parseInt(maxRelease);
            else if (!minRelease.equals(""))
                query += " and f.anneeparution >= " + Integer.parseInt(minRelease);
            else if (!maxRelease.equals(""))
                query += " and f.anneeparution <= " + Integer.parseInt(maxRelease);

            if (!language.equals(""))
                query += " and upper(f.langueoriginale)=upper('" + language + "')";

            if (!director.equals(""))
                query += " and (upper(f.personnefilm.prenom||' '||f.personnefilm.nom) like upper('%" + director + "%'))";

            for (JTextField country : countries) {
                query += " and f.idfilm in (select p.id.idfilm from Paysfilm p where upper(p.paysproduction.nom)=upper('" + country.getText() + "'))";
            }

            for (JTextField genre : genres) {
                query += " and f.idfilm in (select g.id.idfilm from Genrefilm g where upper(g.genre.descripteur)=upper('" + genre.getText() + "'))";
            }

            for (JTextField actor : actors) {
                query += " and f.idfilm in (select a.film.idfilm from Acteurfilm a where (upper(a.personnefilm.prenom||' '||a.personnefilm.nom) like upper('%" + actor.getText() + "%')))";
            }

            query = query.replaceFirst("and ", "");
            query += " order by f.titre asc";

            List lesFilms = session.createQuery(query).list();
            int movieSize = lesFilms.size();

            if (movieSize > 0) {
                for(Object o : lesFilms) {
                    Film film = (Film)o;
                    LocationFilm.movieListModel.addElement(film);
                }
                session.close();
                return true;
            }
                
        session.close();
        return false;
    }
}
