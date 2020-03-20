package roarusko.simpleTimeTracker.model.data.mock;

import java.util.List;
import java.util.stream.Collectors;

import roarusko.simpleTimeTracker.model.data.ChildDAO;
import roarusko.simpleTimeTracker.model.domain.ChildObject;
import roarusko.simpleTimeTracker.model.domain.ParentObject;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * 
 * Käyttäjien, projektien ja merkintöjen tietokantahakuihin tarkoitettu abstrakti luokka.
 * Hyväksyy geneerisenä tyyppiparametrinaan DataObject-rajapinnan toteuttavat luokat. Tarjoaa
 * geneerisenä toteutuksena kaikki muut DAO-rajapinnan metodit, paitsi create.
 * @author aleks
 * @version 17 Feb 2020
 *
 * @param <T> Luokan tyyppiparametri, oltava ChildObject-rajapinnan toteuttava luokka, kuten Project tai Entry
 * @param <P> Luokan "vanhemman" tyyppiparametri, oltava ParentObject-rajapinnan toteuttava luokka, kuten Project tai User
 */
public abstract class MockAbstractChildDAO<T extends ChildObject, P extends ParentObject> extends MockAbstractDAO<T> implements ChildDAO<Integer, T, P> {

    /**
     * Kutsuu yläluokan konstruktoria annetuilla parametreilla
     * @param data Testidataa sisältävä lista
     */
    public MockAbstractChildDAO(List<T> data) {
        super(data);
    }
    
    /**
     * Kutsuu yläluokan konstruktoria annetuilla parametreilla
     * @param data Testidataa sisältävä lista
     * @param idGen Yksilöiviä id-tunnuksia generoiva olio
     */
    public MockAbstractChildDAO(List<T> data, IdGenerator idGen) {
        super(data, idGen);
    }
    
    
    /**
     * @param object haettujen alkioiden "omistaja", eli projektilla käyttäjä ja merkinnällä projekti
     * @return palauttaa kaikki tietylle omistajalle kuuluvat alkiot listalla.
     */
    @Override
    public List<T> list(P object) {
        return data.stream().filter(e -> e.getOwnerId() == object.getId()).collect(Collectors.toList());
    }
    

}
