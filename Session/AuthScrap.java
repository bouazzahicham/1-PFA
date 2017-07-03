
/**
 * Created by Hicham on 23/06/2017.
 */
package sample.Session;
import sample.dataUser.*;

import com.sun.tools.javac.util.*;
import com.sun.tools.javac.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import sample.Main;

import java.awt.*;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class AuthScrap implements Runnable {

public AuthScrap(DataUser dataUser ) {     myThread = new Thread(this);
        this.dataUser = dataUser ;}
public void setLogins(String username, String password)
{
    this.username = username ;
     this.password = password ;
}
static private void sleeping(int time)
{
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
private void dataFriend(String url,Discussion discussion) // discussion a ete dans ses attributs l url
{

    driver2.get(url);
sleeping(2000);
// On charge grace au clique
//Options a choisir
//    while(true) {
//        try {
//            driver2.findElement(By.xpath("//*[@id=\"see_older\"]/a/div/div/div/strong")).click();
//            sleeping(1000);
//        } catch (Exception e) {
//            break;}
//    }
    for(int i =0; i< 10 ; i++) {
        try {
            driver2.findElement(By.xpath("//*[@id=\"see_older\"]/a/div/div/div/strong")).click();
            sleeping(1000);
        } catch (Exception e) {
            break;}
    }


    String sourceCode = driver2.getPageSource();
    System.out.println(sourceCode);
    Document document = Jsoup.parse(sourceCode);
    Elements elements  = document.select("div.voice.acw.abt");
    String  discuss = "";

    for(Element e : elements ) {
        System.out.println("Le texte : "+e.text());
        discuss+= e.text()+"\n";

    }
    discussion.setDiscussion(discuss);

//     Elementdriver2.findElements(By.xpath("//*[@id=\"mid.$cAAAAADSz56hi7NPRYVcu46TFp8aG\"]"));



    // On serialise tous ceci dans un fichier texte

//##########
//    (new Scanner(System.in)).useDelimiter("n").nextLine(); // Attendre avant de passer au prochain mamamiya



}
//627125359683-0qt77p2pa3387ksq6n1o9vfc4fhef4ev.apps.googleusercontent.com
private void getPictures(String userLink) {}

    @Override
    //### Everall ### Tout se joue ici
    public void run()
    {
    System.setProperty("webdriver.chrome.driver", "src/sample/chromedriver");
    driver2= new ChromeDriver();

    //Configuration de la fenetre driver2
        {

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (new Double(screenSize.getWidth())).intValue();
            driver2.manage().window().setPosition(new Point(x / 2, 0));
            driver2.manage().window().setSize(new org.openqa.selenium.Dimension(x / 2, 1080));
        }

        driver2.get("http://facebook.com");
        sleeping(1000);

        // Getting from username and password pour pouvoir aller scrapper et// Pas encore s assurer qu ils sont correct a sidi moulay

                driver2.findElement(By.id("email")).sendKeys(username);
                driver2.findElement(By.id("pass")).sendKeys(password);
                driver2.findElement(By.id("loginbutton")).click();
                sleeping(1000);
                driver2.get("http:///m.facebook.com/messages/?more");
                sleeping(1000);
// On charge dix fois par exemple, pour augmenter le nombre de charge, on boucle sur un while qui catch une exception et break then ;
for(int i =0 ; i < 2 ; i ++ )
        {
            try{
                    driver2.findElement(By.xpath("//*[@id=\"see_older_threads\"]/a/div/div/div/strong")).click();
                    }
            catch (Exception e )
            {
                break;

            }
    sleeping(2000);

        }
        // On collecte les noms des conversations grace au code source
        String codeSource = driver2.getPageSource();
        System.out.println(codeSource);

        //On cree un document grace a JSoup

        Document document = Jsoup.parse(codeSource);
        //La classe <div class="_5s61 _5cn0 _5i2i _52we"><div class="_5xu4"><a class="_5b6s"
//        Elements links = doc.select("div._4g34._5pxb._5i2i._52we > div._5xu4 > h3._52jh._5pxc > a"); // a with href

        Elements links = document.select("div._5s61._5cn0._5i2i._52we > div._5xu4 > a._5b6s");
        ArrayList<String> listeString = new ArrayList<>();

        Discussion discussion ;

        for(Element element : links )
        {
            // IF makaynach 3ad nkamlo otherwise u do this
            TreeSet<String> listeStringSet = new TreeSet<>();
            listeStringSet.add("http://m.facebook.com"+element.attr("href"));

            discussion = new Discussion();
            discussion.setLienDestinataire("http://m.facebook.com" + element.attr("href"));
            for (String i : listeStringSet) {
                System.out.println(i);
                // Traitement de l'url friend, on obtient code source
                dataFriend(i, discussion);
                dataUser.setFriendsDiscussion(discussion);

            }
        }
        dataUser.setBool2(true);

        driver2.get("http://google.com"); // SUCCESS
    }






    public Thread myThread ;


    private WebDriver driver2 ;
    private String username;
    private String password ;

    private DataUser dataUser ;

}
