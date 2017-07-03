package sample ;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.client.util.Preconditions;
//import com.google.api.client.util.store.DataStoreFactory;
//import com.google.api.client.googleapis.media.MediaHttpDownloader;
//import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
//import com.google.api.client.http.GenericUrl;
//import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.drive.model.File;


import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.model.File;


import java.io.IOException;
//import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.util.Arrays;
import java.util.List;
import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
//import java.util.Collections;c








public class Quickstart {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */



    /*
     to have full access I changed this 
   // private static final List<String> SCOPES =Arrays.asList(DriveScopes.DRIVE_METADATA_READONLY);
   with this 
      private static final java.util.Collection<String> SCOPES = DriveScopes.all();
*/  

    private static final java.util.Collection<String> SCOPES =
    DriveScopes.all(); 

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */

    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream("client_secret.json");


        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
  // not a quickstart 
     private static File createFile(Drive service, String title, String Directory , String filename) throws IOException {
    // File's metadata.
    File body = new File();
    Path filePath =  FileSystems.getDefault().getPath(Directory, filename);
    String mimeType = Files.probeContentType(filePath);
    /*
    *in v3 we have setName instead of setTitle
    * and create  instead of insert   
    */
    body.setName(title);
    body.setMimeType(mimeType);

    // Set the parent folder.
  /*
  it is necessary in v3 
   if (parentId != null && parentId.length() > 0) {
      body.setParents(
          Arrays.asList(new ParentReference().setId(parentId)));
    }*/

    // File's content.
    java.io.File fileContent = new java.io.File(Directory+"/"+filename);
    FileContent mediaContent = new FileContent(mimeType, fileContent);
    try {
      File file = service.files().create(body, mediaContent).execute();

      // Uncomment the following line to print the File ID.
      // System.out.println("File ID: " + file.getId());

      return file;
    } catch (IOException e) {
      System.out.println("An error occurred: " + e);
      return null;
    }
  }


    public static void upload(String directory ,String filename )  {
        // Build a new authorized API client service.
        try {
           Drive service = getDriveService();

            createFile(service,"fileUploaded",directory,filename);


        }
        catch(IOException e )
        {
            System.out.println("Hello baby");
        }

    }

}
