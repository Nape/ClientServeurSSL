package objet;

/**
 * Représente un client
 */
public class Client
{
    private int ID;
    private String nom;
    private String prenom;

    public Client(){}
    public Client(String nom, String prenom)
    {
        this.nom = nom;
        this.prenom = prenom;
    }
    public Client(int ID, String nom, String prenom)
    {
        this.ID = ID;
        this.nom = nom;
        this.prenom = prenom;
    }


    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    @Override
    public String toString()
    {
        return "ID: "+this.ID +" Nom: "+ this.nom +" Prénom: "+ this.prenom;
    }
}
