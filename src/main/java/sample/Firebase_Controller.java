package sample;

import com.google.auth.oauth2.GoogleCredentials; //import necessary for Firebase
import com.google.firebase.FirebaseApp; //import necessary for Firebase
import com.google.firebase.FirebaseOptions; //import necessary for Firebase
import com.google.firebase.database.*; //import necessary for Firebase

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList; //import needed for list to function
import java.util.List; //import needed for list to function

public class Firebase_Controller {

    //declaring json's location
    private static final String FILE_NAME = "./stock-application-81280-firebase-adminsdk-7wm1o-a52e51177b.json";

    //declaring Firebase's website
    private static final String DATABASE_NAME = "https://stock-application-81280.firebaseio.com/";
    private static final String URL = "/Admin";

    //declaring variables
    private static FirebaseDatabase db;
    public static Admin globalAdmin = new Admin();
    public static List<Person> persons = new ArrayList<>();
    public static List<Company> companies = new ArrayList<>();

    //method for initializing the Firebase
    public static void initDatabase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(FILE_NAME);

        FirebaseOptions options = new FirebaseOptions.Builder().
                setCredentials(GoogleCredentials.fromStream(serviceAccount)).
                setDatabaseUrl(DATABASE_NAME).build();

        FirebaseApp.initializeApp(options);

        db = FirebaseDatabase.getInstance();
    }

    //method for changing the status of admin to false in Firebase
    public static void changeStatusOfAdminToFalse() {
        //getting reference of the "Object" wanted to be accessed in the Firebase
        DatabaseReference reference = db.getReference(URL);

        //saving manager's status into the specific user in the Firebase
        reference.child("Manager").child("status").setValueAsync(false);
    }

    //method for changing the status of admin to true in Firebase
    public static void changeStatusOfAdminToTrue() {
        //getting reference of the "Object" wanted to be accessed in the Firebase
        DatabaseReference reference = db.getReference(URL);

        //saving manager's status into the specific user in the Firebase
        reference.child("Manager").child("status").setValueAsync(true);
    }

    //method for getting the admin's status from Firebase
    public static void getAdmin() {

        //getting instance of the Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //getting reference of the "Object" wanted to be accessed in the Firebase
        DatabaseReference reference = database.getReference("Admin");

        //adding admin
        reference.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Admin admin = dataSnapshot.getValue(Admin.class);
                globalAdmin.setLogin(admin.getLogin());
                globalAdmin.setPassword(admin.getPassword());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("error");
            }
        });
    }

    //method for saving list of persons from Firebase into the persons list
    public static void getListOfPersons() {

        //getting instance of the Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //getting reference of the "Object" wanted to be accessed in the Firebase
        DatabaseReference reference = database.getReference("User");
        persons = new ArrayList<>();

        //adding each person to the list
        reference.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Person person = dataSnapshot.getValue(Person.class);
                persons.add(person);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("error");
            }
        });
    }

    //method for saving list of companies from Firebase into the companies list
    public static void getListOfCompanies() {

        //getting instance of the Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //getting reference of the "Object" wanted to be accessed in the Firebase
        DatabaseReference reference = database.getReference("Company");

        companies = new ArrayList<>();

        //adding each company to the list
        reference.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Company company = dataSnapshot.getValue(Company.class);
                companies.add(company);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("error");
            }
        });
    }

    //method for saving a list of persons in the Firebase
    public static void savePersons(List<Person> personsInside) {
        //getting reference of the "Object" wanted to be accessed in the Firebase
        DatabaseReference reference = db.getReference("User");

        //saving each person in the Firebase
        for (int i = 0; i < personsInside.size(); i++) {
            String number = "";
            int indexOfUser = i + 1;

            if (indexOfUser < 10) {
                number = "0" + indexOfUser;
            } else {
                number = String.valueOf(indexOfUser);
            }

            reference.child("User" + number).setValueAsync(personsInside.get(i));
        }
    }

    //method for saving a list of companies in the Firebase
    public static void saveCompanies(List<Company> companiesInside) {
        //getting reference of the "Object" wanted to be accessed in the Firebase
        DatabaseReference reference = db.getReference("Company");

        //saving each company in the Firebase
        for (int i = 0; i < companiesInside.size(); i++) {
            int indexOfUser = i + 1;
            reference.child("Company" + indexOfUser).setValueAsync(companiesInside.get(i));
        }
    }

}
