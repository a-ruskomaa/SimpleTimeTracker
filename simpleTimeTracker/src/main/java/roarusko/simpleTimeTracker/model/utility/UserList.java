package roarusko.simpleTimeTracker.model.utility;

import java.util.ArrayList;
import java.util.Arrays;

import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Käyttäjien tallentamiseen tarkoitettu ArrayListin kaltainen tietorakenne.
 * Tarjoaa metodit alkioiden lisäämiselle listan loppuun tai haluttuun indeksiin.
 * Alkioita voi poistaa joko olioviitteen tai indeksin perusteella. Skaalautuu
 * automaattisesti alkioiden määrän ylittäessä sen hetkisen kapasiteetin.
 * 
 * @author aleks
 * @version 17 Feb 2020
 *
 * @example
 * <pre name="test">
 * #import  roarusko.simpleTimeTracker.model.domainModel.User;
 * UserList lista = new UserList(5);
 * lista.size() === 0;
 * lista.isEmpty() === true;
 * 
 * User user1 = new User(1, "Janne");
 * User user2 = new User(2, "Jonne");
 * User user3 = new User(3, "Jenni");
 * lista.add(user1);
 * lista.add(user2);
 * lista.add(user3); //[Janne, Jonne, Jenni]
 * 
 * lista.size() === 3;
 * lista.isEmpty() === false;
 * 
 * User user4 = new User(4, "Joonas");
 * lista.add(0, user4); //[Joonas, Janne, Jonne, Jenni]
 * lista.indexOf(user4) === 0;
 * lista.contains(user4) === true;
 * 
 * lista.remove(user4) === true; // [Janne, Jonne, Jenni]
 * lista.remove(1); // [Janne, Jenni]
 * lista.get(1).equals(user3) === true;
 * 
 * User user5 = new User(5, "Jammu");
 * User user6 = new User(6, "Kyösti");
 * 
 * lista.contains(user5) === false;
 * lista.indexOf(user6) === -1;
 * 
 * lista.add(user5);
 * lista.add(user6); //[Janne, Jenni, Jammu, Kyösti]
 * 
 * lista.size() === 4;
 * 
 * lista.add(user4); //[Janne, Jenni, Jammu, Kyösti, Joonas]
 * lista.add(0, user2); //[Jonne, Janne, Jenni, Jammu, Kyösti, Joonas]
 * 
 * lista.size() === 6;
 * 
 * User user7 = new User(7, "Jorma");
 * lista.set(4, user7); //[Jonne, Janne, Jenni, Jammu, Jorma, Joonas]
 * 
 * lista.contains(user6) === false;
 * 
 * lista.get(1).equals(user1);
 * </pre>
 */
public class UserList extends ArrayList<User> {
    /**
     * 
     */
    private static final long serialVersionUID = 8732321631817964940L;
    private int size;
    private int maxSize;
    private User[] users;

    
    /**
     * Luo uuden UserListin. Asettaa alkuperäiseksi kapasiteetiksi 10 alkiota.
     */
    public UserList() {
        this.users = new User[10];
        this.size = 0;
        this.maxSize = 10;
    }
    
    
    /**
     * Luo uuden UserListin annetulla kapasiteetilla
     * @param capacity Kapasiteetti listan luontihetkellä
     */
    public UserList(int capacity) {
        this.users = new User[capacity];
        this.size = 0;
        this.maxSize = capacity;
    }
    
