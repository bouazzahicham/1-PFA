package sample.dataUser;


import java.io.Serializable;

public class Discussion implements Serializable
{
    private String lienDestinataire ;
    private String discussion ;

    public Discussion()
    {
        discussion = new String();
        lienDestinataire = new String();

    }


    public void  setLienDestinataire(String lienDestinataire )
    {
        this.lienDestinataire = lienDestinataire ;
    }
    public void  setDiscussion(String discussion ) {this.discussion = discussion ;}
    public String  getLienDestinataire()
    {
        return lienDestinataire;
    }
    public String  getDiscussion()
    {
        return discussion;
    }

}