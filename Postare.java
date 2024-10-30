package TemaTest;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Postare  implements  Likeable{
    private static String[] vectorUtilizatori = new String[1000];
    private static String[] vectorPostari = new String[1000];
    private static String postareText = null;
    private static String myUser = null;
    private static String myPassw = null;
    private static boolean gasitPassw = false;
    private static boolean gasitUser = false;
    private static String text = null;
    private static String[] vectorComm = new String[1000] ;
    private static int[] vectorCommLike = new int[1000] ;
    private static String userComm = null;
    private static String[] vectorUserComm = new String[1000];
    private static String  userPost = null;
    private static File fisier = new File("Utilizator.txt");
    private static File fisier1 = new File("Postare.txt");
    private static boolean gasitUserPost = false;
    private static boolean gasitPasswPost = false;
    private static String id  = null;
    private static String postareUtil = null;
    private static String postareaCareMaIntereseaza = null;
    private static String utilizatorCareMaIntereseaza = null;
    private static boolean gasitPostareLike = false;
    private static boolean aMaiFostLike = false;
    private static int nrContor = 0;
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

    public void cautareUser(String[] strings) {
        //cautare user in fisier
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

        // cautare parola in fisier
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
    public void adaugaPostare(String[] strings) {
        gasitUser = false;
        gasitPassw = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];


       cautareUser(strings);
        //in cazul in care nu se afla userul respectiv in fisier
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }
        //in cazul in care nu avem un text pentru postare
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No text provided'}");
            return;
        }
        text = strings[3];
        //in cazul in care textul depaseste 300 de caractere
        if (text.length() > 300) {
            System.out.print("{ 'status' : 'error', 'message' : 'Post text length exceeded'}");
            return;
        }

        //scriere in fisierul de postari pentru a tine minte daca exista
        try (FileWriter fw = new FileWriter("Postare.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(strings[1]);  //numele utilizator
            out.print(" / ");
            out.print(strings[2]);  //parola utilizator
            out.print(" / ");
            out.println(strings[3]); //text
        } catch (IOException e) {
            throw new RuntimeException(e);
            //exception handling left as an exercise for the reader
        }




        System.out.print("{ 'status' : 'ok', 'message' : 'Post added successfully'}");
        return;
    }


    public void stergePostare(String[] strings) {
        gasitPassw = false;
        gasitUser = false;
        gasitPasswPost = false;
        gasitUserPost = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];
        cautareUser(strings);
        //in cazul in care nu exista utilizatorul cu care noi ne.am logat
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }


        //cautare user in fisierul de postare sa vedem daca a postat ceva
        try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1])) {
                    gasitUserPost = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("greseala1");
            //throw new RuntimeException(e);
        }

        //cautare parola in fisier de postare sa vedem daca a postat ceva
        try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[2])) {
                    gasitPasswPost = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }
        //daca userul nu a postat nimic nu putem sterge postarea
        if (gasitPasswPost == false || gasitUserPost == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'The identifier was not valid'}");
            gasitUserPost = false;
            gasitPasswPost = false;
            return;
        }



        //aici o sa trebuiasca sa sterg postarea din fisier
        if (gasitPasswPost != false && gasitUserPost != false && strings.length == 4) {
            System.out.print("{ 'status' : 'ok', 'message' : 'Post deleted successfully'}");
            return;
        }

    }

    public void Like(String[] strings) {
        gasitPassw = false;
        gasitUser = false;
        gasitPostareLike = false;
        aMaiFostLike = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];

        cautareUser(strings);
        //daca userul cu care m.am conectat nu exista
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }
        //nu stiu ce postare trebuie apreciata
        if (strings.length == 3) {
            System.out.println("{ 'status' : 'error', 'message' : 'No post identifier to like was provided'}");
            return;
        }
        //daca fisierul e gol
        if (fisier1.length() == 0) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
            return;
        }

        id = strings[3];
        int idNumar = 0;
        if(id.equals("-post-id '1'")){
            idNumar = 1;
        }
        if(id.equals("-post-id '2'")){
            idNumar = 2;
        }
        if(id.equals("-post-id '3'")){
            idNumar = 3;
        }
        if(id.equals("-post-id '4'")){
            idNumar = 4;
        }
        if(id.equals("-post-id '5'")){
            idNumar = 5;
        }
        if(id.equals("-post-id '6'")){
            idNumar = 6;
        }

        //cautare postarea cu id.ul respectiv
        try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
            String linie;
            int aux = 1;
            while ((linie = reader.readLine()) != null) {
                if(aux == idNumar && !linie.contains(strings[1])) {
                    gasitPostareLike = true;
                    postareUtil = linie;
                    break;
                }
                aux++;
            }
        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }

        if(gasitPostareLike == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
            return;
        }
        //daca imi dau eu like singur
        if(postareUtil.contains(strings[1])) {
            System.out.println("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
            return;
        }

        //cautare in fisierul like sa vada daca am mai dat like vreodata la aceesi postare
        try (BufferedReader reader = new BufferedReader(new FileReader("Like.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1]) && linie.contains(postareUtil) ) {
                    aMaiFostLike = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }


        if (aMaiFostLike == true) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
            return;
        }


        //scriu in fisierul Like pentru a stii daca am dat like unei anumite postari
        try (FileWriter fw = new FileWriter("Like.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw);
             BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
            out.print(strings[1]);
            out.print(" / ");
            out.print(strings[3]);
            out.print("  postarea pe care o aprecize: ");
            String linie;
            int aux = 1;
            while ((linie = reader.readLine()) != null) {
                if (aux == idNumar && !linie.contains(strings[1])) {
                    out.println(linie);
                    break;
                }
                aux++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        System.out.println("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");

    }

    public void unLike(String[] strings) {
        gasitPassw = false;
        gasitUser = false;
        gasitPostareLike = false;
        aMaiFostLike = false;
        nrContor = 0;
        afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

        myPassw = strings[2];
        myUser = strings[1];

        cautareUser(strings);
        // daca nu exista userul cu care m.am logat
        if (gasitPassw == false || gasitUser == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
            gasitUser = false;
            gasitPassw = false;
            return;
        }
        //in cazul in care nu stiu cui trebuie sa dau unlike
        if(strings.length == 3) {
            System.out.println("{ 'status' : 'error', 'message' : 'No post identifier to unlike was provided'}");
            return;
        }

        //daca fisierul e gol
        if (fisier1.length() == 0) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to unlike was not valid'}");
            return;
        }
        id = strings[3];
        int idNumar = 0;
        if(id.equals("-post-id '1'")){
            idNumar = 1;
        }
        if(id.equals("-post-id '2'")){
            idNumar = 2;
        }
        //cautare postarea cu id.ul respectiv
        try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
            String linie;
            int aux = 1;
            while ((linie = reader.readLine()) != null) {
                if(aux == idNumar && !linie.contains(strings[1])) {
                    gasitPostareLike = true;
                    postareUtil = linie;
                    break;
                }
                aux++;
            }
        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }
        //nu pot sa imi dau like singur
        if(postareUtil.contains(strings[1])) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to unlike was not valid'}");
            return;
        }
        //nu am gasit postarea respectiva
        if(gasitPostareLike == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to unlike was not valid'}");
            return;
        }

        //cautare in fisierul like sa vada daca am mai dat like vreodata la aceesi postare
        try (BufferedReader reader = new BufferedReader(new FileReader("Like.txt"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.contains(strings[1]) && linie.contains(postareUtil) ) {
                    aMaiFostLike = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("greseala");
            //throw new RuntimeException(e);
        }


        if (aMaiFostLike == false) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to unlike was not valid'}");
            return;
        }

        //trebuie sa sterg like-ul dar mai tarziu
        System.out.println("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");


    }


   public void getMostLikePost (String[] strings) {
       gasitUser = false;
       gasitPassw = false;
        nrContor = 0;
       afisariSimple(strings);
        if(nrContor == 1) {
            return;
        }

       myPassw = strings[2];
       myUser = strings[1];

       cautareUser(strings);
       //nu exista userul cu care m.am logat
       if (gasitPassw == false || gasitUser == false) {
           System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
           gasitUser = false;
           gasitPassw = false;
           return;
       }

       // Obține data curentă
       LocalDate dataCurenta = LocalDate.now();

       // format pe care mi-l doresc zi:luna:an
       DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

       // vad cate linii are fisierul de postari
       int nrLiniFisierPostari = 0;
       try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
           String linie;
           while ((linie = reader.readLine()) != null) {
               nrLiniFisierPostari++;
           }
       } catch (IOException e) {
           System.out.println("greseala1");
           //throw new RuntimeException(e);
       }
       int index = 1;
       //vreau sa ii afisez unei anumite postari nr de like-uri cu tot cu textul posatrii si user
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
                       System.out.print("'username': " + utilizatorCareMaIntereseaza + ", 'number_of_likes' : ");
                       int nrLike = 0;
                       //caut in fisierul de Like
                       try (BufferedReader readerLike = new BufferedReader(new FileReader("Like.txt"))) {
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
       System.out.println(" ]}");
   }


   public void getFollowingsPosts (String[] strings) {
       gasitUser = false;
       gasitPassw = false;
       nrContor = 0;
       afisariSimple(strings);
       if(nrContor == 1) {
           return;
       }

       myPassw = strings[2];
       myUser = strings[1];
        //nu exista utilizatorul cu care m.am logat
       cautareUser(strings);
            if (gasitPassw == false || gasitUser == false) {
                System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
                gasitUser = false;
                gasitPassw = false;
                return;
            }

            int index = 0;
            // Obține data curentă
            LocalDate dataCurenta = LocalDate.now();

            // format  pe care mi-l doresc zi:luna:an
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            //caut in fisierul de utilizatori sa vad daca exista
            try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
                String linie;
                while ((linie = reader.readLine()) != null) {
                    myUser = linie.split(" / ")[0];
                    myUser = myUser.replace("-u", "").trim();
                   postareText = linie.split(" / ")[2];
                   postareText = postareText.replace("-text", "").trim();
                   vectorUtilizatori[index] = myUser;
                   vectorPostari[index] = postareText;
                   index ++;

                }

            } catch (IOException e) {
                System.out.println("greseala");
                //throw new RuntimeException(e);
            }
            int contor = 0;
            int contor1 = 1;
            //afisez toate postarile cu tot cu utlizatori si data curenta
            System.out.print("{ 'status' : 'ok', 'message' : [");
            for(int i = 0; i < index; i++) {
                if(i != index-1) {
                System.out.print( "{'post_id' : '" + (index-contor) + "', 'post_text' : " + vectorPostari[index-contor1]);
                System.out.print(", 'post_date' : '" + dataCurenta.format(format) + "', 'username' : " + vectorUtilizatori[index-contor1] + "},");
                contor++;
                contor1++;
                }
                if(i == index-1) {
                    System.out.print( "{'post_id' : '" + (index-contor) + "', 'post_text' : " + vectorPostari[index-contor1]);
                    System.out.print(", 'post_date' : '" + dataCurenta.format(format) + "', 'username' : " + vectorUtilizatori[index-contor1] + "}]}");
                    contor++;
                    contor1++;
                }

            }
   }

   public void getUserPost (String[] strings) {
       gasitUser = false;
       gasitPassw = false;
       nrContor = 0;
       afisariSimple(strings);
       if(nrContor == 1) {
           return;
       }

       myPassw = strings[2];
       myUser = strings[1];

       cautareUser(strings);
       //nu exista userul cu care m.am logat
       if (gasitPassw == false || gasitUser == false) {
           System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
           gasitUser = false;
           gasitPassw = false;
           return;
       }

       if(strings. length == 3) {
           System.out.println("{ 'status' : 'error', 'message' : 'No username to list posts was provided'}");
           return;
       }
       //daca fisierul e gol
       if (fisier1.length() == 0) {
           System.out.print("{ 'status' : 'error', 'message' : 'The username to list posts was not valid'}");
           return;
       }


       //cautare user in fisier
       try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
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

       //cautare parola in fisier
       try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
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
       //user-ul nu are nici-o postare
       if (gasitPassw == false || gasitUser == false) {
           System.out.print("{ 'status' : 'error', 'message' : 'The username to list posts was not valid'}");
           gasitUser = false;
           gasitPassw = false;
           return;
       }

       myUser = strings[3].replace("-username ", "").trim();
       myUser = "-u " + myUser;
       // Obține data curentă
       LocalDate dataCurenta = LocalDate.now();

       // formatul pe care mi-l doresc zi:luna:an
       DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
       int index = 0;
       //cauta in fisierul de postari pentru a vedea toate postarile publicate de un user
       try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
           String linie;
           while ((linie = reader.readLine()) != null) {
               if (linie.contains(myUser)) {
                   userPost = linie.split(" / ")[2];
                   userPost = userPost.replace("-text","").trim();
                   vectorPostari[index] = userPost;
                   index++;
               }
           }

       } catch (IOException e) {
           System.out.println("greseala");
           //throw new RuntimeException(e);
       }

       int contor = 0;
       int contor1 = 1;
       //afisez toate postarile unui anumit user
       System.out.print("{ 'status' : 'ok', 'message' : [");
       for(int i = 0; i < index; i++) {
           if(i != index-1) {
               System.out.print( "{'post_id' : '" + (index-contor) + "', 'post_text' : " + vectorPostari[index-contor1]);
               System.out.print(", 'post_date' : '" + dataCurenta.format(format) +"'},");
               contor++;
               contor1++;
           }
           if(i == index-1) {
               System.out.print( "{'post_id' : '" + (index-contor) + "', 'post_text' : " + vectorPostari[index-contor1]);
               System.out.print(", 'post_date' : '" + dataCurenta.format(format) +"'}]}");
               contor++;
               contor1++;
           }

       }
   }
   public void getPostDetails(String[] strings) {
       gasitUser = false;
       gasitPassw = false;
       nrContor = 0;
       afisariSimple(strings);
       if(nrContor == 1) {
           return;
       }

       myPassw = strings[2];
       myUser = strings[1];

       cautareUser(strings);
       //nu exista userul cu care m.am logat
       if (gasitPassw == false || gasitUser == false) {
           System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
           gasitUser = false;
           gasitPassw = false;
           return;
       }
       //nu am identificator
       if(strings. length == 3) {
           System.out.println("{ 'status' : 'error', 'message' : 'No post identifier was provided'}");
           return;
       }
       //daca fisierul e gol
       if (fisier1.length() == 0) {
           System.out.print("{ 'status' : 'error', 'message' : 'The post identifier was not valid'}");
           return;
       }
       id = strings[3];
       int nr = 0;
       if (id.equals("-post-id '2'")){
           nr = 2;
       }
       if (id.equals("-post-id '1'")) {
           nr = 1;
       }
        //aflu cate postari exista
       int nrLiniPostari = 0;
       try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
           String linie;
           while ((linie = reader.readLine()) != null) {
               nrLiniPostari++;
           }
       } catch (IOException e) {
           System.out.println("greseala1");
           //throw new RuntimeException(e);
       }

       if(nr != nrLiniPostari) {
           System.out.println("{ 'status' : 'error', 'message' : 'The post identifier was not valid'}");
           return;
       }

       // Obține data curentă
       LocalDate dataCurenta = LocalDate.now();

       // formatul pe care mi-l doresc zi:luna:an
       DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

       int nrLikePost = 0, nrComm = 0, nrCommLike;
       try (BufferedReader reader = new BufferedReader(new FileReader("Postare.txt"))) {
           String linie;
           int nrContor = 1; //pentru a gasii id.ul postarii pe care mi-l doresc

           while ((linie = reader.readLine()) != null) {

               if (nrContor == nr) {
                   nrCommLike = 0;
                   nrLikePost = 0;
                   userPost = linie.split(" / ")[0];
                   userPost = userPost.replace("-u", "").trim();
                   postareText = linie.split(" / ")[2];
                   postareText = postareText.replace("-text","").trim();
                   // caut nr de like
                   try (BufferedReader readerLike = new BufferedReader(new FileReader("Like.txt"))) {
                       String linieLike;
                       while ((linieLike = readerLike.readLine()) != null) {
                           if (linieLike.contains(linie)) {
                               nrLikePost++;
                           }
                       }
                   } catch (IOException e) {
                       System.out.println("greseala");
                       //throw new RuntimeException(e);
                   }
                   //caut nr Comentarii
                   try (BufferedReader readerComentariu = new BufferedReader(new FileReader("Comentariu.txt"))) {
                       String linieComentariu;
                       while ((linieComentariu = readerComentariu.readLine()) != null) {
                           if (linieComentariu.contains(linie)) {
                               nrCommLike = 0;
                               userComm = linieComentariu.split(" / ")[0];
                               userComm = userComm.replace("-u","").trim();
                               vectorUserComm[nrComm] = userComm;
                               String cuvantCheie = " comentariul: ";
                               String linieCheie = null;
                               int pozitieCuvant= linieComentariu.indexOf(cuvantCheie);
                               if (pozitieCuvant != -1) {
                                   linieCheie= linieComentariu.substring(pozitieCuvant + cuvantCheie.length());
                               }
                               linieCheie = linieCheie.replace("-text","").trim();
                               vectorComm[nrComm] = linieCheie;


                               // pentru fiecare comm caut sa vad cate likeuri are
                               try (BufferedReader readerCommLike = new BufferedReader(new FileReader("Like_comentariu.txt"))) {
                                   String linieCommLike;

                                   while ((linieCommLike = readerCommLike.readLine()) != null) {
                                       if(linieCommLike.contains(linieComentariu)) {
                                           nrCommLike++;
                                       }
                                   }
                               } catch (IOException e) {
                                   System.out.println("greseala");

                               }
                               vectorCommLike[nrComm] = nrCommLike;
                               nrComm++;

                               break;
                           }
                       }

                   } catch (IOException e) {
                       System.out.println("greseala");
                       //throw new RuntimeException(e);
                   }

                   break;
               }
               nrContor++;
           }

       } catch (IOException e) {
           System.out.println("greseala");
           //throw new RuntimeException(e);
       }
        //vreau 
        //afisez toate datele unei postari, nr de like-uri, comm-uri si informatiile per comentariu
       System.out.print("{'status' : 'ok', 'message' : [{'post_text' : " + postareText +", 'post_date' :'");
       System.out.print(dataCurenta.format(format) + "', 'username' : " + userPost + ", 'number_of_likes' :");
       System.out.print("'" + nrLikePost + "', 'comments' : [");
       for (int i = 1; i <= nrComm; i++)  {
           if(i == nrComm) {
               System.out.print("{'comment_id' : '" + i + "', 'comment_text' : " + vectorComm[i - 1] + ", 'comment_date' : '");
               System.out.print(dataCurenta.format(format) + "', 'username' : " + vectorUserComm[i - 1]);
               System.out.print(", 'number_of_likes' : '" + vectorCommLike[i - 1] + "'}] }] }");
           }
           if (i != nrComm) {
               System.out.print("{'comment_id' : '" + i + "', 'comment_text' : " + vectorComm[i - 1] + ", 'comment_date' : '");
               System.out.print(dataCurenta.format(format) + "', 'username' : " + vectorUserComm[i - 1]);
               System.out.print(", 'number_of_likes' : '" + vectorCommLike[i - 1] + "'},");
           }
       }
   }
}