    /**
     * Palauttaa listan koon
     * @return Palauttaa listan koon
     * 
     */
    @Override
    public int size() {
        return this.size;
    }

    
    /**
     * Tutkii onko lista tyhjä
     * @return Palauttaa true jos listalla ei ole yhtään alkiota
     */
    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }

    
    /**
     * Lisää alkion listan viimeiseksi
     * @param user Käyttäjä joka listalle lisätään
     * @return palauttaa false jos operaatio epäonnistui
     */
    @Override
    public boolean add(User user) {
        this.add(size, user);
        return true;
    }
    
    
    /**
     * Lisää alkion listalle annettuun indeksiin. Siirtää seuraavia
     * listalla olevia alkioita yhden indeksin oikealle.
     * @param index Indeksi johon lisätään
     * @param user Käyttäjä joka lisätään
     */
    @Override
    public void add(int index, User user) {
        if (index > size) throw new IndexOutOfBoundsException();
        
        if (this.size == maxSize - 1) {
            expand();
        }
        
        if (index < size) {
            moveOneStepRight(index);
        }
        
        this.users[index] = user;
        this.size++;
        
    }
    
    
    /**
     * Etsii annettua käyttäjää listalta. 
     * @param user Käyttäjä jota etsitään
     * @return Palauttaa käyttäjän indeksin tai -1 jos käyttäjää ei löydy
     */
    public int indexOf(User user) {
        for (int i = 0; i < users.length; i++) {
            if (user.equals(users[i])) return i;
        }
        
        return -1;
    }
    
    
    /**
     * Tutkii esiintyykö annettua käyttäjää listalta
     * @param user Käyttäjä jota etsitään
     * @return Palauttaa true jos käyttäjä löytyy
     */
    public boolean contains(User user) {
        return (this.indexOf(user) >= 0);
    }

    
    
    /**
     * Poistaa alkion annetusta indeksistä. Siirtää listalle jääneitä
     * alkioita yhden askeleen vasemmalle.
     * @param index Indeksi josta poistetaan
     * @return palauttaa poistetun alkion
     */
    @Override
    public User remove(int index) {
        if (index >= size) throw new IndexOutOfBoundsException();
        
        User removed = users[index];
        
        moveOneStepLeft(index);
        this.size--;
        this.users[size] = null;
        
        return removed;
    }


    /**
     * Poistaa annetun alkion, mikäli se löytyy listalta. Siirtää listalle
     * jääneitä alkioita yhden askeleen vasemmalle.
     * @param user Käyttäjä joka poistetaan
     * @return Palauttaa true jos poisto onnistui
     */
    public boolean remove(User user) {
        int index = indexOf(user);
        
        if (index < 0) return false;
        
        this.remove(index);
        return true;
    }

    
//    public boolean addAll(Collection<? extends E> c) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    
//    public boolean addAll(int index, Collection<? extends E> c) {
//        // TODO Auto-generated method stub
//        return false;
//    }


    
    /**
     * Palauttaa alkion annetusta indeksistä. Heittää poikkeuksen
     * jos listalla ei ole indeksissä alkiota.
     * @param index Indeksi josta etsitään.
     * @return Palauttaa annetussa indeksissä olevan käyttäjän
     */
    @Override
    public User get(int index) {
        if (index >= size) throw new IndexOutOfBoundsException();
        return this.users[index];
    }

    
    /**
     * Korvaa annetussa indeksissä olevan alkion uudella alkiolla
     * @param index Indeksi johon uusi alkio asetetaan
     * @param user Käyttäjä joka lisätään listalle
     * @return Palauttaa lisätyn käyttäjän
     */
    @Override
    public User set(int index, User user) {
        if (index >= size) throw new IndexOutOfBoundsException();
        this.users[index] = user;
        return user;
    }

    
    /**
     * Kasvattaa taustalla olevan listarakenteen kokoa 1,5 kertaiseksi
     */
    private void expand() {
        int newSize = size + size / 2;
        User[] newArr = new User[newSize];
        for (int i = 0; i < this.users.length; i++) {
            newArr[i] = users[i];
        }
        this.users = newArr;
        this.maxSize = newSize;
    }
    
    
    /**
     * Siirtää listalla olevia alkioita yhden askeleen vasemmalle
     * alkaen annetusta indeksistä
     * @param fromIndex Ensimmäinen indeksi, johon siirretään
     */
    private void moveOneStepLeft(int fromIndex) {
        for (int i = fromIndex; i < users.length - 1; i++) {
            this.users[i] = this.users[i + 1];
        }
    }
    
    /**
     * Siirtää listalla olevia alkioita yhden askeleen oikealle alkaen
     * annetusta indeksistä.
     * @param fromIndex
     */
    private void moveOneStepRight(int fromIndex) {
        for (int i = users.length - 2; i >= fromIndex; i--) {
            
            this.users[i + 1] = this.users[i];
        }
    }
    
    
    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(users, this.size));
    }


}
