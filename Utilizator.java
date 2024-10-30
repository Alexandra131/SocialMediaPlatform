package TemaTest;

import java.io.*;

public class Utilizator {

    private static String prDouaCar = null;
    private static boolean gasit = false;
    private static String[] vectorUtilizatori = new String[1000];
    private static int[] vectorLike = new int[1000];
    private static String myUser = null;
    private static String myPassw = null;
    private static int[] vectorFollow = new int[1000];
    private static String[] vectorPers = new String[1000];
    private static String userCurent = null;
    private static String passwdCurent = null;
    private static int[] nrUtilizator = new int[1000];
    private  static boolean gasitUser = false;
    private static boolean gasitPassw = false;
    private static String userFollow = null;
    private static String usSFollow = null;
    private  static boolean gasitUserFol = false;
    private static boolean gasitUrmarire = false;
    private static int nrContor = 0;
    private static String userNotFoll = null;
    private static String usSNotFollow = null;
    private static boolean gasitUserUnf = false;
    private static String impartit = null;
    private static String[] vectorUrmaritor = new String[1000];
    private static File fisier = new File("Utilizator.txt");
    private static File fisier4 = new File("Urmariri.txt");
    public Utilizator(){}



    public void printSimplu (String[] strings) {
        if (strings.length == 1) {
            nrContor = 1;
            System.out.print("{ 'status' : 'error', 'message' : 'Please provide username'}");
            return;
        }
        myUser = strings[1];
        prDouaCar = myUser.substring(0,2);
        if (strings.length == 2 && !prDouaCar.equals("-u")) {
            nrContor = 1;
            System.out.print("{ 'status' : 'error', 'message' : 'Please provide username'}");
            return;
        }
        if (strings.length == 2 && prDouaCar.equals("-u")) {
            nrContor = 1;
            System.out.print("{ 'status' : 'error', 'message' : 'Please provide password'}");
            return;

        }
    }
    public void afisariSimple(String[] strings) {
        if (strings.length == 1) {
            nrContor = 1;
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return;
        }
        if (strings.length == 2) {
            nrContor = 1;
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return;
        }
        //daca fisierul e gol
        if (fisier.length() == 0) {
            nrContor = 1;
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            return;
        }
    }
    public void cautarUser(String[] strings) {
        //cauta user in fisier
        try (BufferedReader reader = new BufferedReader(new FileReader("Utilizator.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1])) {
                    gasitUser = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("greseala1");
        }


        //cauta parola in fisier
        try (BufferedReader reader = new BufferedReader(new FileReader("Utilizator.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[2])) {
                    gasitPassw = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }
    }

    public void creareUtilizator (String[] strings){

        nrContor = 0;
        printSimplu(strings);
        if (nrContor == 1) {
            nrContor = 0;
            return;
        }
        myPassw = strings[2];

        //cautare user in fisier
        try (BufferedReader reader = new BufferedReader(new FileReader("Utilizator.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1])) {
                    gasit = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (gasit == true) {
            System.out.print("{ 'status' : 'error', 'message' : 'User already exists'}");
            gasit = false;
            return;
        }
        //scriere in fisier in cazul in care nu s.a gasit deja un user existent
        try (FileWriter fw = new FileWriter("Utilizator.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(strings[1]);
            out.print(" / ");
            out.println(strings[2]);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //exception handling left as an exercise for the reader
        }

        if(gasit == false) {
            System.out.print("{ 'status' : 'ok', 'message' : 'User created successfully'}");
            return;
        }

    }

    public void urmarireUtilizator(String[] strings) {
        gasitPassw = false;
        gasitUser = false;
        gasitUserFol = false;
        gasitUrmarire = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];


        //daca fisierul e gol
        if (fisier.length() == 0) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            return;
        }
        myPassw = strings[2];

        cautarUser(strings);
        //nu exista user-ul cu care m-am logat
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }
        //nu stiu pe cine sa urmaresc
        if (strings.length == 3 ) {
            System.out.print("{ 'status' : 'error', 'message' : 'No username to follow was provided'}");
            return;
        }
        userFollow = strings[3];
        usSFollow = userFollow.replace("-username", "").trim();

        // Adaugă prefixul "-u"
        usSFollow = "-u " + usSFollow;

        if(usSFollow.equals(myUser)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to follow was not valid'}");
            return;
        }

        //cauta user in fisier
        try (BufferedReader reader = new BufferedReader(new FileReader("Utilizator.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(usSFollow)) {
                    gasitUserFol = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("greseala1");
        }

        if (gasitUserFol == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to follow was not valid'}");
            return;
        }

        //cautam in fisier sa vedem daca urmareste deja

        try (BufferedReader reader = new BufferedReader(new FileReader("Urmariri.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1]) && linie.contains(strings[2])) {
                    if(linie.contains(usSFollow)) {
                        gasitUrmarire = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //nu putem sa mia urmarim daca urmarim deja
        if (gasitUrmarire == true) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to follow was not valid'}");
            return;
        }

        if (gasitUserFol == true) {
            // adauga in fisierul de urmarire userul care urmareste si pe cine vrea sa urmareasca
            try (FileWriter fw = new FileWriter("Urmariri.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.print(strings[1]);
                out.print(" / ");
                out.print(strings[2]);
                out.print(" / ");
                out.println(usSFollow);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            System.out.print("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");
            return;
        }

    }

    public void unfollowUtilizator(String[] strings) {
        gasitPassw = false;
        gasitUser = false;
        gasitUserUnf = false;
        gasitUrmarire = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];

        cautarUser(strings);
        //userul cu care m.am logat nu exista
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }
        //nu stiu cui sa dau unfollow
        if(strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No username to unfollow was provided'}");
            return;
        }
        userNotFoll = strings[3];
        usSNotFollow = userNotFoll.replace("-username", "").trim();

        // Adaugă prefixul "-u"
        usSNotFollow = "-u " + usSNotFollow;

        if(usSNotFollow.equals(strings[1])) {
            System.out.println("{ 'status' : 'error', 'message' : 'The username to unfollow was not valid'}");
            return;
        }
        //cauta user in fisier
        try (BufferedReader reader = new BufferedReader(new FileReader("Utilizator.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(usSNotFollow)) {
                    gasitUserUnf = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("greseala1");
        }

        if (gasitUserUnf == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to unfollow was not valid'}");
            return;
        }
        //cautam in fisier sa vedem daca urmareste deja

        try (BufferedReader reader = new BufferedReader(new FileReader("Urmariri.txt"))) {
            String linie;

            while ((linie = reader.readLine()) != null) {
                // System.out.println(linie);
                if (linie.contains(strings[1])) {
                    if(linie.contains(usSNotFollow)) {
                        gasitUrmarire = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //nu pot sa dau unfolllow daca nu il am la follow
        if (gasitUrmarire == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to unfollow was not valid'}");
            return;
        }
        //trebuie sa sterg din fisierul de urmariri pentru a nu mai fi la follow
        if(gasitUrmarire == true){
            System.out.println("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");
            return;
        }

    }
    public void getFollowing (String[] strings) {
        gasitUser = false;
        gasitPassw = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];
        cautarUser(strings);
        //nu exista userul cu care m.am logat
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }
        int contor = 0;

        // cauta in fisierul de Urmariri sa vedem pe cine urmareste
        try (BufferedReader reader = new BufferedReader(new FileReader("Urmariri.txt"))) {
            String linie;
            int i = 0;
            //System.out.print("{ 'status' : 'ok', 'message' : [ ");
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1]) && linie.contains(strings[2]) ) {
                    impartit = linie;
                    impartit = impartit.split(" / ")[2];
                    impartit = impartit.replace("-u","").trim();
                    // System.out.print(impartit);
                    vectorUrmaritor[contor] = impartit;
                    contor++;
                }
            }
        } catch (IOException e) {
            System.out.println("greseala1");
        }

        System.out.print("{ 'status' : 'ok', 'message' : [ ");
        for (int i = 0; i < contor-1; i++) {
            System.out.print(vectorUrmaritor[i] + ", ");
        }
        System.out.print(vectorUrmaritor[contor - 1] + " ]}");
    }

    public void getFollowers(String[] strings) {
        gasitUser = false;
        gasitPassw = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];

        cautarUser(strings);
        //nu exista userul cu care m.am logat
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }
        if(strings.length == 3) {
            System.out.println("{ 'status' : 'error', 'message' : 'No username to list followers was provided'}");
            return;
        }
        //daca fisierul de Urmariri e gol
        if (fisier4.length() == 0) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to list followers was not valid'}");
            return;
        }

        int contor = 0;
        // cauta in fisierul de Urmariri sa vedem pe cine urmareste
        try (BufferedReader reader = new BufferedReader(new FileReader("Urmariri.txt"))) {
            String linie;
            int i = 0;
            //System.out.print("{ 'status' : 'ok', 'message' : [ ");
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1])) {
                    impartit = linie;
                    impartit = impartit.split(" / ")[0];
                    impartit = impartit.replace("-u","").trim();
                    // System.out.print(impartit);
                    vectorUrmaritor[contor] = impartit;
                    contor++;
                }
            }
        } catch (IOException e) {
            System.out.println("greseala1");
        }
        System.out.print("{ 'status' : 'ok', 'message' : [ ");
        for (int i = 0; i < contor-1; i++) {
            System.out.print(vectorUrmaritor[i] + ", ");
        }
        System.out.print(vectorUrmaritor[contor - 1] + " ]}");
    }

    public void getMostFollow(String[] strings) {
        gasitUser = false;
        gasitPassw = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];

        cautarUser(strings);
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }

        int nrNrmax = 5;
        int indexVectorNrPostari = 0;
        System.out.print("{ 'status' : 'ok', 'message' : [");
        try (BufferedReader reader = new BufferedReader(new FileReader("Utilizator.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                userCurent = linie.split(" / ")[0];
                passwdCurent = linie.split(" / ")[1];
                int nr_postari = 0;
                try (BufferedReader readerUrmariri = new BufferedReader(new FileReader("Urmariri.txt"))) {
                    String linieUrmariri;

                    while ((linieUrmariri = readerUrmariri.readLine()) != null) {
                        if (linieUrmariri.contains(userCurent) && !linieUrmariri.contains(passwdCurent)) {
                            nr_postari ++;
                        }
                    }

                    //vreau sa adaug in fisierul de nrUrmaririPerUtilizator
                    try (FileWriter fw = new FileWriter("nrUrmaririPerUtilizator.txt", true);
                         BufferedWriter bw = new BufferedWriter(fw);
                         PrintWriter out = new PrintWriter(bw)) {
                        out.print(userCurent);
                        out.print(" / ");
                        out.print(passwdCurent);
                        out.print(" / ");
                        out.println(nr_postari);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    vectorPers[indexVectorNrPostari] = userCurent;
                    vectorFollow[indexVectorNrPostari] = nr_postari;
                    nrUtilizator[indexVectorNrPostari] = indexVectorNrPostari + 1;
                    indexVectorNrPostari++;


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i<indexVectorNrPostari;i++)
            for(int j = i+1; j < indexVectorNrPostari;j++)
                if(vectorFollow[i]< vectorFollow[j])
                {
                    int aux;
                    aux = vectorFollow[i];
                    vectorFollow[i] = vectorFollow[j];
                    vectorFollow[j] = aux;
                    String contor;
                    contor = vectorPers[i];
                    vectorPers[i] = vectorPers[j];
                    vectorPers[j] = contor;
                }


        for(int i = 0; i < 5; i++) {

            if (i != 4) {
                System.out.print("{'username' : ");
                vectorPers[i] = vectorPers[i].replace("-u", "").trim();
                System.out.print(vectorPers[i] + ",'number_of_followers' : ' " + vectorFollow[i] + "' },");

            }
            if(i == 4) {
                System.out.print("{'username' : ");
                vectorPers[i] = vectorPers[i].replace("-u", "").trim();
                System.out.print(vectorPers[i] + ",'number_of_followers' : ' " + vectorFollow[i] + "' } ]}");

            }



        }
    }

    public void getMostLike(String[] strings) {
        gasitUser = false;
        gasitPassw = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];

        cautarUser(strings);
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }

        int nrLike = 0;
        int nr_pers= 0;
        System.out.print("{ 'status' : 'ok', 'message' : [");
        try (BufferedReader reader = new BufferedReader(new FileReader("Utilizator.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                myUser = linie.split(" / ")[0];
                myPassw = linie.split(" / ")[1];
                nrLike = 0;


                //caut in fisierul de like al postarilor
                try (BufferedReader readerLike = new BufferedReader(new FileReader("Like.txt"))) {
                    String linieLike;
                    while ((linieLike = readerLike.readLine()) != null) {
                        if (linieLike.contains(myUser) && linieLike.contains(myPassw)) {
                            nrLike++;
                            // System.out.println("nr" +nrLike);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("greseala");
                    //throw new RuntimeException(e);
                }

                //caut in fisierul de like al comentariilor
                try (BufferedReader readerComm = new BufferedReader(new FileReader("Like_comentariu.txt"))) {
                    String linieComm;
                    //  int aux = 1;
                    while ((linieComm = readerComm.readLine()) != null) {

                        String delimitator = "postarea pe care o comenteaza:";
                        // Încercăm să identificăm șirul specific și să eliminăm textul care urmează
                        int indexDelimitator = linieComm.indexOf(delimitator);
                        if (indexDelimitator != -1) {
                            // Dacă șirul este găsit, excluzăm textul care urmează după acesta
                            String linieFaraTextDupaDelimitator = linieComm.substring(0, indexDelimitator + delimitator.length());
                            if (linieFaraTextDupaDelimitator.contains(myUser) && linieFaraTextDupaDelimitator.contains(myPassw)) {
                                nrLike++;
                            }
                        } else {
                            if (linieComm.contains(myUser) && linieComm.contains(myPassw)) {
                                System.out.println(linieComm);
                                nrLike++;
                            }
                        }

                    }
                } catch (IOException e) {
                    System.out.println("greseala");
                    //throw new RuntimeException(e);
                }

                vectorUtilizatori[nr_pers] = myUser;
                vectorLike[nr_pers] = nrLike;
                nr_pers++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //ordonez descrescator
        for (int i = 0; i < nr_pers; i++)
            for(int j = i+1; j < nr_pers;j++)
                if(vectorLike[i]< vectorLike[j])
                {
                    int aux;
                    aux = vectorLike[i];
                    vectorLike[i] = vectorLike[j];
                    vectorLike[j] = aux;
                    String contor;
                    contor = vectorUtilizatori[i];
                    vectorUtilizatori[i] = vectorUtilizatori[j];
                    vectorUtilizatori[j] = contor;
                }

        //ordonez lexicografic
        for (int i = 0; i<nr_pers;i++)
            for(int j = i+1; j < nr_pers;j++)
                if(vectorLike[i] == vectorLike[j])
                {
                    int rezultat = vectorUtilizatori[i].compareTo(vectorUtilizatori[j]);
                    if(rezultat > 0) {
                        String contor;
                        contor = vectorUtilizatori[i];
                        vectorUtilizatori[i] = vectorUtilizatori[j];
                        vectorUtilizatori[j] = contor;
                    }
                }


        for(int i = 0; i < 5; i++) {

            if (i != 4) {
                System.out.print("{'username' : ");
                vectorUtilizatori[i] = vectorUtilizatori[i].replace("-u", "").trim();
                System.out.print(vectorUtilizatori[i] + ",'number_of_likes' : ' " + vectorLike[i] + "' },");

            }
            if(i == 4) {
                System.out.print("{'username' : ");
                vectorUtilizatori[i] = vectorUtilizatori[i].replace("-u", "").trim();
                System.out.print(vectorUtilizatori[i] + ",'number_of_likes' : ' " + vectorLike[i] + "' }]}");

            }



        }
    }
}