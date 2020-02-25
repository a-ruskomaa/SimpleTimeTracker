package roarusko.simpleTimeTracker.model.data;

import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Käyttäjiä käsitteleviä tiedonkäsittelyoperaatioita toteuttavan luokan rajapinta. Perii DAO:n 
 * metodit read(), update() sekä delete(). Tarjoaa oman toteutuksen create-metodille.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public interface UserDAO extends DAO<Integer, User> {

    /**
     * Lisää pysyvään muistiin uuden käyttäjän tiedot, jotka ottaa vastaan User-oliona
     * @param object Lisättävä käyttäjä
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public User create(User object);



}
