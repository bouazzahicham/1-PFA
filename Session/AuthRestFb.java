package sample.Session;
import sample.dataUser.*;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.experimental.api.Groups;
import com.restfb.types.Group;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.sun.tools.javac.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;

public class AuthRestFb implements Runnable
{   // Should be lunched within a thread
    public AuthRestFb(DataUser dataUser) {
        //f0848094574feb15ee5fd3e41fc704ec
        domain = "http://google.com"; // Image SUCESS
        appId = "202483543605835";
        authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appId + "&redirect_uri="+domain + "&scope=user_about_me,"
                + "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_birthday,user_education_history,"
//                + "user_events,user_photos,user_friends,user_games_activity,user_groups,user_hometown,user_interests,user_likes,user_location,user_photos,user_relationship_details,"
                + "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email";
//                + "publish_actions,read_friendlists,read_insights,read_mailbox,read_page_mailboxes,read_stream,rsvp_event" ;

        myThread = new Thread(this);
this.dataUser = dataUser ;


    }
    static private void sleeping(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void setLogins(String username, String password) {
        this.username = username ;
        this.password = password ;
    }

    @Override
    public void run() // throws IOException
    {
        System.setProperty("webdriver.chrome.driver", "/Users/mac/Desktop/myDev/JavaAll/JavaPFA/src/sample/chromedriver");
        //Ouverture du premier Driver :
        driver1= new ChromeDriver();

        //Configuration du driver1
        {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (new Double(screenSize.getWidth())).intValue();
            driver1.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
            driver1.manage().window().setSize(new org.openqa.selenium.Dimension(x / 2, 1080));
        }

        driver1.get(authUrl);
        //Remplissage automatique du username et password ;
        {sleeping(2000);
        driver1.findElement(By.id("email")).sendKeys(username);
        driver1.findElement(By.id("pass")).sendKeys(password);
        driver1.findElement(By.id("loginbutton")).click();}



        // On doit valider automatiquement

        //


        while (true) {

            if (!driver1.getCurrentUrl().contains("facebook.com")) {
                System.out.println("CHROOOME DRivER");
                String url = driver1.getCurrentUrl();
                accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
                driver1.quit();
                break;
            }
        }

// tous notre boulot sera ici , Get le maximum d information avec restFB et l access token

        FacebookClient fbClient = new DefaultFacebookClient(accessToken);


        String affichage = "";

        //Bloc : avoir les postFeed du user
        {
            Connection<Post> result = fbClient.fetchConnection("me/feed", Post.class);
            int counter = 0;
            try {
                for (List<Post> page : result) {
                    for (Post aPost : page) {
                        counter++;
                        affichage = aPost.getMessage() + "\n" + aPost.getId();
                    }
                }
            }
            catch (Exception exception ){}

                affichage += "\nCompteur : " + counter ;
                System.out.println(affichage);
                dataUser.setPostFeed(affichage);
        }

        // Bloc : Avoir les  pages d un utilisateur, puis on les scrap individuellemenet grace a la deuxieme methode
        {
            affichage = "";
            Connection<Page> result = fbClient.fetchConnection("me/accounts",Page.class);
            int counter = 0;
            for (List<Page> page : result) {
                for (Page aPost : page) {
                    counter++;
                    affichage += aPost.getName() +"\n"+ aPost.getLikes() + "\n" +"http://fb.com/"+aPost.getId();

                }
                affichage+= "\nCompteur : " + counter ;
                System.out.println(affichage);
                dataUser.setPagesUser(affichage);}

        }


        // Bloc : Avoir  les pages aimées par l utilisateur
        {affichage = "";

            Connection<Page> result = fbClient.fetchConnection("me/likes",Page.class);
            int counter = 0;
            for (List<Page> feedPage : result) {
                for (Page aPost : feedPage) {
                    counter++;
                    affichage = aPost.getName() + "\n" + aPost.getId() ;

                }
                affichage += "Nombre de resultats = : " + (counter);
                System.out.println(affichage);
                dataUser.setLikedPages(affichage);

            }}

        // Bloc : Avoir les groups user joined
        {affichage = "";
            int counter = 0 ;
            Connection<Group> groups = fbClient.fetchConnection("me/groups",Group.class);
            for(List<Group> groupPage : groups )
            {
                for(Group aGroup : groupPage){
                    affichage =  aGroup.getName() + "\n" + aGroup.getId();
                    counter ++ ;
//                    System.out.println("Partie Owner du groupe : \n"+aGroup.getOwner().getName());
//                    System.out.println("Partie Owner du groupe : \n"+aGroup.getOwner().getId()+"\n\n");

                }
                affichage += "Compteur  : \n" + counter ;
            System.out.println(affichage);
            dataUser.setGroupsUser(affichage);
            }}


        // Bloc: Les groupes dont il est admin
//        User user = fbClient.fetchObject("me", User.class);
        // ####IMPOSSSIBLE#### Logiciel doit etre validé par facebook
//        {
//
//
//
//            System.out.println("####### Les groupes dont il est admin !!! ####### \n\n\n\n");
//            Connection<Group> myGroupsFeed = fbClient.fetchConnection("me/admined_groups",Group.class);
//
//            for(List<Group> page : myGroupsFeed)
//                for(Group aGroup : page)
//                {
//                    System.out.println(aGroup.getName());
//                }
//        }


        //Bloc : Avoir les membres d'un certain groupe
        {affichage = "";
            Connection<Group> groupsFeed = fbClient.fetchConnection("me/groups",Group.class);

            for(List<Group> page :groupsFeed) {
                for (Group aGroup : page) {
                    affichage = "Id du groupe : " +"\n"+"http://fb.com/"+aGroup.getId()+"\n"+aGroup.getName()+"\n"+"\t\t"+aGroup.getDescription()+"\n" ;
                    Connection<User> userFeed = fbClient.fetchConnection(aGroup.getId() + "/members", User.class);
                    for (List<User> userPage : userFeed) {
                        for (User aUser : userPage)
                            affichage+= "\n" + aUser.getName() + "\thttp://fb.com/" + aUser.getId() + "\n\n" ;

                    }
                    System.out.println(affichage);
                    dataUser.setMembersGroups(affichage);
                }
                }}


        //Bloc : Avoir les posts d un groupe
        { affichage = "";

            Connection<Group> groupsFeed= fbClient.fetchConnection("me/groups",Group.class);
            affichage += "\n\n\n\n";
            for(List<Group> page : groupsFeed)
                for(Group aGroupe : page)
                {
                    affichage+= "Nom du groupe : \t"+ aGroupe.getName()+"\t"+aGroupe.getId() ;
                    Connection<Post> postFeed = fbClient.fetchConnection(aGroupe.getId()+"/feed",Post.class);

                    for(List<Post> postPage : postFeed)
                    {
                        for(Post aPost : postPage)
                        {
                        affichage+= "\nInside the last loop____________________________";
                        affichage+="Message : ---->"+aPost.getMessage() ;
                        affichage+= "Lien ----->\tfb.com/"+aPost.getId();

                        }
                    }

                    System.out.println(affichage);
                    dataUser.setGroupsPosts(affichage);
                }}

// Very limited car si un utiisateur n a pas les memes droits , walooo

        // Friend's Data

        {affichage = "";

            Connection<User> myFriends = fbClient.fetchConnection("me/friends", User.class);
            affichage += "\n\nTotal des amis : \t" + myFriends.getTotalCount() ;
            int counter= 0;
            for (List<User> aFriend : myFriends) {
                for (User friend : aFriend) {

                    affichage += "Nom : "+friend.getName() ;
                    affichage += "Email:" + friend.getEmail() ;
                    affichage += "About : " + friend.getAbout() ;
                    affichage += "Lien : " + "fb.com/" + friend.getId() + "\n\n" ;

                }
                affichage += ++counter + "\n\n\n" ;
                System.out.println(affichage);
                dataUser.setFriendsData(affichage);
            }}

            dataUser.setBool1(true);



    }





    private WebDriver driver1 ;
    private String  accessToken ;
    private String domain ;
    private String appId ;
    private  String authUrl ;
    private String username, password ;
    private DataUser dataUser ;

    public Thread myThread;

}
