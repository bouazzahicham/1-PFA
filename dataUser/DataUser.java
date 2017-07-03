package sample.dataUser;

//Toute les informations a serialiser sont ici

import java.io.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

// Le choix de arrayList est justifié par son parcours lors de la lecture de O(1);
// Le add est toujours en fin de liste aussi
public class DataUser implements  Serializable
{
private ArrayList<String> postFeed ;
private ArrayList<String> pagesUser ;
private ArrayList<String> likedPages ;
private ArrayList<String> groupsUser ;
private ArrayList<String> membersGroups ;
private ArrayList<String> groupsPosts;
private ArrayList<String> friendsData;

private ArrayList<Discussion> friendsDiscussion;


private boolean bool1 ;
private  boolean bool2 ;

public Thread myThread ;


public DataUser()
{
    postFeed = new ArrayList<>();
    pagesUser = new ArrayList<>();
    likedPages = new ArrayList<>();
    groupsUser = new ArrayList<>();
    membersGroups = new ArrayList<>();
    groupsPosts = new ArrayList<>();
    friendsData = new ArrayList<>();

    friendsDiscussion = new ArrayList<>();

    bool1 = false ;
    bool2 = false ;

}


public void setBool1(Boolean bool) {bool1 = bool ;}
public void setBool2(Boolean bool) {bool2 = bool ;}
public boolean getBool1() {return bool1;}
public boolean getBool2() {return bool2;}

public void setPostFeed(String string) {postFeed.add(string);}
public void setGroupsUser(String string) {groupsUser.add(string);}
public void setPagesUser(String string) {pagesUser.add(string);}
public void setLikedPages(String string)
{
likedPages.add(string);
}
public void setMembersGroups(String string) {
    membersGroups.add(string);
}
public void setGroupsPosts(String string)
{
groupsPosts.add(string);
}
public void setFriendsData(String string)
{
friendsData.add(string);
}
public void setFriendsDiscussion(Discussion discussion)
{
friendsDiscussion.add(discussion);
}








// Pour le serialiser , on lance un thread qui attend que les deux miner ont finit

}


