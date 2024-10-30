       MiniPlatforma de Socializare este o mică aplicație de socializare construită în Java, care facilitează crearea și partajarea de postări între utilizatori.
 In prima instanta am creat un utilizator, in cazul in care acesta nu exista deja, pentru a putea avea mai multe posibilitati de interactionare si l-am adaugat in
 fisierul de Utilizatori.txt. Am creat o postare pentru un utilizator, pe care am adaugat-o in fisierul de Postare.txt dar ca un utilizator sa poata posta ceva
 trebuie sa fie logat pe contul sau si sa aiba un cont existent, iar postarea sa sa nu contina nu numar mai mare de 300 caractere. Putem de asemenea sa stergem
 o anumita postare pe care noi am creat-o, si in acelasi timp trebuie sa fim logati, nu putem sa stergem o postare care nu ne apartine. Avem de asemenea dreptul
 de a urmarii alti utilizatori, dar putem sa ii urmarim cu anumite conditii, in primul rand sa fim logati la un cont existent, sa existe contul pe care dorim
 sa-l urmarim si de asemenea sa nu il urmarim deja, adica nu putem sa urmarim un utilizator de doua ori dacaa nu i-am dat unfollow. Pentru a da unfollow trebuie
 de asemenea sa fim logati la un cont existent, contul caruia vrem saa dam unfollow sa existe si putem sa.l eliminam din urmaritorii nostrii doar daca se numara
 printre ei..
       Putem sa aprecim postarile utilizatorilor existenti dar de asemenea trebuie sa fim logati la un cont existent, dar in schimb nu ne putem aprecia singuri
 postarile si nici nu putem sa dam like unei postari de doua ori daca nu am dat unlike, unlike putem sa dam la orice postare care se afla in lista de postari
 apreciate de userul la care suntem logati, nu putem sa dam unlike la o postare care nu este apreciata de userul la care suntem logati sau la o postare a unui
 user inexistent.
       Putem de asemenea sa comentam o postare, daca suntem logati la un user existent, si daca postarea exista, de asemenea aici ne putem comenta si propria
 postare.Putem sterge comentariul unei postarii, doar daca suntem logati la un user existent si daca comentariu a fost adaugat de catre noi. Putem sa aprecim
 un comentariu existent, daca suntem logati la un user existent dar in schimb nu putem sa ne aprecim propriul nostru comentariu, si nici nu putem sa aprecim
 un comentariu de doua ori, daca nu am dat unlike, unlike la un comentariu putem da doar daca l.am apreciat la un anumit moment dat, daca nu a fost apreciat
 nu putem da unlike, si de asemenea trebuie sa fim logati la un cont existent.
       De asemena am spus mai sus ca putem sa urmarim diferiti useri care exista, si care acestia pot posta anumite lucruri la un anumit moment dat, iar noi
 putem afisa lista de postari descrescatoare a utilizaorilor pe care ii urmarim doar in cazul in care acestia posteaza ceva, iar maxim putem sa facem un top
 cinci al persoanelor cu cele mai multe postari din lista noastra de urmaritori.
        Putem de asemenea sa afisam lista postari per utilizator ordonate descrescator dupa data, dar pentru asta trebuie sa fim logati la un user existent,
 doar in caul in care fisierul de postari nu este gol, si daca username-ul pentru a afisa datele despre postari exista si are postari despre care putem afla
 inforamtii.
        De asemenea putem afla toate datele unei anumite postari, cu un anumit id, doar daca suntem logati la un cont existent, si putem sa aflam mesaj
 postarii, numarul de aprecieri pe care le.a acumulat, numarul de comentarii, numarul de aprecieri per comenariu si mesajul fiecarui comentariu, dar asta
 doar in cazul in care exist postarea cu id-ul respectiv.
        De asemenea putem sa afisam lista de urmaritori per utilizator, dar pentru asta trebuie sa fim logati la un cont existent, daca acesta nu urmareste
 pe nimeni se intoarce un mesaj de eroare.
        Putem sa aflam ce urmaritori are o anumita perosana, dar in acest caz trebuie sa fim logati la un cont existent, si de asemnea sa primim contul
 despre care vrem sa afla anumite informatii, in cazul in care contul furnizat nu este unul valid se intoarce mesaj de eroare, in cazul in care nu are urmaritori
 de asemenea se intoarce mesaj de eroare.
        De asemenea putem sa facem un top cinci al celor mai apreciate postari de pe platforma, dar pentru asta trebuie sa fim logati la un cont existent, trebuie
 verificat daca exista      postari, si de asemnea sa ordonam postarile in mod descrescator si sa le afisam pe primele 5, de asemenea trebuie sa furnizam textul
 postari, utilizatorul care a creat.o si numarul de like-uri.
         De asemenea putem face un top cinci al celor mai comentate postari de pe platforma, dar pentru asta trebuie sa fim logati la un cont existent, trebuie
 verificat daca exista postari, si de asemnea sa ordonam postarile in ordine descresactoate si sa le afisam pe primale 5,de asemenea trebuie sa furnizam textul
 postari, utilizatorul care acreat.o si numarul de cometarii.
          De asemenea putem face un top cinci al celor mai urmarite persoane de pe platforma,dar pentru asta trebuie sa fim logati la un cont existent, trebuie
 sa ordonam persoanele care au urmaritori in mod descrescator sa sa afisam informatiile despre acestia.
           De asemenea putem sa vedem cine are cele mai multe like.uri de pe platforma, adunate atat din postari cat si comentarii, pentru asta trebuie sa fim
 logati la un cont existent, sa ordonam descrescator in functie de numarul total de like.uri iar apoi sa afisam informatiile doar pentru primii cinci.
            De asemenea putem sa stergem toate datele din platforma, golind toate fisierele existente prin comanda -cleanup-all
