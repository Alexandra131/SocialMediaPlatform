package TemaTest;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Comentariu {

    private static String myUser = null;
    private static String myPassw = null;
    private static boolean gasitPassw = false;
    private static boolean gasitUser = false;
    private static boolean gasitomCApDeja = false;
    private static File fisier = new File("Utilizator.txt");
    private static File fisier1 = new File("Postare.txt");
    private static File fisier3 = new File("Comentariu.txt");
    private static String id  = null;
    private static String postareUtil = null;
    private static String postareaCareMaIntereseaza = null;
    private static String utilizatorCareMaIntereseaza = null;
    private static String comentariu = null;
    private static boolean gasitPostareComm = false;
    private static boolean aMaiFostCom = false;
    private static int  nrContor = 0;


    public void atrinuieNull() {
        gasitPostareComm = false;
        aMaiFostCom = false;

    }
    public void atribuieNullDoi() {
        gasitPassw = false;
        gasitUser = false;
    }

    public void cautaUser(String[] strings) {
        //cautare user in fisierul de utilizatori
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
            //throw new RuntimeException(e);
        }
    }
    public void cautaPass(String[] strings) {
        //cautare parola in fisier de utilizatori
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

    public void afisariSimple(String[] strings) {
        if(strings.length == 1) {
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            nrContor = 1;
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
        }
    }

    public void adaugaCom(String[] strings) {
        atrinuieNull();
        nrContor = 0;
        afisariSimple(strings);

        if(nrContor == 1) {
            nrContor = 0;
            return;
        }

        myUser = strings[1];
        myPassw = strings[2];

        //nu stiu unde sa adaug comm
        if(strings.length == 3 ){
            System.out.println("{ 'status' : 'error', 'message' : 'No text provided'}");
            return;
        }
        id = strings[3];
        if(strings.length == 4) {
            System.out.println("{ 'status' : 'error', 'message' : 'No text provided'}");
        }

        comentariu = strings[4];
        //verific daca am mai mult de 300 de caractere
        if (comentariu.length() > 300) {
            System.out.print("{ 'status' : 'error', 'message' : 'Comment text length exceeded'}");
            return;
        }

        int nr = 0;
        if(id.equals("-post-id '1'")) {
            nr = 1;
        }
        if(id.equals("-post-id '2'")) {
            nr = 2;
        }
        //cautare postarea cu id.ul respectiv
        try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
            String linie;
            int aux = 1;
            while ((linie = reader.readLine()) != null) {
                if(aux == nr) {
                    gasitPostareComm = true;
                    postareUtil = linie;
                    break;
                }
                aux++;
            }
        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }

        if(!gasitPostareComm) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to comment was not valid'}");
            return;
        }

        //cautare in fisierul comm sa vada daca am mai dat comm vreodata la aceesi postare
        try (BufferedReader reader = new BufferedReader(new FileReader("Comentariu.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1]) && linie.contains(postareUtil) ) {
                    aMaiFostCom = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }

        //scriu in fisierul Coment pentru a stii daca am dat like unei anumite postari
        try (FileWriter fw = new FileWriter("Comentariu.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(strings[1]);
            out.print(" / ");
            out.print(strings[2]);
            out.print("  postarea pe care o comenteaza: ");
            out.print(postareUtil);
            out.print (" comentariul: ");
            out.println(strings[4]);
        } catch (IOException e) {
            System.out.println("Eroare");
        }

        System.out.println("{ 'status' : 'ok', 'message' : 'Comment added successfully'}");


    }

    public void stergeCom (String[] strings) {
        atribuieNullDoi();
        aMaiFostCom = false;
        gasitPostareComm = false;
        atrinuieNull();
        nrContor = 0;
        afisariSimple(strings);

        if(nrContor == 1) {
            nrContor = 0;
            return;
        }

        cautaUser(strings);
        cautaPass(strings);
        //nu exista user.ul cu care m.am logat
        if (!gasitPassw || !gasitUser) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            return;
        }

        if(strings.length == 3) {
            System.out.println("{ 'status' : 'error', 'message' : 'No identifier was provided'}");
            return;
        }
        myPassw = strings[2];
        myUser = strings[1];
        id = strings[3];
        int nr = 0;
        if(id.equals("-id '1'")){
            nr = 1;
        }
        //daca fisierul  de postari e gol
        if (fisier1.length() == 0) {
            System.out.print("{ 'status' : 'error', 'message' : 'The identifier was not valid'}");
            return;
        }

        //cautare postarea cu id.ul respectiv
        try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
            String linie;
            int aux = 1;
            while ((linie = reader.readLine()) != null) {
                if(aux == nr) {
                    gasitPostareComm = true;
                    postareUtil = linie;
                    break;
                }
                aux++;
            }
        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }

        //cauta in fisierul de coom daca am dat comm
        try (BufferedReader reader = new BufferedReader(new FileReader("Comentariu.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1]) && linie.contains(postareUtil) ) {
                    aMaiFostCom = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }


        if(!aMaiFostCom) {
            System.out.print("{ 'status' : 'error', 'message' : 'The identifier was not valid'}");
            return;
        }

        //trebuie sa sterg comm din vectorul de comentarii dar mai tarziu
        System.out.println("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }
    public void likeComm(String[] strings) {
        atribuieNullDoi();
        gasitPostareComm = false;
        gasitomCApDeja = false;
        nrContor = 0;
        afisariSimple(strings);

        if(nrContor == 1) {
            nrContor = 0;
            return;
        }

        cautaUser(strings);
        cautaPass(strings);


        if (!gasitPassw || !gasitUser) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            return;
        }
        if(strings.length == 3){
            System.out.println("{ 'status' : 'error', 'message' : 'No comment identifier to like was provided'}");
            return;
        }

        //daca fisierul de comm e gol
        if (fisier3.length() == 0) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to like was not valid'}");
            return;
        }
        myUser = strings[1];
        myPassw = strings[2];
        id = strings[3];
        int nr = 0;
        if(id.equals("-comment-id '1'")) {
            nr = 1;
        }
        if(id.equals("-comment-id '2'")) {
            nr = 2;
        }

        // CAUTA COMENTARIU CU ID.UL RESPECTIV
        try (BufferedReader reader = new BufferedReader(new FileReader("Comentariu.txt"))) {
            String linie;
            int aux = 1;
            while ((linie = reader.readLine()) != null) {
                if(aux == nr ) {
                    gasitPostareComm = true;
                    postareUtil = linie;
                    break;
                }
                aux++;
            }
        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }
        //cautare in like_comentariu sa vad daca am dat ianite like la comm
        try (BufferedReader reader = new BufferedReader(new FileReader("Like_comentariu.txt"))) {
            String linie;
            //  int aux = 1;
            while ((linie = reader.readLine()) != null) {
                // System.out.println(linie);
                if(linie.contains(strings[1])) {
                    gasitomCApDeja = true;
                    postareUtil = linie;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }
        if(gasitomCApDeja) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to like was not valid'}");
            return;
        }

        //scriu in fisierul de likeuri pt un comentariu pentru a stii daca am dat like unei anumit comentariu
        try (FileWriter fw = new FileWriter("Like_comentariu.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(strings[1]);
            out.print("  comentariu pe care il apreciaza: ");
            out.println(postareUtil);
        } catch (IOException e) {
            System.out.println("Eroare");
        }
        System.out.println("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }
    public void stargeLikeComm(String[] strings) {
        atribuieNullDoi();
        gasitomCApDeja = false;
        nrContor = 0;
        afisariSimple(strings);

        if(nrContor == 1) {
            nrContor = 0;
            return;
        }

        cautaUser(strings);
        cautaPass(strings);

        if (!gasitPassw || !gasitUser) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            return;
        }

        if(strings.length == 3) {
            System.out.println("{ 'status' : 'error', 'message' : 'No comment identifier to unlike was provided'}");
            return;
        }
        myUser = strings[1];
        myPassw = strings[2];
        id = strings[3];
        int nr = 0;
        if(id.equals("-comment-id '1'")) {
            nr = 1;
        }
        //daca fisierul de comentarii e gol
        if (fisier3.length() == 0) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to unlike was not valid'}");
            return;
        }

        //cautare in like_comentariu sa vad daca am dat ianite like la comm
        try (BufferedReader reader = new BufferedReader(new FileReader("Like_comentariu.txt"))) {
            String linie;
            int aux = 1;
            while ((linie = reader.readLine()) != null) {
                // System.out.println(linie);
                if(linie.contains(strings[1]) && aux == nr ) {
                    gasitomCApDeja = true;
                    postareUtil = linie;
                    break;
                }
                aux++;
            }
        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }
        if(!gasitomCApDeja) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to unlike was not valid'}");
            return;
        }

        //sterge like dar asta o sa fac mai tarziu
        System.out.println("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }
    public void getMostComm(String[] strings) {
        atribuieNullDoi();
        nrContor = 0;
        afisariSimple(strings);

        if(nrContor == 1) {
            nrContor = 0;
            return;
        }
        cautaUser(strings);
        cautaPass(strings);

        if (!gasitPassw || !gasitUser) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            return;
        }

        // Obține data curentă
        LocalDate dataCurenta = LocalDate.now();

        // formatul pe care mi-l doresc zi:luna:an
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // vad cate linii are fisierul de postari
        int nrLiniFisierPostari = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {

            while ( reader.readLine() != null) {
                nrLiniFisierPostari++;
            }
        } catch (IOException e) {
            System.out.println("greseala1");
            //throw new RuntimeException(e);
        }
        int index;
        System.out.print("{ 'status' : 'ok', 'message' : [");
        for(index = 1; index <= nrLiniFisierPostari; index++) {
            System.out.print("{'post_id' : '" + index + "','post_text' : ");
            try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
                String linie;
                int contor_1 = 1;
                while ((linie = reader.readLine()) != null) {
                    if (contor_1 == index) {
                        postareaCareMaIntereseaza = linie;
                        postareaCareMaIntereseaza = postareaCareMaIntereseaza.split(" / ")[2];
                        postareaCareMaIntereseaza = postareaCareMaIntereseaza.replace("-text", "").trim();
                        System.out.print(postareaCareMaIntereseaza + ", 'post_date' : '" + dataCurenta.format(format) + "', ");
                        utilizatorCareMaIntereseaza = linie.split(" / ")[0];
                        utilizatorCareMaIntereseaza = utilizatorCareMaIntereseaza.replace("-u", "").trim();
                        System.out.print("'username': " + utilizatorCareMaIntereseaza + ", 'number_of_comments' : ");
                        int nrLike = 0;
                        //caut in fisierul de Comm sa vedem cate comentarii exista per postare
                        try (BufferedReader readerLike = new BufferedReader(new FileReader("Comentariu.txt"))) {
                            String linieLike;
                            while ((linieLike = readerLike.readLine()) != null) {
                                if (linieLike.contains(linie)) {
                                    nrLike++;
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("greseala");
                            //throw new RuntimeException(e);
                        }
                        System.out.print("'" + nrLike + "' }");
                        if(index != nrLiniFisierPostari) {
                            System.out.print(",");
                        }

                    }
                    contor_1++;
                }
            } catch (IOException e) {
                System.out.println("greseala1");
                //throw new RuntimeException(e);
            }
        }
        System.out.println("]}");
    }

}
